package grp4cams;

import java.time.LocalDate;

/**
Represents the attendees in a camp
@author Jasmine Goh
@version 1.0
@since 2023-11-26
*/

public class CampInfo {
    private String campName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate rcDate;
    private String userGroup;
    private String location;
    private int totalSlot;
    private int currentSlot;
    private int ccSlot;
    private int currentCCSlot;
    private String desc;
    private String sIC;
    
    /**
     * All information about This Camp
     * @param campName This Camp's Name
     * @param startDate This Camp's start date
     * @param endDate This Camp's end date
     * @param rcDate This Camp's registration end date
     * @param userGroup This Camp's user group
     * @param location This Camp's location
     * @param totalSlot This Camp's total slot
     * @param ccSlot This Camp's total committee slot
     * @param desc This Camp's description
     * @param sIC This Camp's staff in charge
     */
    public CampInfo(String campName, LocalDate startDate, LocalDate endDate, LocalDate rcDate, String userGroup,
			String location, int totalSlot, int ccSlot, String desc, String sIC) {
		super();
		this.campName = campName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rcDate = rcDate;
		this.userGroup = userGroup;
		this.location = location;
		this.totalSlot = totalSlot;
		this.ccSlot = ccSlot;
		this.desc = desc;
		this.sIC = sIC;
		this.currentSlot = totalSlot;
		this.currentCCSlot = ccSlot;
		
	}

    /**
     * get Camp Name
     * @return This Camp's Name
     */
    public String getCampName() {
        return campName;
    }

    /**
     * get Camp start date
     * @return This Camp's start date
     */
    public LocalDate getStartDate() {
		return startDate;
	}

    /**
     * change Camp start date
     * @param startDate new start date
     */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

    /**
     * get Camp end date
     * @return This Camp's end date
     */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * change Camp end date
	 * @param endDate new end date
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

    /**
     * get Camp registration end date
     * @return This Camp's registration end date
     */
	public LocalDate getRcDate() {
        return rcDate;
    }

	/**
	 * get Camp registration end date
	 * @param rcDate new registration end date
	 */
    public void setRcDate(LocalDate rcDate) {
        this.rcDate = rcDate;
    }
    
    /**
     * get Camp's user group
     * @return This Camp's user group
     */
    public String getUserGroup() {
        return userGroup;
    }

    /**
     * change Camp user group
     * @param userGroup new user group
     */
    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * get Camp location
     * @return This Camp's location
     */
    public String getLocation() {
        return location;
    }

    /**
     * change Camp's location
     * @param location new Camp location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * get Camp's total slot
     * @return This Camp's total slot
     */
    public int getTotalSlot() {
        return totalSlot;
    }

    /**
     * change Camp's total slot
     * @param totalSlot new Camp's total slot
     */
    public void setTotalSlot(int totalSlot) {
        this.totalSlot = totalSlot;
    }
  
    /**
     * get Camp's current slot
     * @return This Camp's current slot
     */
    public int getCurrentSlot() {
		return currentSlot;
	}
    
    /**
     * change Camp's current slot
     * @param currentSlot new Camp's current slot
     */
	public void setCurrentSlot(int currentSlot) {
		this.currentSlot = currentSlot;
	}

	/**
	 * get Camp Committee Ccslot
	 * @return This Camp Committee's Ccslot
	 */
	public int getCcSlot() {
        return ccSlot;
    }

	/**
	 * change Camp Committee's Ccslot
	 * @param ccSlot new Camp Committee's Ccslot
	 */
    public void setCcSlot(int ccSlot) {
        this.ccSlot = ccSlot;
    }

    /**
     * get Current Camp Committee Ccslot 
     * @return This Current Camp Committee Ccslot
     */
	public int getCurrentCCSlot() {
		return currentCCSlot;
	}

	/**
	 * change Current Camp's Committee Ccslot
	 * @param currentCCSlot new Current Camp Committee's Ccslot
	 */
	public void setCurrentCCSlot(int currentCCSlot) {
		this.currentCCSlot = currentCCSlot;
	}
    
	/**
	 * get Camp's Description
	 * @return This Camp's Description
	 */
    public String getDesc() {
        return desc;
    }

    /**
     * change Camp's Description
     * @param desc new Camp's Description
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * get Camp's Staff-In-Charge
     * @return This Camp's Staff-In-Charge
     */
    public String getsIC() {
        return sIC;
    }

    /**
     * change Camp's Staff-In-Charge
     * @param sIC new Camp's Staff-In-Charge
     */
    public void setsIC(String sIC) {
        this.sIC = sIC;
    }

}
