package grp4cams;

/**
Represents the committee members in a camp
@author Chia Jia En
@version 1.0
@since 2023-11-26
*/

public class Committee {
	/**
	 * This current student as camp committee member
	 */
	private User user;
	/**
	 * This Committee Member's points
	 */
	private int points;
	/**
	 * This Committee Member's submitted suggestions
	 */
	private int submittedSuggestions;
	/**
	 * This Committee Member's approved suggestions
	 */
	private int approvedSuggestions;
	/**
	 * This Committee Member's completed enquires
	 */
	private int completedEnquiries;
	
	/**
	 * Constructor for the Committee class, specifying the User object
	 * @param user This Committee Member's User
	 */
	public Committee(User user) {
		this.user = user;
		this.points = 0;
		this.approvedSuggestions = 0;
		this.completedEnquiries = 0;
	}
	
	/**
	 * Get Committee's User
	 * @return This Commitee's user 
	 */
	public User getUser() {
        return user;
    }
	
	/**
	 * Set Committee's User
	 * @param user of the committee member
	 */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get Committee's Point
     * @return This Committee's points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Set Commitee's Points
     * @param points to set as the commitee's points
     */
    public void setRole(int points) {
        this.points = points;
    }
    
    /**
     * Get Committee's number of submitted suggestions
     * @return This Commitee's number of submitted suggestions
     */
    public int getSubmittedSuggestions() {
		return submittedSuggestions;
	}
    
    /**
     * Get Committee's number of approved suggestions
     * @return This Committee's number of approved suggestions
     */
    public int getApprovedSuggestions() {
		return approvedSuggestions;
	}
    
    /**
     * Get Committee's number of completed enquiries
     * @return This Committee's number of completed enquiries
     */
    public int getCompletedEnquiries() {
		return completedEnquiries;
	}
    
    /**
     * Adds points to the committee's current points based on suggestions or enquiries
     * @param type of submission: enquiry/suggestion(approved)/suggestion(pending)
     */
    public void addPoints(String type) {
        this.points += 1;
        if (type.equalsIgnoreCase("subSuggestion")) {
            this.submittedSuggestions += 1;
        } else if (type.equalsIgnoreCase("apprvSuggestion")) {
            this.approvedSuggestions += 1;
        } else if (type.equalsIgnoreCase("completedEnquiry")) {
            this.completedEnquiries += 1;
        } else {
        	System.out.println("Invalid Point Type.");
        }
    }
    
    /**
     * Removes points from committee's current points when they delete an unapproved suggestion
     */
    public void removePoints() {
        if (this.points > 0) {
            this.points -= 1;
            this.submittedSuggestions -= 1;
        }
    }
}
