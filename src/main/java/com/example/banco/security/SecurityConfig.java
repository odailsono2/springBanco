package com.example.banco.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // habilita POST, DELETE, PUT via Postman
            .httpBasic(Customizer.withDefaults()) // Permite autenticação HTTP Basic
            .formLogin(Customizer.withDefaults()) // Habilita o formulário de login
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/banco/public/**").permitAll() // Acesso público
                .requestMatchers("/banco/admin/**").hasAuthority("ROLE_ADMIN") // Apenas ADMIN
                .requestMatchers("/banco/private/**").hasRole("USER") // Apenas USER
                .requestMatchers("/login", "/logout", "/error").permitAll() 
                // .requestMatchers("/banco/login", "/banco/logout").permitAll() // Permite acesso ao login e logout
            );
    
        return http.build();
    }
    @Bean
    public UserDetailsService users() {
        PasswordEncoder encoder = passwordEncoder(); // Obtem o PasswordEncoder
        UserBuilder users = User.builder().passwordEncoder(encoder::encode); // Adiciona codificação

        UserDetails user = users
            .username("user")
            .password("user") // Será codificado automaticamente
            .roles("USER")
            .build();

        UserDetails admin = users
            .username("admin")
            .password("admin") // Será codificado automaticamente
            .roles("USER", "ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Codificador seguro
    }
}
