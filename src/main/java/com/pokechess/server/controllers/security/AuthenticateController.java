package com.pokechess.server.controllers.security;

import com.pokechess.server.controllers.security.dto.jwt.JwtRequestDTO;
import com.pokechess.server.controllers.security.dto.jwt.JwtResponseDTO;
import com.pokechess.server.controllers.security.dto.user.register.UserRegisterResponseDTO;
import com.pokechess.server.controllers.security.dto.user.register.UserRegisterRequestDTO;
import com.pokechess.server.controllers.security.mapper.UserMapper;
import com.pokechess.server.exceptions.JwtException;
import com.pokechess.server.exceptions.UserException;
import com.pokechess.server.models.globals.user.User;
import com.pokechess.server.models.globals.user.UserPrincipal;
import com.pokechess.server.services.security.AuthenticateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.pokechess.server.filter.security.JwtRequestFilter.*;

@RestController
public class AuthenticateController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticateService authenticateService;

    public AuthenticateController(AuthenticationManager authenticationManager,
                                  AuthenticateService authenticateService) {
        this.authenticationManager = authenticationManager;
        this.authenticateService = authenticateService;
    }

    /**
     *
     * @throws UserException Bad credentials
     */
    @PostMapping(value = "/api/v1/authenticate")
    public ResponseEntity<JwtResponseDTO> createAuthenticationToken(@RequestBody JwtRequestDTO authenticationRequest) {
        try {
            Authentication authUser = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            UserPrincipal user = (UserPrincipal) authUser.getPrincipal();
            final String accessToken = authenticateService.doGenerateAccessToken(user.getTrainerName());
            final String refreshToken = authenticateService.doGenerateRefreshToken(user.getTrainerName());
            return ResponseEntity.ok(new JwtResponseDTO(accessToken, authenticateService.expirationValidity, refreshToken));
        } catch (Exception e) {
            throw JwtException.of(JwtException.JwtExceptionType.BAD_CREDENTIALS);
        }
    }

    /**
     *
     * @throws UserException Username already exist exception
     * @throws UserException Trainer name already exist exception
     */
    @PostMapping(value = "/api/v1/register")
    public ResponseEntity<UserRegisterResponseDTO> saveUser(@RequestBody @Valid UserRegisterRequestDTO user) {
        User userRegistered = this.authenticateService.registerUser(UserMapper.mapUserFromUserRegisterRequestDTO(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.mapUserToUserRegisterResponseDTO(userRegistered));
    }

    /**
     *
     * @throws UserException User not found exception
     */
    @GetMapping(value = REFRESH_TOKEN_END_POINT)
    public ResponseEntity<JwtResponseDTO> refreshToken(@RequestAttribute(name = REQUEST_TRAINER_ATTRIBUTE) String trainerName) {
        final String accessToken = authenticateService.doGenerateAccessToken(trainerName);
        final String refreshToken = authenticateService.doGenerateRefreshToken(trainerName);
        return ResponseEntity.ok(new JwtResponseDTO(accessToken, authenticateService.refreshExpirationValidity, refreshToken));
    }
}
