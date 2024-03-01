package grp4cams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents the suggestion
 * @author Chia Jia En
 * @version 1.0
 * @since 2023-11-26
 */
public class Suggestions {
	/**
	 * user of the student that made This suggestion
	 */
	private User student;
	/**
	 * the suggestion made
	 */
    private String suggestionString;
    /**
     * status of the suggestion
     */
    private String approved;
    /**
     * list of suggestions 
     */
    private List<String> suggestions;
    
    /**
     * Constructor for the suggestion
     * @param student Student that made the suggestion
     * @param suggestionString The suggestion in a string format
     * @param suggestions The suggestion in a List format
     */
    public Suggestions(User student, String suggestionString, List<String> suggestions) {
        this.student = student;
        this.suggestionString = suggestionString;
        this.approved = "Pending";
        this.suggestions = suggestions;
    }

    /**
     * Get the student that made the suggestion
     * @return the student that made the suggestion
     */
    public User getStudent() {
        return student;
    }

    /**
     * Get the suggestion in a string form
     * @return the suggestion in the string format
     */
    public String getSuggestionString() {
        return suggestionString;
    }

    /**
     * Checks if the suggestion has been approved
     * @return the status of the suggestion
     */
    public String isApproved() {
        return approved;
    }
    
    /**
     * Gets the suggestion in the List form
     * @return the suggestion in the List format
     */
    public List<String> getSuggestions() {
        return suggestions;
    }
    
    /**
     * Approves a suggestion
     */
    public void approveSuggestion() {
        this.approved = "Approved";
    }
    
    /**
     * Rejects a suggestion
     */
    public void rejectSuggestion() {
    	this.approved = "Rejected";
    }
    
    /**
     * Add a suggestion
     * @param index The index of the type of suggestion being made
     * @param newSuggestion The suggestion, in a string form
     */
    public void addSuggestions(int index, String newSuggestion) {
    	this.suggestions.add(Integer.toString(index));
    	this.suggestions.add(newSuggestion);
    }
    
    /**
     * Edit a suggestion
     * @param newSuggestionString The new suggestion, in a string form
     * @param newSuggestions The new suggestion, in a List form
     */
    public void editSuggestions(String newSuggestionString, List<String> newSuggestions) {
        if (this.approved == "Pending") {
            this.suggestionString = newSuggestionString;
            this.suggestions = newSuggestions;
            System.out.println("Your suggestion had been edited.");
        } 
        else if (this.approved == "Rejected"){
        	System.out.println("Your suggestion has been rejected. It can no longer be edited.");
        }
        else System.out.println("Your suggestion has been approved. It can no longer be edited.");
            
    }
    
    /**
     * Delete a suggestion
     * @param campSuggestions The list of suggestions from a specific camp
     */
    public void deleteSuggestions(List<Suggestions> campSuggestions) {
        if (this.approved == "Pending") {
            campSuggestions.remove(this);
        } else {
            System.out.println("Your suggestion has been processed. It can no longer be deleted.");
        }
    }  
}
