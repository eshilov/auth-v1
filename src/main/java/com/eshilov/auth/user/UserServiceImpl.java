package com.eshilov.auth.user;

import static java.util.UUID.randomUUID;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    @Override
    public User createUser(User user) {
        var id = randomUUID();
        var newUser =
                User.builder()
                        .id(id)
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .build();
        users.put(id, newUser);
        return newUser;
    }
}
