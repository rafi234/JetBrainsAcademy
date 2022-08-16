package engine.business.service;

import engine.business.dto.UserDto;
import engine.business.impl.UserDetailsImpl;
import engine.business.mapper.Mapper;
import engine.persistance.User;
import engine.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User with email: " + username + " does not exist!");
        return new UserDetailsImpl(user.get());
    }

    public void addUser(UserDto userDto) {
        User user = Mapper.mapUserDtoToUser(userDto);
        user.setPassword(encoder.encode(user.getPassword()));
        if (emailExists(userDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
    }

    public boolean emailExists(String email) {
        for (User user : userRepository.findAll()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
