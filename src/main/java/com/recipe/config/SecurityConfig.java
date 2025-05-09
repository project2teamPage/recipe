package com.recipe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http.formLogin(Customizer.withDefaults());
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .formLogin(form -> form
//
//                        .loginPage("/login")
//                        .loginProcessingUrl("/login") // form action
//                        .failureUrl("/login?error")
//                        .usernameParameter("loginId") // input name
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/", true) // 성공시 리다이렉트
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/")
//                )
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "/user/**", "admin/**").permitAll()
//                        .requestMatchers("/admin/**").hasRole("admin").anyRequest().authenticated()
//                        .requestMatchers("/css/**", "/images/**", "/javascript/**").permitAll()
//                        .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


}


