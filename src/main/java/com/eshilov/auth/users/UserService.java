package com.eshilov.auth.users;

import com.eshilov.auth.generated.model.User;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    Optional<User> findUserByUsername(String username);
}
