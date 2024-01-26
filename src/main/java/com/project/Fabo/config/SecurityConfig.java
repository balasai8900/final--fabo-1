package com.project.Fabo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project.Fabo.service.UserService;


@Configuration
public class SecurityConfig {

    //bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //authenticationProvider bean definition
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationSuccessHandler successHandler) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/superadmin").hasRole("SUPERADMIN")
                                .requestMatchers("/admin").hasRole("ADMIN_SUPPORT")
                                .requestMatchers("/admin").hasRole("ADMIN_PRODUCTS")
                                .requestMatchers("/admin").hasRole("ADMIN_ACCOUNTS")
                                .requestMatchers("/admin").hasRole("ADMIN_CREATIVES")
                                .requestMatchers("/client").hasRole("CLIENT_SUPPORT")
                                .requestMatchers("/client").hasRole("CLIENT_PRODUCTS")
                                .requestMatchers("/client").hasRole("CLIENT_ACCOUNTS")
                                .requestMatchers("/client").hasRole("CLIENT_CREATIVES")
                                .requestMatchers("/admin").hasAnyRole("ADMIN_SUPPORT", "ADMIN_PRODUCTS", "ADMIN_ACCOUNTS")
                                .requestMatchers("/client").hasAnyRole("CLIENT_SUPPORT", "CLIENT_PRODUCTS", "CLIENT_ACCOUNTS")
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/showLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/home")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();
    }

}