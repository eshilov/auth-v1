package com.eshilov.auth.users;

import static java.util.UUID.randomUUID;

import com.eshilov.auth.generated.model.User;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public User createUser(User user) {
        var id = randomUUID();
        var newUser =
                User.builder()
                        .id(id)
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .build();
        users.put(newUser.getUsername(), newUser);
        return newUser;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }
}
