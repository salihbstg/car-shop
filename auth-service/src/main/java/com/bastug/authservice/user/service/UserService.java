package com.bastug.authservice.user.service;

import com.bastug.authservice.auth.dto.*;


public interface UserService {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);

    LoginResponse refreshToken(RefreshTokenRequest token);
}
