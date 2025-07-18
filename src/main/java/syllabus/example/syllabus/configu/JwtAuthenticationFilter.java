package syllabus.example.syllabus.configu;

import syllabus.example.syllabus.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService ;

    private final TokenRepository tokenrepository;

    private  final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/login") ||
                request.getServletPath().contains("/api/v1/auth") ||
                request.getServletPath().equals("/favicon.ico")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader= request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(authHeader==null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt= authHeader.substring(7);
        System.out.println(request.getHeaders("token"));

        System.out.println(jwt);
        String secretKey = request.getParameter("secret");
        String clientKey = request.getParameter("client");


        userEmail= jwtService.extractUsername(jwt);//todo extract the userEmail from JWT token

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails userDetails =  this.userDetailsService.loadUserByUsername(userEmail);
            var isTokenVadDataBase=tokenrepository.findByToken(jwt)
                    .map(t->!t.isExpired())
                    .orElse(false);
            if(((jwtService.isTokenValid(jwt, userDetails)&& jwtService.isValidSecretAndClientKeys(secretKey, clientKey)))
                    && isTokenVadDataBase) {
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails,null, userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);

    }




}