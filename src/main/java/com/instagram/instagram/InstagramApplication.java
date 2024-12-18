package com.instagram.instagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.instagram.instagram.entity.UserEntity;
import com.instagram.instagram.repository.UserRepo;
import com.instagram.instagram.service.PostService;
import com.instagram.instagram.service.PostServiceImpl;
import com.instagram.instagram.service.UserService;
import com.instagram.instagram.service.UserServiceImpl;

@SpringBootApplication(scanBasePackages = { "com.instagram" })
@ComponentScan("com.instagram")
public class InstagramApplication {
    //\@Autowired
//	private UserRepo userRepo;

	public static void main(String[] args) {
		
        UserService userepo=null;
		AbstractApplicationContext context = (AbstractApplicationContext) SpringApplication.run(InstagramApplication.class, args);
		  userepo = (UserServiceImpl) context.getBean("userservice");
		  System.out.println(userepo.findById("65b4ea1dcdb48125fe2a4626"));
	}
	@Bean(name="userservice")
	@Primary
	public UserService user() {
		return new UserServiceImpl();
	}
	
	@Bean(name="postservice")
	@Primary
	public PostService post() {
		return new PostServiceImpl();
	}
	

//	@Override
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		for (UserEntity customer : userRepo.findAll()) {
//		      System.out.println(customer);
//		    }
//		
//	}

//	@Override
//	public void run(String... args) throws Exception {
//		UserServiceImpl userRepo=null;
//
//		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//		//userRepo = (UserServiceImpl)context.getBean("userservice");
//
//
//		// TODO Auto-generated method stub
//		listAll();
//		
//	}
//	public void listAll() {
//		 System.out.println("Listing sample data");
//		 //userRepo.finduser().forEach(u -> System.out.println(u));
//		 }

}
