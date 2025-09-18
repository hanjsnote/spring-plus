package org.example.expert.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.dto.response.LogResponse;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.enums.LogType;
import org.example.expert.domain.log.repository.LogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    @Transactional(readOnly = true)
    public Page<LogResponse> getLog(
            LogType type,
            Long userId,
            Long todoId,
            Long commentId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {

        Page<Log> logs = logRepository.findAllByFilter(
                type, userId, todoId, commentId, startDate, endDate, pageable
        );
        return logs.map(LogResponse::of);
    }

    @Transactional
    public void save(Log logEntity) {
        logRepository.save(logEntity);
    }
}
