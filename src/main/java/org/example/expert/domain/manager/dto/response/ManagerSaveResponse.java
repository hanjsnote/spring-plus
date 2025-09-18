package org.example.expert.domain.manager.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserResponse;

@Getter
public class ManagerSaveResponse {

    private final Long id;
    private final UserResponse user;
    private final Long todoId;

    public ManagerSaveResponse(Long id, UserResponse user, Long todoId) {
        this.id = id;
        this.user = user;
        this.todoId = todoId;
    }
}
