package grp4cams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
Contains the different actions a user of CAMs can do
@author Jasmine Goh
@version 1.0
@since 2023-11-26
*/

public class UserFunction {

	/**
	 * constructor for the UserFunction class
	 */
    public UserFunction() {}
	
    /**
     * Things that staff can do
     * @param sc user's input
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     * @param loginManager login/logout
     * @param userList list of existing users
     */
	public static void staffFunction(Scanner sc, List<Camp> campList, User currentUser, LoginManager loginManager, UserList userList) {
		int option;

		do {
			Menu.staffMenu();
			System.out.print("Enter option: ");
			option = sc.nextInt();

			switch (option) {
			case 1:
				// create camp
				Camp newCamp = StaffFunction.createNewCamp(sc, currentUser, campList);
				newCamp.printAllCampDetails();
				break;

			case 2:
				// view all camps
				Camp.sortedViewAllCampList(sc, campList, currentUser);
				break;

			case 3:
				// view my camps
				boolean foundCamps = StaffFunction.viewMyCamps(campList, currentUser);

				if (foundCamps) {
					StaffFunction.manageCampOptions(sc, campList, currentUser);
				}
				System.out.println("");
				break;

			case 4:
				// change password
				currentUser.changePassword(sc, loginManager, userList);
				break;

			case 5:
				// logout
				currentUser = null;
				System.out.println("\nLogged out successfully.");
				break;

			default:
				System.out.println("Invalid option!");
				break;
			}

		} while (option != 5);
	}

    /**
     * Things that students can do
     * @param sc user's input
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     * @param loginManager login/logout
     * @param userList list of existing users
     */
	public static void studentFunction (Scanner sc, List<Camp> campList, User currentUser, LoginManager loginManager, UserList userList){
		int option;

		do {
			Menu.studentMenu();
			System.out.print("Enter option: ");
			option = sc.nextInt();

			switch (option) {
			case 1:
				// view all faculty and NTU camps
				Camp.sortedViewAllCampList(sc, campList, currentUser);
				break;

			case 2:
				// view registered camp
				StudentFunction.viewRegCamps(campList,currentUser);
				break;

			case 3:
				// register for camp
				StudentFunction.regCamp(sc, campList, currentUser);
				break;

			case 4:
				// enquire about camp
				StudentFunction.enquiries(sc, campList, currentUser);
				break;

			case 5:
				// withdraw from camp
				StudentFunction.withdrawFromCamp(sc, campList, currentUser);
				break;
				
			case 6:
				// committee functions
				if(!Validator.alrdyCC(campList, currentUser)) {
					System.out.println("\nYou are not a Committee Member for any camp!");
				} else {
					CommitteeFunction.cMain(sc, campList, currentUser);
				}
				break;
				
			case 7:
				// change password
				currentUser.changePassword(sc, loginManager, userList);
				break;
				
			case 8:	
				// logout
				currentUser = null;
				System.out.println("\nLogged out successfully.");
				break;

			default:
				System.out.println("Invalid option!");
				break;
			}

		} while (option != 8);
	}
}

