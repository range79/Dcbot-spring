package com.range.discordbot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfIg {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf-> csrf.disable())
                //disable form login
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/admin-panel").hasRole("ADMIN")
                                .anyRequest().permitAll());
        return http.build();
    }
}
