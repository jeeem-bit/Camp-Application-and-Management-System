package grp4cams;

import java.util.Scanner;

/**
Manage User Login into CAMs
@author Jasmine Goh
@version 1.0
@since 2023-11-25
*/


public class LoginManager {
	
	/**
	 * constructor for the LoginManager class
	 */
    public LoginManager() {}
	
	/**
	 * To Manage User's Login into CAMs
	 * @param userList List of existing users
	 * @return current user of CAMs
	 */
    public User userLogin(UserList userList) {
    	
    	System.out.println("----- Login -----\n");
        Scanner sc = new Scanner(System.in);
        String userID;
        String pw;
        User currentUser = null;


        do {
            System.out.print("Enter UserID: ");
            userID = sc.next();
            System.out.print("Enter Password: ");
            pw = sc.next();
            
            currentUser = User.validateUser(userID, pw, userList);

            if (currentUser != null && currentUser.isFirstLogin()) {
                System.out.println("\nLogging in for the first time -> please change your password");
                System.out.print("Enter new password (at least 8 characters with at least 1 numeric digit): ");
                String newPW = sc.next();

                while (!Validator.isValidPassword(newPW)) {
                    System.out.println("Invalid password. Please make sure it's at least 8 characters long and contains at least 1 numeric digit.");
                    System.out.print("Enter new password: ");
                    newPW = sc.next();
                }

                currentUser.setPw(newPW);
                currentUser.setFirstLogin(false);
                System.out.println("Password changed successfully -> please log in again\n");
                currentUser = null; // to initiate re-login
            } else if (currentUser != null) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Incorrect userID or password -> please try again\n");
            }
        } while (currentUser == null);


        return currentUser;
    }
}
