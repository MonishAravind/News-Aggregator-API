/*package com.example.task.util;

import com.example.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/authenticate",
                                "/api/v1/auth/refresh-token",
                                "/tasks",
                                "/tasks/{id}",
                                "/tasks/priority/{level}",
                                "/tasks/status/{status}",
                                "/api/v1/login",
                                "/api/v1/user/{username}",
                                "/api/v1/preferences",
                                "/h2-console/**",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        http.headers().frameOptions().disable();
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
*/