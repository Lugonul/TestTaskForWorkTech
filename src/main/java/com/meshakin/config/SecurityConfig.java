package com.meshakin.config;

import com.meshakin.entity.UserAccess;
import com.meshakin.repository.UserAccessRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    @Transactional
    public CommandLineRunner initUsers(UserAccessRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (repository.findByUserLogin("user").isEmpty()) {
                UserAccess user = new UserAccess();
                user.setUserLogin("user");
                user.setUserPassword(encoder.encode("user"));
                user.setUserRole("USER");
                repository.save(user);
            }

            if (repository.findByUserLogin("admin").isEmpty()) {
                UserAccess admin = new UserAccess();
                admin.setUserLogin("admin");
                admin.setUserPassword(encoder.encode("admin"));
                admin.setUserRole("ADMIN");
                repository.save(admin);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}