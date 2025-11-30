package com.project_agh.payrollmanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Class defining the security configuration for the application
@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService; // Dependency on the custom UserDetailsService (CustomUserDetailsService)

    // Constructor for injecting the UserDetailsService
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Defines the PasswordEncoder bean.
     * BCrypt is the standard, secure algorithm recommended for password hashing.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines the Authentication Provider.
     * DaoAuthenticationProvider uses the UserDetailsService and PasswordEncoder
     * to perform the actual authentication (fetching user data and checking the password).
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Set the custom service that loads user details from the database
        authProvider.setUserDetailsService(userDetailsService);
        // Set the encoder used to verify the submitted password against the stored hash
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Defines the Security Filter Chain, configuring HTTP request security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disables CSRF protection (often disabled for simple APIs,
                // but should be kept enabled for production web apps using forms)
                .csrf(AbstractHttpConfigurer::disable)
                // Registers the custom authentication provider
                .authenticationProvider(authenticationProvider())
                // Configuration for authorization of HTTP requests
                .authorizeHttpRequests(auth -> auth
                        // Allows public access to static resources and specific endpoints
                        .requestMatchers("/images/**","/", "/login", "/css/**", "/js/**").permitAll()
                        // Requires authentication for all other requests
                        .anyRequest().authenticated()
                )
                // Configuration for the standard HTML form login process
                .formLogin(form -> form
                        // Specifies the custom login page URL
                        .loginPage("/login")
                        // URL where Spring Security expects the POST request with credentials
                        .loginProcessingUrl("/login")
                        // Specifies the name of the request parameter for the username (email)
                        .usernameParameter("email")
                        // Specifies the name of the request parameter for the password
                        .passwordParameter("password")
                        // Redirect URL after successful login (always redirects here)
                        .defaultSuccessUrl("/dashboard", true)
                        // Redirect URL after failed login
                        .failureUrl("/login?error")
                )
                // Configuration for the logout process
                .logout(logout -> logout
                        // URL to trigger logout
                        .logoutUrl("/logout")
                        // Redirect URL after successful logout
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }
}