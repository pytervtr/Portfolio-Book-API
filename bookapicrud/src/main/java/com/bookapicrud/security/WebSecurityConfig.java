package com.bookapicrud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebMvc
@AllArgsConstructor
public class WebSecurityConfig implements WebMvcConfigurer {

    private final JwtAuthFilter jwtAuthFilter;

    private final UserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain sercurityFilterChain(HttpSecurity http) throws Exception{//add login calls

        return http
            .csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/auth/test").permitAll()                
                .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/activateAccount/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/auth/refreshToken").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/auth/requestPassword").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/auth/recoverPassword/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/users/test").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/books/test").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/books/title/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/books/author/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/books/list").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement((session) -> session 
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
        
            .build()
        ;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
            .allowedOrigins("/**")
            .allowedMethods("OPTIONS", "POST", "GET", "PUT", "DELETE")
            .allowedHeaders("*")
            .exposedHeaders("*");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean 
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .build();
    }


}
