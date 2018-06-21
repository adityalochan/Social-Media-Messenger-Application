package aditya.messenger.database;

import java.util.*;

import aditya.messenger.model.Message;
import aditya.messenger.model.Profile;

public class DatabaseClass {

	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();
	
	
	public static Map<Long, Message> getMessages() {
	return messages;
	}
	
	public static Map<String, Profile> getProfiles(){
		return profiles;
	}
}
