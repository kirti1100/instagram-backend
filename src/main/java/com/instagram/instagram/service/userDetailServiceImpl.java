package com.instagram.instagram.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.instagram.instagram.dto.User;
import com.instagram.instagram.dto.UserPrincipal;
import com.instagram.instagram.entity.UserEntity;
import com.instagram.instagram.repository.UserRepo;

@Component
public class userDetailServiceImpl implements UserDetailsService{
     @Autowired
     private UserRepo userrepo;
     
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserEntity> userobj=userrepo.findByEmail(username);
		UserEntity userentity=userobj.get();
		User user= userentity.setUser(userentity);
		if(userentity!=null) {
			
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("user not found exception");
		}
		
	}
	
	@Bean
	public PasswordEncoder pswrdEncoder() {
		return new BCryptPasswordEncoder();
		
	}

}
