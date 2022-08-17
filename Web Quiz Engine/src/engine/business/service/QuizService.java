package engine.business.service;

import engine.business.dto.AnswerDto;
import engine.business.dto.QuizDto;
import engine.business.impl.UserDetailsImpl;
import engine.persistance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuizService {

    private final String correctAnswer = "Congratulations, you're right!";
    private final String wrongAnswer = "Wrong answer! Please, try again.";
    private final QuizRepository quizRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuizzesCompletedRepository quizzesCompletedRepository;

    @Autowired
    public QuizService(
            QuizRepository quizRepository,
            AnswerRepository answerRepository,
            UserRepository userRepository,
            QuizzesCompletedRepository quizzesCompletedRepository
    ) {
        this.quizRepository = quizRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.quizzesCompletedRepository = quizzesCompletedRepository;
    }

    public QuizDto addQuiz(QuizDto quizDto, UserDetailsImpl userDetails) {
        if (quizDto.getAnswer() == null)
            quizDto.setAnswer(new ArrayList<>());
        User currentUser = userRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow();
        Quiz quiz = quizRepository.save(
                new Quiz(quizDto.getTitle(), quizDto.getText(), currentUser)
        );
        quiz.setOptions(getOptions(quiz, quizDto));
        return new QuizDto(quiz);
    }

    private List<Answer> getOptions(Quiz quiz, QuizDto quizDto) {
        List<Answer> answers = new ArrayList<>();
        Set<Integer> correctAnswers = new HashSet<>(quizDto.getAnswer());
        for (int i = 0; i < quizDto.getOptions().size(); ++i) {
            Answer answer = new Answer(quiz, quizDto.getOptions().get(i), i, correctAnswers.contains(i));
            answers.add(answerRepository.save(answer));
        }
        return answers;
    }

    public QuizDto getQuizById(int id) {
        return new QuizDto(getQuizByIdOrThrow(id));
    }

    public Slice<QuizDto> getQuizzes(Integer pageNo) {
        PageRequest page = PageRequest.of(pageNo, 10);
        Slice<Quiz> quizzes = quizRepository.findAll(page);
        return quizzes.map(QuizDto::new);
    }

    public Slice<QuizzesCompleted> getQuizzesCompleted(int pageNo, int id) {
        PageRequest pageRequest = PageRequest.of(
                pageNo,
                10,
                Sort.by("completedAt").descending()
        );
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found!")
        );

        return quizzesCompletedRepository.findAllByUserCompleted(pageRequest, user);
    }

    public AnswerDto solveQuiz(int id, int[] answer, UserDetailsImpl userDetails) {
        Quiz quiz = getQuizByIdOrThrow(id);
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        int[] correctAns = quiz.getCorrectAnswers();
        Arrays.sort(answer);
        Arrays.sort(correctAns);
        boolean result = Arrays.equals(correctAns, answer);
        String message = result ? correctAnswer : wrongAnswer;
        if (result) {
            quizzesCompletedRepository.save(
                    new QuizzesCompleted(
                            id,
                            LocalDateTime.now(),
                            user.orElseThrow(() -> new UsernameNotFoundException("User not found!")))
            );
        }
        return new AnswerDto(result, message);
    }

    public void deleteQuizById(int id, UserDetailsImpl userDetails) {
        Quiz quiz = getQuizByIdOrThrow(id);
        if (!quiz.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quizRepository.deleteById(id);
    }

    private Quiz getQuizByIdOrThrow(int id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return optionalQuiz.get();
    }
}
