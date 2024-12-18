package com.instagram.instagram.repository;

import java.util.List;
import java.util.Optional;

import com.instagram.instagram.entity.UserEntity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface UserRepo extends MongoRepository<UserEntity,ObjectId>{
    List<UserEntity> findAll();
   // List<UserEntity> findById(ObjectId objectId);
	//Optional<UserEntity> findOne(Query query, Class<UserEntity> class1, String string);

	Optional<UserEntity> findByEmail(String email);
	
	Optional<UserEntity> findByName(String name);

	List<UserEntity> findByNameContainingIgnoreCase(String emailId);

	

	//void findByIdAndUpdate(ObjectId followId);

	//Optional<UserEntity> findByEmail(String Email);
}
