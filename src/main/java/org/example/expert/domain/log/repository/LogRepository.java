package org.example.expert.domain.log.repository;

import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.enums.LogType;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> user(User user);

    @Query("SELECT l FROM Log l " +
            "WHERE (:type IS NULL OR l.type = :type) " +
            "AND (:userId IS NULL OR l.user.id = :userId) " +
            "AND (:todoId IS NULL OR l.todoId = :todoId) " +
            "AND (:commentId IS NULL OR l.commentId = :commentId) " +
            "AND (:startDate IS NULL OR l.createdAt >= :startDate) " +
            "AND (:endDate IS NULL OR l.createdAt <= :endDate) " +
            "ORDER BY l.createdAt DESC"
    )
    Page<Log> findAllByFilter(@Param("type") LogType type,
                              @Param("userId") Long userId,
                              @Param("todoId") Long todoId,
                              @Param("commentId") Long commentId,
                              @Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate,
                              Pageable pageable);
}
