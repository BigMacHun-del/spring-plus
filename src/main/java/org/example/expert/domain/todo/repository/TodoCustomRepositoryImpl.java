package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Todo> search(String weather, LocalDateTime start, LocalDateTime end, Pageable pageable) {

        List<Todo> todos = queryFactory
                .selectFrom(todo)
                .join(todo.user, user).fetchJoin()
                .where(
                        weather != null ? todo.weather.eq(weather) : null,
                        start != null ? todo.modifiedAt.goe(start) : null,
                        end != null ? todo.modifiedAt.loe(end) : null
                )
                .orderBy(todo.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(todo.count())
                .from(todo)
                .where(
                        weather != null ? todo.weather.eq(weather) : null,
                        start != null ? todo.modifiedAt.goe(start) : null,
                        end != null ? todo.modifiedAt.loe(end) : null
                )
                .fetchOne();

        return new PageImpl<>(todos, pageable, total != null ? total : 0);
    }

    @Override
    public Page<TodoSearchResponse> searchByCondition(
            String title,
            LocalDateTime start,
            LocalDateTime end,
            String nickname,
            Pageable pageable
    ) {
        List<TodoSearchResponse> results = queryFactory
                .select(Projections.constructor(TodoSearchResponse.class,
                        todo.title,
                        manager.count(),
                        comment.count()
                ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .leftJoin(manager.user, user)
                .where(
                        titleContains(title),
                        createdAtBetween(start, end),
                        nicknameContains(nickname)
                )
                .groupBy(todo.id, todo.title, todo.createdAt)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(todo.countDistinct())
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .where(
                        titleContains(title),
                        createdAtBetween(start, end),
                        nicknameContains(nickname)
                )
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? todo.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression createdAtBetween(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null) return todo.createdAt.between(start, end);
        if (start != null) return todo.createdAt.goe(start);
        if (end != null) return todo.createdAt.loe(end);
        return null;
    }

    private BooleanExpression nicknameContains(String nickname) {
        return nickname != null ? user.nickname.containsIgnoreCase(nickname) : null;
    }
}