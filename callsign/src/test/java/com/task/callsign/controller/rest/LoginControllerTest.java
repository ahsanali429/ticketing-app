package com.task.callsign.controller.rest;

import com.task.callsign.controllers.rest.LoginController;
import com.task.callsign.models.dto.AuthenticationRequestDTO;
import com.task.callsign.models.dto.AuthenticationResponseDTO;
import com.task.callsign.services.CallSignUserDetailsService;
import com.task.callsign.util.JWTHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTHelper jwtHelper;
    @Mock
    private CallSignUserDetailsService callSignUserDetailsService;
    @Mock
    private Authentication authentication;
    @Mock
    private UserDetails userDetails;

    private LoginController loginController;

    private static final String JWT_TOKEN = "ABC-123";
    private static final String USER_NAME = "user-test";
    private static final String PASSWORD = "password-test";



    @Before
    public void setup() {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(USER_NAME, PASSWORD);

        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authentication);

        loginController = new LoginController(authenticationManager, callSignUserDetailsService, jwtHelper);

    }

    @Test
    public void returnsJWTOnSuccess(){

        // Mocking
        when(callSignUserDetailsService.loadUserByUsername(USER_NAME)).thenReturn(userDetails);
        when(jwtHelper.generateToken(userDetails)).thenReturn(JWT_TOKEN);

        // Invocation
        ResponseEntity<AuthenticationResponseDTO> response = loginController
            .createAuthToken(new AuthenticationRequestDTO(USER_NAME, PASSWORD));

        // Assertions
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(JWT_TOKEN, response.getBody().getToken());

    }

    @Test
    public void returnErrorIfUserNotFound(){

        // Mocking
        when(callSignUserDetailsService.loadUserByUsername(USER_NAME)).thenThrow(UsernameNotFoundException.class);

        // Invocation
        ResponseEntity<AuthenticationResponseDTO> response = loginController
            .createAuthToken(new AuthenticationRequestDTO(USER_NAME, PASSWORD));

        // Assertion
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void returnErrorIfTokenGenerationFails(){

        // Mocking
        when(callSignUserDetailsService.loadUserByUsername(USER_NAME)).thenReturn(userDetails);
        when(jwtHelper.generateToken(userDetails)).thenThrow(BadCredentialsException.class);

        // Invocation
        ResponseEntity<AuthenticationResponseDTO> response = loginController
            .createAuthToken(new AuthenticationRequestDTO(USER_NAME, PASSWORD));

        // Assertion
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
