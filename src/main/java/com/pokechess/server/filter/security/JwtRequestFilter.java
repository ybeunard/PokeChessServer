package com.pokechess.server.filter.security;

import com.pokechess.server.exceptions.JwtException;
import com.pokechess.server.models.globals.user.User;
import com.pokechess.server.models.globals.user.UserPrincipal;
import com.pokechess.server.services.security.AuthenticateService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_START = "Bearer ";

    public static final String REQUEST_USERNAME_ATTRIBUTE = "username";
    public static final String REQUEST_TRAINER_ATTRIBUTE = "trainerName";
    public static final String REFRESH_TOKEN_END_POINT = "/api/v1/refreshtoken";
    public static final String WEBSOCKET_CONNECTION_END_POINT = "/api/v1/pokechess";
    public static final String LOAD_DATA_END_POINT = "/api/v1/load";

    private final AuthenticateService authenticateService;

    public JwtRequestFilter(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    /**
     *
     * @throws JwtException expired jwt token exception
     * @throws JwtException token provided is not a jwt token
     */
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);

        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        // Once we get the token validate it.
        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith(BEARER_TOKEN_START) &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            String jwtToken = requestTokenHeader.substring(7);
            Optional<User> userOpt = REFRESH_TOKEN_END_POINT.equals(request.getRequestURI()) ?
                    authenticateService.validateRefreshToken(jwtToken) :
                    authenticateService.validateAccessToken(jwtToken, !Arrays.asList(WEBSOCKET_CONNECTION_END_POINT, LOAD_DATA_END_POINT).contains(request.getRequestURI()));
            userOpt.ifPresent(user-> {
                UserPrincipal userPrincipal = UserPrincipal.getBuilder().username(user.getUsername())
                        .password(user.getPasswordHashed()).trainerName(user.getTrainerName()).build();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userPrincipal, null, userPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                request.setAttribute(REQUEST_USERNAME_ATTRIBUTE, user.getUsername());
                request.setAttribute(REQUEST_TRAINER_ATTRIBUTE, user.getTrainerName());
            });
        }
        chain.doFilter(request, response);
    }
}
