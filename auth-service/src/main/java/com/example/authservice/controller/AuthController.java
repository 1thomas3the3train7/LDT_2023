package com.example.authservice.controller;

import com.example.authservice.model.MainDtoResponse;
import com.example.authservice.model.UserDto;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Mono<UserDto> login(@RequestBody UserDto userDto) {
        return authService.login(userDto);
    }
    @PostMapping("/register")
    public Mono<MainDtoResponse<UserDto>> register(@RequestBody UserDto userDto) {
        return authService.register(userDto);
    }
}
