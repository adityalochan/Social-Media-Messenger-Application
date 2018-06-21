package aditya.messenger.service;

import java.util.*;

import aditya.messenger.database.DatabaseClass;
import aditya.messenger.exception.DataNotFoundException;
import aditya.messenger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService(){
		messages.put(1L, new Message(1,"Hello World", "Koushik"));
		messages.put(2L, new Message(2,"Hello Jersey!", "Koushik"));
	}
	
	public List<Message> getAllMessages(){
		
//		Message m1 = new Message(1L, "Hello World", "Koushik");
//		Message m2 = new Message(2L, "Hello Jersey!", "Koushik");
//		List<Message> list = new ArrayList<>();
//		list.add(m1);
//		list.add(m2);
//		return list;
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year){
		List<Message> messageForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for( Message message: messages.values()){
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year) { 
				// keep comparing messages and message that matches the calendar year 
				messageForYear.add(message);
			}
		}
		return messageForYear;
	}
	
	public List<Message> getAllMessagesPagination(int start, int size){
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if(start + size > list.size())
			return new ArrayList<Message>();
		return list.subList(start, start+size);
	}
	
//	public Message getMessage(long id){
//		return messages.get(id);
//	}
	public Message getMessage(long id){
		Message message = messages.get(id);
		if(message == null){
			throw new DataNotFoundException("message with id"+ id + " not found");
		}
		return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if(message.getId() <=0){
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
}
