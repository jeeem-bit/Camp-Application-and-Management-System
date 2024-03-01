package grp4cams;

/**
All the menus that appears in CAMs
@author Jasmine Goh
@version 1.0
@since 2023-11-26
*/

public class Menu {
	
	/**
	 * constructor for the Menu class
	 */
    public Menu() {}
	
    /**
     * print staff menu
     */
	public static void staffMenu() {
		System.out.println("\n----- STAFF -----");
		System.out.println("1. Create Camp");
		System.out.println("2. View All Camps");
		System.out.println("3. View My Camps");
		System.out.println("4. Change Password");
		System.out.println("5. Logout");
	}
		
	/**
	 * print list of things staff can do to their camp
	 */
	public static void campMenu() {
		System.out.println("1. Edit Camp");
		System.out.println("2. Delete Camp");
		System.out.println("3. Toggle Visibility of Camp");
		System.out.println("4. View Suggestions");
		System.out.println("5. Generate List Of Registered Students");
		System.out.println("6. Generate Camp Commmitte Performance Report");
		System.out.println("7. View Enquiries");
		System.out.println("8. Back to Staff Menu");
	}
	
	/**
	 * print suggestion menu
	 */
	public static void suggestionMenuStaff() {
		System.out.println("-  Manage Suggestions -");
		System.out.println("1. Accept Suggestion");
		System.out.println("2. Reject Suggestion");
        System.out.println("3. Back to Main Menu");
	}
	
	/**
	 * print list of camp information that can be edited
	 */
	public static void editCampMenu() {
        System.out.println("\n1. Start Date");
        System.out.println("2. End Date");
        System.out.println("3. Registration Closing Date");
        System.out.println("4. User Group");
        System.out.println("5. Location");
        System.out.println("6. Total Slots for Camp");
        System.out.println("7. Camp Committee Slots");
        System.out.println("8. Description");
        System.out.println("0. Finish Editing");
	}
	
	/**
	 * print student menu
	 */
	public static void studentMenu() {
		System.out.println("\n----- STUDENT -----");
		System.out.println("1. View all Faculty and NTU Camps");
		System.out.println("2. View Registered Camp");
		System.out.println("3. Register for Camp");
		System.out.println("4. Enquire about Camp");
		System.out.println("5. Withdraw from Camp");
		System.out.println("6. Committee Menu");
		System.out.println("7. Change Password");
		System.out.println("8. Logout");
	}
	
	/**
	 * print list of things committee member can do to the camp
	 */
	public static void commMenu() {
		System.out.println("\n----- Committee Function -----");
		System.out.println("1. Manage Suggestions");
		System.out.println("2. Manage Enquires");
		System.out.println("3. Generate List Of Registered Students");
		System.out.println("4. Return to Student Menu");
	}
	
	/**
	 * print enquires menu for student
	 */
	public static void enquiryMenuStudent() {
		System.out.println("\n------ Enquiries ------");
		System.out.println("1. Edit An Enquiry");
		System.out.println("2. Delete An Enquiry");
		System.out.println("3. Add An Enquiry");
		System.out.println("4. Return to Student Menu");
	}
	
	/**
	 * print enquires menu for staff
	 */
	public static void enquiryMenuStaffComm() {
		System.out.println("\n------ Enquiries ------");
		System.out.println("1. Reply An Enquiry");
		System.out.println("2. Return to Camp Menu");
	}
	
	/**
	 * print suggestions menu for committee member
	 */
	public static void suggestionMenuCommittee() {
		System.out.println("\n------ Suggestions ------");
		System.out.println("1. Edit A Suggestion");
		System.out.println("2. Delete A Suggestion");
		System.out.println("3. Add A Suggestion");
		System.out.println("4. Return to Student Menu");
	}
	
	/**
	 * print list of ways camps can be sorted/filtered by
	 */
	public static void viewMenu() {
		System.out.println("\n------ Filter View ------");
		System.out.println("1. Sort By Alphabet");
		System.out.println("2. Sort By Start Date");
		System.out.println("3. Sort By End Date");
		System.out.println("4. Filter By Location");
		System.out.println("5. Filter By Start Month");
		System.out.println("6. Filter By End Month");
		System.out.println("7. Close Filter View");
	}
	
	/**
	 * print suggestion menu for staff
	 */
	public static void suggestionManager() {
		System.out.println("\n------ Suggestion Manager ------");
		System.out.println("1. Approve A Suggestion");
		System.out.println("2. Reject A Suggestion");
		System.out.println("3. Return to Camp Menu");
	}
}
