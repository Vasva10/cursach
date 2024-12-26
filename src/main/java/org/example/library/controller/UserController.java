package org.example.library.controller;

import org.example.library.domain.User;
import org.example.library.domain.UserRole;
import org.example.library.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public User signup(@RequestParam String login,
                       @RequestParam String password,
                       @RequestParam String name) {
        // Хэшируем пароль
        String passwordHash = passwordEncoder.encode(password);
        User newUser = User.builder()
                .login(login)
                .passwordHash(passwordHash)
                .name(name)
                .role(UserRole.READER) // по умолчанию роль читатель
                .build();
        return userService.saveUser(newUser);
    }
}
