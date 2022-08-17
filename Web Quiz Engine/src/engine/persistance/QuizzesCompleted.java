package engine.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class QuizzesCompleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userCompleted;
    @Column(name = "quizzes_completed_id", nullable = false)
    @JsonProperty(value = "id")
    private int quizzesCompletedId;
    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;

    public QuizzesCompleted(int quizzesCompletedId, LocalDateTime completedAt, User userCompleted) {
        this.quizzesCompletedId = quizzesCompletedId;
        this.completedAt = completedAt;
        this.userCompleted = userCompleted;
    }
}
