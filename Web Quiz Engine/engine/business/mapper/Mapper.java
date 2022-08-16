package engine.business.mapper;

import engine.business.dto.UserDto;
import engine.business.impl.UserDetailsImpl;
import engine.persistance.User;

public final class Mapper {

    public static User mapUserDtoToUser(UserDto userDto) {
        String roles = userDto.getRoles() == null ? "ROLE_USER" : userDto.getRoles();
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRoles(roles);
        return user;
    }
}
