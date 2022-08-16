package engine.business.impl;

import engine.persistance.QuizzesCompleted;
import engine.persistance.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    @Getter
    private int id;
    private String email;
    private String password;

    private List<QuizzesCompleted> quizzesCompleted;
    private List<GrantedAuthority> roles;

    public UserDetailsImpl(User user) {
        id = user.getId();
        email = user.getEmail();
        password = user.getPassword();
        roles = List.of(new SimpleGrantedAuthority(user.getRoles()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
