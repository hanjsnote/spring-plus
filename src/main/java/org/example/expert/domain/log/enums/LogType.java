package org.example.expert.domain.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogType {

    USER_SIGNUP("signup_user", "'{1}' 회원가입 했습니다."),
    USER_SIGNIN("signin_user", "'{1}' 로그인 했습니다."),
    TODO_CREATED("created_todo", "새로운 일정 '{1}'을 생성했습니다."),
    COMMENT_CREATED("created_comment", "'{1}' 새로운 댓글이 생성했습니다."),
    REQUEST_MANAGER("manager_request", "'{1}' 매니저 등록 했습니다.");

    private final String action;
    private final String description;
}
