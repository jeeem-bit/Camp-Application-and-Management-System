package grp4cams;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
The Camps in CAMs
@author Jasmine Goh
@version 1.0
@since 2023-11-26
*/
public class Camp {
	/**
	 * current camp information
	 */
    private CampInfo cInfo;
    /**
     * list of students attending this camp
     */
    private List<Attendee> attendeesList;
    /**
     * list of committee member in this camp
     */
    private List<Committee> committeeList;
    /**
     * list of suggestions for this camp
     */
    private List<Suggestions> suggestionsList;
    /**
     * list of enquires for this camp
     */
    private List<Enquiry> enquiriesList;
    /**
     * visibility of camp to students
     */
    private boolean isVisible;

    /**
     * All related data about Camp
     * @param campInfo detailed information about camp
     */
	public Camp(CampInfo campInfo) {
        this.cInfo = campInfo;
        this.attendeesList = new ArrayList<>();
        this.committeeList = new ArrayList<>();
        this.suggestionsList = new ArrayList<>();
        this.enquiriesList = new ArrayList<>();
        this.isVisible = true;
    }
	
	/**
	 * get Camp's Information
	 * @return This Camp's Information
	 */
	public CampInfo getcInfo() {
		return cInfo;
	}

	/**
	 * set Camp's Information
	 * @param cInfo New Camp's Information
	 */
	public void setcInfo(CampInfo cInfo) {
		this.cInfo = cInfo;
	}

	/**
	 * get list of attendee attending this camp
	 * @return This Camp's attendee list
	 */
    public List<Attendee> getAttendeesList() {
        return attendeesList;
    }
    
    /**
	 * get list of attendee's enquires regarding this camp
	 * @return This Camp's enquiring list
     */
    public List<Enquiry> getEnquiriesList(){
    	return enquiriesList;
    }
    
    /**
	 * get list of attendee's suggestions regarding this camp
	 * @return This Camp's suggestions list
     */
    public List<Suggestions> getSuggestionsList() {
        return suggestionsList;
    }
    
    /**
	 * get list of committee members in this camp
	 * @return This Camp's suggestions list
     */
    public List<Committee> getCommitteeList(){
    	return committeeList;
    }
    
    /**
     * add new committee member into committee list
     * @param committeeMember new committee member
     */
    public void addCommitteeMember(Committee committeeMember) {
    	committeeList.add(committeeMember);
    }
    
    /**
     * get list of students that registered for this camp
     * @return list of registered students in this camp
     */
    public List<Attendee> getRegisteredStudents() {
        List<Attendee> registeredStudents = new ArrayList<>();
        for (Attendee attendee : attendeesList) {
            if (attendee.getRole().equalsIgnoreCase("ATTENDEE") || attendee.getRole().equalsIgnoreCase("COMMITTEE")) {
                registeredStudents.add(attendee);
            }
        }
        return registeredStudents;
    }
    
    /**
     * get list of students that registered for this camp as Committee
     * @return list of committee in this camp
     */
    public List<Attendee> getCommitteeMembers() {
        List<Attendee> registeredStudents = new ArrayList<>();
        for (Attendee attendee : attendeesList) {
            if ( attendee.getRole().equalsIgnoreCase("COMMITTEE")) {
                registeredStudents.add(attendee);
            }
        }
        return registeredStudents;
    }
    
    /**
     * get list of students that registered for this camp as ATTENDEE
     * @return list of attendee in this camp
     */
    public List<Attendee> getAttendees() {
        List<Attendee> registeredStudents = new ArrayList<>();
        for (Attendee attendee : attendeesList) {
            if (attendee.getRole().equalsIgnoreCase("ATTENDEE")) {
                registeredStudents.add(attendee);
            }
        }
        return registeredStudents;
    }
    
    /**
     * add new attendee member into attendee list
     * @param user new attendee member
     */
    public void addtoAList(Attendee user) {
        attendeesList.add(user);
    }
    
    /**
     * add new enquire into attendee list
     * @param newEnquiry new enquire
     */
    public void addEnquiry(Enquiry newEnquiry) {
    	enquiriesList.add(newEnquiry);
    	System.out.println("Enquiry added!");
    }
    
    /**
     * delete enquire
     * @param enquiry enquire to delete
     */
    public void deleteEnquiry(Enquiry enquiry) {
    	if (enquiriesList.contains(enquiry)) {
            enquiriesList.remove(enquiry);
            System.out.println("Enquiry has been deleted successfully.");
        } else {
            System.out.println("Enquiry not found.");
        }
    }
	
    /**
     * Show enquires for this camp
     * @param enquiriesList This camp's enquire list
     * @param campName name of camp to give enquire
     * @return list of enquires for this camp
     */
    public Map<Integer, Enquiry> showStaffEnquiry(Map<Integer, Enquiry> enquiriesList, String campName) {
    	System.out.println("\n----- Enquiries in " + campName + "-----");
    	int i = 0;
    	for (Enquiry enquiry : this.getEnquiriesList()) {
    	enquiriesList.put(i, enquiry);
    	System.out.println("Index: " + i);
    	System.out.println("Camp: " + this.getcInfo().getCampName());
    	System.out.println("Enquiry: " + enquiry.getEnquiry());
    	System.out.println("Status: " + (enquiry.isProcessed() ? "Processed" : "Pending"));
    	System.out.println("Reply from Staff: " + enquiry.getReplyStaff());
		System.out.println("Reply from Commitee: " + enquiry.getReplyComm());
    	System.out.println("----------------------");
    	i++;
    	}
    	return enquiriesList;
    }
    
    /**
     * Show suggestions for this camp
     * @param suggestionsList This camp's suggestion list
     * @param campName name of camp to give suggestion
     * @return list of suggestions for this camp
     */
    public Map<Integer, Suggestions> showStaffSuggestions(Map<Integer, Suggestions> suggestionsList, String campName) {
    	System.out.println("\n----- Enquiries in " + campName + "-----");
    	int i = 0;
    	for (Suggestions suggestion : this.getSuggestionsList()) {
    	suggestionsList.put(i, suggestion);
    	System.out.println("Index: " + i);
    	System.out.println("Camp: " + this.getcInfo().getCampName());
    	System.out.println("Suggestion: " + suggestion.getSuggestionString());
    	System.out.println("Status: " + (suggestion.isApproved()));
    	System.out.println("----------------------");
    	i++;
    	}
    	return suggestionsList;
    }
    
    /**
     * Remove attendee from this camp
     * @param attendee attendee to remove
     */
    public void removeAttendee(Attendee attendee) {
        if (attendeesList != null) {
            attendeesList.remove(attendee);
        }
    }
	
    /**
     * return visibility of this camp
     * @return visibility of this camp
     */
	public boolean isVisible() {
		return isVisible;
	}
	
	/**
	 * toggle visibility
	 */
    public void toggleVisibility() {
        isVisible = !isVisible;
    }
 
    /**
     * Create new Camp 
     * @param campName new camp name
     * @param startDate new camp start date
     * @param endDate new camp end date
     * @param rcDate new camp registration end date
     * @param userGroup new camp user group
     * @param location new camp location
     * @param totalSlot new camp total slot
     * @param ccSlot new camp total camp slot
     * @param desc new camp description
     * @param sIC new camp staff in charge
     * @return new camp info
     */
	public static Camp createCamp(String campName, LocalDate startDate, LocalDate endDate, LocalDate rcDate, 
			String userGroup, String location, int totalSlot, int ccSlot, String desc, String sIC) {
        CampInfo campInfo = new CampInfo(campName, startDate, endDate, rcDate, userGroup, location, totalSlot, ccSlot, desc, sIC);
        return new Camp(campInfo);
    }
    
	/**
	 * print this camp details for staff
	 */
    public void printAllCampDetails() {
    	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	
        System.out.printf("\nCamp: %s\n", cInfo.getCampName());
        System.out.println("Start Date: " + cInfo.getStartDate().format(dateFormatter));
        System.out.println("End Date: " + cInfo.getEndDate().format(dateFormatter));
        System.out.println("Registration Closing Date: " + cInfo.getRcDate().format(dateFormatter));
        System.out.println("User Group: " + cInfo.getUserGroup());
        System.out.println("Location: " + cInfo.getLocation());
        System.out.println("Total Camp Slots: " + cInfo.getTotalSlot());
        System.out.println("Current Available Camp Slot: " + cInfo.getCurrentSlot());
        System.out.println("Total Camp Committee Slots: " + cInfo.getCcSlot());
        System.out.println("Current Available Camp Committee Slots: " + cInfo.getCurrentCCSlot());
        System.out.println("Description: " + cInfo.getDesc());
        System.out.println("Staff In Charge: " + cInfo.getsIC());
        
    }
    
    /**
     * print this camp details for student
     */
    public void printStudCampDetails() {
    	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	
        System.out.printf("\nCamp: %s\n", cInfo.getCampName());
        System.out.println("Start Date: " + cInfo.getStartDate().format(dateFormatter));
        System.out.println("End Date: " + cInfo.getEndDate().format(dateFormatter));
        System.out.println("Registration Closing Date: " + cInfo.getRcDate().format(dateFormatter));
        System.out.println("Location: " + cInfo.getLocation());
        System.out.println("Current Available Camp Slot: " + cInfo.getCurrentSlot());
        System.out.println("Current Available Camp Committee Slots: " + cInfo.getCurrentCCSlot());
        System.out.println("Description: " + cInfo.getDesc());
        System.out.println("Staff In Charge: " + cInfo.getsIC());
    }
  
    /**
     * view all camps 
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     */
    public static void viewAllCamps(List<Camp> campList, User currentUser) {
    	
        System.out.println("\n----- All Camps -----");
        if (campList.isEmpty()) {
        	System.out.println("No camps available!");
        } else {
        	// filter for students and staff
        	if (currentUser.getMember().equalsIgnoreCase("staff")) {
        		for (Camp camp : campList) {
	            camp.printAllCampDetails();
        		}
        	} else if (currentUser.getMember().equalsIgnoreCase("student")) {
        		for (Camp camp : campList) {
        			if(camp.isVisible == true) {
        				String UG = camp.cInfo.getUserGroup();
        				if(UG.equalsIgnoreCase(currentUser.getFaculty()) || UG.equalsIgnoreCase("NTU")) {
        					camp.printStudCampDetails();
        				}
        			}
        		}
        	}
        }
    }

    /**
     * view camps by various filters (alphabet, start date, end date, location)
     * @param sc user's input
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     */
    public static void sortedViewAllCampList(Scanner sc, List<Camp> campList, User currentUser) {
    	int option = 0;
    	
    	Collections.sort(campList, Comparator.comparing(camp -> camp.getcInfo().getCampName().toLowerCase()));
    	viewAllCamps(campList, currentUser);
    	List<Camp> tempCampList = new ArrayList<>();
    	
        do {
        	Menu.viewMenu();
        	System.out.print("Enter option: ");
			option = sc.nextInt();
			
			switch(option) {
			case 1: // filter by alphabet
				Collections.sort(campList, Comparator.comparing(camp -> camp.getcInfo().getCampName().toLowerCase()));
		    	viewAllCamps(campList, currentUser);
		    	break;
			case 2: // filter by start date
				Collections.sort(campList, Comparator.comparing(camp -> camp.getcInfo().getStartDate()));
		    	viewAllCamps(campList, currentUser);
				break;
			case 3: // filter by end date
				Collections.sort(campList, Comparator.comparing(camp -> camp.getcInfo().getEndDate()));
				viewAllCamps(campList, currentUser);
				break;
			case 4: // filter by locations
				System.out.println("Enter Location To Filter: ");
				sc.nextLine();
				String location = sc.nextLine();
	        	for (Camp camp : campList) {
	        		if(camp.getcInfo().getLocation().toLowerCase().equalsIgnoreCase(location.toLowerCase())) {
	        			tempCampList.add(camp);
	        		}
	        	}
				viewAllCamps(tempCampList, currentUser);
				break;
			case 5: // filter by start month
				System.out.println("Enter Start Month To Filter (e.g., January): ");
				sc.nextLine();
			    String startMonth = sc.nextLine();
			    tempCampList = campList.stream()
			            .filter(camp -> camp.getcInfo().getStartDate().getMonth().toString().equalsIgnoreCase(startMonth))
			            .collect(Collectors.toList());
			    Collections.sort(tempCampList, Comparator.comparing(camp -> camp.getcInfo().getCampName().toLowerCase()));
			    viewAllCamps(tempCampList, currentUser);
			    break; 
			case 6: // filter by end month
				System.out.println("Enter End Month To Filter (e.g., January): ");
				sc.nextLine();
			    String endMonth = sc.nextLine();
			    tempCampList = campList.stream()
			            .filter(camp -> camp.getcInfo().getEndDate().getMonth().toString().equalsIgnoreCase(endMonth))
			            .collect(Collectors.toList());
			    Collections.sort(tempCampList, Comparator.comparing(camp -> camp.getcInfo().getCampName().toLowerCase()));
			    viewAllCamps(tempCampList, currentUser);
				break;
			case 7: // return
				break; 
			default:
				System.out.println("Invalid Choice");
				break;
			} 
        }while(option!=7);
    }

    /**
     * deleted suggestion
     * @param selectedSuggestion suggestion to delete
     */
	public void deleteSuggestion(Suggestions selectedSuggestion) {
		if (suggestionsList.contains(selectedSuggestion)) {
			suggestionsList.remove(selectedSuggestion);
        } else {
            System.out.println("Suggestion not found.");
        }
	}

	/**
	 * add suggestion
	 * @param newSuggestion new suggestion to add
	 */
	public void addSuggestion(Suggestions newSuggestion) {
		suggestionsList.add(newSuggestion);
    	System.out.println("Suggestion added!");
	}

	/**
	 * Show enquire for this camp
	 * @param enquiriesList2 list of enquire
	 * @param cCamp this camp
	 * @return list of enquires for this camp
	 */
	public Map<Integer, Enquiry> showCommEnquiry(Map<Integer, Enquiry> enquiriesList2, Camp cCamp) {
		System.out.println("\n----- Enquiries in " + cCamp.getcInfo().getCampName() + "-----");
    	int i = 0;
    	for (Enquiry enquiry : this.getEnquiriesList()) {
    		enquiriesList2.put(i, enquiry);
    		System.out.println("Index: " + i);
    		System.out.println("Camp: " + this.getcInfo().getCampName());
    		System.out.println("Enquiry: " + enquiry.getEnquiry());
    		System.out.println("Status: " + (enquiry.isProcessed() ? "Processed" : "Pending"));
    		System.out.println("Reply from Staff: " + enquiry.getReplyStaff());
    		System.out.println("Reply from Commitee: " + enquiry.getReplyComm());
    		System.out.println("----------------------");
    		i++;
    	}
    	return enquiriesList2;
    }
}
