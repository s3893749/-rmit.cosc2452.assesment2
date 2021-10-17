//Import Java Date Utils, this is required for the time stamping for created Tickets and updating the last modified Value
import java.util.Date;

// *** Start FacilityManager Main Class File *** \\

public class FacilityManager {
	
	// *** Process initial variable declarations *** \\

	// Interface Window Size
	private int interfaceWidth;
	private int interfaceHeight;
	
	// Interface PopUp Window Size
	private int popUpInterfaceWidth;
	private int popUpInterfaceHeight;
	
	// Setup issue data arrays for storing user inputs
	private String[] issueName;
	private String[] issueDescription;
	private int[] issuePriorty;
	private boolean[] issueResolved;
	private String[] issueModified;
	private String[] issueCreated;
	
	// Array counter and max record variables
	private int maxRecords;
	private int currentRecord;
	
	// Create table variable used to display all the issue ticket entry's
	private int reportTable;
	
	// Define GTerm Windows - Main Interface & PopUp Interface
	private GTerm gtPopUp;
	//main GT variable used for main interface
	private GTerm gt;
	
	// Ticket Creation Inputs
	private int issueNameInput;
	private int issueDescriptionInput;
	private int issuePriortyInput;

	// *** Constructor function used to set program variables, called once at the creation of the class instance *** \\
	
	public FacilityManager() {
		
		// Set the interface width and height for the primary and pop-up
		this.interfaceWidth = 1285;
		this.interfaceHeight = 725;
		
		this.popUpInterfaceWidth = 305;
		this.popUpInterfaceHeight = 410;
		
		// Set the starting Max Record and Current Record Counter, - CurrentRecords are used to count how many total we have and MaxRecords is used to keep track of the current total array size
		this.maxRecords = 10;
		this.currentRecord = 0;
		
		// Create the GTerm instance and pass it the interface width and height
		gt = new GTerm(interfaceWidth, interfaceHeight);
		
		// Create the starting arrays and pass them the maxRecord starting size
		this.issueName = new String[maxRecords];
		this.issueDescription = new String[maxRecords];
		this.issuePriorty = new int[maxRecords];
		this.issueResolved = new boolean[maxRecords];
		this.issueModified = new String[maxRecords];
		this.issueCreated = new String[maxRecords];
		
		//*** Draw the main interface elements to the screen (Drawn in constructor as this only needs to occur once) ***\\
		
		// Draw Main Title & Set Font Size
		this.gt.setXY(85, 20);
		this.gt.setFontSize(26);
		this.gt.println("Facility Manager");
		this.gt.setFontSize(12);
		this.gt.setXY(0, 0);
				
		// Draw Main Reporting Table that contains all the issue ticket records
		this.gt.setXY(20, 160);
		this.reportTable = this.gt.addTable(1220, 500, "Issue	Description	Priorty	Resolved Status	Last Modified	Created");
		this.gt.setXY(0, 0);
		
		// Draw the user Bottoms used to access each of the functions (User Button get passed a name, the object calling the button "This" and the function name)
		this.gt.setXY(20, 100);
		this.gt.addButton("Add Ticket", this, "createRecordInterface");
		this.gt.setXY(0, 0);
		this.gt.setXY(120, 100);
		this.gt.addButton("Edit Ticket", this, "editRecordInterface");
		this.gt.setXY(0, 0);
		this.gt.setXY(230, 100);
		this.gt.addButton("Change Status", this, "changeStatusButton");
		this.gt.setXY(0, 0);
		this.gt.setXY(350, 100);
		this.gt.addButton("Delete Ticket", this, "removeRecordButton");
		this.gt.setXY(0, 0);

		
		// Draw Credits Jack Harris
		this.gt.setXY(960, 680);
		this.gt.println("Created By Jack Harris | 25/04/2021");
		this.gt.setXY(0, 0);
		this.gt.setXY(960, 700);
		this.gt.println("ITP Assesment 2 RMIT / OUA");
		this.gt.setXY(0, 0);
		
		// Finally draw the top banner art work, this occurs last so all other text and buttons are layered on top on the banner
		this.gt.addImageIcon("gui.png");
	}
	
	//*** Main Runtime Function, Used only to create the Facility Manager Class Instance ***\\
	public static void main(String[] args){
		
		// Main Runtime Function only create instance of Facility Manger Class
		// Suppressed Warning used to hide the "unused" warning as we don't reference Facility Manger again in this class
		@SuppressWarnings("unused")
		FacilityManager self = new FacilityManager();
			 
	}
	
	//*** Create Record Interface, Function Triggered by the button to open up the GTerm PopUp Window for the creation of a new entry ***\\
	
	public void createRecordInterface() {
		
		// Create the GTerm PopUp window and pass it the width and height variables as defined in the constructor
		this.gtPopUp = new GTerm(this.popUpInterfaceWidth, this.popUpInterfaceHeight);
		
		// User Inputs for new ticket creation
		// The following inputs are the GTerm Text Fields and Titles where the user enters the data that they wish to pass to he new issue ticket.
		// These follow 5 Steps, 1. set the XY values for the field title, 2. print the field title name, 3. set the XY value for the field input, 4. Create the input field 5. repeat step 1-4
		
		// Step 1 Set the XY position of the field title
		this.gtPopUp.setXY(20, 60);
		// Step 2 Print the Title Name
		this.gtPopUp.println("Issue Name:");
		// Step 3 set the XY position of the Text Field Input
		this.gtPopUp.setXY(20, 80);
		// Step 4 Create the InpuField
		this.issueNameInput = this.gtPopUp.addTextField("", 200);
		//___________________________________________________________________\\
		// Step 1 Set the XY position of the field title
		this.gtPopUp.setXY(20, 120);
		// Step 2 Print the Title Name
		this.gtPopUp.println("Issue Description:");
		// Step 3 set the XY position of the Text Field Input
		this.gtPopUp.setXY(20, 140);
		// Step 4 Create the InpuField
		this.issueDescriptionInput = this.gtPopUp.addTextArea("", 200, 100);
		//___________________________________________________________________\\
		// Step 1 Set the XY position of the field title
		this.gtPopUp.setXY(20, 260);
		// Step 2 Print the Title Name
		this.gtPopUp.println("Priorty / Severity:");
		// Step 3 set the XY position of the Text Field Input
		this.gtPopUp.setXY(20, 280);
		// Step 4 Create the InpuField
		this.issuePriortyInput = this.gtPopUp.addTextField("", 200);
		// Step 4A (The Priority has a extra line of text and so requires 2 additional print-line statements to facilitate this.)
		this.gtPopUp.println("");
		this.gtPopUp.println("Enter a Number From 1 - 10");
		//___________________________________________________________________\\
		// Create the "Create Ticket Button"
		this.gtPopUp.setXY(20, 340);
		this.gtPopUp.addButton("Create Ticket", this, "createRecordButton");
		this.gtPopUp.setXY(20, 0);
		
		//*** GUI / Art ***\\
		
		// Print the Top Heading Title & Set Font Color,Text Size & GUI Position.
		this.gtPopUp.setFontColor(255, 255, 255);
		this.gtPopUp.setFontSize(24);
		this.gtPopUp.setXY(45, 10);
		this.gtPopUp.println("Create Ticket");
		
		// Load the Top Banner art work for (Loaded Last so all items are drawn in front of it)
		this.gtPopUp.setXY(0, 0);
		this.gtPopUp.addImageIcon("popup-banner.png");

	}
	
	//*** Create Record Button Function, Used to grab the data from the input values and pass to the processing function ***\\
	
	public void createRecordButton() {
		
		//firstly check that the priority is a numerical value, if it does not throw a error process the ticket creation, else display a error
		if (!intChecker(this.gtPopUp.getTextFromEntry(this.issuePriortyInput))) {
			
			//display message if the priority was not a numerical value
			this.gt.showWarningDialog("Priorty Must Be a Numerical Value");
			
			}else {
				
				// Add all the user inputed data into an array for easy storage and pass that array back to the createRecord function for handling
				// Creating array with length of 3 called output
				String[] output = new String[3];
				// Inserting the issue name to slot 1
				output[0] = this.gtPopUp.getTextFromEntry(this.issueNameInput);
				// Inserting the issue description to slot 2
				output[1] = this.gtPopUp.getTextFromEntry(this.issueDescriptionInput);
				// Inserting the Priority into slot 3
				output[2] = this.gtPopUp.getTextFromEntry(this.issuePriortyInput);
				
				// Call the "createRecord" function for handling the ticket creation, and pass the output array to the function for process.
				createRecord(output);
				
				// Finally now close the gtPopUp window, this returns the user seamlessly to the main interface screen.
				this.gtPopUp.close();
			}
		

	}
	
	//*** Primary Create Record function used to process all the user data and create the record. ***\\
	
	private void createRecord(String[] input) {

		//check to ensure the array has space to populate records, if not increase by 10 and create new arrays
		if (this.currentRecord == this.maxRecords) {
			
			// Add 10 to max Records for array expansion, this means every time this is triggered the array will expand by 10 record entry's.
			this.maxRecords += 10;
			
			// Create new temporary holding arrays with the new "maxRecord" size
			String[] newIssueName = new String[this.maxRecords];
			String[] newIssueDescription = new String[this.maxRecords];
			int[] newIssuePriorty = new int[this.maxRecords];
			boolean[] newIssueResolved = new boolean[this.maxRecords];
			String[] newIssueModified = new String[this.maxRecords];
			String[] newIssueCreated = new String[this.maxRecords];
			
			// move all the data from the old arrays into the new temporary holding arrays
			
			// "recordNumber" integer represents the numerical value for the record position in the arrays
			// Starting at 0 it will count up and transfer all the current records that exist in the main arrays to the new temporary holding arrays.		
			int recordNumber = 0;
			
			// While the current recordNumber is less than "currentRecord" (CurrentRecord = the total amount of records in the system) copy all the data and increase the record number
			while(recordNumber < this.currentRecord) {
				
				// The following code copies over all the data from the old arrays to the new expanded arrays
				newIssueName[recordNumber] = this.issueName[recordNumber];
				newIssueDescription[recordNumber] = this.issueDescription[recordNumber];
				newIssuePriorty[recordNumber] = this.issuePriorty[recordNumber];
				newIssueResolved[recordNumber] = this.issueResolved[recordNumber];
				newIssueModified[recordNumber] = this.issueModified[recordNumber];
				newIssueCreated[recordNumber] = this.issueCreated[recordNumber];
				
				//finally increase the recordNumber that has been processed
				recordNumber++;
			}
			
			// Once the above code is complete we can set our existing permanent arrays to the size and value of our new temporary holding arrays, to set an array to the value of a second array you can simply use "=" as show below
			this.issueName = newIssueName;
			this.issueDescription = newIssueDescription;
			this.issuePriorty = newIssuePriorty;
			this.issueResolved = newIssueResolved;
			this.issueModified = newIssueModified;
			this.issueCreated = newIssueCreated;
		
		// This else statement refers to the initial check to see if we had space left in the arrays, if they have room then the program will jump to here and process the addition of a new array
		}else {
			
			// Create the current Date instance needed to get the date for the time created time stamp
			Date currentDate = new Date();

			// Grab the input array that we exported from the user data and enter it into the current records
			this.issueName[this.currentRecord]=input[0];
			this.issueDescription[this.currentRecord]=input[1];
			// Integer ParseInt is used to convert the string number to a proper integer, Integer checking was performed back at the button function so we don't need to worry about it here.
			this.issuePriorty[this.currentRecord]= Integer.parseInt(input[2]);
			// The default state of a record is false to resolved, no user input needed
			this.issueResolved[this.currentRecord]=(false);
			// Set last modified time stamp, this is the same as the created time for new tickets but is overridered later when a user makes changes to the record
			this.issueModified[this.currentRecord] = String.valueOf(currentDate);
			// Set the created time stamp, this is never changed and serves as the record for when the ticket was created.
			this.issueCreated[this.currentRecord] = String.valueOf(currentDate);
			
			// Lastly Increase the currentRecord count so that the system knows now many total records we have!
			this.currentRecord ++;
		}
		
		//Finally in this function we perform a GUI update for our table via the "update" Function Here
		update();
	}
	
	//*** Record Selection Function, Used to grab the ID of the row that the user has selected from the report table ***\\
	
	private int selectRecord() {
		
		// Create a selected variable and set it to the index of the row that the user has selected
		int selected = this.gt.getIndexOfSelectedRowFromTable(this.reportTable);
		
		// Return the selected variable, this means that in a separate function the user can simply call selectRecord() to receive the row input.
		return selected;
	}
	
	
	//*** Remove Record Button, this function is sued to process the user input for what record to be removed and passes it to the back-end function removeRecord ***\\
	
	public void removeRecordButton() {
		
		// Create a selectedRecord variable and grab the user row selection from the function that handles it
		int selectedRecord = selectRecord();
		
		// Check if no row was selected, if this is true then -1 is returned and the system will throw the user a error
		if (selectedRecord == -1) {
			// Throw GTerm warning dialog error if no row was selected (-1 return value)
			this.gt.showWarningDialog("Please Select a Valid Ticket");
		}else {
			// Else process the "removeRecord" Function and pass it the row that the user has selected
		removeRecord(selectedRecord);
		}
	}
	
	//*** Remove record main function, it handles the selected record input from the "removeRecordButton" function and process the removal request ***\\
	
	private void removeRecord(int selectedRecord) {
			
			// Two variables are needed to keep track of the removal, one for the current Record tracking named "recordNumber" and one for the new record number tracking called "newRecordNumber"
			// The two variable system is required as we need to create new arrays and omit one of the entries.
			int recordNumber = 0;
			int newRecordNumber = 0;
			
			// Create the new temporary holding arrays 
			String[] newIssueName = new String[this.maxRecords];
			String[] newIssueDescription = new String[this.maxRecords];
			int[] newIssuePriorty = new int[this.maxRecords];
			boolean[] newIssueResolved = new boolean[this.maxRecords];
			String[] newIssueModified = new String[this.maxRecords];
			String[] newIssueCreated = new String[this.maxRecords];
			
			// process the copying of old data into the new arrays with a while loop, while the recordNumber < maxRecords process this loop
			while( recordNumber < this.maxRecords) {
				// With in this while loop we need a if statement to check the record number that we are at currently is the one selected by the user, if it is do nothing, else if it is not then copy the data
			if (recordNumber != selectedRecord) {
				
				// If the RecordNubmer is not the same as the selectedRecord then copy this data over
				newIssueName[newRecordNumber] = this.issueName[recordNumber];
				newIssueDescription[newRecordNumber] = this.issueDescription[recordNumber];
				newIssuePriorty[newRecordNumber] = this.issuePriorty[recordNumber];
				newIssueResolved[newRecordNumber] = this.issueResolved[recordNumber];
				newIssueModified[newRecordNumber] = this.issueModified[recordNumber];
				newIssueCreated[newRecordNumber] = this.issueCreated[recordNumber];
				
				// Increase the new record Number Loop
				newRecordNumber++;
			}
			// Increase the old record number count
			recordNumber ++;
			}
			
			// Now the data has all been copies excluding the selected entry then -1 from the CurrentRecord & MaxRecord counts to accommodate the reduction
			this.currentRecord -=1;
			this.maxRecords -=1;
			
			// now we have excluded the selected from from the new temporary data arrays and copied over all other data we can set the main primary arrays to the value of the temporary arrays to make the change permanent.
			this.issueName = newIssueName;
			this.issueDescription = newIssueDescription;
			this.issuePriorty = newIssuePriorty;
			this.issueResolved = newIssueResolved;
			this.issueModified = newIssueModified;
			this.issueCreated = newIssueCreated;
			
			//finally process the update function to visually update the changes on the record table
			update();
		
	}
	
	//*** Edit Record Interface Function, this is called when the user selects a record and then clicks the edit button ***\\
	
	public void editRecordInterface() {
		
		// Firstly we create a selectedRecord integer variable and set it to the value of the row that the user has selected
		int selectedRecord = selectRecord();
		
		// Next we perform  a check to ensure this is a valid Ticket, if no row form the table is selected then -1 is returned so we check to see if it is -1, if it is then throw a GTerm pop-up warning else continue the code.
		if (selectedRecord == -1) {
			// Throw Warning is no table row is selected
			this.gt.showWarningDialog("Please Select a Valid Ticket");
		}else {
			
		
		// Create the GTerm PopUp window and pass it the width and height variables as defined in the constructor
		this.gtPopUp = new GTerm(this.popUpInterfaceWidth, this.popUpInterfaceHeight);
		
		// User Inputs Editing Ticket Data
		// This is a similar process to creating a new ticket with the same steps used, the only difference is when we add the text fields we grab the content from the associated arrays and populate the arrays with them by default.
		
		// Step 1, Set the XY position of the field title
		this.gtPopUp.setXY(20, 60);
		// Step 2, Print the field title
		this.gtPopUp.println("Issue Name:");
		// Step 3, Set the TextField XY GUI cords.
		this.gtPopUp.setXY(20, 80);
		// Step 4, This step differs from creating a new ticket as we can see it doesn't leave the field blank but instead fetch the data from the assorted arrays to populate the field.
		this.issueNameInput = this.gtPopUp.addTextField(this.issueName[selectedRecord], 200);
		//___________________________________________________________________\\
		// Step 1, Set the XY position of the field title
		this.gtPopUp.setXY(20, 120);
		// Step 2, Print the field title
		this.gtPopUp.println("Issue Description:");
		// Step 3, Set the TextField XY GUI cords.
		this.gtPopUp.setXY(20, 140);
		// Step 4, This step differs from creating a new ticket as we can see it doesn't leave the field blank but instead fetch the data from the assorted arrays to populate the field.
		this.issueDescriptionInput = this.gtPopUp.addTextArea(issueDescription[selectedRecord], 200, 100);
		//___________________________________________________________________\\
		// Step 1, Set the XY position of the field title
		this.gtPopUp.setXY(20, 260);
		// Step 2, Print the field title
		this.gtPopUp.println("Priorty / Severity:");
		// Step 3, Set the TextField XY GUI cords.
		this.gtPopUp.setXY(20, 280);
		// Step 4, This step differs from creating a new ticket as we can see it doesn't leave the field blank but instead fetch the data from the assorted arrays to populate the field.
		this.issuePriortyInput = this.gtPopUp.addTextField(String.valueOf(this.issuePriorty[selectedRecord]), 200);
		// (The Priority has a extra line of text and so requires 2 additional print-line statements to facilitate this.)
		this.gtPopUp.println("");
		this.gtPopUp.println("Enter a Number From 1 - 10");
		//___________________________________________________________________\\
		// Create the "Create Ticket Button"
		this.gtPopUp.setXY(20, 340);
		this.gtPopUp.addButton("Save Ticket", this, "editRecordButton");
		//___________________________________________________________________\\
		// Finally on the edit ticket screen we also display the last time the ticket was edited at the bottom of the ticked edit window"
		// Set XY cords to bottom of window
		this.gtPopUp.setXY(20, 380);
		// Set Font Size to small 8 (normal = 12)
		this.gtPopUp.setFontSize(8);
		// Print the last edited text and time stamp to the window
		this.gtPopUp.println("Last Edited: "+this.issueModified[selectedRecord]);
		this.gtPopUp.setXY(20, 0);
		
		//*** GUI / Art ***\\
		
		// Print the Top Heading Title & Set Font Color,Text Size & GUI Position.
		this.gtPopUp.setFontColor(255, 255, 255);
		this.gtPopUp.setFontSize(24);
		this.gtPopUp.setXY(45, 10);
		this.gtPopUp.println("Edit Ticket");
		
		// Print the top banner art work to the window, this is printed last so all other items are drawn ontop of it in case of overlap
		this.gtPopUp.setXY(0, 0);
		this.gtPopUp.addImageIcon("popup-banner.png");
		}

	}
	
	//*** Edit Record processing button, this grabs the changes from the editRecordInterface and combines them into an array to be processed by the "editRecord" processing function ***\\ 
	
	public void editRecordButton() {
		
		// Firstly check that the priority is a numerical value, if not throw a error, else process the ticket creation
		if (!intChecker(this.gtPopUp.getTextFromEntry(issuePriortyInput))) {
			
			// Display message if the priority was not a numerical value
			this.gt.showWarningDialog("Priorty Must Be a Numerical Value");
			
			// If it is a numerical value for priority continue saving the changes.
			}else {
				
				// Get the selected record from the selectRecord function 
				// (The editRecordInterface will perform a check to ensure that a valid table row is select so we don't need to perform the check again here)
				int selectedRecord = selectRecord();
				
				// create a new array called output, set the fist value to the record number that we are editing.
				// Next add all the values into the next 3 inputs.
				String[] output = new String[4];
				output[0] = String.valueOf(selectedRecord);
				output[1] = this.gtPopUp.getTextFromEntry(this.issueNameInput);
				output[2] = this.gtPopUp.getTextFromEntry(this.issueDescriptionInput);
				output[3] = this.gtPopUp.getTextFromEntry(this.issuePriortyInput);
				
				// Pass current output array to createRecord function for Processing
				editRecord(output);
				
				//close the pop-up window now we have collected all the user data an sent it to processing function
				this.gtPopUp.close();
			}
		

		
	}
	
	//*** EditRecord function provides all the edit changes for the system and receives its data from the editRecordButton via a output array ***\\ 
	
	private void editRecord(String[] input) {
		
		// Firstly grab the selected record id that we set to location 0 in the output array and set it to the local selected record variable
		int selectedRecord = Integer.parseInt(input[0]);
		
		// Now create a currentDate  instance we we need to update the last modified date times stamp on a edit
		Date currentDate = new Date();
		
		// Now we have all our data, our record id and the currentTime we can set the values.
		// We set our values by setting the selectedRecord value to the assorted input[] of the array of new data, remember that input[0] is the selected record so user data starts at input[1]
		this.issueName[selectedRecord]=input[1];
		this.issueDescription[selectedRecord]=input[2];
		this.issuePriorty[selectedRecord]= Integer.parseInt(input[3]);
		this.issueModified[selectedRecord] = String.valueOf(currentDate);
		
		// Now we have made our changes run the update function to reflect the changes on the primary record table
		update();
		
	}
	
	//***  Change Status Button, this button will grab the selected record and check that we have selected a table row ***\\ 
	
	public void changeStatusButton() {
		
		// Grab the selected row from the selectRecord function
		int selectedRecord = selectRecord();
		
		// Perform a check to ensure a row is selected, if no row is selected it will return -1, if -1 then display GTerm Error
		if (selectedRecord == -1) {
			// Show GTerm error when no row is selected
			this.gt.showWarningDialog("Please Select a Valid Ticket");
		}else {
			//if a row is selected then pass that to the change status processing function below
			changeStatus(selectedRecord);
		}
		
	}
	
	//***  Change status processing function, grabs the selected record as a input and will flip the status from true to false ***\\ 
	
	private void changeStatus(int selectedRecord) {
		
		// Get current date and time need for marking a time last
		Date currentDate = new Date();
		
		// Check if the issue is resolved (true) if it is flip it to false and update the modified time stamp value
		if (this.issueResolved[selectedRecord]) {
			this.issueResolved[selectedRecord] = false;
			// Display GTerm prompt that the status has changed
			this.gt.showMessageDialog("Resovled Status Changed to False");
			this.issueModified[selectedRecord] = String.valueOf(currentDate);
		}else {
			// Else if it is not true then update the false value to be true and then update the latest modified time stamp value
			this.issueResolved[selectedRecord] = true;
			// Display GTerm prompt that the status has changed
			this.gt.showMessageDialog("Resovled Status Changed to True");
			this.issueModified[selectedRecord] = String.valueOf(currentDate);
		}
		
		// Finally perform a GUI update to reflect these changes in the main record table
		update();
		
	}
	
	//***  True or False Integer Checker (Checks if a string is a numerical value) via input string ***\\ 
	
	private boolean intChecker(String input) {
		
		// Create boolean value for is numerical and set it to true;
		boolean dataTypeNumberIsNumerical = true;
		
		// Create try catch test to see if the Integer conversion throws a error
		try {
			// Suppress warning used due to the local variables not being called again
		 @SuppressWarnings("unused")
		int convertedInteger = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			// If a error has been cached it will set dataTypeNumber to false else it will leave it true
			dataTypeNumberIsNumerical = false;
		}
			// Check what the boolean value of dataTypeNumber is, true or false
		if (dataTypeNumberIsNumerical) {

			//If it is still true that means the string can be converted to a integer and return true!
			return true;
			
		} else
		//else it means that its is letters that are not numbers that can be converted and return false.
		return false;
	}

	//***  Main Table Update Function ***\\ 
	
	private void update() {
		
		// Firstly clear the current table of all data 
		this.gt.clearRowsOfTable(this.reportTable);	
		
		// Create a record number variable and use it to count up to the currentRecord (Max) starting from 0
		int recordNumber = 0;
		
		//while the recordNumber is less than the currentRecord (Max) add the row to table and populate it with data for that record number
		while (recordNumber < currentRecord) {
			this.gt.addRowToTable(this.reportTable, this.issueName[recordNumber]+"\t"+this.issueDescription[recordNumber]+"\t"+this.issuePriorty[recordNumber]+"\t"+this.issueResolved[recordNumber]+"\t"+this.issueModified[recordNumber]+"\t"+this.issueCreated[recordNumber]);
			// Increase the record number each time the loop runs
			recordNumber++;
			
		}
	
	}

	//***  END PROGRAM ***\\ 

}
