package syllabus.example.syllabus.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import syllabus.example.syllabus.configu.JwtService;


import syllabus.example.syllabus.token.Token;
import syllabus.example.syllabus.token.TokenRepository;
import syllabus.example.syllabus.token.TokenType;
import syllabus.example.syllabus.users.Role;
import syllabus.example.syllabus.users.User;
import syllabus.example.syllabus.users.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserRepository repository;
    private final UserDetailsService userDetailsService;

    private final TokenRepository tokenrepository;


    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {
        String email = request.getEmail();

        // Check if the email already exists in the database
        if (repository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .grade(request.getGrade())
                .jobfunction(request.getFunction())
                .password(passwordEncoder.encode(request.getPassword()))
                .confirmPassword(passwordEncoder.encode(request.getConfirmPassword()))
                .role(Role.USER)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken((User) user);

        //revokeAllUserTokens(user);
        savedUserToken(savedUser, jwtToken);

        Role role = user.getRole();
        String FirstName = user.getFirstName();
        String LastName = user.getLastName();
        String grade = user.getGrade();
        String function = user.getJobfunction();
        Integer id = user.getId();
        String message = "Register Success";
        return AuthenticationResponse.builder()
                .accesstoken(jwtToken)
                .role(role)
                .id(id)
                .FirstName(FirstName)
                .LastName(LastName)
                .grade(grade)
                .function(function)
                .message(message)
                .build();

    }

    private void savedUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)

                .build();
        tokenrepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenrepository.findValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);

        });

        tokenrepository.saveAll(validUserTokens);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        if (user == null) {
            AuthenticationResponse response = AuthenticationResponse.builder()
                    .message("invalid email/password")
                    .build();
            return response;
        } else {
            var jwtToken = jwtService.generateToken(user);

            revokeAllUserTokens(user);

            savedUserToken(user, jwtToken);

            String username = jwtService.extractUsername(jwtToken);
            Date accessTokenExpiration = jwtService.extractExpiration(jwtToken);

            Role role = user.getRole();
            String FirstName = user.getFirstName();
            String LastName = user.getLastName();
            String grade = user.getGrade();
            String fuction = user.getJobfunction();
            Integer id = user.getId();
            String message = "Login Success";
            AuthenticationResponse response = AuthenticationResponse.builder()

                    .accesstoken(jwtToken)
                    .accessTokenExpiration(accessTokenExpiration)
                    .role(role)
                    .id(id)
                    .FirstName(FirstName)
                    .LastName(LastName)
                    .grade(grade)
                    .function(fuction)
                    .message(message)
                    .build();
            return response;
        }
    }

}
