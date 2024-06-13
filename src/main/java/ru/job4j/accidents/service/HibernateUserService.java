package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorityRepository;
import ru.job4j.accidents.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HibernateUserService implements UserService {
    private final PasswordEncoder encoder;
    private final UserRepository users;
    private final AuthorityRepository authorities;

    @Override
    public Optional<User> register(User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        try {
            user = users.save(user);
        } catch (Exception e) {
            user = null;
        }
        return Optional.ofNullable(user);
    }
}
