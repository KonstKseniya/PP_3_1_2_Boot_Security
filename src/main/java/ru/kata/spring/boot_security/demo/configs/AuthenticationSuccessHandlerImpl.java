package ru.kata.spring.boot_security.demo.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.stream.Stream;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String redirectUrl = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .flatMap(role -> role.equals("ROLE_ADMIN") ? Stream.of("/admin/edit_panel") :
                        role.equals("ROLE_USER") ? Stream.of("/user/profile") :
                                Stream.empty())
                .findFirst()
                .orElse("/login");

        response.sendRedirect(redirectUrl);
    }
}