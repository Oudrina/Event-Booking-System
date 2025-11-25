package com.events.eventsbooking.auth;

import com.events.eventsbooking.email.EmailService;
import com.events.eventsbooking.email.EmailTemplateName;
import com.events.eventsbooking.security.JwtService;
import com.events.eventsbooking.token.Token;
import com.events.eventsbooking.token.TokenRepo;
import com.events.eventsbooking.user.Role;
import com.events.eventsbooking.user.User;
import com.events.eventsbooking.user.UserRepo;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepo tokenRepo;
    private final EmailService emailService;
    private final AuthenticationManager manager;
    private final JwtService jwtService;

    @Value("${applictaion.mailing.frontend}")
    private String activationUrl;


    public void register(RegistrationRequest request) throws MessagingException {

        User user = User.builder()
                .FirstName(request.getFirstname())
                .LastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getFirstname())
                .role(Role.USER)
                .accountLocked(false)
                .enabled(false)
                .build();

        userRepo.save(user);
//        Email validation
        sendEmailValidation(user);

    }

    private void sendEmailValidation(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

//      send email
//        implement email service
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                newToken,
                activationUrl,
                "Activation Account"

        );
    }

    private String generateAndSaveActivationToken(User user) {
        String tokenGenerated = generateActivationCode(6);

        var tokenToSend = Token.builder()
                .token(tokenGenerated)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        tokenRepo.save(tokenToSend);

        return tokenGenerated;
    }

    private String generateActivationCode(int length) {
        String character = "123456789";

        StringBuilder codeBuild = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(character.length());
            codeBuild.append(character.charAt(randomIndex));
        }
        return codeBuild.toString();
    }

    public AuthenticationToken authenticate(@Valid AuthenticationRequest request) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = (User) authentication.getPrincipal();

        claims.put("fullName", user.getFullName());
        claims.put("username", user.getUsername());

        var jwtToken = jwtService.generateToken(claims, user);

        return AuthenticationToken.builder()
                .token(jwtToken)
                .build();
    }


    public void activate_account(String token) throws MessagingException {
        Token savedtoken = tokenRepo.findByToken(token).orElseThrow(
                () -> new RuntimeException("Token not found")
        );

        if (LocalDateTime.now().isAfter(savedtoken.getExpiresAt())) {
            sendEmailValidation(savedtoken.getUser());
            throw new RuntimeException("Code expired , New code has been sent to the same email address");

        }
        User user = userRepo.findById(savedtoken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);

        userRepo.save(user);
        savedtoken.setValidatedAt(LocalDateTime.now());
        tokenRepo.save(savedtoken);
    }

    public User findUserByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }
}
