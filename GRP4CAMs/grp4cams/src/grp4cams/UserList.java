package grp4cams;

import java.util.ArrayList;

/**
Represents the attendees in a camp
@author Jasmine Goh
@version 1.0
@since 2023-11-25
*/

public class UserList {
	
	/**
	 * List of Existing Users in CAMs
	 */
	private ArrayList<User> userList;
	
	/**
	 * constructor for the UserList class
	 * include list of users in CAMs
	 */
	public UserList() {
		
		userList = new ArrayList<>();
		
		// Adding staff to userList
	    userList.add(new User("Madhukumar", "HUKUMAR", "password", "SCSE", "staff"));
		userList.add(new User("Alexei", "OURIN", "password", "ADM", "staff"));
		userList.add(new User("Chattopadhyay", "UPAM", "password", "EEE", "staff"));
		userList.add(new User("Datta", "ANWIT", "password", "SCSE", "staff"));
		userList.add(new User("Arvind", "ARVI", "password", "NBS", "staff"));
		
		// Adding student to userList
		userList.add(new User("CHERN", "YCHERN", "password", "SCSE", "student"));
		userList.add(new User("KOH", "KOH1", "password", "ADM", "student"));
		userList.add(new User("BRANDON", "BR015", "password", "EEE", "student"));
		userList.add(new User("CALVIN", "CT113", "password", "SCSE", "student"));
		userList.add(new User("CHAN", "YCN019", "password", "NBS", "student"));
		userList.add(new User("DENISE", "DL007", "password", "SCSE", "student"));
		userList.add(new User("DONG", "DON84", "password", "ADM", "student"));
		userList.add(new User("ERNEST", "ELI34", "password", "EEE", "student"));
		userList.add(new User("LEE", "LE51", "password", "SCSE", "student"));
		userList.add(new User("LIU", "SL22", "password", "NBS", "student"));
		userList.add(new User("RAWAL", "AKY013", "password", "SSS", "student"));
	}

	/**
	 * get CAMs User List
	 * @return List of existing users
	 */
    public ArrayList<User> getUserList() {
        return userList;
    }
}
