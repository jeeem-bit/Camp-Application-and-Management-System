package grp4cams;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
Represents the users of CAMs
@author Jasmine Goh
@version 1.0
@since 2023-11-25
*/

public class User {
	/**
	 * Cuurent User's Name
	 */
	private String name;
	/**
	 * Current User's ID
	 */
    private String UserID;
	/**
	 * Current User's password
	 */
    private String pw;
	/**
	 * Current User's faculty
	 */
    private String faculty;
	/**
	 * Current User's email
	 */
    private String email;
	/**
	 * Current User's member in CAMs (STAFF OR STUDENT)
	 */
    private String member;
    /**
     * if it is current user first time logging in
     */
    private boolean firstLogin;
    /**
     * if current user has withdrawn from camp
     */
    private boolean withdrawnFromCamp;

    /**
     * Users of the CAMs application
     * @param name This User's Name
     * @param userID This User's ID
     * @param pw This User's PW
     * @param faculty This User's Faculty
     * @param mem This User's Member in CAMs (STAFF OR STUDENT)
     */
    public User(String name, String userID, String pw, String faculty, String mem){
        super();
        this.name = name;
        this.UserID = userID;
        this.pw = pw;
        this.faculty = faculty;
        this.member = mem;
        this.firstLogin = true;
        this.email = userID + "@e.ntu.edu.sg";
        
    }
    
	/**
	 * get User's Name
	 * @return This User's Name
	 */
	public String getName() {
		return name;
	}
    
	/**
	 * get User's ID
	 * @return This User's ID
	 */
	public String getUserID() {
		return UserID;
	}

	/**
	 * get User's Password
	 * @return This User's Password
	 */
	public String getPw() {
		return pw;
	}

	/**
	 * set User's Password
	 * @param pw This User's Password
	 */
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	/**
	 * get User's Faculty
	 * @return This User's Faculty
	 */
	public String getFaculty() {
		return faculty;
	}

	/**
	 * get User's Email
	 * @return This User's Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * get User's Role whether they are a student or staff
	 * @return This User's Role
	 */
	public String getMember() {
		return member;
	}

	/**
	 * check whether it is user's first time logging into CAMs
	 * @return this User has login before
	 */
    public boolean isFirstLogin() {
        return firstLogin;
    }

    /**
     * set User's first login
     * @param firstLogin whether the user has log in before
     */
    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
	
	/**
	 * Return User that is in the user list
	 * @param userID This User's ID
	 * @param pw This User's password
	 * @param userList List of existing users
	 * @return user if user exist
	 */
	public static User validateUser(String userID, String pw, UserList userList) {
		ArrayList<User> users = userList.getUserList();
		for (User user : users) {
	        if (userID.equalsIgnoreCase(user.getUserID()) && pw.equals(user.getPw())) {
	            return user;
	        }
	    }
	    return null;
	}
	
	/**
	 * Change User's password
	 * @param sc User's input
	 * @param loginManager Login Manager
	 * @param userList List of existing users
	 */
	public void changePassword(Scanner sc, LoginManager loginManager, UserList userList) {
	    boolean validPassword = false;

	    while (!validPassword) {
	        System.out.print("Enter new password (at least 8 characters with at least 1 numeric digit): ");
	        String newPW = sc.next();

	        if (Validator.isValidPassword(newPW)) {
	            setPw(newPW);
	            System.out.println("Password changed successfully. Please log in again.\n");
	            validPassword = true;
	        } else {
	            System.out.println("Invalid password. Please make sure it's at least 8 characters long and contains at least 1 numeric digit.");
	        }
	    }

	    loginManager.userLogin(userList);
	}
    
	/**
	 * list of camps that the user has withdrawn from
	 */
    private List<String> withdrawnCampNames = new ArrayList<>();
    
    /**
     * get the list of camps that the user has withdrawn from
     * @return list of camps that This User has withdrawn from
     */
    public List<String> getWithdrawnCampNames() {
        return withdrawnCampNames;
    }
    
    /**
     *  add camp into the list of camps that the user has withdrawn from
     * @param campName Name of camp that User has withdrawn from
     */
    public void addWithdrawnCampName(String campName) {
        withdrawnCampNames.add(campName);
    }
    
}
