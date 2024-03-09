package com.formapp.damnjan.services;

import com.formapp.damnjan.entities.UserEntity;
import com.formapp.damnjan.exceptions.ExceptionSupplier;
import com.formapp.damnjan.mappers.UserMapper;
import com.formapp.damnjan.models.request.RefreshTokenRequestDto;
import com.formapp.damnjan.models.request.SingInRequestDto;
import com.formapp.damnjan.models.request.SingUpRequestDto;
import com.formapp.damnjan.models.response.AuthResponseDto;
import com.formapp.damnjan.repositories.UserRepository;
import com.formapp.damnjan.utils.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public AuthService(UserRepository userRepository,
                       JWTUtils jwtUtils, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void signUp(SingUpRequestDto singUpRequestDto) {

        try {
            if (userRepository.findByUsername(singUpRequestDto.username()).isPresent()) {
                throw ExceptionSupplier.usernameAlreadyExists.get();
            }

            UserEntity userEntity = userMapper.signUpRequestDtoToUserEntity(singUpRequestDto);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

            userRepository.save(userEntity);

        } catch (Exception e) {
            throw ExceptionSupplier.internalServerError.get();
        }
    }

    public AuthResponseDto signIn(SingInRequestDto singInRequestDto) {

        AuthResponseDto authResponseDto = new AuthResponseDto();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(singInRequestDto.username(),
                    singInRequestDto.password()));

            UserEntity userEntity = userRepository.findByUsername(singInRequestDto.username())
                    .orElseThrow(ExceptionSupplier.usernameNotFound);
            String jwt = jwtUtils.generateToken(userEntity);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), userEntity);
            authResponseDto.setToken(jwt);
            authResponseDto.setRefreshToken(refreshToken);
            authResponseDto.setExpiration("24 Hours");

        } catch (Exception e) {
            throw ExceptionSupplier.internalServerError.get();
        }

        return authResponseDto;
    }

    public AuthResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {

        AuthResponseDto authResponseDto = new AuthResponseDto();
        String username = jwtUtils.extractUsername(refreshTokenRequestDto.token());
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(ExceptionSupplier.usernameNotFound);

        if (jwtUtils.isTokenValid(refreshTokenRequestDto.token(), userEntity)) {
            String token = jwtUtils.generateToken(userEntity);
            authResponseDto.setToken(token);
            authResponseDto.setRefreshToken(refreshTokenRequestDto.token());
            authResponseDto.setExpiration("24Hr");
        }

        return authResponseDto;
    }
}
