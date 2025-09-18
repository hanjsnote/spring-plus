package org.example.expert.domain.log.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.domain.auth.dto.response.SigninResponse;
import org.example.expert.domain.auth.dto.response.SignupResponse;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.log.enums.LogType;
import org.example.expert.domain.manager.dto.response.ManagerSaveResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.service.UserService;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final UserService userService;
    private final LoggingCommon loggingCommon;

    @Pointcut("execution(* org.example.expert.domain.auth.service.AuthService.signup(..))")
    private void signup() {}

    @Pointcut("execution(* org.example.expert.domain.auth.service.AuthService.signin(..))")
    private void signin() {}

    @Pointcut("execution(* org.example.expert.domain.todo.service.TodoService.saveTodo(..))")
    private void saveTodo() {}

    @Pointcut("execution(* org.example.expert.domain.comment.service.CommentService.saveComment(..))")
    private void saveComment() {}

    @Pointcut("execution(public * org.example.expert.domain.manager.service.ManagerService.saveManager(..))")
    private void saveManager() {}

    @AfterReturning(pointcut = "signup()", returning = "result")
    public void signupLog(Object result) {

        if (result instanceof SignupResponse signupResponse) {
            // 회원 가입 후 생성된 User 정보
            Long userId = signupResponse.getUserId();
            User user = userService.getUserById(userId);

            // LogType enum에서 description 가져와서 {1} 치환
            String description = LogType.USER_SIGNUP.getDescription()
                            .replace("{1}", user.getNickname());

            loggingCommon.createAndSaveLog(
                    LogType.USER_SIGNUP,
                    user,
                    null,
                    null,
                    description
            );
        }
    }

    @AfterReturning(pointcut = "signin()", returning = "result")
    public void signinLog(Object result) {
        if (result instanceof SigninResponse) {

            User user = loggingCommon.getCurrentUser();

            String description = LogType.USER_SIGNIN.getDescription()
                    .replace("{1}", user.getNickname());

            loggingCommon.createAndSaveLog(
                    LogType.USER_SIGNIN,
                    user,
                    null,
                    null,
                    description
            );
        }
    }

    @AfterReturning(pointcut = "saveTodo()", returning = "result")
    public void saveTodoLog(Object result) {

        if (result instanceof TodoSaveResponse todoSaveResponse) {
            User user = loggingCommon.getCurrentUser();

            String description = LogType.TODO_CREATED.getDescription()
                    .replace("{1}", todoSaveResponse.getTitle());

            loggingCommon.createAndSaveLog(
                    LogType.TODO_CREATED,
                    user,
                    todoSaveResponse.getId(),
                    null,
                    description
            );
        }
    }

    @AfterReturning(pointcut = "saveComment()", returning = "result")
    public void saveCommentLog(Object result) {

        if (result instanceof CommentSaveResponse commentSaveResponse) {
            User user = loggingCommon.getCurrentUser();

            String description = LogType.COMMENT_CREATED.getDescription()
                    .replace("{1}", commentSaveResponse.getContents());

            loggingCommon.createAndSaveLog(
                    LogType.COMMENT_CREATED,
                    user,
                    commentSaveResponse.getTodoId(),
                    commentSaveResponse.getId(),
                    description
            );
        }
    }

    @AfterReturning(pointcut = "saveManager()", returning = "result")
    public void saveManagerLog(Object result) {

        if (result instanceof ManagerSaveResponse managerSaveResponse) {
            User user = loggingCommon.getCurrentUser();

            String description = LogType.REQUEST_MANAGER.getDescription()
                    .replace("{1}", managerSaveResponse.getUser().getNickname());

            loggingCommon.createAndSaveLog(
                    LogType.REQUEST_MANAGER,
                    user,
                    managerSaveResponse.getTodoId(),
                    null,
                    description
            );
        }
    }
}


