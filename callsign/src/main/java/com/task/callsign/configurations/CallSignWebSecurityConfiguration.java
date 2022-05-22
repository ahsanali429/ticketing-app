package com.task.callsign.configurations;

import com.task.callsign.filters.JWTRequestFilter;
import com.task.callsign.jobs.DeliveryMonitoringScheduledJob;
import com.task.callsign.services.CallSignUserDetailsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class CallSignWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CallSignUserDetailsService callSignUserDetailService;

    @Autowired
    private JWTRequestFilter jwtRequestFilter;

    @Autowired
    private JWTEntryPointConfiguration jwtEntryPointConfiguration;

    @Bean
    public PasswordEncoder getPasswordEncoderBean(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(callSignUserDetailService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this api
        httpSecurity.csrf().disable()
            // dont authenticate this particular request
            .authorizeRequests().antMatchers("/authenticate", "/actuator/**").permitAll()
            // all other requests need to be authenticated
            .anyRequest().authenticated()
            // make sure we use stateless session; session won't be used to
            // store user's state.
            .and().exceptionHandling().authenticationEntryPoint(jwtEntryPointConfiguration)
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
