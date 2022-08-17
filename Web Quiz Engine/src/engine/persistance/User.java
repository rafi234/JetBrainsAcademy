package engine.persistance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(mappedBy = "user")
    @Column(name = "user_quizzes")
    private List<Quiz> quizzesAddedByUser = new ArrayList<>();

    @OneToMany(mappedBy = "userCompleted")
    @Column(name = "quizzes_solved")
    private List<QuizzesCompleted> quizzesCompleted = new ArrayList<>();

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    private String password;

    private String roles;
}
