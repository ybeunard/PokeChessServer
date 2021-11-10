package com.pokechess.server.controllers.security;

import com.pokechess.server.controllers.security.dto.JwtRequestDTO;
import com.pokechess.server.controllers.security.dto.JwtResponseDTO;
import com.pokechess.server.controllers.security.dto.UserDTO;
import com.pokechess.server.controllers.security.dto.UserRegisterDTO;
import com.pokechess.server.controllers.security.mapper.UserMapper;
import com.pokechess.server.exceptions.JwtException;
import com.pokechess.server.exceptions.UserException;
import com.pokechess.server.models.globals.User;
import com.pokechess.server.services.security.AuthenticateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.pokechess.server.filter.security.JwtRequestFilter.REFRESH_TOKEN_END_POINT;
import static com.pokechess.server.filter.security.JwtRequestFilter.REQUEST_USERNAME_ATTRIBUTE;

@RestController
@CrossOrigin
public class AuthenticateController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticateService authenticateService;

    public AuthenticateController(AuthenticationManager authenticationManager, AuthenticateService authenticateService) {
        this.authenticationManager = authenticationManager;
        this.authenticateService = authenticateService;
    }

    /**
     *
     * @throws UserException Bad credentials
     */
    @RequestMapping(value = "/api/v1/authenticate", method = RequestMethod.POST)
    public ResponseEntity<JwtResponseDTO> createAuthenticationToken(@RequestBody JwtRequestDTO authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            final UserDetails userDetails = authenticateService.loadUserByUsername(authenticationRequest.getUsername());
            final String accessToken = authenticateService.doGenerateAccessToken(userDetails.getUsername());
            final String refreshToken = authenticateService.doGenerateRefreshToken(userDetails.getUsername());
            return ResponseEntity.ok(new JwtResponseDTO(accessToken, authenticateService.jwtValidity, refreshToken));
        } catch (Exception e) {
            throw JwtException.of(JwtException.JwtExceptionType.BAD_CREDENTIALS);
        }
    }

    /**
     *
     * @throws UserException Username already exist exception
     * @throws UserException Trainer name already exist exception
     */
    @RequestMapping(value = "/api/v1/register", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserRegisterDTO user) {
        User userRegistered = this.authenticateService.registerUser(UserMapper.mapUserFromUserRegisterDTO(user));
        return ResponseEntity.ok(UserMapper.mapUserToUserDTO(userRegistered));
    }

    /**
     *
     * @throws UserException User not found exception
     */
    @RequestMapping(value = REFRESH_TOKEN_END_POINT, method = RequestMethod.GET)
    public ResponseEntity<?> refreshToken(@RequestAttribute(name = REQUEST_USERNAME_ATTRIBUTE) String username) {
        final String accessToken = authenticateService.doGenerateAccessToken(username);
        final String refreshToken = authenticateService.doGenerateRefreshToken(username);
        return ResponseEntity.ok(new JwtResponseDTO(accessToken, authenticateService.refreshExpirationDateInMs, refreshToken));
    }
}
