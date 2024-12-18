package com.instagram.instagram.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.instagram.instagram.entity.PostEntity;

public interface PostRepo extends MongoRepository<PostEntity, ObjectId>{
	
	List<PostEntity> findByPostedby(ObjectId userId);

	Optional<PostEntity>  findById(ObjectId id);

	Boolean deleteById(String postId);

}
