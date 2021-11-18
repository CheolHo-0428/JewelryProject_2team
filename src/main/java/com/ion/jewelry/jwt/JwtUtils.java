package com.ion.jewelry.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {   // jwtUtils는 사용자가 선언해야함
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${bezkoder.app.jwtSecret}")  // 식별자 역할 application.properties에 등록이 되어있음
	private String jwtSecret;

	@Value("${bezkoder.app.jwtExpirationMs}") //위와동일
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {							//토큰만들자!

		MemberSecurity userPrincipal = (MemberSecurity) authentication.getPrincipal();
		
		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)   
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		System.out.println("token : " + token);
		
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	// jwtUtils 클래스에서 Token 검사를 함
	public boolean validateJwtToken(String authToken) {
		try {
			
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			
			System.out.println("authToken : " + authToken);
			
			return true;
			
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
