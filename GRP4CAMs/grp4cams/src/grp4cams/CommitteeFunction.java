package grp4cams;

import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains the different actions a committee member can do
 * @author Chia Jia En
 * @version 1.0
 * @since 2023-11-26
 *
 */

public class CommitteeFunction {
	
	/**
	 * MAIN function of the Committee Members
	 * @param sc Current scanner
	 * @param campList List of all existing camps
	 * @param currentUser User that called the function
	 */
	public static void cMain(Scanner sc, List<Camp> campList, User currentUser) {
		
		Camp cCamp = null;
		Committee commMember = null;
		for (Camp camp : campList) {
			for (Committee committee : camp.getCommitteeList()) {
				if (committee.getUser().equals(currentUser)) {
					cCamp = camp;
					commMember = committee;
				}
			}
		}
		
		System.out.printf(("\n--- %s CAMP COMMITTEE ---\n"), cCamp.getcInfo().getCampName());
		cCamp.printStudCampDetails();
		
		int coption;

        do {
            Menu.commMenu();
            System.out.print("Enter option: ");
            coption = sc.nextInt();

            switch (coption) {
                case 1:
                    // manage suggestions
                	manageSuggestions(sc, cCamp, commMember);
                    break;

                case 2:
                    // manage enquires
                	commEnquiry(sc, cCamp, commMember);
                    break;

                case 3:
                    // generate list of registered students
                	System.out.println("Select Filter:");
                    System.out.println("1. View All Participants");
                    System.out.println("2. View Attendees Only");
                    System.out.println("3. View Committee Members Only");
                    
                    coption = sc.nextInt();
                    switch (coption) {
                    	case 1:
                    		generateRSListCommittee(sc, cCamp, commMember.getUser(), 1);
                    		break;
                    	case 2:
                    		generateRSListCommittee(sc, cCamp, commMember.getUser(), 2);
                    		break;
                    	case 3:
                    		generateRSListCommittee(sc, cCamp, commMember.getUser(), 3);
                    		break;
                    	default:
                    		System.out.println("No such choice.");
                    		break;
                    }
                    break;

                case 4:
                    // return to student menu
                    break;
                    
                default:
                    System.out.print("Invalid option!");
                    break;
            }
        } while (coption != 4);
		
		
	}
	
	/**
	 * Function to help member manage their suggestions
	 * @param sc Current scanner
	 * @param cCamp Current camp that committee member is committee for
	 * @param commMember Current member calling the function
	 */
	public static void manageSuggestions(Scanner sc, Camp cCamp, Committee commMember) {
		 List<Suggestions> suggestions = cCamp.getSuggestionsList();
	     Map<Integer, Suggestions> suggestionMap = new HashMap<>();
	     int i = 0;
	     int option;
	     String s = null;
	     // print all suggestions
	     System.out.println("\n----- Your Suggestions -----");
	     
	     // if empty, print to show
	     if (suggestions.isEmpty()) {
	            System.out.println("No suggestions available for this camp.");
	     }
	     // else, print all suggestions
	     else {
	    	 for (Suggestions suggestion : suggestions) {
				if (suggestion.getStudent().equals(commMember.getUser())) {
					suggestionMap.put(i, suggestion);
			        System.out.println("Index: " + i);
			        System.out.println("Camp: " + cCamp.getcInfo().getCampName());
			        System.out.println("Suggestion: " + suggestion.getSuggestionString());
			        System.out.println("Status: " + (suggestion.isApproved()));
			        System.out.println("----------------------");
			        i++;
			    }
			 }System.out.println("End of Enquiries.");
	    }
	     
	    // print menu
	     do {
	    	 Menu.suggestionMenuCommittee();
	    	 System.out.print("Enter option: ");
	    	 option = sc.nextInt();
	    	 switch(option) {
	    	 case 1: // edit suggestion
	    		 editSuggestion(sc, cCamp, commMember, suggestionMap);
	    		 break;
		        
	    	 case 2: // delete suggestion
	    		 deleteSuggestion(sc, cCamp, commMember, suggestionMap);
	    		 break;
				
	    	 case 3: // add suggestion
	    		 addSuggestion(sc, cCamp, commMember);
	    		 break;
	    	 case 4: // return to student menu
	    		 break;
	    	 default:
					System.out.println("Invalid Selection");
					break;
	    	 }
	     }while(option != 1 && option != 2 && option != 3 && option != 4);
	}
	
	/**
	 * To generate the list of registered students 
	 * @param sc Current scanner
	 * @param cCamp Current camp that committee member is committee for
	 * @param commMember Current member calling the function
	 * @param filter How the member wants to filter the details
	 */
	 public static void generateRSListCommittee(Scanner sc, Camp cCamp, User commMember, int filter){
	    	// check if camp exists 
	    	List<Attendee> registeredStudents = null; 
	    	if (filter == 1) {registeredStudents = cCamp.getRegisteredStudents();}
	    	else if (filter == 2) {registeredStudents = cCamp.getAttendees();}
	    	else if (filter == 3) {registeredStudents = cCamp.getCommitteeMembers();}
	    			
	    	String fileName = "CampReport_Committee_" + cCamp.getcInfo().getCampName() + ".txt";
	    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
	            // boolean fileExists = new File(fileName).exists();
	    		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        	
	    		writer.write("\nCamp: " + cCamp.getcInfo().getCampName() + "\n");
	    		writer.write("Start Date: " + cCamp.getcInfo().getStartDate().format(dateFormatter) + "\n");
	    		writer.write("End Date: " + cCamp.getcInfo().getEndDate().format(dateFormatter)+ "\n");
	    		writer.write("Registration Closing Date: " + cCamp.getcInfo().getRcDate().format(dateFormatter)+ "\n");
	    		writer.write("Location: " + cCamp.getcInfo().getLocation()+ "\n");
	    		writer.write("Current Available Camp Slot: " + cCamp.getcInfo().getCurrentSlot()+ "\n");
	    		writer.write("Current Available Camp Committee Slots: " + cCamp.getcInfo().getCurrentCCSlot()+ "\n");
	    		writer.write("Description: " + cCamp.getcInfo().getDesc()+ "\n");
	    		writer.write("Staff In Charge: " + cCamp.getcInfo().getsIC()+ "\n");
	            writer.write("Name\tRole\n");
	            
	    	if (!registeredStudents.isEmpty()) {
	    		System.out.println("\nGenerating List For " + cCamp + "...");
	    	    for (Attendee attendee : registeredStudents) {
	    	    	writer.write(attendee.getUser().getName() + "\t" + attendee.getRole() + "\n");
	    	    }
	    	    System.out.println("TXT File Generated: " + fileName);
	    	    return;
	    	}
	    	
	    	else System.out.println("There are no such students registered for " + cCamp + " yet.");
	    	return;
	    	
	    	} 
	    	catch (IOException e) {
	            System.out.println("Error Writing To File: " + e.getMessage());
	            return;
	    	}
	 }
	 
	 /**
	  * Enquiry function for the committee members
	  * @param sc Current scanner
	  * @param cCamp Current camp that committee member is committee for
	  * @param currentUser Current member calling the function
	  */
	 public static void commEnquiry(Scanner sc, Camp cCamp, Committee currentUser) {
	    	sc.nextLine();
	    	int coption = 0;
	    	String s = null;
	    	
	    	Map<Integer, Enquiry> enquiriesList = new HashMap<>();
	    	
	    	enquiriesList = cCamp.showCommEnquiry(enquiriesList, cCamp);

	    	// if no enquiries, print no enquiries
	    	if (enquiriesList.size() == 0) {System.out.println("There are no enquiries.");}
	    	else{System.out.println("End of Enquiries.");}
	       
	    	// print enquiry menu 
	        do {
	        	Menu.enquiryMenuStaffComm();
	        	System.out.print("Enter option: ");
	        	coption = sc.nextInt();
	        	
	        	switch(coption) {
	        	case 1:
	        		// reply to enquiry
	        		System.out.println("Select Enquiry to Reply To: ");
	        		coption = sc.nextInt();
	        		
	        		// while incorrect enquiry
	        		while(!enquiriesList.containsKey(coption)) {
	        			System.out.println("Select a Valid Enquiry to Reply To: ");
	        			coption = sc.nextInt();
	        		}
	        		// get reply and set processed
	        		System.out.println("Enter Reply: ");
	        		sc.nextLine();
	        		s = sc.nextLine();
	        		enquiriesList.get(coption).processEnquiryComm(s);
	        		currentUser.addPoints("compEnquiries");
	        		break;
	        	case 2:
	        		// exit to menu
	        		break;
	        	} 
	        	if (coption != 2) {enquiriesList = cCamp.showCommEnquiry(enquiriesList, cCamp);}
	        } while (coption != 2);
	    }
	 
	 /**
	  * Adding a suggestion to change a camp's details
	  * @param sc Current scanner
	  * @param cCamp Current camp that committee member is committee for
	  * @param commMember Current member calling the function
	  */
	 public static void addSuggestion(Scanner sc, Camp cCamp, Committee commMember) {
		 int option; 
		 final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		 System.out.println("\n--- Add Suggestion For Camp: " + cCamp.getcInfo().getCampName() + " ---");
		 
		 String suggestionString = null;
		 List<String> suggestionList = new ArrayList<>();
		 Suggestions newSuggestion;
		 
		 cCamp.printAllCampDetails();
         DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

         Menu.editCampMenu();
         System.out.print("Enter your choice: ");
         option = sc.nextInt();
         sc.nextLine();
         switch(option) {
             case 1:
             	System.out.printf("\nCurrent Camp Start Date: %s\n", cCamp.getcInfo().getStartDate().format(dateFormatter));
                 LocalDate newStartDate;
                 do {
                     newStartDate = Validator.getValidDateInput(sc, "Enter new Camp Start Date (dd-mm-yyyy): ");
                     if (newStartDate.isBefore(cCamp.getcInfo().getRcDate())) {
                         System.out.println("Invalid date. Start date must be after the Registration Closing Date!");
                     } else if (newStartDate.isAfter(cCamp.getcInfo().getEndDate())) {
                         System.out.println("Invalid date. Start Date must be before the End Date!");
                     } else {
                    	 suggestionString = "Change start date to " + newStartDate;
                    	 suggestionList.add("1");
                    	 String strDate = newStartDate.format(CUSTOM_FORMATTER);
                    	 suggestionList.add(strDate);
                    	 newSuggestion = new Suggestions(commMember.getUser(), suggestionString, suggestionList);
                		 cCamp.addSuggestion(newSuggestion);
                		 commMember.addPoints("subSuggestion");
                		 break;
                     }
                 } while (newStartDate.isBefore(cCamp.getcInfo().getRcDate()) || newStartDate.isAfter(cCamp.getcInfo().getEndDate()));
                 break;
             
             case 2:
             	System.out.printf("\nCurrent Camp End Date: %s\n", cCamp.getcInfo().getEndDate().format(dateFormatter));
             	LocalDate newEndDate;
             	do {
             	    newEndDate = Validator.getValidDateInput(sc, "Enter new Camp End Date (dd-mm-yyyy): ");
             	    if (newEndDate.isBefore(cCamp.getcInfo().getStartDate())) {
             	        System.out.println("Invalid date. End Date must be after or on the Start Date!");
             	    } else {
             	    	suggestionString = "Change start date to: " + newEndDate + ".";
             	    	suggestionList.add("2");
             	    	String strDate = newEndDate.format(CUSTOM_FORMATTER);
             	    	suggestionList.add(strDate);
             	    	newSuggestion = new Suggestions(commMember.getUser(), suggestionString, suggestionList);
             	    	cCamp.addSuggestion(newSuggestion);
             	    	commMember.addPoints("subSuggestion");
             	    	break;
             	    }
             	} while (newEndDate.isBefore(cCamp.getcInfo().getStartDate()));
             	break;
             
             case 3:
             	System.out.printf("\nCurrent Registration Closing Date: %s\n", cCamp.getcInfo().getRcDate().format(dateFormatter));
             	LocalDate newRcDate;
             	do {
             	    newRcDate = Validator.getValidDateInput(sc, "Enter new Registration Closing Date (dd-mm-yyyy): ");
             	    
             	    if (newRcDate.isAfter(cCamp.getcInfo().getStartDate())) {
             	        System.out.println("Invalid date. Registration Closing Date must be before the Start Date!");
             	    } else if (newRcDate.isBefore(LocalDate.now())){
             	    	System.out.println("Invalid date. Registration Date cannot be before Today's Date!");
             	    } else {
             	    	suggestionString = "Change registration closing date to: " + newRcDate + ".";
             	    	suggestionList.add("3");
             	    	String strDate = newRcDate.format(CUSTOM_FORMATTER);
             	    	suggestionList.add(strDate);
             	    	newSuggestion = new Suggestions(commMember.getUser(), suggestionString, suggestionList);
             	    	cCamp.addSuggestion(newSuggestion);
             	    	commMember.addPoints("subSuggestion");
             	    	break;
             	    }
             	} while (newRcDate.isAfter(cCamp.getcInfo().getStartDate()));
             	break;
             	
             case 4:
             	System.out.printf("\nCurrent User Group: %s\n", cCamp.getcInfo().getUserGroup());
         	    int choice;
         	    do {
         	    	suggestionList.add("4");
         	    	System.out.println("Update User Group:");
         	    	System.out.println("Open to -> (1) Own school or (2) Whole NTU: ");
         	        choice = sc.nextInt();
         	        if (choice == 1) {
         	        	suggestionString = "Update User Group to: Own School.";
         	        } else if (choice == 2) {
         	        	suggestionString = "Update User Group to: Whole NTU.";
         	        } else {
         	            System.out.println("Invalid choice. Please enter 1 or 2!");
         	        }
         	    } while (choice != 1 && choice != 2);
         	    suggestionList.add(Integer.toString(choice));
    	    	newSuggestion = new Suggestions(commMember.getUser(), suggestionString, suggestionList);
    	    	cCamp.addSuggestion(newSuggestion);
    	    	commMember.addPoints("subSuggestion");
                break;
            
             case 5:
             	System.out.printf("\nCurrent Camp Location: %s\n", cCamp.getcInfo().getLocation());
                System.out.println("Enter new Camp Location: ");
                String newLocation = sc.next().toUpperCase();
                suggestionString = "Change camp location to: " + newLocation + ".";
     	    	suggestionList.add("5");
     	    	suggestionList.add(newLocation);
     	    	newSuggestion = new Suggestions(commMember.getUser(), suggestionString, suggestionList);
     	    	cCamp.addSuggestion(newSuggestion);
     	    	commMember.addPoints("subSuggestion");
                break;
                 
             case 6:
             	int oldTslot = cCamp.getcInfo().getTotalSlot();
             	int cSlot = cCamp.getcInfo().getCurrentSlot();
             	int signUps = oldTslot - cSlot;
             	System.out.printf("\nCurrent Total Camp Slots: %d\n", oldTslot);
             	System.out.printf("Num of Camp Sign Ups: %d\n", signUps);
         		int newTslot = 0;
         		do {
         			System.out.println("Enter new Total Slot for Camp: ");
         		    newTslot = sc.nextInt();

         		    if (newTslot <= 0) {
         		    	System.out.println("Invalid input. Total Camp slot must be above 0!");
         		    } else if (newTslot < cCamp.getcInfo().getCcSlot()) {
         		    	System.out.println("Invalid input. Total Camp Slots must be above Total Camp Committee slot!");
         		    } else if (newTslot < signUps) {
         		    	System.out.println("Invalid input. Students have already started signing up hence Total Camp Slot cannot be less than total signups!");
         		    }
         		    
         		} while (newTslot <= 0 || newTslot < cCamp.getcInfo().getCcSlot() || newTslot < signUps);
                suggestionString = "Change new Total Slot For Camp to: " + newTslot + ".";
      	    	suggestionList.add("6");
      	    	suggestionList.add(Integer.toString(newTslot));
      	    	newSuggestion = new Suggestions(commMember.getUser(), suggestionString, suggestionList);
      	    	cCamp.addSuggestion(newSuggestion);
      	    	commMember.addPoints("subSuggestion");
                break;
                 
             case 7:
             	int oldCCslot = cCamp.getcInfo().getCcSlot();
             	int cCCSlot = cCamp.getcInfo().getCurrentCCSlot();
             	int ccSignUps = oldCCslot - cCCSlot;
             	System.out.printf("\nCurrent Total Camp Committee Slots: %d\n", oldCCslot);
             	System.out.printf("Num of Committee Sign Ups: %d\n", ccSignUps);
             	int newCCslot = 0;
                 do {
                     System.out.println("Enter new Total Camp Committee Slots: ");
                     newCCslot = sc.nextInt();
                     
                     if (newCCslot > cCamp.getcInfo().getTotalSlot()) {
                     	System.out.println("Invalid input. Total Camp Committee Slots must be below Total Camp slot!");
                     } else if (newCCslot < 0) {
         		        System.out.println("Invalid input. Total Camp Committee Slots cannot be below 0!");
         		    } else if (newCCslot > 10) {
         		        System.out.println("Invalid input. Total Camp Committee Slots cannot be above 10!");	
         		    } else if (newCCslot < ccSignUps) {
         		    	System.out.println("Invalid input. Students have already started signing up hence Total Committee Slots cannot be less than total signups!");
         		    }
                     
                 } while (newCCslot > cCamp.getcInfo().getTotalSlot() || newCCslot < 0 || newCCslot > 10);
                suggestionString = "Change new Total Camp Committee Slot to: " + newCCslot + ".";
       	    	suggestionList.add("7");
       	    	suggestionList.add(Integer.toString(newCCslot));
       	    	newSuggestion = new Suggestions(commMember.getUser(), suggestionString, suggestionList);
       	    	cCamp.addSuggestion(newSuggestion);
       	    	commMember.addPoints("subSuggestion");
                 break;
                 
             case 8:
            	 System.out.printf("\nCurrent Camp Description: %s\n", cCamp.getcInfo().getDesc());
            	 System.out.println("Enter new Camp Description: ");
            	 String newCampDesc = sc.nextLine();
            	 suggestionString = "Change Camp Description to: " + newCampDesc + ".";
            	 suggestionList.add("8");
            	 suggestionList.add(newCampDesc);
            	 newSuggestion = new Suggestions(commMember.getUser(), suggestionString, suggestionList);
            	 cCamp.addSuggestion(newSuggestion);
            	 commMember.addPoints("subSuggestion");
            	 break;

             default:
                 System.out.println("Invalid option.");
         }
         	System.out.println("");
     }
	 
	 /**
	  * Delete a submitted, but not processed, suggestion
	  * @param sc Current scanner
	  * @param cCamp Current camp that committee member is committee for
	  * @param commMember Current member calling the function
	  * @param suggestionMap A list that holds all suggestions the member has made
	  */
	 public static void deleteSuggestion(Scanner sc, Camp cCamp, Committee commMember, Map<Integer, Suggestions> suggestionMap) {
		// check if user has any enquiries 
		 int suggestionIndex; 
		 Suggestions selectedSuggestion;
		 if (suggestionMap.size() == 0) {
			System.out.println("You have no suggestions to delete.");
			return;
		 }
			
		do {
			System.out.print("Enter the index of the suggestion you want to delete: ");
			suggestionIndex = sc.nextInt();
	    } while (!suggestionMap.containsKey(suggestionIndex));

		selectedSuggestion = suggestionMap.get(suggestionIndex);
		    
		if (selectedSuggestion.isApproved() == "Rejected" || selectedSuggestion.isApproved() == "Approved") { 
			System.out.println("Your enquiry has been processed. It cannot be edited.");
			return;
		}
		    
		// delete
		cCamp.deleteSuggestion(selectedSuggestion);
		commMember.removePoints();
		System.out.println("Suggestion has been deleted successfully.");
		return;
	 }
	 
	 /**
	  * Edit a submitted, but not processed, suggestion
	  * @param sc Current scanner
	  * @param cCamp Current camp that committee member is committee for
	  * @param commMember Current member calling the function
	  * @param suggestionMap A list that holds all suggestions the member has made
	  */
	 public static void editSuggestion(Scanner sc, Camp cCamp, Committee commMember, Map<Integer, Suggestions> suggestionMap) {
		 int suggestionIndex; 
		 Suggestions selectedSuggestion;
		 // if no suggestions, cannot choose
		 if (suggestionMap.size() == 0) {
			System.out.println("You have no suggestions to edit.");
			return;
		 }
				
		do {
			System.out.print("Enter the index of the suggestion you want to edit: ");
			suggestionIndex = sc.nextInt();
			} while(!suggestionMap.containsKey(suggestionIndex));
				
			selectedSuggestion = suggestionMap.get(suggestionIndex);
				
			// if the suggestion has already been approved or rejected, cannot change
			if (selectedSuggestion.isApproved() == "Rejected" || selectedSuggestion.isApproved() == "Approved") { 
				System.out.println("Your enquiry has been processed. It cannot be edited.");
				return;
			}
			
			System.out.println("\n----- Selected Suggestion -----");
	        System.out.println("Camp: " + cCamp.getcInfo().getCampName());
	        System.out.println("Suggestion: " + selectedSuggestion.getSuggestionString());
	        System.out.println("Status: " + (selectedSuggestion.isApproved()));
	        System.out.println("----------------------");
	            
	        cCamp.deleteSuggestion(selectedSuggestion);
	        commMember.removePoints();
	        addSuggestion(sc,cCamp,commMember);
	      
	        // selectedSuggestion.editSuggestions(s);
	        return;
	 }
}


