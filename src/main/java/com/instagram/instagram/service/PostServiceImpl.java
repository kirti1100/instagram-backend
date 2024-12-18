package com.instagram.instagram.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
//import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.schema.JsonSchemaObject.Type;
import org.springframework.stereotype.Service;

import com.instagram.instagram.dto.Post;
import com.instagram.instagram.dto.Postedby;
import com.instagram.instagram.dto.User;
import com.instagram.instagram.entity.CommentsEntity;
import com.instagram.instagram.entity.PostEntity;
import com.instagram.instagram.repository.PostRepo;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    public PostRepo postrepo;
    
    @Autowired
    public UserServiceImpl userservice; 
    
    public Post setallPost(PostEntity postentity,User user) {
       Post post= new Post();
       List<User> newlist = new ArrayList<>();
       List<Postedby> newcommentlist = new ArrayList<>();
       post.setId(postentity.getId().toHexString());
       post.setTitle(postentity.getTitle());
       post.setBody(postentity.getBody());
       post.setPicture(postentity.getPicture());
       //User user= userservice.findById(postentity.getPostedby().toString());
       post.setPostedby(user);
       if(postentity.getLikes()!=null) {
    	   postentity.getLikes().forEach((id)->{
        	   User user1 = userservice.findById(id.toString());
        	   newlist.add(user1);
        			   });
           post.setLikes(newlist);
       }else {
    	   post.setLikes(newlist);
    	   }
       if(postentity.getComments()!=null) {
    	   postentity.getComments().forEach(obj->{
        	   Postedby postedby= new Postedby();
               User user2= userservice.findById(obj.getPostedby().toString());
               postedby.setPostedby(user2);
               postedby.setText(obj.getText());
               newcommentlist.add(postedby);
       
           });
           post.setComments(newcommentlist);
       }
       
       return post;
    }
    
	@Override
	public List<Post>  getallPost() {
		// TODO Auto-generated method stub
		List<PostEntity> postlist= postrepo.findAll();
		List<Post> posts = new ArrayList<>();
		postlist.forEach(obj->{
			//System.out.println("getallpost"+obj);
			User user= userservice.findById(obj.getPostedby().toString());
			//System.out.println("gettttt"+user);
			Post postobject=this.setallPost(obj,user);
			posts.add(postobject);

		});
		return posts;
	}

	@Override
	public List<Post> getmypost(String email) {
		User user=userservice.findByEmail(email);
		System.out.println("userid"+user.getId()+"postid");
		List<PostEntity> postlist=postrepo.findByPostedby(new ObjectId(user.getId())); 
		System.out.println("post object"+postlist);
		if(postlist!=null) {
			List<Post> posts = new ArrayList<>();
			postlist.forEach(obj->{
				Post postobject=this.setallPost(obj,user);
				posts.add(postobject);

			});
			return posts;
			
		}
		
        
		return null;
	}

	@Override
	public List<Post> getuserpost(String email) {
		User user=userservice.findByEmail(email);
		List<Post> posts = new ArrayList<>();
		user.following.forEach(id->{
		 List<PostEntity> postlist=postrepo.findByPostedby(new ObjectId(id)); 
		 if(postlist!=null) {
				postlist.forEach(obj->{
					Post postobject=this.setallPost(obj,user);
					posts.add(postobject);
				});
			}
		 
		});
		return posts;
	}

	@Override
	public PostEntity createpost(Post post,String email) {
		User user=userservice.findByEmail(email);
		System.out.println("createpost"+user.getId()+user.getName());
		PostEntity entityObj=new PostEntity();
		entityObj.setTitle(post.getTitle());
		entityObj.setBody(post.getBody());
		entityObj.setPicture(post.getPicture());
		entityObj.setPostedby(new ObjectId(user.getId()));
		postrepo.save(entityObj);
		System.out.println("postentity of create post"+entityObj);
		return entityObj;
		
		
	}

	@Override
	public Post likePost(String Id, String email) {
		User user=userservice.findByEmail(email);
		System.out.println("likeid"+Id+Type.of(user.getId()));
		Optional<PostEntity> entityObj=
				postrepo.findById(new ObjectId(Id));
		System.out.println("likepost"+entityObj);
		if(entityObj.isPresent()) {
			System.out.println("present"+entityObj.get());
			List<ObjectId> listId=entityObj.get().getLikes();
			List<String> listNew=new ArrayList<>();
			if(listId!=null) {
				listId.forEach(e->{
					listNew.add(e.toHexString());
				});
			}else {
				listId=new ArrayList<>();
			}
			
			System.out.println(listId);
			if(listNew.contains(user.getId())) {
				System.out.println("id match");
				return this.setallPost(entityObj.get(), user);
			}
			
			listId.add(new ObjectId(user.getId()));
			entityObj.get().setLikes(listId);
			postrepo.save(entityObj.get());
		}
		return this.setallPost(entityObj.get(), user);
		//return entityObj.get();
	}
	
	@Override
	public Post unLikePost(String Id, String email) {
		User user=userservice.findByEmail(email);
		System.out.println("unlikeid"+Id+user.getId());
		Optional<PostEntity> entityObj=postrepo.findById(new ObjectId(Id));
		if(entityObj.isEmpty()) {
			System.out.println("returning true");
			return null;
		}
		System.out.println("unlike2"+entityObj.get());
		List<ObjectId> listId=entityObj.get().getLikes();
		System.out.println("listid"+listId);
		try {
			listId.remove(new ObjectId(user.getId()));
			entityObj.get().setLikes(listId);
			postrepo.save(entityObj.get());
			System.out.println("after"+entityObj.get());
		}catch(Exception e) {
			System.out.println("exception occured"+e);
		}
		return this.setallPost(entityObj.get(), user);
	}

	@Override
	public PostEntity addComment(String postId, String text, String name) {
		User user=userservice.findByEmail(name);
		Optional<PostEntity> entityObj=postrepo.findById(new ObjectId(postId));
		CommentsEntity commentobj=new CommentsEntity();
		List<CommentsEntity> commentlist=entityObj.get().getComments();
		commentobj.setPostedby(new ObjectId(user.getId()));
		commentobj.setText(text);
		commentlist.add(commentobj);
		entityObj.get().setComments(commentlist);
		postrepo.save(entityObj.get());
		return entityObj.get();
	}

	@Override
	public PostEntity deletePost(String postId, String email) {
		User user=userservice.findByEmail(email);
		Optional<PostEntity> entityObj=postrepo.findById(new ObjectId(postId));
		System.out.println("delete user id"+user.getId());
		
		if(entityObj.isPresent()) {
			System.out.println("delete post"+entityObj.get().getPostedby().toString());
			if(user.getId().equals(entityObj.get().getPostedby().toString())) {
				System.out.println("true");
				postrepo.deleteById(entityObj.get().getId());
				return entityObj.get();
			}
		}
		
		return null;
	}

	@Override
	public PostEntity deleteComment(String commentId, String postId, String name) {
		User user=userservice.findByEmail(name);
		Optional<PostEntity> entityObj=postrepo.findById(new ObjectId(postId));
		if(entityObj.isEmpty()) {
			return null;
		}
		List<CommentsEntity> comments=entityObj.get().getComments();
		System.out.println("listofcomments"+comments);
		entityObj.get().getComments().forEach(commententity->{
			System.out.println("comments"+commententity);
			String userId= user.getId();
			String postedId=commententity.getId().toString();
			if(postedId.equals(userId)) {
				comments.remove(commententity);
				System.out.println("comments2"+comments);
			}
		});
		entityObj.get().setComments(comments);
		return entityObj.get();
	}

}
