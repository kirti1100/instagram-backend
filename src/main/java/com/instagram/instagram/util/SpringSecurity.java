package com.instagram.instagram.util;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.SessionManagementDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
@Configuration
@EnableWebSecurity
public class SpringSecurity{
	
	@Autowired(required=true)
	public UserDetailsService userDetailsService;
	
	@Autowired
	public jwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain securityfilterChain(HttpSecurity http) throws Exception {
		return http
		           .csrf(csrf -> csrf.disable())
		           .httpBasic(Customizer.withDefaults())
		           .cors(cors -> cors.configurationSource(request -> {
		                CorsConfiguration config = new CorsConfiguration();
		                config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Replace with your frontend's origin
		                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		                config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type","body"));
		                return config;
		            }))
		           .authorizeHttpRequests(customizer->customizer.requestMatchers("signin","signup").permitAll().anyRequest().authenticated())
		           .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		           .build();
	}
	
	@Bean 
	public AuthenticationProvider authenticationprovider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		provider.setUserDetailsService(userDetailsService);
		System.out.println("provider"+provider);
		return provider;
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configure) throws Exception {
		return configure.getAuthenticationManager();
	}
	
      
	
	}
