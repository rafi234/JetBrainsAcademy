package engine.business.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDto {
    @Email(regexp = "[\\w!@#$%^&*()+=]*@[\\w!@#$%^&*()+=]*\\.[\\w!@#$%^&*()+=]*")
    private String email;
    @Pattern(regexp = ".{5,}")
    private String password;
    private String roles;

}
