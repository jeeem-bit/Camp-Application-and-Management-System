package grp4cams;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an enquiry
 * @author jiaen
 * @version 1.0
 * @since 2023-11-26
 */
public class Enquiry {
	private User student;
    private String enquiryString;
    private boolean processed;
    private String replyStringStaff;
    private String replyStringComm;
    
    /**
     * Constructor for an enquiry
     * @param student The user of the student that made This enquiry
     * @param enquiryString The ennquiry being made 
     */
    public Enquiry(User student, String enquiryString) {
        this.student = student;
        this.enquiryString = enquiryString;
        this.processed = false;
        this.replyStringStaff = null;
        this.replyStringComm = null;
    }

    /**
     * Get the student that made This enquiry
     * @return the student that made the enquiry
     */
    public User getStudent() {
        return student;
    }
    
    /**
     * Get the enquiry 
     * @return the enquiry string
     */
    public String getEnquiry() {
        return enquiryString;
    }

    /**
     * Returns if the enquiry is processed 
     * @return true or false, depending on if the enquiry has been processed
     */
    public boolean isProcessed() {
        return processed;
    }
    
    /**
     * Get the staff's reply to the enquiry
     * @return the enquiry string from the staff
     */
    public String getReplyStaff() {
        return replyStringStaff;
    }
    
    /**
     * Update an enquiry after it has been replied to by the staff
     * @param replyString The reply the staff has given to the enquiry
     */
    public void processEnquiryStaff(String replyString) {
        this.processed = true;
        this.replyStringStaff = replyString;
    }
    
    /**
     * Get the committee's reply to the enquiry
     * @return the enquiry string from the committee
     */
    public String getReplyComm() {
        return replyStringComm;
    }
    
    /**
     * Update an enquiry after it has been replied to by the committee
     * @param replyStringComm The reply the committee has given to the enquiry
     */
    public void processEnquiryComm(String replyStringComm) {
        this.processed = true;
        this.replyStringComm = replyStringComm;
    }
    
    /**
     * Edit the selected enquiry
     * @param newEnquiryString The new enquiry string
     */
    public void editEnquiry(String newEnquiryString) {
        if (!processed) {
            this.enquiryString = newEnquiryString;
            System.out.println("Your enquiry had been edited.");
        } else {
            System.out.println("Your enquiry has been processed. It can no longer be edited.");
        }
    }
    
    /**
     * Delete the selected enquiry
     * @param campEnquiries The list of enquiries that a camp has
     */
    public void deleteEnquiry(List<Enquiry> campEnquiries) {
        if (!processed) {
            campEnquiries.remove(this);
        } else {
            System.out.println("Your enquiry has been processed. It can no longer be deleted.");
        }
    }
}
