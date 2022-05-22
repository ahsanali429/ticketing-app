package com.task.callsign.controllers.rest;

import com.task.callsign.configurations.JWTEntryPointConfiguration;
import com.task.callsign.models.dto.AuthenticationRequestDTO;
import com.task.callsign.models.dto.AuthenticationResponseDTO;
import com.task.callsign.services.CallSignUserDetailsService;
import com.task.callsign.util.JWTHelper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private final AuthenticationManager authenticationManager;

    private final CallSignUserDetailsService callSignUserDetailsService;

    private final JWTHelper jwtHelper;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager,
                           CallSignUserDetailsService callSignUserDetailsService,
                           JWTHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.callSignUserDetailsService = callSignUserDetailsService;
        this.jwtHelper = jwtHelper;
    }

    /**
     * Authenticates the user by given username & password.
     * Returns a valid Http 200 response, Along with a valid JWT token if provided user details are authentic.
     * In case of any unknown exception return HTTP 500 internal server response.
     * Any authentication failures are caught by spring automatically and an HTTP 401 response is sent.
     *
     * @param authenticationRequestDTO {@link AuthenticationRequestDTO} instance containing username & password
     * @return {@link ResponseEntity} wrapping the authentication token
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> createAuthToken(@RequestBody AuthenticationRequestDTO authenticationRequestDTO){

        LOGGER.info("inside /authenticate with request: {}", authenticationRequestDTO);
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequestDTO.getUsername(),
                authenticationRequestDTO.getPassword()
            )
        );
        try {
            UserDetails userDetails = callSignUserDetailsService
                .loadUserByUsername(authenticationRequestDTO.getUsername());

            return ResponseEntity.ok(AuthenticationResponseDTO.builder()
                .token(jwtHelper.generateToken(userDetails))
                .build());
        }catch (BadCredentialsException bce) {
            LOGGER.error("Bad credential error in authentication with error", bce);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (Exception exception) {
            LOGGER.error("Error in authentication with error", exception);
            return ResponseEntity.internalServerError().build();
        }
    }
}
