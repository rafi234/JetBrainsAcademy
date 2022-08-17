package engine.persistance;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private int quizId;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @OneToMany(mappedBy = "quiz", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Column(name="answer")
    @Size(min = 2)
    private List<Answer> options;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Quiz(String title, String text, User user) {
        this.title = title;
        this.text = text;
        this.user = user;
    }

    public int[] getCorrectAnswers() {
        return options.stream()
                .filter(Answer::isCorrect)
                .mapToInt(Answer::getOptionId)
                .toArray();
    }
}
