package tp.eni_store.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tp.eni_store.service.AuthService;
import tp.eni_store.service.ServiceResponse;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final ObjectMapper objectMapper;

    public JwtAuthFilter(AuthService authService, ObjectMapper objectMapper) {
        this.authService = authService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();

        if (!url.startsWith("/api/create-token")) {
            String token = request.getHeader("Authorization");
            ServiceResponse<Boolean> serviceResponse = authService.checkToken(token);

            //si pas bon (!202 code métier)
            if (!(serviceResponse.code == 202)) {
                //Force la réponse http à être en JSON
                response.setContentType("application/json");

                //il faut écrire dans le body de la réponse le message métier
                //Object mapper pour mettre du contenu dans la réponse
                objectMapper.writeValue(response.getWriter(), serviceResponse);

                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
