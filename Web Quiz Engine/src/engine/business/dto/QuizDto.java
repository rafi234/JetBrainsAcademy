package engine.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.persistance.Answer;
import engine.persistance.Quiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class QuizDto {

    private int id;

    @NotBlank(message = "Title can not be empty!")
    private String title;

    @NotBlank(message = "Text can not be empty!")
    private String text;

    @NotNull(message = "Options can not be empty!")
    @Size(min = 2, message = "Size of options array must be bigger or equal 2!")
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;

    @JsonIgnore
    private int quizId;

    public QuizDto(Quiz quiz) {
        this.id = quiz.getQuizId();
        this.title = quiz.getTitle();
        this.text = quiz.getText();
        this.options = quiz.getOptions().size() == 0 ? Collections.emptyList()
                : quiz.getOptions().stream()
                .map(Answer::getOption)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        this.quizId = quiz.getQuizId();

    }
}
