package engine.presentation;

import engine.business.dto.UserDto;
import engine.business.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class APISecurityController {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public APISecurityController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/api/register")
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@Valid @RequestBody UserDto user) {
        userDetailsService.addUser(user);
    }


}
