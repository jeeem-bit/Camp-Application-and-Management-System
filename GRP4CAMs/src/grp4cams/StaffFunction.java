package grp4cams;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
Contains the different actions a staff can do
@author Jasmine Goh
@version 1.0
@since 2023-11-26
*/

public class StaffFunction {
	
	/**
	 * constructor for the StaffFunction class
	 */
    public StaffFunction() {}
	
	
	/**
	 * View all camps created by current user (staff)
	 * @param campList list of existing camps
	 * @param currentUser current user of CAMs
	 * @return false if user did not create any camps
	 */
    public static boolean viewMyCamps(List<Camp> campList, User currentUser) {
        System.out.print("\n----- My Camps -----\n");
        boolean foundCamps = false;
        
        Collections.sort(campList, Comparator.comparing(camp -> camp.getcInfo().getCampName().toLowerCase()));
        
        for (Camp camp : campList) {
            if (camp.getcInfo().getsIC().equals(currentUser.getName())) {
                camp.printAllCampDetails();
                foundCamps = true;
            }
        }
        
        if (!foundCamps) {
            System.out.println("You have not created any camps!");
        }
        
        System.out.println("\n----------------------");
		return foundCamps;
    }
       
    /**
     * create new camp
     * @param sc user's input
     * @param currentUser current user of CAMs
     * @param campList list of existing camps
     * @return return the new camp created by user
     */
    public static Camp createNewCamp(Scanner sc, User currentUser, List<Camp> campList) {
		System.out.println("\n----- Create Camp -----");

		String campName;
		boolean isUnique;
		do {
			System.out.println("Enter Camp Name: ");
			campName = sc.next();
			sc.nextLine();
			isUnique = Validator.isUniqueName(campName, campList);
			if (!isUnique) {
				System.out.println("Camp name must be unique. Please choose a different name.");
			}
		} while (!isUnique);
		
	    LocalDate startDate;
	    do {
	        startDate = Validator.getValidDateInput(sc, "Enter Camp Start Date (dd-mm-yyyy): ");
	        if (startDate == null || startDate.isBefore(LocalDate.now())) {
	            System.out.println("Invalid date. Start Date cannot be before Today's Date!");
	        }
	    } while (startDate == null || startDate.isBefore(LocalDate.now()));

	    LocalDate endDate;
	    do {
	        endDate = Validator.getValidDateInput(sc, "Enter Camp End Date (dd-mm-yyyy): ");
	        if (endDate == null || endDate.isBefore(startDate)) {
	            System.out.println("Invalid date. End Date must be after or on the Start Date!");
	        }
	    } while (endDate == null || endDate.isBefore(startDate));

	    LocalDate rcDate;
	    do {
	        rcDate = Validator.getValidDateInput(sc, "Enter Registration Closing Date (dd-mm-yyyy): ");
	        if (rcDate == null || rcDate.isAfter(startDate)) {
	            System.out.println("Invalid date. Registration Closing Date must be before the Start date!");
	        } else if (rcDate.isBefore(LocalDate.now())){
	        	System.out.println("Invalid date. Registration Closing Date cannot be before Today's Date!");
	        }
	    } while (rcDate == null || rcDate.isAfter(startDate) || rcDate.isBefore(LocalDate.now()));
        
	    String userGroup = null;
	    int choice;
	    do {
	    	System.out.println("Open to -> (1) Own school or (2) Whole NTU: ");
	        choice = sc.nextInt();
	        if (choice == 1) {
	            userGroup = currentUser.getFaculty();
	        } else if (choice == 2) {
	            userGroup = "NTU";
	        } else {
	            System.out.println("Invalid choice. Please enter 1 or 2!");
	        }
	    } while (choice != 1 && choice != 2);
		
		System.out.println("Enter Camp Location: ");
		String location = sc.next();
		sc.nextLine();
		
		int totalSlot = 0;
		do {
			System.out.println("Enter Total Slot for Camp: ");
		    totalSlot = sc.nextInt();

		    if(totalSlot <= 0) {
		    	System.out.println("Invalid input. Total Camp slot must be above 0!");
		    }
		    
		} while (totalSlot <= 0 );
		
		int ccSlot = 0;
		do {
		    System.out.println("Enter Total Camp Committee Slots: ");
		    ccSlot = sc.nextInt();

		    if(ccSlot > totalSlot) {
		    	System.out.println("Invalid input. Total Camp Committee Slots must be below Total Camp slot!");
			} else if (ccSlot < 0) {
		        System.out.println("Invalid input. Total Camp Committee Slots cannot be below 0!");
		    } else if (ccSlot > 10) {
		        System.out.println("Invalid input. Total Camp Committee Slots cannot be above 10!");	
		    }
		    
		} while (ccSlot > totalSlot || ccSlot < 0 || ccSlot > 10);
		sc.nextLine();
		
		System.out.println("Enter Camp Description: ");
		String desc = sc.nextLine();
		
		Camp newCamp = Camp.createCamp(campName.toUpperCase(), startDate, endDate, rcDate, userGroup, location.toUpperCase(), totalSlot, ccSlot, desc, currentUser.getName());
		campList.add(newCamp);
		System.out.println("Successfully Created!");
		return newCamp;
    }

   /**
     * list of things staff can do to the camp they created
     * @param sc user's input
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
    */
    public static void manageCampOptions(Scanner sc, List<Camp> campList, User currentUser) {
        int coption;

        do {
            Menu.campMenu();
            System.out.print("Enter option: ");
            coption = sc.nextInt();

            switch (coption) {
                case 1:
                    // edit camp
                    editCamp(sc, campList, currentUser);
                    break;

                case 2:
                    // delete camp
                    deleteCamp(sc, campList, currentUser);
                    break;

                case 3:
                    // toggle visibility of camp
                    toggleCampVisibility(sc, campList, currentUser);
                    break;

                case 4:
                    // view suggestions
                	staffSuggestions(sc, campList, currentUser);
                    break;

                case 5:
                    // view list Of registered students
                	System.out.println("Select Filter:");
                    System.out.println("1. View All Participants");
                    System.out.println("2. View Attendees Only");
                    System.out.println("3. View Committee Members Only");
                    
                    coption = sc.nextInt();
                    switch (coption) {
                    	case 1:
                    		generateRSList(sc, campList, currentUser, 1);
                    		break;
                    	case 2:
                    		generateRSList(sc, campList, currentUser, 2);
                    		break;
                    	case 3:
                    		generateRSList(sc, campList, currentUser, 3);
                    		break;
                    	default:
                    		System.out.println("No such choice.");
                    		break;
                    }
                    break;
                    
                case 6:
                	// generate committee member performance report
                	generateCPR(sc, campList, currentUser);
                	break;

                case 7:
                    // view and reply enquiries
                	staffEnquiry(sc,campList,currentUser);
                    break; 
                case 8:
                	// Exit the loop
                	break;
                default:
                    System.out.print("Invalid option!");
                    break;
            }
            if (coption != 8) {
            	viewMyCamps(campList, currentUser);
            }
        } while (coption != 8);
    }

    /**
     * edit camp
     * @param sc user's input
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     */
    public static void editCamp(Scanner sc, List<Camp> campList, User currentUser) {
        System.out.print("Enter the name of the camp to edit: ");
        String campToEdit = sc.next();
        sc.nextLine();
        
        boolean campFound = false;
        for (Camp camp : campList) {
            if (camp.getcInfo().getCampName().equalsIgnoreCase(campToEdit) && camp.getcInfo().getsIC().equals(currentUser.getName())) {
               
            	System.out.println("\n----- Editing Camp -----");
                camp.printAllCampDetails();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                
                boolean editing = true;
                while (editing) {
                	System.out.printf("\n- Edit Camp %s -", campToEdit.toUpperCase());
                	Menu.editCampMenu();
                	System.out.print("Enter your choice: ");
                    int editOption = sc.nextInt();
                    sc.nextLine();
                    
                    switch(editOption) {
                
                    case 1:
                    	System.out.printf("\nCurrent Camp Start Date: %s\n", camp.getcInfo().getStartDate().format(dateFormatter));
                        LocalDate newStartDate;
                        do {
                            newStartDate = Validator.getValidDateInput(sc, "Enter new Camp Start Date (dd-mm-yyyy): ");
                            if (newStartDate.isBefore(camp.getcInfo().getRcDate())) {
                                System.out.println("Invalid date. Start date must be after the Registration Closing Date!");
                            } else if (newStartDate.isAfter(camp.getcInfo().getEndDate())) {
                                System.out.println("Invalid date. Start Date must be before the End Date!");
                            } else {
                            	camp.getcInfo().setStartDate(newStartDate);
                            }
                        } while (newStartDate.isBefore(camp.getcInfo().getRcDate()) || newStartDate.isAfter(camp.getcInfo().getEndDate()));
                        break;
                    
                    case 2:
                    	System.out.printf("\nCurrent Camp End Date: %s\n", camp.getcInfo().getEndDate().format(dateFormatter));
                    	LocalDate newEndDate;
                    	do {
                    	    newEndDate = Validator.getValidDateInput(sc, "Enter new Camp End Date (dd-mm-yyyy): ");
                    	    if (newEndDate.isBefore(camp.getcInfo().getStartDate())) {
                    	        System.out.println("Invalid date. End Date must be after or on the Start Date!");
                    	    } else {
                    	        camp.getcInfo().setEndDate(newEndDate);
                    	    }
                    	} while (newEndDate.isBefore(camp.getcInfo().getStartDate()));
                    	break;
                    
                    case 3:
                    	System.out.printf("\nCurrent Registration Closing Date: %s\n", camp.getcInfo().getRcDate().format(dateFormatter));
                    	LocalDate newRcDate;
                    	do {
                    	    newRcDate = Validator.getValidDateInput(sc, "Enter new Registration Closing Date (dd-mm-yyyy): ");
                    	    
                    	    if (newRcDate.isAfter(camp.getcInfo().getStartDate())) {
                    	        System.out.println("Invalid date. Registration Closing Date must be before the Start Date!");
                    	    } else if (newRcDate.isBefore(LocalDate.now())){
                    	    	System.out.println("Invalid date. Registration Date cannot be before Today's Date!");
                    	    } else {
                    	        camp.getcInfo().setRcDate(newRcDate);
                    	        break;
                    	    }
                    	} while (newRcDate.isAfter(camp.getcInfo().getStartDate()));
                    	break;
                    	
                    case 4:
                    	System.out.printf("\nCurrent User Group: %s\n", camp.getcInfo().getUserGroup());
                	    int choice;
                	    do {
                	    	System.out.println("Update User Group:");
                	    	System.out.println("Open to -> (1) Own school or (2) Whole NTU: ");
                	        choice = sc.nextInt();
                	        if (choice == 1) {
                	        	camp.getcInfo().setUserGroup(currentUser.getFaculty());
                	        } else if (choice == 2) {
                	        	camp.getcInfo().setUserGroup("NTU");
                	        } else {
                	            System.out.println("Invalid choice. Please enter 1 or 2!");
                	        }
                	    } while (choice != 1 && choice != 2);
                        break;
                   
                    case 5:
                    	System.out.printf("\nCurrent Camp Location: %s\n", camp.getcInfo().getLocation());
                        System.out.println("Enter new Camp Location: ");
                        camp.getcInfo().setLocation(sc.next().toUpperCase());
                        sc.nextLine();
                        break;
                        
                    case 6:
                    	int oldTslot = camp.getcInfo().getTotalSlot();
                    	int cSlot = camp.getcInfo().getCurrentSlot();
                    	int signUps = oldTslot - cSlot;
                    	System.out.printf("\nCurrent Total Camp Slots: %d\n", oldTslot);
                    	System.out.printf("Num of Camp Sign Ups: %d\n", signUps);
                		int newTslot = 0;
                		do {
                			System.out.println("Enter new Total Slot for Camp: ");
                		    newTslot = sc.nextInt();

                		    if (newTslot <= 0) {
                		    	System.out.println("Invalid input. Total Camp slot must be above 0!");
                		    } else if (newTslot < camp.getcInfo().getCcSlot()) {
                		    	System.out.println("Invalid input. Total Camp Slots must be above Total Camp Committee slot!");
                		    } else if (newTslot < signUps) {
                		    	System.out.println("Invalid input. Students have already started signing up hence Total Camp Slot cannot be less than total signups!");
                		    }
                		    
                		} while (newTslot <= 0 || newTslot < camp.getcInfo().getCcSlot() || newTslot < signUps);
                        camp.getcInfo().setTotalSlot(newTslot);
                        camp.getcInfo().setCurrentSlot(newTslot-signUps);
                        break;
                        
                    case 7:
                    	int oldCCslot = camp.getcInfo().getCcSlot();
                    	int cCCSlot = camp.getcInfo().getCurrentCCSlot();
                    	int ccSignUps = oldCCslot - cCCSlot;
                    	System.out.printf("\nCurrent Total Camp Committee Slots: %d\n", oldCCslot);
                    	System.out.printf("Num of Committee Sign Ups: %d\n", ccSignUps);
                    	int newCCslot = 0;
                        do {
                            System.out.println("Enter new Total Camp Committee Slots: ");
                            newCCslot = sc.nextInt();
                            
                            if (newCCslot > camp.getcInfo().getTotalSlot()) {
                            	System.out.println("Invalid input. Total Camp Committee Slots must be below Total Camp slot!");
                            } else if (newCCslot < 0) {
                		        System.out.println("Invalid input. Total Camp Committee Slots cannot be below 0!");
                		    } else if (newCCslot > 10) {
                		        System.out.println("Invalid input. Total Camp Committee Slots cannot be above 10!");	
                		    } else if (newCCslot < ccSignUps) {
                		    	System.out.println("Invalid input. Students have already started signing up hence Total Committee Slots cannot be less than total signups!");
                		    }
                            
                        } while (newCCslot > camp.getcInfo().getTotalSlot() || newCCslot < 0 || newCCslot > 10);
                        camp.getcInfo().setCcSlot(newCCslot);
                        camp.getcInfo().setCurrentCCSlot(newCCslot-ccSignUps);
                        sc.nextLine();
                        break;
                        
                    case 8:
                    	System.out.printf("\nCurrent Camp Description: %s\n", camp.getcInfo().getDesc());
                        System.out.println("Enter new Camp Description: ");
                        camp.getcInfo().setDesc(sc.nextLine());
                        break;

                    case 0:
                        editing = false;
                        break;

                    default:
                        System.out.println("Invalid option. Please enter a valid option.");
                    }
                    System.out.println("");
                }                    
                System.out.println("Camp successfully updated!");
                campFound = true;
                break;
            }
         }
        
         if (!campFound) {
            System.out.println("Camp not found or You don't have permission to edit this camp!");
        }
    }   
    
    /**
     * delete camp
     * @param sc user's input
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     */
    public static void deleteCamp(Scanner sc, List<Camp> campList, User currentUser) {
        System.out.println("Enter the name of the camp to delete: ");
        String campName = sc.next();

        Camp campToDelete = null;
        for (Camp camp : campList) {
            if (camp.getcInfo().getCampName().equalsIgnoreCase(campName) && camp.getcInfo().getsIC().equalsIgnoreCase(currentUser.getName())) {
                campToDelete = camp;
                break;
            }
        }

        if (campToDelete != null) {
            campList.remove(campToDelete);
            System.out.printf("Camp '%s' deleted successfully!\n", campName);
        } else {
        	System.out.printf("Camp '%s' not found or you don't have permission to delete it!\n", campName);
        }
    }

    /**
     * toggle visibility of camp
     * @param sc user's input
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     */
    public static void toggleCampVisibility(Scanner sc, List<Camp> campList, User currentUser) {
        System.out.println("Enter the name of the camp to toggle visibility: ");
        String campName = sc.next();
        String visibility;

        for (Camp camp : campList) {
            if (camp.getcInfo().getCampName().equalsIgnoreCase(campName) && camp.getcInfo().getsIC().equalsIgnoreCase(currentUser.getName())) {
                camp.toggleVisibility();
                if (camp.isVisible() == true) {
                	visibility = "Visible to Students";
                } else {
                	visibility = "Not visible to Students";
                }
                System.out.printf("Visibility of camp '%s' toggled successfully. New visibility: %s\n", campName, visibility);
                return;
            }
        }
        System.out.printf("Camp '%s' not found or you don't have permission to toggle its visibility!\n", campName);
    }
    
    /**
     * generate registered student list
     * @param sc user's input
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     * @param filter choice of students, attendee and committee
     */
    public static void generateRSList(Scanner sc, List<Camp> campList, User currentUser, int filter){
    	System.out.println("Enter the name of camp to view student list: ");
    	String campName = sc.next();

    	// check if camp exists 
    	if (Validator.existingCamp(campName, campList, currentUser) == false) {
    		System.out.printf("Camp '%s' not found or you don't have permission to access!\n", campName);
    		return;
    	}
    	
    	Camp camp = Validator.campFinderByName(campName,campList);
    	
    	List<Attendee> registeredStudents = null; 
    	if (filter == 1) {registeredStudents = camp.getRegisteredStudents();}
    	else if (filter == 2) {registeredStudents = camp.getAttendees();}
    	else if (filter == 3) {registeredStudents = camp.getCommitteeMembers();}
    			
    	String fileName = "CampReport_" + camp.getcInfo().getCampName() + ".txt";
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            // boolean fileExists = new File(fileName).exists();
    		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        	
    		writer.write("\nCamp: " + camp.getcInfo().getCampName() + "\n");
    		writer.write("Start Date: " + camp.getcInfo().getStartDate().format(dateFormatter) + "\n");
    		writer.write("End Date: " + camp.getcInfo().getEndDate().format(dateFormatter) + "\n");
    		writer.write("Registration Closing Date: " + camp.getcInfo().getRcDate().format(dateFormatter) + "\n");
    		writer.write("Location: " + camp.getcInfo().getLocation() + "\n");
    		writer.write("Current Available Camp Slot: " + camp.getcInfo().getCurrentSlot() + "\n");
    		writer.write("Current Available Camp Committee Slots: " + camp.getcInfo().getCurrentCCSlot() + "\n");
    		writer.write("Description: " + camp.getcInfo().getDesc() + "\n");
    		writer.write("Staff In Charge: " + camp.getcInfo().getsIC() + "\n");
            writer.write("Name\tRole\n");
            
    	if (!registeredStudents.isEmpty()) {
    		System.out.println("\nGenerating List For " + campName + "...");
    	    for (Attendee attendee : registeredStudents) {
    	    	writer.write(attendee.getUser().getName() + "\t" + attendee.getRole() + "\n");
    	    }
    	    System.out.println("TXT File Generated: " + fileName);
    	    return;
    	}
    	
    	else System.out.println("There are no such students registered for " + campName + " yet.");
    	return;
    	
    	} 
    	catch (IOException e) {
            System.out.println("Error Writing To File: " + e.getMessage());
            return;
    	}
    }
    
    /**
     * Staff enquiries page
     * @param sc user's input
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     */
    public static void staffEnquiry(Scanner sc, List<Camp> campList, User currentUser) {
    	sc.nextLine();
    	int coption = 0;
    	String s = null;
    	System.out.print("Select camp to view enquiries from: ");
    	String campName = sc.nextLine();
    	
    	Camp camp = Validator.campFinderByName(campName, campList);
    	Map<Integer, Enquiry> enquiriesList = new HashMap<>();
    	
    	// if camp does not exist
    	if (camp == null) {
    		System.out.println("No such camp exists.");
    		return;
    	}
    	// if user is not IC of camp
    	if (!camp.getcInfo().getsIC().equals(currentUser.getName())) {
    		System.out.println("You do not have access to this camp.");
    		return;
    	}
    	
    	enquiriesList = camp.showStaffEnquiry(enquiriesList, campName);

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
        		enquiriesList.get(coption).processEnquiryStaff(s);
        		break;
        	case 2:
        		// exit to menu
        		break;
        	} 
        	if (coption != 2) {enquiriesList = camp.showStaffEnquiry(enquiriesList, campName);}
        } while (coption != 2);
    }

    /**
     * generate Committee Performance Report
     * @param sc user's input
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     */
    public static void generateCPR(Scanner sc, List<Camp> campList, User currentUser) {
    	String campName = sc.nextLine();
    	System.out.println("Enter the name of camp to generate Committee Performance Report: ");
    	campName = sc.nextLine();
    	
    	// check if camp exists 
    	if (Validator.existingCamp(campName, campList, currentUser) == false) {
    		System.out.printf("Camp '%s' not found or you don't have permission to access!\n", campName);
    		return;
    	}
    	
    	Camp camp = Validator.campFinderByName(campName,campList);
    	
    	// if user is not IC of camp
    	if (!camp.getcInfo().getsIC().equals(currentUser.getName())) {
    		System.out.println("You do not have access to this camp.");
    		return;
    	}
    	
    	List<Committee> commList = camp.getCommitteeList();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("CPR_" + campName))) {
            writer.write("Committee Performance Report\n\n");
            writer.write(String.format("%-20s%-10s%-30s%-30s%-30s\n", "Name", "Points", "Submitted Suggestions", "Approved Suggestions", "Completed Enquiries"));
            
            for (Committee committee : commList) {
                writer.write(String.format("%-20s%-10d%-30d%-30d%-30d\n",
                        committee.getUser().getName(),
                        committee.getPoints(),
                        committee.getSubmittedSuggestions(),
                        committee.getApprovedSuggestions(),
                        committee.getCompletedEnquiries()));
            }

            System.out.println("Committee Performance Report generated successfully: " + "CPR_" + campName);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    /**
     * Staff suggestions page
     * @param sc user's input
     * @param campList list of existing camps
     * @param currentUser current user of CAMs
     */
    public static void staffSuggestions(Scanner sc, List<Camp> campList, User currentUser) {
    	sc.nextLine();
    	int coption = 0;
    	String s = null;
    	System.out.print("Select camp to view suggestions from: ");
    	String campName = sc.nextLine();
    	
    	Camp camp = Validator.campFinderByName(campName, campList);
    	Map<Integer, Suggestions> suggestionsList = new HashMap<>();
    	
    	// if camp does not exist
    	if (camp == null) {
    		System.out.println("No such camp exists.");
    		return;
    	}
    	// if user is not IC of camp
    	if (!camp.getcInfo().getsIC().equals(currentUser.getName())) {
    		System.out.println("You do not have access to this camp.");
    		return;
    	}
    	
    	suggestionsList = camp.showStaffSuggestions(suggestionsList, campName);

    	// if no enquiries, print no enquiries
    	if (suggestionsList.size() == 0) {System.out.println("There are no suggestions.");}
    	else{System.out.println("End of Suggestions.");}
    	
    	do {
        	Menu.suggestionManager();
        	System.out.print("Enter option: ");
        	coption = sc.nextInt();
        	
        	switch(coption) {
        	case 1: // approve
        		if (suggestionsList.size() == 0) {
        			System.out.println("There are no suggestions.");
        			break;
        		}
        	
        		System.out.println("Select Suggestion to Approve: ");
        		coption = sc.nextInt();
        		
        		// while incorrect suggestion
        		while(!suggestionsList.containsKey(coption)) {
        			System.out.println("Select a Valid Suggestion to Approve: ");
        			coption = sc.nextInt();
        		}
        		
        		// if suggestion has already been processed, break
        		if (suggestionsList.get(coption).isApproved() == "Approved" || suggestionsList.get(coption).isApproved() == "Rejected") {
        			System.out.println("This suggestion has been processed.");
        			break;
        		};
        		
        		// approve and edit
        		suggestionsList.get(coption).approveSuggestion();
        		suggestionApproval(sc, camp, currentUser, suggestionsList.get(coption));
        		break;
        	case 2: // reject
        		if (suggestionsList.size() == 0) {
        			System.out.println("There are no suggestions.");
        			break;
        		}
        		System.out.println("Select Suggestion to Reject: ");
        		coption = sc.nextInt();
        		
        		// while incorrect enquiry
        		while(!suggestionsList.containsKey(coption)) {
        			System.out.println("Select a Valid Suggestion to Reject: ");
        			coption = sc.nextInt();
        		}
        		
        		// if suggestion has already been processed, break
        		if (suggestionsList.get(coption).isApproved() != "Pending") {
        			System.out.println("This suggestion has been processed.");
        			break;
        		}
        		
        		suggestionsList.get(coption).rejectSuggestion();
        		break;
        	case 3: // quit
        		break;
        	}
        	if (coption != 3) {suggestionsList = camp.showStaffSuggestions(suggestionsList, campName);}
        } while (coption != 3);
    }
    
    /**
     * Staff suggestion approval page
     * @param sc user's input
     * @param camp name of camp to provide suggestions
     * @param currentUser current user of CAMs
     * @param suggestion suggestions
     * @return true if suggestions have been approved
     */
    public static boolean suggestionApproval(Scanner sc, Camp camp, User currentUser, Suggestions suggestion) {
    	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        boolean editted = false;
        List<String> suggestionString = suggestion.getSuggestions();
        int option = Integer.parseInt(suggestionString.get(0));
               
        System.out.println("\n----- Editing Camp -----");
        System.out.println(suggestion.getSuggestionString());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        switch(option) {
        	case 1:
        		System.out.printf("\nCurrent Camp Start Date: %s\n", camp.getcInfo().getStartDate().format(dateFormatter));
                LocalDate newStartDate = LocalDate.parse(suggestionString.get(1), formatter);
                camp.getcInfo().setStartDate(newStartDate);
                editted = true;
                break;
                    
            case 2:
            	System.out.printf("\nCurrent Camp End Date: %s\n", camp.getcInfo().getEndDate().format(dateFormatter));
                LocalDate newEndDate = LocalDate.parse(suggestionString.get(1), formatter);
                camp.getcInfo().setEndDate(newEndDate);
                editted = true;
                break;
             
            case 3:
            	System.out.printf("\nCurrent Registration Closing Date: %s\n", camp.getcInfo().getRcDate().format(dateFormatter));
            	LocalDate newRcDate = LocalDate.parse(suggestionString.get(1), formatter);
            	camp.getcInfo().setRcDate(newRcDate);
            	editted = true;
            	break;
                    	
            case 4:
            	System.out.printf("\nCurrent User Group: %s\n", camp.getcInfo().getUserGroup());
            	int choice = Integer.parseInt(suggestionString.get(1));
            	if (choice == 1) {
            		camp.getcInfo().setUserGroup(currentUser.getFaculty());
            	}
            	else {
            	camp.getcInfo().setUserGroup("NTU");
            	}
                break;

            case 5:
            	System.out.printf("\nCurrent Camp Location: %s\n", camp.getcInfo().getLocation());
                camp.getcInfo().setLocation(suggestionString.get(1).toUpperCase());
                break;
            
            case 6:
            	int oldTslot = camp.getcInfo().getTotalSlot();
            	int cSlot = camp.getcInfo().getCurrentSlot();
            	int signUps = oldTslot - cSlot;
            	System.out.printf("\nCurrent Total Camp Slots: %d\n", oldTslot);
            	System.out.printf("Num of Camp Sign Ups: %d\n", signUps);
            	int newTslot = Integer.parseInt(suggestionString.get(1));
                camp.getcInfo().setTotalSlot(newTslot);
                camp.getcInfo().setCurrentSlot(newTslot-signUps);
                break;
                        
            case 7:
            	int oldCCslot = camp.getcInfo().getCcSlot();
            	int cCCSlot = camp.getcInfo().getCurrentCCSlot();
            	int ccSignUps = oldCCslot - cCCSlot;
                int newCCslot = Integer.parseInt(suggestionString.get(1));
                camp.getcInfo().setCcSlot(newCCslot);
                camp.getcInfo().setCurrentCCSlot(newCCslot-ccSignUps);
                break;
                        
            case 8:
            	System.out.printf("\nCurrent Camp Description: %s\n", camp.getcInfo().getDesc());
            	camp.getcInfo().setDesc(suggestionString.get(1));
                break;

            case 0:
            	break;
            default:
            	System.out.println("Invalid action.");
        }                  
        if (editted) {
        	System.out.println("Camp successfully updated!");   
        	suggestion.getStudent(); 
        	for(Committee committee: camp.getCommitteeList()){
        		if(committee.getUser().getName() == suggestion.getStudent().getName()) {
        			committee.addPoints("apprvSuggestion");
        		}
        	}
        }
        return editted;
    }   
}
