package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.log.enums.LogType;
import org.example.expert.domain.user.entity.User;

@Getter
@Entity
@Table(name="logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Log extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LogType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long todoId;

    private Long commentId;

    @Column(nullable = false)
    private String description;

    private Log(LogType type, User user, Long todoId, Long commentId, String description){
        this.type = type;
        this.user = user;
        this.todoId = todoId;
        this.commentId = commentId;
        this.description = description;
    }

    public static Log of(LogType type, User user, Long todoId, Long commentId, String description){
        return new Log(type, user, todoId, commentId, description);
    }
}
