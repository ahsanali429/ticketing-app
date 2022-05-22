package com.task.callsign.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Configuration
public class JWTEntryPointConfiguration implements AuthenticationEntryPoint, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTEntryPointConfiguration.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException{
        LOGGER.error(String.format("Error in authorization, request: %s, ", request), authException);
        response.sendError(
            HttpServletResponse.SC_UNAUTHORIZED,
            "User is not authorized to perform this action, Message: " + authException.getMessage()
        );
    }
}
