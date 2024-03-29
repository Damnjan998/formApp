package com.formapp.damnjan.config;

import com.formapp.damnjan.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.formapp.damnjan.config.SecurityConstants.*;
import static com.formapp.damnjan.config.SwaggerConstants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JWTAuthFilter jwtAuthFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JWTAuthFilter jwtAuthFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(AUTH_PATTERN,
                                SWAGGER_UI_INDEX,
                                SWAGGER_DOCS,
                                SWAGGER_V3_DOCS,
                                V2_API_DOCS,
                                SWAGGER_RESOURCES,
                                SWAGGER_CONFIGURATION_UI,
                                SWAGGER_CONFIGURATION_SECURITY,
                                SWAGGER_UI_HTML,
                                SWAGGER_UI,
                                WEBJARS).permitAll()
                        .requestMatchers(HttpMethod.POST, FORM_PATTERN).hasAnyAuthority(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, FORM_PATTERN).hasAnyAuthority(ADMIN)
                        .requestMatchers(HttpMethod.PUT, FORM_PATTERN).hasAnyAuthority(ADMIN)
                        .requestMatchers(HttpMethod.POST, FIELD_PATTERN).hasAnyAuthority(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, FIELD_PATTERN).hasAnyAuthority(ADMIN)
                        .requestMatchers(HttpMethod.PUT, FIELD_PATTERN).hasAnyAuthority(ADMIN)
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }
}
