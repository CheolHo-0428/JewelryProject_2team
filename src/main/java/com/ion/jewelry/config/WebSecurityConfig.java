package com.ion.jewelry.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.ion.jewelry.jwt.AuthEntryPointJwt;
import com.ion.jewelry.jwt.AuthTokenFilter;
import com.ion.jewelry.service.MemberDetailsServiceImpl;




//Security에서는 모든 웹페이지에 대한 요청이 들어올 때 OncePerRequestFilter로 오는 것으로 설계가 되어 있음
//OncePerRequestFilter에서 수행하는 동작은 웹페이지에 접근할 때 현재 로그인이 되어 있는 상태인지에 대한 부분을 검사함
//configuration:해당 클래스를 Configuration으로 등록합니다
//EnableWebSecurity:Spring security 활성화
//EnableGlobalMethodSecurity:Controller에서 특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 @PreAuthorize 어노테이션을 사용하는데, 해당 어노테이션에 대한 설정을 활성화시키는 어노테이션입니다. (필수는 아닙니다.)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)   
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	MemberDetailsServiceImpl memberDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
		
//		OncePerRequestFilter로부터 상속받은 객체로, 직접 선언해야 함
//		로그인을 시도할 때 객체를 생성하며, Security의 내장 객체(UserDetails)를 가지고 인증을 수행함
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(memberDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
		
//		Security 내장객체인 AuthenticationManager를 반환합니다. 별도로 선언은 하지 않아도 되며, 
//		인증 수행을 위한 동작을 나타냄.
	}

	@Bean
	public PasswordEncoder passwordEncoder() {  //내장객체로, 비밀번호 암호화를 담당
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()   // rest api이므로 csrf 보안이 필요없으므로 disable처리
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()   // jwt token으로 인증할것이므로 세션필요없음
			.authorizeRequests()
			.antMatchers("/jewelry/**").permitAll()	
			.antMatchers("/jewelry/auth/**").permitAll()
			.antMatchers("/jewelry/member/**").permitAll()
			.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
