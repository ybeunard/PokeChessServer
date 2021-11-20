package com.pokechess.server.config.security;

import com.pokechess.server.filter.security.JwtRequestFilter;
import com.pokechess.server.services.security.AuthenticateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtRequestFilter jwtRequestFilter;
    private final AuthenticateService authenticateService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(JwtRequestFilter jwtRequestFilter, AuthenticateService authenticateService,
                             JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             PasswordEncoder passwordEncoder) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.authenticateService = authenticateService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.passwordEncoder = passwordEncoder;
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(authenticateService).passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Add a filter to validate the tokens with every request
                .addFilterBefore(jwtRequestFilter, BasicAuthenticationFilter.class)
                // We don't need CSRF for this example
                .csrf().disable()
                // dont authenticate this particular requests
                .authorizeRequests().antMatchers("/api/v1/register", "/api/v1/authenticate").permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated().and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
