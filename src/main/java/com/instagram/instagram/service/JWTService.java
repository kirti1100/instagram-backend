package com.instagram.instagram.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
//import java.util.Base64;
//import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

//import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
    private String secretKey="";
    
	public JWTService(){
		try {
			KeyGenerator keygen= KeyGenerator.getInstance("HmacSha256");
			SecretKey sk=keygen.generateKey();
			secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
	}
	public String jsonWebToken(String name) {
		// TODO Auto-generated method stub
		Map<String,Object> claims=new HashMap<>();
		return Jwts.builder()
		.setClaims(claims)
		.setSubject(name)
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis()+60*60*60*30))
		.signWith(generateKey())
		.compact();
	}
	
	private Key generateKey() {
		byte[] keyBytes= Decoders.BASE64.decode(secretKey);
		
		return Keys.hmacShaKeyFor(keyBytes);
	}
	public String extractUsername(String jWT) {
		return extractClaim(jWT,Claims::getSubject);
	}
	private <T> T extractClaim(String jWT, Function<Claims,T> claimResolver) {
		final Claims claim=extractAllClaims(jWT);
		return claimResolver.apply(claim);
	}
	private Claims extractAllClaims(String jWT) {
		return Jwts.parserBuilder()
				.setSigningKey(generateKey())
				.build().parseClaimsJws(jWT).getBody();
	}
	public boolean validateToken(String jWT, UserDetails userDetails) {
		final String userName= extractUsername(jWT);
		return (userName.equals(userDetails.getUsername())) && !isTokenExpired(jWT);
	}
	private boolean isTokenExpired(String jWT) {
		return extractExpiration(jWT).before(new Date());
	}
     private Date extractExpiration(String jWT) {
    	 return extractClaim(jWT, Claims::getExpiration);
     }
}
