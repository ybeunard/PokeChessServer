package com.pokechess.server.services.security;

import com.pokechess.server.exceptions.JwtException;
import com.pokechess.server.exceptions.UserException;
import com.pokechess.server.models.globals.user.User;
import com.pokechess.server.models.globals.user.UserPrincipal;
import com.pokechess.server.repositories.user.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticateService implements UserDetailsService {

    private final String jwtSecret;
    public final long expirationValidity;
    public final long refreshExpirationValidity;

    private final SessionManagerService sessionManagerService;
    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;

    public AuthenticateService(@Value("${jwt.secret}") String jwtSecret,
                               @Value("${jwt.expirationValidity}") long expirationValidity,
                               @Value("${jwt.refreshExpirationValidity}") long refreshExpirationValidity,
                               SessionManagerService sessionManagerService,
                               UserRepository userRepository,
                               PasswordEncoder bcryptEncoder) {
        this.jwtSecret = jwtSecret;
        this.expirationValidity = expirationValidity;
        this.refreshExpirationValidity = refreshExpirationValidity;
        this.sessionManagerService = sessionManagerService;
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    /**
     *
     * @throws UserException User not found exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.getByUsername(username);
        return UserPrincipal.getBuilder().username(user.getUsername()).password(user.getPasswordHashed())
                .trainerName(user.getTrainerName()).build();
    }

    /**
     *
     * @throws UserException Username already exist exception
     * @throws UserException Trainer name already exist exception
     */
    public User registerUser(User user) {
        user.setPasswordHashed(bcryptEncoder.encode(user.getPasswordHashed()));
        return this.userRepository.create(user);
    }

    /**
     *
     * @return The user if the access token is valid else an empty optional,
     *      check if the access token is the active jwt token
     *      check if the token is the User token
     *      check if the token is not expired
     * @throws JwtException expired jwt token exception
     * @throws JwtException incorrect jwt token exception
     */
    public Optional<User> validateAccessToken(String accessToken) {
        Claims claims = getAllClaimsFromToken(accessToken);
        if (isTokenExpired(claims.getExpiration()))
            throw JwtException.of(JwtException.JwtExceptionType.EXPIRED_JWT_TOKEN);

        try {
            User user = this.userRepository.getByTrainerName(claims.getSubject());
            if (user.getTrainerName().equals(claims.getSubject()) && this.sessionManagerService.isConnected(user.getUsername())
                    && Objects.nonNull(user.getAccessTokenId()) && user.getAccessTokenId().equals(claims.getId())) {
                return Optional.of(user);
            }
        } catch (UserException ignored) { }
        return Optional.empty();
    }

    /**
     *
     * @return The user if the refresh token is valid else an empty optional,
     *      check if the token is not expired
     *      check if the token is the User token
     *      check if the access token is the active jwt token
     * @throws JwtException expired jwt token exception
     * @throws JwtException incorrect jwt token exception
     */
    public Optional<User> validateRefreshToken(String refreshToken) {
        Claims claims = getAllClaimsFromToken(refreshToken);
        if (isTokenExpired(claims.getExpiration()))
            throw JwtException.of(JwtException.JwtExceptionType.EXPIRED_JWT_TOKEN);

        try {
            User user = this.userRepository.getByTrainerName(claims.getSubject());
            if (user.getTrainerName().equals(claims.getSubject()) &&
                    Objects.nonNull(user.getRefreshTokenId()) && user.getRefreshTokenId().equals(claims.getId())) {
                return Optional.of(user);
            }
        } catch (UserException ignored) { }
        return Optional.empty();
    }

    /**
     *
     * @return generate access token for user, save its new ID
     *      ID is a random UUID generated
     *      Subject is the username
     *      IssueAt is the current date
     *      Expiration configurable in properties
     *      Signature in HS512 algorithm and secret key configurable in properties
     * @throws UserException User not found exception
     */
    public String doGenerateAccessToken(String trainerName) {
        String accessTokenId = this.userRepository.patchAccessTokenId(trainerName, UUID.randomUUID().toString());
        return Jwts.builder().setClaims(new HashMap<>()).setId(accessTokenId).setSubject(trainerName).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationValidity * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    /**
     *
     * @return generate refresh token for user, save its new ID
     *      ID is a random UUID generated
     *      Subject is the username
     *      IssueAt is the current date
     *      Expiration configurable in properties
     *      Signature in HS512 algorithm and secret key configurable in properties
     * @throws UserException User not found exception
     */
    public String doGenerateRefreshToken(String trainerName) {
        String refreshTokenId = this.userRepository.patchRefreshTokenId(trainerName, UUID.randomUUID().toString());
        return Jwts.builder().setClaims(new HashMap<>()).setId(refreshTokenId).setSubject(trainerName).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationValidity * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    // check if the token has expired
    private Boolean isTokenExpired(Date expiration) {
        return Objects.nonNull(expiration) && expiration.before(new Date());
    }

    /**
     *
     * @return Claims on the given jwt token
     * @throws JwtException incorrect jwt token exception
     */
    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw JwtException.of(JwtException.JwtExceptionType.INCORRECT_JWT_TOKEN);
        }
    }
}
