package com.pokechess.server.filter.security;

import com.pokechess.server.services.security.AuthenticateService;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_START = "Bearer ";
    public static final String REFRESH_TOKEN_END_POINT = "/api/v1/refreshtoken";
    public static final String REQUEST_USERNAME_ATTRIBUTE = "username";

    private final AuthenticateService authenticateService;

    public JwtRequestFilter(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);

        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        // Once we get the token validate it.
        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith(BEARER_TOKEN_START) &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            String jwtToken = requestTokenHeader.substring(7);

            if ((REFRESH_TOKEN_END_POINT.equals(request.getRequestURI()) && authenticateService.validateRefreshToken(jwtToken))
                    || authenticateService.validateAccessToken(jwtToken)) {
                UserDetails userDetails = authenticateService.loadUserByUsername(authenticateService.getUsernameFromToken(jwtToken));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                request.setAttribute(REQUEST_USERNAME_ATTRIBUTE, userDetails.getUsername());
            }
        }
        chain.doFilter(request, response);
    }
}
