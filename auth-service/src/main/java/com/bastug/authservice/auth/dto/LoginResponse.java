package com.bastug.authservice.auth.dto;

import com.bastug.authservice.user.entity.Role;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        Long userId,
        String username,
        String email,
        Role role
) {
}
