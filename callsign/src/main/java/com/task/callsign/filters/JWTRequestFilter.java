package com.task.callsign.filters;

import com.task.callsign.services.CallSignUserDetailsService;
import com.task.callsign.util.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private final CallSignUserDetailsService callSignUserDetailsService;
    private final JWTHelper jwtHelper;
    private final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private final String AUTHORIZATION_URI = "/authenticate";

    @Autowired
    public JWTRequestFilter(CallSignUserDetailsService callSignUserDetailsService, JWTHelper jwtHelper) {
        this.callSignUserDetailsService = callSignUserDetailsService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException{

        if(!request.getRequestURI().equalsIgnoreCase(AUTHORIZATION_URI)) {
            Optional<String> token = getTokenFromRequestHeader(request);

            if (token.isPresent()) {
                String jwt = token.get();
                String userName = jwtHelper.extractUsernameFromToken(jwt);

                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = callSignUserDetailsService.loadUserByUsername(userName);

                    if (jwtHelper.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                            );

                        usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }

            }
        }
        chain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequestHeader(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER_KEY);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }
}
