package engine.presentation;

import engine.business.dto.AnswerDto;
import engine.business.dto.QuizDto;
import engine.business.impl.UserDetailsImpl;
import engine.business.service.QuizService;
import engine.persistance.QuizzesCompleted;
import engine.persistance.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class APIQuizController {

    private final QuizService quizService;

    @Autowired
    public APIQuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/quizzes")
    public QuizDto postNewQuiz(
            @Valid @RequestBody QuizDto quizDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return quizService.addQuiz(quizDto, userDetails);
    }

    @GetMapping("/quizzes/{id}")
    public QuizDto getQuizById(@PathVariable int id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/quizzes")
    public ResponseEntity<Slice<QuizDto>> getQuizzes(
            @RequestParam(defaultValue = "0") Integer page
    ) {
        return ResponseEntity
                .ok()
                .body(quizService.getQuizzes(page));
    }

    @PostMapping("/quizzes/{id}/solve")
    public AnswerDto solveQuizWithId(
            @PathVariable int id,
            @RequestBody Map.Entry<String, int[]> answer,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        return quizService.solveQuiz(id, answer.getValue(), userDetails);
    }

    @GetMapping("/quizzes/completed")
    @ResponseStatus(HttpStatus.OK)
    public Slice<QuizzesCompleted> getQuizzesCompleted(
            @RequestParam(defaultValue = "0") Integer page,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return quizService.getQuizzesCompleted(page, userDetails.getId());
    }

    @DeleteMapping("/quizzes/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteQuiz(
            @PathVariable int id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        quizService.deleteQuizById(id, userDetails);
    }
}
