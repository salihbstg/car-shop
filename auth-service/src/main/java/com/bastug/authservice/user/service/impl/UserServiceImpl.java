package com.bastug.authservice.user.service.impl;

import com.bastug.authservice.auth.dto.*;
import com.bastug.authservice.jwt.JwtService;
import com.bastug.authservice.user.dto.CustomerDetail;
import com.bastug.authservice.user.entity.Role;
import com.bastug.authservice.user.entity.User;
import com.bastug.authservice.user.exception.*;
import com.bastug.authservice.user.feign.CustomerFeign;
import com.bastug.authservice.user.repository.UserRepository;
import com.bastug.authservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomerFeign customerFeign;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new EmailAlreadyExistsException(registerRequest.email());
        }
        if (userRepository.existsByUsername(registerRequest.username())) {
            throw new UsernameAlreadyExistsException();
        }
        if (customerFeign.existsByPhone(registerRequest.phone())) {
            throw new PhoneAlreadyExistsException();
        }
        User user = new User();
        user.setRole(Role.USER);
        user.setEmail(registerRequest.email());
        user.setUsername(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        CustomerDetail customerDetail = new CustomerDetail(
                registerRequest.firstName(),
                registerRequest.lastName(),
                registerRequest.email(),
                registerRequest.username(),
                registerRequest.phone()
        );
        customerFeign.createCustomer(customerDetail);
        userRepository.save(user);
        return new RegisterResponse(
                jwtService.generateToken(registerRequest.email()),
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                customerDetail
        );
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByEmailOrUsername(loginRequest.identifier(), loginRequest.identifier());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Şifre hatalı!");
        }
        return new LoginResponse(
                jwtService.generateToken(user.getUsername()),
                jwtService.generateRefreshToken(user.getEmail()),
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        if(!Objects.equals(jwtService.extractTokenType(refreshTokenRequest.refreshToken()), "refresh")){
            throw new TokenIsNotRefreshException();
        }
        if(!jwtService.isTokenValid(refreshTokenRequest.refreshToken())){
            throw new ExpiredTokenException();
        }
        String username = jwtService.extractUsername(refreshTokenRequest.refreshToken());
        Optional<User> optionalUser=userRepository.findByEmailOrUsername(username, username);
        if (optionalUser.isPresent()) {
            return new LoginResponse(
                    jwtService.generateToken(username),
                    jwtService.generateRefreshToken(username),
                    optionalUser.get().getId(),
                    username,
                    optionalUser.get().getEmail(),
                    optionalUser.get().getRole()
            );
        }
        throw new UserNotFoundException();
    }
}
