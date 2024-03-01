package grp4cams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
Main page of CAMs
@author Jasmine Goh
@version 1.0
@since 2023-11-26
*/

public class CAMs {
	
	/**
	 * constructor for the CAMs class
	 */
    public CAMs() {}
	
	/**
	 * Main Application CAMs
	 * @param args MAIN APP
	 */
	public static void main(String[] args) {
		
		UserList userList = new UserList();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to Camp Application and Management System (CAMs)");
		
		User currentUser = null;
		LoginManager loginManager = new LoginManager();
		
		List<Camp> campList = new ArrayList<>();
	    Camp newCamp = Camp.createCamp("TEST1", LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 2), LocalDate.of(2023, 11, 30), "NTU", "NTU", 3, 2, "TEST 1", "Arvind");
	    campList.add(newCamp);
	    newCamp = Camp.createCamp("TEST2", LocalDate.of(2024, 3, 2), LocalDate.of(2024, 3, 3), LocalDate.of(2023, 11, 29), "NBS", "HALL", 4, 2, "TEST 2", "Arvind");
	    campList.add(newCamp);
	    newCamp = Camp.createCamp("TEST3", LocalDate.of(2023, 11, 27), LocalDate.of(2023, 11, 27), LocalDate.of(2023, 11, 25), "NBS", "NTU", 2, 1, "TEST 2", "Arvind");
	    campList.add(newCamp);
	    newCamp = Camp.createCamp("TEST4", LocalDate.of(2025, 11, 26), LocalDate.of(2025, 11, 27), LocalDate.of(2025, 11, 25), "SCSE", "HALL 11", 2, 1, "DEMO NOT NBS", "Datta");
	    campList.add(newCamp);
	    newCamp = Camp.createCamp("TEST5", LocalDate.of(2025, 3, 2), LocalDate.of(2025, 3, 3), LocalDate.of(2024, 11, 29), "NTU", "HALL 7", 4, 2, "DEMO DELETE", "Arvind");
	    campList.add(newCamp);
	    newCamp = Camp.createCamp("TEST6", LocalDate.of(2025, 3, 3), LocalDate.of(2025, 3, 4), LocalDate.of(2024, 10, 29), "NTU", "HALL 7", 4, 2, "DEMO TOGGLE", "Arvind");
	    campList.add(newCamp);
		
		do {
			
		currentUser = loginManager.userLogin(userList);
        
			if(currentUser != null) {
				
				if (currentUser.getMember().equalsIgnoreCase("staff")) {
					//STAFF
					UserFunction.staffFunction(sc, campList, currentUser, loginManager, userList);
					
				} else if (currentUser.getMember().equalsIgnoreCase("student")) {
					// STUDENT
					UserFunction.studentFunction(sc, campList, currentUser, loginManager, userList);
				}
			}
			
			System.out.print("Log in again? (Y/N): ");
			String con = sc.next();
			
			if (!con.equalsIgnoreCase("Y")) {
				System.out.println("Exiting CAMs. Goodbye!");
				break;
			} else {
				System.out.println();
			}
			
		} while (true);
		
		sc.close();
	}
}