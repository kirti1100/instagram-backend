package com.instagram.instagram.util;

import java.io.IOException;

//import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.instagram.instagram.service.JWTService;
import com.instagram.instagram.service.userDetailServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
//@RequiredArgsConstructor
public class jwtAuthenticationFilter extends OncePerRequestFilter{
	@Autowired
    private JWTService jwtService;
	
	@Autowired
	private ApplicationContext context;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
			)throws ServletException, IOException {
		
		 String authentication= request.getHeader("Authorization");
		 System.out.println(authentication);
		 String JWT=null;
		String userEmail=null;
		if(authentication != null && authentication.startsWith("Bearer ")){
			JWT = authentication.split(" ")[1].trim();
			userEmail= jwtService.extractUsername(JWT);
			
		}
		if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails= context.getBean(userDetailServiceImpl.class).loadUserByUsername(userEmail);
			System.out.println("userdetails"+userDetails.getAuthorities());
			if(jwtService.validateToken(JWT,userDetails)) {
				UsernamePasswordAuthenticationToken authToken=
						new UsernamePasswordAuthenticationToken(userEmail,JWT,userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
