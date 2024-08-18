package jp.seido.naoyuki.lib.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import jp.seido.naoyuki.lib.entity.User;
import jp.seido.naoyuki.lib.repository.UserRepository;
import lombok.RequiredArgsConstructor;


@Lazy
@Component
@RequiredArgsConstructor
public class UserStoreDb implements UserStore {
    private final UserRepository userRepository;

    @Override
    public User register(String username, String password) {
        return userRepository.save(User.builder().username(username).password(password).build());
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }
}
