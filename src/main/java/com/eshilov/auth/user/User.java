package com.eshilov.auth.user;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {

    private final UUID id;
    private final String username;
    private final String password;
}
