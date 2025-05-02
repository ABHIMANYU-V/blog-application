package com.prototype.blog_application.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import com.prototype.blog_application.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtilityService {

	@Value("${app.jwt.secret}")
	private String jwtSecretString;
	
	@Value("${app.jwt.expiration-ms}")
	private Long jwtExpirationInMs;
	
	private SecretKey jwtSecret;
	
	@PostConstruct
	public void init() {
		this.jwtSecret = Keys.hmacShaKeyFor(jwtSecretString.getBytes());
	}
	
	public  String generateToken(User user) {
		Map<String,Object> claims = new HashMap<>();
		claims.put("role", user.getRole());
		return Jwts.builder()
				.setSubject(user.getEmail())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
				.addClaims(claims)
				.signWith(jwtSecret,SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Claims parseClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(jwtSecret)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String extractEmail(String token) {
		return parseClaims(token).getSubject();
	}
	
	public String getRoleFromToken(String token) {
		Claims claims = parseClaims(token);
		return claims.get("role",String.class);
	}
	
	public boolean validateToken(String token) {
		 try {
	            parseClaims(token);
	            return true;
	        } catch (ExpiredJwtException e) {
	            System.out.println("JWT expired: " + e.getMessage());
	        } catch (MalformedJwtException e) {
	            System.out.println("Invalid JWT: " + e.getMessage());
	        } catch (Exception e) {
	            System.out.println("JWT validation failed: " + e.getMessage());
	        }
	        return false;
	}
}
