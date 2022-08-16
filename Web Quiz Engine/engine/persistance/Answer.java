package engine.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Answer {

    @Id
    @JsonIgnore
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int answerId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @NotEmpty
    @Column(name = "option")
    private String option;

    @JsonIgnore
    @Column(name = "optionId")
    private int optionId;

    @Column(name = "correct")
    @JsonIgnore
    private boolean correct = false;

    public Answer(Quiz quiz, String option, int optionId, boolean correct) {
        this.correct = correct;
        this.quiz = quiz;
        this.optionId = optionId;
        this.option = option;
    }
}
