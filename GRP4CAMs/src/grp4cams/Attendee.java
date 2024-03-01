
package grp4cams;

/**
Represents the attendees in a camp
@author Jasmine Goh
@version 1.0
@since 2023-11-25
*/

public class Attendee {
	/**
	 * user attending the camp
	 */
    private User user;
	/**
	 * role in camp
	 */
    private String role;

	/**
	 * Attendee
	 * @param user This Attendee's Name
	 * @param role This Attendee's Role
	 */
	public Attendee(User user, String role) {
        this.user = user;
        this.role = role;
    }
	
	/**
	 * get Attendee's Name
	 * @return This Attendee's Name
	 */
    public User getUser() {
        return user;
    }
    
	/**
	 * set User
	 * @param user Set Attendee's Name
	 */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * get Role
     * @return This Attendee's Role
     */
    public String getRole() {
        return role;
    }

    /**
     * set Role
     * @param role Set Attendee's Role
     */
    public void setRole(String role) {
        this.role = role;
    }
}
