package com.instagram.instagram.service;

import java.util.List;

import com.instagram.instagram.dto.Post;
import com.instagram.instagram.entity.PostEntity;

public interface PostService {

	List<Post> getallPost();

	List<Post> getmypost(String userId);

	List<Post> getuserpost(String email);

	PostEntity createpost(Post post,String email);
	
	Post likePost(String Id,String email);
	
	Post unLikePost(String Id,String email);

	PostEntity addComment(String postId, String text, String name);

	PostEntity deletePost(String postId, String email);

	PostEntity deleteComment(String commentId, String postId, String name);

}
