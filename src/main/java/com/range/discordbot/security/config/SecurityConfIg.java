package com.range.discordbot.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfIg {



    @Value("${app.admin-name}")
    private String adminname;

    @Value("${app.admin-pass}")
    private String adminpass;

    @Value("${app.mod-name}")
    private String modName;

    @Value("${app.mod-pass}")
    private String modPass;






        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .formLogin(form -> form
                            .loginPage("/login") // GET ile login.html'i döneceksin
                            .loginProcessingUrl("/perform_login") // Formun POST ettiği yer
                            .defaultSuccessUrl("/admin", true) // Giriş başarılı olursa buraya yönlendir
                            .failureUrl("/login?error=true") // Hatalıysa yine login'e ama error parametresiyle
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout=true")
                            .permitAll()
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()

                            .requestMatchers("/servers/register-server/**","/").hasRole("ADMIN")



                            .anyRequest().authenticated()
                    );

            return http.build();
        }





    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public DaoAuthenticationProvider authenticationProvider(UserDetailsService uds, PasswordEncoder pe) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(uds);

        authProvider.setPasswordEncoder(pe);

        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        return new InMemoryUserDetailsManager(

                User  //building admin user
                        .withUsername(adminname)
                        .password(passwordEncoder.encode(adminpass))
                        .roles("ADMIN")
                        .build(),

                User    //building moderator
                        .withUsername(modName)
                        .password(passwordEncoder.encode(modPass))
                        .roles("MODERATOR")
                        .build()
        );
    }
}
