package com.application.muksullang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().disable()			//cors 방지
		.csrf().disable()			//csrf 방지
		.formLogin().disable()		//기본 로그인페이지 없애기
		.headers().frameOptions().disable();
		
		return http.build();
	}
	 
	/*
	 * @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .cors().disable()
	            .csrf().disable()
	            .authorizeHttpRequests(authorizeRequests ->
	                authorizeRequests
	                    .requestMatchers("/login", "/logout").permitAll() // 로그인과 로그아웃 페이지 허용
	                    .anyRequest().authenticated() // 나머지 요청은 인증 필요
	            )
	            .formLogin(formLogin ->
	                formLogin
	                    .loginPage("/login") // 사용자 정의 로그인 페이지
	                    .permitAll()
	            )
	            .logout(logout ->
	                logout
	                    .logoutUrl("/logout") // 로그아웃 URL
	                    .logoutSuccessUrl("/login?logout") // 로그아웃 후 리다이렉트 페이지
	                    .permitAll()
	            )
	            .headers(headers ->
	                headers
	                    .frameOptions().disable()
	            );

	        return http.build();
	    }
	 * */
	 
}
