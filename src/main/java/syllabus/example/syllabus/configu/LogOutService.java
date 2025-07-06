package syllabus.example.syllabus.configu;


import syllabus.example.syllabus.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogOutService implements LogoutHandler {

    private final TokenRepository tokenrepository;



    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication
    ) {
        final String authHeader= request.getHeader("Authorization");
        final String jwt;
        if(authHeader==null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt= authHeader.substring(7);
        var storedToken = tokenrepository.findByToken(jwt)
                .orElse(null);
        // Check if token is valid (not expired and not revoked) in the database
        boolean isTokenValid = tokenrepository.findByToken(jwt)
                .map(t -> !t.isExpired() )
                .orElse(false);

        if (storedToken != null && isTokenValid) {
            storedToken.setExpired(true);

            tokenrepository.save(storedToken);
        } else {
            // Set response status to 403 Forbidden if token is invalid
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
