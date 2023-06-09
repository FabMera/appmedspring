package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component("securityFilter")
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Obtener el token del header
        var token = request.getHeader("Authorization");
        System.out.println("Obtenemos el token del header" + token);
        if (token != null) {
            System.out.println("Validamos que token no es null");
            token = token.replace("Bearer ", "");
            System.out.println(token);
            System.out.println(tokenService.getSubject(token));//Este usuario tiene sesion?
        }
        filterChain.doFilter(request, response);
    }
}