package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorityRepository;
import ru.job4j.accidents.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HibernateUserService implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateUserService.class.getName());
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
            return Optional.of(user);
        } catch (Exception exception) {
            LOG.info("Пользователь уже зарегистрирован", exception);
        }
        return Optional.empty();
    }
}
