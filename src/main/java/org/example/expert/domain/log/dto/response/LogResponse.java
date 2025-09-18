package org.example.expert.domain.log.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.enums.LogType;
import org.example.expert.domain.user.dto.response.UserResponse;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LogResponse {

    private final Long id;
    private final LogType type;
    private final Long todoId;
    private final Long commentId;
    private final LocalDateTime createdAt;
    private final String description;
    private final UserResponse user; // 작성자 정보

    public static LogResponse of(Log log){
        return LogResponse.builder()
                .id(log.getId())
                .type(log.getType())
                .todoId(log.getTodoId())
                .commentId(log.getCommentId())
                .createdAt(log.getCreatedAt())
                .description(log.getDescription())
                .user(UserResponse.from(log.getUser()))
                .build();
    }
}

