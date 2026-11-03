package com.example.application.model;

import com.example.application.entity.CredentialEntity;
import com.example.application.entity.UserEntity;
import com.example.application.value.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Set;

@AllArgsConstructor
@Getter
public class CustomUser implements UserDetails {
    @Serial
    private static final long serialVersionUID = 0L;
    private final String username;
    private final String password;
    private final Set<GrantedAuthority> authorities;

    public static UserDetails build(CredentialEntity credentialEntity) {
        UserEntity user = credentialEntity.getUser();
        UserType userType = user.getUserType();
        return new CustomUser(user.getUsername(), credentialEntity.getEncodedPassword(), Set.of(new SimpleGrantedAuthority(userType.name())));
    }

}
