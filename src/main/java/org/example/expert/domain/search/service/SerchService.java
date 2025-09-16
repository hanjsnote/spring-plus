package org.example.expert.domain.search.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.manager.repository.ManagerRepository;
import org.example.expert.domain.search.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SerchService {

    private final TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public Page<TodoSearchResponse> execute(int page, int size, String title, String nickname){

        Pageable pageable = PageRequest.of(page, size);

        Page<TodoSearchResponse> todos;

        if (title != null) {
            todos = todoRepository.searchTodoByTitle(title, pageable);
        }
        else if (nickname != null) {
            todos = todoRepository.searchTodoByNickname(nickname, pageable);
        }
        else {
            todos = Page.empty(pageable);
        }

        return todos.map(todo -> new TodoSearchResponse(
                todo.getTitle(),
                todo.getManagerCount(),
                todo.getCommentCount()
        ));
    }
}
