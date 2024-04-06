package com.gagan.basicauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class BasicAuthConfig {

    private final AppBasicAuthenticationEntryPoint appBasicAuthenticationEntryPoint;

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("password"))
                .roles("Admin")
                .build();
        UserDetails developer = User
                .withUsername("developer")
                .password(passwordEncoder().encode("developer"))
                .roles("Developer")
                .build();

        return new InMemoryUserDetailsManager(admin, developer);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authHttpCustomizer ->
                        authHttpCustomizer.requestMatchers("/public").permitAll()
                                .requestMatchers("/authorize").hasAnyRole("Developer", "Admin")
                                .requestMatchers("/authenticate").hasRole("Admin")
                                .anyRequest().authenticated())
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.authenticationEntryPoint(appBasicAuthenticationEntryPoint));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
