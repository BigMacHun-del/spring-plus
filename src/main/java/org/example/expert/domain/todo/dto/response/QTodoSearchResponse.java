package org.example.expert.domain.todo.dto.response;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;

import javax.annotation.processing.Generated;

@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTodoSearchResponse extends ConstructorExpression<TodoSearchResponse> {

    private static final long serialVersionUID = 1L;

    public QTodoSearchResponse(Expression<String> title, Expression<Long> managerCount, Expression<Long> commentCount) {
        super(TodoSearchResponse.class,
                new Class<?>[]{ String.class, long.class, long.class },
                title, managerCount, commentCount);
    }
}