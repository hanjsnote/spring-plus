package org.example.expert.domain.log.aspect;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.enums.LogType;
import org.example.expert.domain.log.service.LogService;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.service.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class LoggingCommon {

    private final LogService logService;
    private final UserService userService;

    public User getCurrentUser() {
        // SecurityContextHolder에서 인증 정보 꺼내기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("인증된 사용자가 없습니다.");
        }
        // 필터에서 세팅한 AuthUser
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        return userService.getUserById(authUser.getId());
    }
    //필요한 데이터만 뽑아서 로그 생성
    @Async
    @Transactional
    public void createAndSaveLog(LogType logType, User user, Long todoId, Long commentId, String description){
        Log log = Log.of(
                logType,
                user,
                todoId,
                commentId,
                description
        );
        logService.save(log);
    }

}
