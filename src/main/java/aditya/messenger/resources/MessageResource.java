package aditya.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import aditya.messenger.model.Message;
import aditya.messenger.resources.beans.MessageFilterBean;
import aditya.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();
	
	
	//@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
//	public List<Message> getMessages(@QueryParam("year") int year,
//									 @QueryParam("start") int start,
//									 @QueryParam("size") int size)
	public List<Message> getJsonMessages(@BeanParam MessageFilterBean filterBean)
	{	
		System.out.println("JSON method called");
		if(filterBean.getYear() > 0){
			return messageService.getAllMessagesForYear(filterBean.getYear());	
		}
		if(filterBean.getStart()>=0 && filterBean.getSize()>0){
			return messageService.getAllMessagesPagination(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}	
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXmlMessages(@BeanParam MessageFilterBean filterBean)
	{
		System.out.println("XML method called");
		if(filterBean.getYear() > 0){
			return messageService.getAllMessagesForYear(filterBean.getYear());	
		}
		if(filterBean.getStart()>=0 && filterBean.getSize()>0){
			return messageService.getAllMessagesPagination(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
//	@POST
//	public Message addMessages(Message message){
//		return messageService.addMessage(message);
//	}
	
//	@POST
//	public Response addMessage(Message message) throws URISyntaxException {
//		Message newMessage = messageService.addMessage(message);
//		return Response.created(new URI("/messenger/webapi/messages/" + newMessage.getId()))
//		.entity(newMessage)
//		.build();
//	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
				.entity(newMessage)
				.build();
	}
	
	@PUT
	@Path("/{messageId}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId") long id, Message message){
		message.setId(id);
		return messageService.updateMessage(message);
	}	
	
	@DELETE
	@Path("/{messageId}")
//	@Produces(MediaType.APPLICATION_JSON)
	public void deleteMessage(@PathParam("messageId") long id){
		messageService.removeMessage(id);
	}
	
	@GET
	@Path("/{messageId}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Message getMessage(@PathParam("messageId") long id){
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo){
	//	messageService.getAllMessages(messageId);
	//	return "Got path param : " + messageId;
//		return messageService.getMessage(id);
			Message message = messageService.getMessage(id);
			message.addLink(getUriForSelf(uriInfo, message), "self");
			message.addLink(getUriForProfile(uriInfo, message), "profile");
			message.addLink(getUriForComments(uriInfo, message), "comments");	
			return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		// TODO Auto-generated method stub
		URI uri = uriInfo.getBaseUriBuilder()                 			//https://localhost:8080/messenger/webapi/
				.path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")	
				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())	 
				.build();
		return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()                 			//https://localhost:8080/messenger/webapi/
		.path(ProfileResource.class)
		.path(message.getAuthor())								       //										 /profiles
		.build();
		return uri.toString();
		
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
		.path(MessageResource.class)
		.path(Long.toString(message.getId()))
		.build()
		.toString();
		return uri;
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource(){
		return new CommentResource();
	}
}
