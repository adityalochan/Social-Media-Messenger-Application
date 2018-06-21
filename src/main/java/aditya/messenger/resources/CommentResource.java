package aditya.messenger.resources;

import java.util.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import aditya.messenger.model.Comment;
import aditya.messenger.service.CommentService;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class CommentResource {

	private CommentService commentService = new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId) { 
		return commentService.getAllComments(messageId);
	}
	
	@POST 
	public Comment addMessage(@PathParam("messageId") long messageId, Comment comment) {
		return commentService.addComment(messageId, comment);
	}
	
	@PUT 
	@Path("/{commentId}")
	public Comment updateMessage(@PathParam("messageId") long messageId, @PathParam("commentId") long id, Comment comment){
		comment.setId(id);
		return commentService.updateComment(messageId, comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId){
		commentService.removeComment(messageId, commentId);
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getMessage(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId){
		return commentService.getComment(messageId, commentId);
	}
	

//	@GET 
//	@Path("/{commentId}")
//	public String test2(@PathParam("messageId") long messageID, @PathParam("commentId") long commentID){
//		// here commentid should get commentID portion from the url we type
//		// here messageId is not in the above mentioned method path annotation. Instead it is 
//		// actually in the parent resource path annotation but since this is the call that actually led to 
//		// execution of the method annotation @Path("/{commentId}") in the CommentResource.java file 
//		// the parameter value is taked from that method.
//		return "Method to return comment ID: " + commentID + " for message: " + messageID;
//	}
}
