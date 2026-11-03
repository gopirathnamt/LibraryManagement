package com.example.application.service.impl;

import com.example.application.dto.request.LoginRequest;
import com.example.application.dto.response.LoginResponse;
import com.example.application.dto.value.TokenType;
import com.example.application.entity.CredentialEntity;
import com.example.application.entity.UserEntity;
import com.example.application.exception.UserException;
import com.example.application.model.CreateUserRequest;
import com.example.application.repository.CredentialRepository;
import com.example.application.repository.UserRepository;
import com.example.application.service.ILoginService;
import com.example.application.value.UserType;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class LoginService implements ILoginService {

    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(CredentialRepository credentialRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        CredentialEntity credentialEntity = credentialRepository.findByUserUsernameAndDeletedFalse(loginRequest.username())
                .orElseThrow(() -> new UserException("User not exists"));
        String encodedPassword = credentialEntity.getEncodedPassword();
        boolean matches = passwordEncoder.matches(loginRequest.password(), encodedPassword);
        if (matches) {
            String token = Base64.getEncoder()
                    .encodeToString((loginRequest.username() + ":" + loginRequest.password()).getBytes());
            return new LoginResponse(token, TokenType.BASIC, credentialEntity.getUser().getUserType());
        } else {
            throw new UserException("Username and password mismatch");
        }
    }

    @Override
    public String create(CreateUserRequest userRequest) {
        String encodedPassword = passwordEncoder.encode(userRequest.password());
        userRepository.findByUsernameAndDeletedFalse(userRequest.username()).ifPresent(user -> {
            throw new UserException("User already exists");
        });
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequest.username());
        userRepository.saveAndFlush(userEntity);
        CredentialEntity credentialEntity = new CredentialEntity();
        credentialEntity.setEncodedPassword(encodedPassword);
        credentialEntity.setUser(userEntity);
        credentialRepository.saveAndFlush(credentialEntity);
        return "User Created Successfully";
    }

    @PostConstruct
    public void postConstruct() {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsernameAndDeletedFalse("admin");
        if (optionalUserEntity.isEmpty()) {
            String encodedPassword = passwordEncoder.encode("admin");

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("admin");
            userEntity.setUserType(UserType.ADMIN);
            userRepository.saveAndFlush(userEntity);
            CredentialEntity credentialEntity = new CredentialEntity();
            credentialEntity.setEncodedPassword(encodedPassword);
            credentialEntity.setUser(userEntity);
            credentialRepository.saveAndFlush(credentialEntity);
        }
    }
}
