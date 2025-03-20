package com.instagram.instagram.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.instagram.dto.CommentObject;
import com.instagram.instagram.dto.Post;
import com.instagram.instagram.entity.PostEntity;
import com.instagram.instagram.service.PostService;
import com.instagram.instagram.util.ResponseHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PostController {
	
	@Autowired(required = true)
	public PostService postservice;
	
	@Autowired
	private ResponseHandler responseHandler;
	
	private static String errormsg;
	private static String successmsg;
	
    @GetMapping("/allpost")
    public List<Post> getAllPost() {
    	List<Post> post= postservice.getallPost();
    	return post;
    }
    
    @GetMapping("/mypost")
    public List<Post> getmypost(HttpServletRequest request){
    	System.out.println("controller"+"request: "+request.getUserPrincipal().getName()+"response: ");
    	List<Post> mypost = postservice.getmypost(request.getUserPrincipal().getName());
    	System.out.println("mypostcontroller"+mypost);
    	return mypost;
    }
    
    @GetMapping("/userpost")
    public List<Post> getuserpost(HttpServletRequest request,
			HttpServletResponse response){
    	List<Post> userpost = postservice.getuserpost(request.getUserPrincipal().getName());
    	return userpost;
    }
    
    @PostMapping("/createpost")
    public ResponseEntity<Object> createpost(@RequestBody Post obj,HttpServletRequest request) {
    	String title=obj.getTitle();
    	String body=obj.getBody();
    	String picture=obj.getPicture();
    	if(title==null || body==null || picture==null) {
    		errormsg="Please provide all fields";
    		return responseHandler.generatePostResponse(errormsg, HttpStatusCode.valueOf(422), null, null);
    	}
    	PostEntity postentity=postservice.createpost(obj,request.getUserPrincipal().getName());
    	if(postentity!=null) {
    		successmsg="successfully created";
    		return responseHandler.generatePostResponse(null, HttpStatusCode.valueOf(201), postentity,successmsg);
    	}else {
    		errormsg="something went wrong";
    		return responseHandler.generatePostResponse(errormsg, HttpStatusCode.valueOf(422), obj, null);
    	}
    }
    @PutMapping("/likes")
    public ResponseEntity<Object> likePost(@RequestBody String postId,HttpServletRequest request){
    	if(postId==null) {
    		errormsg="please provide ID";
    		return responseHandler.generatePostResponse(errormsg, HttpStatusCode.valueOf(422), null, null);
    	}
    	System.out.println("likes controller"+postId);
    	Post postentity=postservice.likePost(postId,request.getUserPrincipal().getName() );
    	return responseHandler.generatePostResponse(errormsg, HttpStatusCode.valueOf(201), postentity, "successfull");
    	
    }
    
    @PutMapping("/unLike")
    public ResponseEntity<Object> unLikePost(@RequestBody String postId,HttpServletRequest request){
    	if(postId==null) {
    		errormsg="please provide ID";
    		return responseHandler.generatePostResponse(errormsg, HttpStatusCode.valueOf(422), null, null);
    	}
    	Post postentity=postservice.unLikePost(postId,request.getUserPrincipal().getName() );
    	return responseHandler.generatePostResponse(errormsg, HttpStatusCode.valueOf(201), postentity, "successfull");
    	
    }
    
    @PutMapping("/comment")
    public ResponseEntity<Object> addComment(@RequestBody CommentObject obj,HttpServletRequest request){
          String postId= obj.getpostId();
          String text= obj.getText();
          if(postId==null || text==null) {
        	  errormsg="please provide ID";
      		return responseHandler.generatePostResponse(errormsg, HttpStatusCode.valueOf(422), null, null);
          }
         PostEntity postentity= postservice.addComment(postId,text,request.getUserPrincipal().getName());
         return responseHandler.generatePostResponse(errormsg, HttpStatusCode.valueOf(201), postentity, "successfull");
    	
    }
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable String postId,HttpServletRequest request){
    	PostEntity entity=postservice.deletePost(postId,request.getUserPrincipal().getName());
    	if(entity==null) {
    		errormsg="Post not found";
      		return responseHandler.generatePostResponse(errormsg, HttpStatusCode.valueOf(422),entity, null);
    	}
    	return responseHandler.generatePostResponse(errormsg, HttpStatusCode.valueOf(201), entity, "deleted successfully");
    }
    
    @DeleteMapping("/deleteComment/{postId}")
    public ResponseEntity<Object> deleteComment(@PathVariable String commentId,@RequestBody String postId,HttpServletRequest request){
    	PostEntity entity=postservice.deleteComment(commentId,postId,request.getUserPrincipal().getName());
    	if(entity==null) {
    		errormsg="Post not found";
      		return responseHandler.generatePostResponse(errormsg, HttpStatusCode.valueOf(422),entity, null);
    	}
    	return responseHandler.generatePostResponse(errormsg, HttpStatusCode.valueOf(201), entity, "deleted successfully");
    }
    
}
