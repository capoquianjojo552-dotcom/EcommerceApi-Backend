package com.ws101.capoquian.banawis.EcommerceApi.config;

import org.springframework.securityy.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                .requestMatchers("/api/v1/auth/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/orders").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/products").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(withDefaults())
            .csrf(withDefaults());
        
        return http.build();
    }
}
