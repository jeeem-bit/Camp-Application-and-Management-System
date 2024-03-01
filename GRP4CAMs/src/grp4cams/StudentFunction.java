package grp4cams;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
Contains the different actions a student can do
@author Jasmine Goh
@version 1.0
@since 2023-11-26
*/

public class StudentFunction {
	
	/**
	 * constructor for the StudentFunction class
	 */
    public StudentFunction() {}

    /**
     * View all camps registered by current user (student)
	 * @param campList list of existing camps
	 * @param currentUser current user of CAMs
     * @return false if user did not register for any camp
     */
    public static boolean viewRegCamps(List<Camp> campList, User currentUser) {
        System.out.println("\n----- Camps Registered -----");
        boolean foundA = false;
        
        for (Camp camp : campList) {
        	for (Attendee attendee : camp.getAttendeesList()) {
        		if (attendee.getUser().equals(currentUser)) {
        			foundA = true;
        			camp.printStudCampDetails(); 
        			System.out.printf("Camp Role: %s\n", attendee.getRole()); 
        		}
        	}
        }

        if (!foundA) {
            System.out.println("\nYou have not registered for any camps!");
        } else {
        	System.out.println("\n----------------------");
        }

        return foundA;
    }
    
    /**
     * find camp in camp list by camp name
	 * @param campList list of existing camps
	 * @param campName name of camp to find
     * @return camp if found
     */
    public static Camp findCampByName(List<Camp> campList, String campName) {
        for (Camp camp : campList) {
            if (camp != null) { 
                CampInfo campInfo = camp.getcInfo();
                if (campInfo != null && campInfo.getCampName().equalsIgnoreCase(campName)) {
                    return camp;
                }
            }
        }
        return null;
    }
	
    /**
     * Register for camp 
     * @param sc user's input
	 * @param campList list of existing camps
	 * @param currentUser current user of CAMs
     */
	public static void regCamp(Scanner sc, List<Camp> campList, User currentUser) {
	    Camp.viewAllCamps(campList, currentUser);

	    System.out.println("\n----- Register for Camp -----");

	    String campName;
	    boolean isfound;
	    boolean isfull = false;
	    boolean isover = false;
	    boolean clash = false;
	    boolean hassignedup = false;

	    System.out.println("Enter Camp Name: ");
	    campName = sc.next();
	    sc.nextLine();
	    isfound = Validator.FacultyFound(campName, campList, currentUser);
	    hassignedup = Validator.hasSignedUp(campName, campList, currentUser);
	    isfull = Validator.isFull(campName, campList);
	    isover = Validator.isOver(campName, campList);
	    clash = Validator.dateClash(campName, campList, currentUser);
	    	    
	    if (!isfound) {
	    	System.out.println("Camp not found!");
	    } else if(hassignedup) {
	    	System.out.println("You have already signed up for this camp!");
	    } else if(currentUser.getWithdrawnCampNames().contains(campName)) {
	    	System.out.println("You have already withdrawn from this camp. Cannot register again!");
	    } else if(isover) {
	    	System.out.print("Registration Date is over!\n");
	    } else if(isfull) {
	    	System.out.printf("Sorry! Camp %s is currently full!\n", campName.toUpperCase());
	    } else if(clash) {
	    	System.out.print("Date clashes with your registered camp!\n");
	    } else {
	    	Camp selectedCamp = findCampByName(campList, campName);
	    	processReg(sc, campList, selectedCamp, currentUser);
	    }
	}

	/**
	 * Process register
     * @param sc user's input
	 * @param campList list of existing camps
	 * @param selectedCamp camp to register for
	 * @param currentUser current user of CAMs
	 */
	private static void processReg(Scanner sc, List<Camp> campList, Camp selectedCamp, User currentUser) {
	      String role = null;
	      int choice;
	      boolean alrdyCC = false;
	      boolean isfullCC = true;

	      do {
	          System.out.println("Sign up as -> (1)Attendee (2)Committee Member (3)Exit :");
	          choice = sc.nextInt();

	          if (choice == 1) {
	              role = "ATTENDEE";
	              
	          } else if (choice == 2) {
	            role = "COMMITTEE";
	            // already a CC
	            if (Validator.alrdyCC(campList, currentUser)) {
	              System.out.println("You are already a Camp Committee Member for another camp!");
	              alrdyCC = true;
	            // if not full, become a CC
	            } else if (!Validator.isFullCC(selectedCamp)) {
	              int ccSlot = selectedCamp.getcInfo().getCurrentCCSlot();
	              selectedCamp.getcInfo().setCurrentCCSlot(--ccSlot);
	              isfullCC = false;
	              
	              Committee committeeMember = new Committee(currentUser);
	              selectedCamp.addCommitteeMember(committeeMember);
	            } else {
	              System.out.println("There are enough Committee Members for this camp!");
	            }

	          } else if (choice == 3){
	            System.out.println("Back to Homepage!");
	          } else {
	              System.out.println("Invalid choice. Please enter 1 or 2 or 3!");
	          }

	      } while (choice != 1 && choice != 2 && choice != 3);

	      if (choice == 1 || choice == 2 && alrdyCC == false && isfullCC == false) {
	        Attendee user = new Attendee(currentUser, role);
	        selectedCamp.addtoAList(user);
	        int tSlot = selectedCamp.getcInfo().getCurrentSlot();
	        selectedCamp.getcInfo().setCurrentSlot(--tSlot);
	        System.out.println("Successfully Registered!");
	      }
	}
	
	/**
	 * Withdraw from camp 
     * @param sc user's input
	 * @param campList list of existing camps
	 * @param currentUser current user of CAMs
	 */
	public static void withdrawFromCamp(Scanner sc, List<Camp> campList, User currentUser) {
	    System.out.println("\n----- Withdraw from Camp -----");
	    
	    String campName;
	    boolean isfound;
	    boolean isaCC;
	    
	    System.out.println("Enter Camp Name: ");
	    campName = sc.next();
	    sc.nextLine();
	    isfound = Validator.FacultyFound(campName, campList, currentUser);
	    isaCC = Validator.isaCC(campName, campList, currentUser);    
	    
	    if (!isfound) {
	    	System.out.println("Camp not found!");
	    } else if (isaCC) {
	    	System.out.printf("You can't withdraw as you are a Committee member for %s!\n", campName.toUpperCase());
	    } else {
	
	        if (currentUser.getWithdrawnCampNames().contains(campName)) {
	            System.out.println("You have already withdrawn from this camp. Cannot withdraw again!");
	            return;
	        }
	        
	        Camp selectedCamp = findCampByName(campList, campName);
	        Iterator<Attendee> iterator = selectedCamp.getAttendeesList().iterator();
	        while (iterator.hasNext()) {
	            Attendee attendee = iterator.next();
	            if (attendee.getUser().equals(currentUser)) {
	                iterator.remove();
	                int tSlot = selectedCamp.getcInfo().getCurrentSlot();
	                selectedCamp.getcInfo().setCurrentSlot(++tSlot);
	                currentUser.addWithdrawnCampName(campName);
	                System.out.println("Successfully Withdrawn!");
	                break;
	            }
	        }
	    }    
	}
	
	/**
	 * Student enquires functions
     * @param sc user's input
	 * @param campList list of existing camps
	 * @param currentUser current user of CAMs
	 */
	public static void enquiries(Scanner sc, List<Camp> campList, User currentUser) {
		Map<Integer, Enquiry> studentEnquiries = new HashMap<>();
		Map<Integer, Camp> campEnquiries = new HashMap<>();
		int choice;
		int enquiryindex;
		String s = null;
		Enquiry selectedEnquiry;
		// print all enquiries
		System.out.println("\n----- Your Enquiries -----");
		int i = 0;
		for (Camp camp : campList) {
			for (Enquiry enquiry : camp.getEnquiriesList()) {
				if (enquiry.getStudent().equals(currentUser)) {
					studentEnquiries.put(i, enquiry);
					campEnquiries.put(i, camp);
		            System.out.println("Index: " + i);
		            System.out.println("Camp: " + camp.getcInfo().getCampName());
		            System.out.println("Enquiry: " + enquiry.getEnquiry());
		            System.out.println("Status: " + (enquiry.isProcessed() ? "Processed" : "Pending"));
		            System.out.println("Reply from Staff: " + enquiry.getReplyStaff());
		    		System.out.println("Reply from Commitee: " + enquiry.getReplyComm());
		            System.out.println("----------------------");
		            i++;
		            }
		        }
		}
		if (studentEnquiries.size() == 0) {System.out.println("You have no enquiries.");}
		else{System.out.println("End of Enquiries.");}
		
		do {
			// print enquiry menu
			Menu.enquiryMenuStudent();
			System.out.println("Enter option: ");
			choice = sc.nextInt();	
			
			sc.nextLine();
			
			switch (choice) {
			case 1: // edit enquiry
				
				// if no enquiries, cannot choose
				if (studentEnquiries.size() == 0) {
					System.out.println("You have no enquiries to edit.");
					break;
				}
				
				do {
					System.out.print("Enter the index of the enquiry you want to edit: ");
					enquiryindex = sc.nextInt();
				} while(!studentEnquiries.containsKey(enquiryindex));
				
				selectedEnquiry = studentEnquiries.get(enquiryindex);
				
				if (selectedEnquiry.isProcessed()) { 
					System.out.println("Your enquiry has been processed. It cannot be edited.");
					break;
				}
				System.out.println("\n----- Selected Enquiry -----");
	            System.out.println("Camp: " + campEnquiries.get(enquiryindex).getcInfo().getCampName());
	            System.out.println("Enquiry: " + selectedEnquiry.getEnquiry());
	            System.out.println("Status: " + (selectedEnquiry.isProcessed() ? "Processed" : "Pending"));
	            System.out.println("----------------------");
	            
	            System.out.println("Enter your edited enquiry: ");
	            sc.nextLine();
	            s = sc.nextLine();	
				selectedEnquiry.editEnquiry(s);
				break; 	
			
			case 2: // delete enquiry
				
				// check if user has any enquiries 
				if (studentEnquiries.size() == 0) {
					System.out.println("You have no enquiries to delete.");
					break;
				}
				
				do {
			        System.out.print("Enter the index of the enquiry you want to delete: ");
			        enquiryindex = sc.nextInt();
			    } while (!studentEnquiries.containsKey(enquiryindex));

			    Enquiry selectedEnquiryDeletion = studentEnquiries.get(enquiryindex);
			    
			    if (selectedEnquiryDeletion.isProcessed()) { 
			    	System.out.println("Your enquiry has been processed. It cannot be deleted.");
			    	break;
			    }
			    
			    // delete
			    campEnquiries.get(enquiryindex).deleteEnquiry(selectedEnquiryDeletion);
			    break;
				
			case 3: // add enquiry
				System.out.println("Select Camp: ");
				s = sc.nextLine().trim();
				
				// if camp does not exist
				while (s.isEmpty() || Validator.campFinderByName(s, campList) == null) {
				    System.out.println("Camp does not exist. Select a valid camp: ");
				    s = sc.nextLine();
				}
				
				Camp selectedCamp = Validator.campFinderByName(s, campList);
				
				// if not an attendee
				if(!Validator.hasSignedUp(s, campList, currentUser)) {
					System.out.println("You are not an attendee of this camp.");
					break;
				}
				
				// if attendee, check if cc. if not add enquiry
				if (Validator.isaCC(s, campList, currentUser)) {
					System.out.println("Committee members cannot add enquiries for camps they are committee members of.");
					break;
				}
			
				System.out.println("Selected Camp: " + s);
				System.out.println("Enter Enquiry: ");
				Enquiry newEnquiry = new Enquiry(currentUser, sc.nextLine());
				selectedCamp.addEnquiry(newEnquiry);
				break;
			case 4: // return
				break;				
			default:
				System.out.println("Invalid Selection");
				break;
			}	
		}while (choice != 1 && choice != 2 && choice != 3 && choice != 4);
	}	
}