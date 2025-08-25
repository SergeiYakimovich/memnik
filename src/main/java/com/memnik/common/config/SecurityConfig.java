package com.memnik.common.config;

import com.memnik.service.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.memnik.common.constants.Constants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HOME_URL, CONTACTS_URL, REGISTRATION_URL,
                                STORAGE + "/**", "/css/**",
                                CONFIRM_URL + "/**", UNSUBSCRIBE_URL + "/**").permitAll()
                        .requestMatchers(ADMIN_URL + "/**", "/swagger-ui/**", "/v3/api-docs/**").hasRole("ADMIN")
                        .requestMatchers( TAG_URL + "/**", MEM_URL + "/**", JOKE_URL + "/**",
                                POSTCARD_URL + "/**", QUOTE_URL + "/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated())

                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .rememberMe(rememberMe -> rememberMe.key("AbcdEfghIjkl..."));

        return http.build();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
