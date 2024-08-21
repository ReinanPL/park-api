package com.reinan.demo_park_api.security;

import com.reinan.demo_park_api.entity.User;
import com.reinan.demo_park_api.exception.EntityNotFoundException;
import com.reinan.demo_park_api.repository.UserRepository;
import com.reinan.demo_park_api.web.dto.UserLoginRequest;
import com.reinan.demo_park_api.web.dto.UserTokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final JwtEncoder jwtEncoder;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserTokenResponse generateToken(User user){
        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.getRole();

        var claims = JwtClaimsSet.builder()
                .issuer("reinan-webservices")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new UserTokenResponse(jwtValue, expiresIn);
    }

    public User checkUserToken(UserLoginRequest loginRequest) {
        var user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + loginRequest.getUsername()));

        if (user == null || !user.isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("User or password is invalid!");
        }
        return user;
    }
}
