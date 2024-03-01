package grp4cams;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
Represents the attendees in a camp
@author Jasmine Goh
@version 1.0
@since 2023-11-26
*/

public class Validator {
	
	/**
	 * constructor for the Validator class
	 */
    public Validator() {}
	
	/**
	 * check if password is at least 8 characters long and contain at least 1 numeric digit
	 * @param newpw new password
	 * @return true new password is valid
	 */
    public static boolean isValidPassword(String newpw) {
        return newpw.length() >= 8 && newpw.matches(".*\\d.*");
    }
	
    /**
     * format date 
     */
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	/**
	 * check if date is correct format
	 * @param scanner user's input
	 * @param prompt date prompt
	 * @return valid date
	 */
    public static LocalDate getValidDateInput(Scanner scanner, String prompt) {
        LocalDate date = null;
        do {
            System.out.println(prompt);
            String dateString = scanner.nextLine();
            try {
                date = LocalDate.parse(dateString, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format dd-mm-yyyy.");
            }
        } while (true);
        return date;
    }
    
    /**
     * check if camp name is unique
     * @param campName new camp name
     * @param campList existing camp
     * @return true camp name is unique
     */
    public static boolean isUniqueName(String campName, List<Camp> campList) {
        for (Camp existingCamp : campList) {
            if (existingCamp.getcInfo().getCampName().equalsIgnoreCase(campName)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * check if camp's user group is current CAMs user faculty or NTU
     * @param campName camp Name
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     * @return true camp is found
     */
    public static boolean FacultyFound(String campName, List<Camp> campList, User currentUser) {
        for (Camp camp : campList) {
            if (camp.getcInfo().getCampName().equalsIgnoreCase(campName) && camp.isVisible() == true) {
                String userGroup = camp.getcInfo().getUserGroup();
                return userGroup.equalsIgnoreCase(currentUser.getFaculty()) || userGroup.equalsIgnoreCase("NTU");
            }
        }
        return false;
    }
    /**
     * check if user has already signed up for the camp
     * @param campName camp Name
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     * @return true user has signed up for this camp 
     */
    public static boolean hasSignedUp(String campName, List<Camp> campList, User currentUser) {
    	for (Camp camp : campList) {
    		if (camp.getcInfo().getCampName().equalsIgnoreCase(campName)) {
		    	for (Attendee attendee : camp.getAttendeesList()) {
		            if (attendee.getUser().equals(currentUser)) {
		                return true; 
		            }
		        }
    		}
    	}
		return false;
    }
    
    /**
     * check if the camp is full
     * @param campName camp Name
     * @param campList list of existing camps
     * @return true camp is full
     */
    public static boolean isFull(String campName, List<Camp> campList) {
        int cSlot = 0;
    	for (Camp camp : campList) {
            if (camp.getcInfo().getCampName().equalsIgnoreCase(campName)) {
            	cSlot = camp.getcInfo().getCurrentSlot();
            	break;
            }
    	}
    	return (cSlot <= 0);
    }
       
    /**
     * check if camp registration date is over
     * @param campName camp Name
     * @param campList list of existing camps
     * @return true if camp registration date is over
     */
    public static boolean isOver(String campName, List<Camp> campList) {
    	LocalDate rcDate = null;
    	for (Camp camp : campList) {
            if (camp.getcInfo().getCampName().equalsIgnoreCase(campName)) {
            	rcDate = camp.getcInfo().getRcDate();
            	break;
            }
    	}
    	
    	if(rcDate != null && rcDate.isBefore(LocalDate.now())){
    		return true;
    	} else {
    		return false;
    	}		
    }
    
    /**
     * check if date of camp clash with user's existing camps
     * @param campName camp Name
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     * @return true if camp clash with user's existing camps
     */
    public static boolean dateClash(String campName, List<Camp> campList, User currentUser) {
    	LocalDate regSdate = null;
    	LocalDate regEdate = null;

    	for (Camp camp : campList) {
    		if (camp.getcInfo().getCampName().equalsIgnoreCase(campName)) {
    			regSdate = camp.getcInfo().getStartDate();
    			regEdate = camp.getcInfo().getEndDate();
    		}
    	}
    	
        if (regSdate == null || regEdate == null) {
            return false;
        }

    	for (Camp camp : campList) {
    		for (Attendee attendee : camp.getAttendeesList()) {
    			if (attendee.getUser().equals(currentUser)) {
    				if (regSdate.isBefore(camp.getcInfo().getEndDate()) && regEdate.isAfter(camp.getcInfo().getStartDate()) ||
    						regSdate.equals(camp.getcInfo().getEndDate()) || regEdate.equals(camp.getcInfo().getStartDate())) {
    					return true; // clash detected
    				}
    			}
    		}

    	}
    	return false;
    }
    
    /**
     * check if the camp committee is full
     * @param rCamp camp to register
     * @return true camp committee is full
     */
    public static boolean isFullCC(Camp rCamp) {
		return (rCamp.getcInfo().getCurrentCCSlot() <= 0);
    }
 
    /**
     * check if user is already camp committee for another camp
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     * @return true if user is already camp committee for another camp
     */
    public static boolean alrdyCC(List<Camp> campList, User currentUser) {
    	for (Camp camp : campList) {
	    	for (Attendee attendee : camp.getAttendeesList()) {
	            if (attendee.getUser().equals(currentUser)) {
	                if (attendee.getRole().equalsIgnoreCase("Committee")) {
	                	return true;
	                }
	            }
	        }
    	}
		return false;
    }
    
    /**
     * check if user has already registered for this camp
     * @param camp camp Name
     * @param currentUser current user of CAMs
     * @return true if user has already registered for this camp
     */
    public static boolean isRegistered(Camp camp, User currentUser) {
        for (Attendee attendee : camp.getAttendeesList()) {
            if (attendee.getUser().equals(currentUser)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * check if user is a camp committee for this camp
     * @param campName camp Name
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     * @return true if user is a camp committee for this camp
     */
    public static boolean isaCC(String campName, List<Camp> campList, User currentUser) {
    	for (Camp camp : campList) {
    		if (camp.getcInfo().getCampName().equalsIgnoreCase(campName)) {
    	    	for (Attendee attendee : camp.getAttendeesList()) {
    	            if (attendee.getUser().equals(currentUser)) {
    	                if (attendee.getRole().equalsIgnoreCase("Committee")) {
    	                	return true;
    	                }
    	            }
    	        }
	        }
    	}
		return false;
    }
   
    /**
     * check if camp exists for user faculty and NTU-
     * @param campName camp Name
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     * @return true if camp exists for user faculty and NTU
     */
    public static boolean existingCamp(String campName, List<Camp> campList, User currentUser) {
        for (Camp camp : campList) {
            if (camp.getcInfo().getCampName().equalsIgnoreCase(campName)) {
                String userGroup = camp.getcInfo().getUserGroup();
                return userGroup.equalsIgnoreCase(currentUser.getFaculty()) || userGroup.equalsIgnoreCase("NTU");
            }
        }
        return false;
    }
    
    /**
     * find camp by name
    * @param campName camp Name
     * @param campList list of existing camps
     * @return true if camp is found
     */
    public static Camp campFinderByName(String campName, List<Camp> campList) {
        for (Camp camp : campList) {
            if (camp.getcInfo().getCampName().equalsIgnoreCase(campName)) {
                return camp;
            }
        }
        return null; // Camp not found
    }
    
    /**
     * check if is current user is a committee member
     * @param attendeeList list of attendees for all camp
     * @param currentUser currentUser current user of CAMs
     * @return true if current user is a committee member
     */
    public static boolean isCommitee(List<Attendee> attendeeList, User currentUser) {
    	for (Attendee attendee : attendeeList) {
    		// if is attendee
            if (attendee.getUser().getUserID().equalsIgnoreCase(currentUser.getUserID())) {
            	// if is committee
                if (attendee.getRole().equalsIgnoreCase("COMMITEE")) {
                	return true;
                }
            }
        }
        return false; 
    }
    
    /**
     * check if current user is a staff
     * @param currentUser current user of CAMs
     * @return true if current user is a staff
     */
    public static boolean isStaff(User currentUser) {
    	if(currentUser.getMember().equalsIgnoreCase("staff")) {return true;}
        return false; 
    }
}
