package com.byteoffset.okarmusk.user.data.provider.api.rest;

import com.byteoffset.okarmusk.user.data.provider.domain.user.User;
import com.byteoffset.okarmusk.user.data.provider.domain.user.UserException;
import com.byteoffset.okarmusk.user.data.provider.domain.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController("/users")
public class UserController {
    private final UserService service;

    // TODO: add validation
    // TODO: add error handling
    @GetMapping("/{login}")
    public ResponseEntity<User> getUser(@PathVariable String login) throws UserException {
        log.info("Getting user data with login: {}", login);
        var user = service.getUser(login);

        return ResponseEntity.ok(user);
    }
}
