# Camp Application and Management System (CAMs)

This is a Java-based Camp Application and Management System (CAMs) that allows staff, students, and camp committee members to manage, view, and register for camps within NTU. The application will act as a centralized hub for all staff, students, and camp committee members.

## Requirements

* Java Development Kit (JDK) 8 or higher
* A Java Integrated Development Environment (IDE) such as Eclipse, IntelliJ IDEA, or NetBeans

## Getting Started

1. Clone the repository:
git clone https://github.com/yourusername/Camp-Application-and-Management-System.git

2. Import the project into your IDE.

3. Build the project using Maven or Gradle.

4. Run the application.

5. Access the application at `http://localhost:8080`

## User Authentication and Authorization

* All users will need to login to this hub using their user account.
* User ID will be the NTU network user ID, that is the part before @ in email address.
* Assume all users use the default password, which is password.
* A user can change password in the system.
* A user will have faculty information. E.g, EEE, SCSE.
* A student list and staff list can be initiated through files uploaded into the system at initialization. The sample student file and staff file are provided.

## Staff Capabilities

* A staff will be able to create, edit and delete camps.
* A staff can toggle the visibility of the camp to be "on" or "off". This will be reflected in the camp list that will be visible to students.
* A staff can view all camps.
* A staff can see list of camps that his/her created in a separate menu list so they can edit the camps they created.
* A staff can view and reply to enquiries from students to the camp(s) his/her has created.
* A staff can view and approve suggestions to changes to camp details from camp committee.
* A staff can generate a report of the list of students attending each camp that his/her has created. The list will include details of the camp as well as the roles of the participants. There should be filters for how the staff would want to generate the list. (attendee, camp committee, etc.) (generate in either txt or csv format).
* A staff can also generate a performance report of the camp committee members.

## Camp Information

* Each camp should have Camp Name
* Dates
* Registration closing date
* User group this camp is open to (own school or whole NTU)
* Location
* Total Slots
* Camp Committee Slots (max 10)
* Description
* Staff in charge (automatically tied to the staff who created it)

## Student Capabilities

* A student can only view the list of camps that are open to his/her user group (SCSE, whole NTU etc.) and if their visibility has been toggled “on”.
* A student can view the remaining slots of each camp that is open to his/her.
* A student will be able to register for camps either as a camp attendee or camp committee.
* A student can submit enquiries regarding a camp. (only staff and camp committees in charge of that camp can view it)
* A student can view, edit, and delete their enquiries before it is processed.
* The status of the student as a camp committee will be reflected in their profile.
* A student is only able to be in the camp committee for one camp but can attend multiple camps.
* A student is not allowed to register for multiple camps if there are clashes in the dates.
* A student only can register a camp before it is full.
* A student only can register a camp before it’s registration deadline.
* A student can see the camps that his/her has already registered for and his/her roles (attendee OR camp committee)
* A student is allowed to withdraw from camps that his/her has already registered for. (The remaining slot will be updated automatically. But the student is not allowed to register the same camp again.)

## Camp Committee Member Capabilities

* A camp committee member can view the details of the camp that he/she has registered for.
* A camp committee member can submit suggestions for changes to camp details to staff
