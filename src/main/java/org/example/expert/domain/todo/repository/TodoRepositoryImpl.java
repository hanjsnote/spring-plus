package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.example.expert.domain.search.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.user.entity.QUser.user;
import static org.example.expert.domain.comment.entity.QComment.comment;


@Repository
@AllArgsConstructor
public class TodoRepositoryImpl implements TodoCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {

        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(todo)
                .leftJoin(todo.user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne()
        );
    }

    @Override
    public Page<TodoSearchResponse> searchTodoByTitle(String title, Pageable pageable) {
        List<TodoSearchResponse> results = jpaQueryFactory
                .select(Projections.constructor( //DTO에 데이터를 바로 매핑
                        TodoSearchResponse.class,
                        todo.title,
                        manager.countDistinct(),    //담당자 수
                        comment.countDistinct()     //댓글 수
                ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .where(todo.title.contains(title))
                .groupBy(todo.id)                   // todo 별로 집계 (count)를 계산하기 위해 필요
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())       //페이징 시작 위치
                .limit(pageable.getPageSize())      //페이징 사이즈
                .fetch();                           //실제 DB실행 결과를 DTO 리스트로 반환

        // 전체 개수 조회
        Long total = jpaQueryFactory
                .select(todo.countDistinct())
                .from(todo)
                .where(todo.title.contains(title))
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }

    @Override
    public Page<TodoSearchResponse> searchTodoByNickname(String nickname, Pageable pageable) {
        List<TodoSearchResponse> results = jpaQueryFactory
                .select(Projections.constructor(
                        TodoSearchResponse.class,
                        todo.title,
                        manager.countDistinct(),   // 담당자 수
                        comment.countDistinct()    // 댓글 수
                ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .leftJoin(todo.comments, comment)
                .where(user.nickname.contains(nickname))
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        Long total = jpaQueryFactory
                .select(todo.countDistinct())
                .from(todo)
                .where(user.nickname.contains(nickname))
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }

}
