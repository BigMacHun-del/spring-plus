package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository {

    private final JPAQueryFactory queryFactory;

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

        //Page 객체를 만들기 위해 전체 데이터 수
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
}
