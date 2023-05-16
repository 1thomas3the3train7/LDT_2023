package com.example.authservice.service.impl;

import com.example.authservice.model.MainDtoResponse;
import com.example.authservice.model.UserDto;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Gson gson;
    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private final UserService userService;

    @Override
    public Mono<UserDto> login(UserDto userDto) {
        return userService.checkLoginUser(userDto)
                .flatMap(u -> {
                    if (u != null) {
                        return redisTemplate.opsForValue().set(u.getEmail(),
                                UUID.nameUUIDFromBytes(gson.toJson(u).getBytes()).toString());
                    }
                    return Mono.empty();
                })
                .map(m -> UserDto.builder().email(userDto.getEmail()).password(m.toString()).build());
    }

    @Override
    public Mono<MainDtoResponse<UserDto>> register(UserDto userDto) {
        return userService.register(userDto)
                .map(m -> MainDtoResponse.<UserDto>builder().data(m).build());
    }
}
