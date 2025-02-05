package com.example.FurnitureLand.Config;

import com.example.FurnitureLand.Util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    // Constructor to inject JwtUtil
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get Authorization header
        String header = request.getHeader("Authorization");

        // Check if the header contains the Bearer token
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Extract token from the header

            // Validate the token
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.getEmailFromToken(token); // Get email from the token

                // Set the authentication in the security context
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                // If token is invalid, send unauthorized error
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or Expired Token");
                return;
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}

