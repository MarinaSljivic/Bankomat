package atm.user.options;

import java.util.Scanner;

import atm.data.CSVFile;
import atm.stuff.ATM;
import atm.stuff.ATMSession;

/**
 * This class contains all the options for an administrator user.
 * 
 * @author Marina Sljivic
 *
 */
public class AdminUser {
	
	/**
	 * Displays to the user the options (near every possible option there is a number),<br>
	 * depending on his choice launches the selected option.
	 */
	public static void displayOptions() {
		Scanner scan=new Scanner(System.in);
		int option=0;
		do{//print the options while it's not selected option 4 - logout
			System.out.println("*************************************************************");
			System.out.println("OPTIONS");
			System.out.println("1. Add new user\n"
					+ "2. Remove an user\n"
					+ "3. Change banknotes status\n"
					+ "4. Log off\n"
					+ "*************************************************************");
			System.out.println("Enter the number near the option: ");
			
			boolean inputIsOk=false;//checks the user input if it's 1,2,3 or 4
			do{
				try{
					inputIsOk=true;//suppose it's ok
					option=Integer.parseInt(scan.next());//take the option choice
					switch (option){
						case 1:
							addUser();
							break;
						case 2:
							removeUser();
							break;
						case 3:
							changeBanknoteStatus();
							break;
						case 4:
							ATMSession.logout();
							break;
						default:
							inputIsOk=false;//if the choice is an integer but not 1,2,3 or 4 then the input isn't ok
							System.out.println("You entered a wrong number. Try again: ");
							break;
					}
				}catch(Exception e){//if an integer wasn't entered
					inputIsOk=false;
					System.out.println("Wrong syntax. Enter the NUMBER near the option: ");
				}
			}while(!inputIsOk);
		}while(option!=4);		
	}

	/**
	 * Adds to ATM's usersList a new user (administrator or regular).
	 */
	public static void addUser(){
		//take the username of the new User
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter username: ");
		String username=scan.nextLine();
		
		//while the username isn't free, contains a ',' or is empty 
		//ask again to input another username
		while(ATMSession.getIndexOfUser(username)!=-1 || username.contains(",") || username.equals("")){
			System.out.println("Invalid username. Choose another username:");
			username=scan.nextLine();		
		}
		
		//while the password for the new user is not valid(contains a ',' or is empty)
		//keep asking to enter it again
		System.out.println("Enter password: ");
		String password=scan.nextLine();
		while(password.contains(",") || password.equals("")){
			System.out.println("Invalid password. Enter the password again.");
			password=scan.nextLine();
		}
		
		//enter if the user will be a regular one or an administrator
		String userRole;
		do{
			System.out.println("Enter 'r' for regular user, 'a' for administrator user: ");
			userRole=scan.next();
			
			//if it's regular we need an amount
			if(userRole.equals("r")){
				boolean inputIsOk=false;
				System.out.println("Enter the user's principal amount: ");
				//check if the amount is valid, if not try again
				do{	
					try{
						inputIsOk=true;
						double amount=Double.parseDouble(scan.next());
						//create a new User and add it to the ATM's usersList
						User newUser = new User(username, password, amount, false, 0);
						ATM.getUsersList().add(newUser);
						System.out.println("User added successfully.");
						//write the updated data (in the ATM's usersList) in users.csv file
						CSVFile.writeTo(ATM.USER_CSV, ATM.getUsersList());
						
					}catch (NumberFormatException e){
						inputIsOk=false;
						System.out.println("Wrong syntax. Enter the user's principal amount: ");
					}
				}while(!inputIsOk);
			}
			//if it's an administrator user then just add the user
			else if(userRole.equals("a")){
				User newUser = new User(username, password, 0, false, 0);
				ATM.getUsersList().add(newUser);

				//write the updated data (in the ATM's usersList) in users.csv file
				CSVFile.writeTo(ATM.USER_CSV, ATM.getUsersList());
				
			}
			//if you entered something different from 'a' or 'r'
			else{
				System.out.println("You entered a wrong letter. Try again.");
			}

		}while(!userRole.equals("r") && !userRole.equals("a"));
		
	}
	
	/**
	 * Removes an User in the ATM's usersList if it exists.
	 */
	public static void removeUser(){
		//take the username of the user you want remove
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the username of the user you want to remove: ");
		String username=scan.nextLine();
		
		//if the username is not of the current administrator user
		if(!ATMSession.getUser().getUsername().equals(username)){
			try{
				//try to remove the user
				ATM.getUsersList().remove(ATMSession.getIndexOfUser(username));
				//write the updated data (in the ATM's usersList) in users.csv file
				CSVFile.writeTo(ATM.USER_CSV, ATM.getUsersList());
				System.out.println("User removed successfully.");
				
			}catch(UnsupportedOperationException e){//if the remove operation is not supported by this list
				System.out.println("Sorry, it was impossible to remove that user.");
			}catch(IndexOutOfBoundsException e){//if the index of the user is out of range
				System.out.println("The username you entered doesn't exist.");
			}
		}else{//if the inputed username is of the current administrator
			System.out.println("You can't remove yourself.");
		}
	}
	
	/**
	 * For each banknotes in the ATM's cashContainer prints out the status.
	 */
	public static void printBanknoteStatus(){ 
		System.out.println("Banknotes' current status is:\n"
				+ "10$ - "+ATM.getCash().getTens()+" | "
				+ "20$ - "+ATM.getCash().getTwenties()+" | "
				+ "50$ - "+ATM.getCash().getFifties()+" | "
				+ "100$ - "+ATM.getCash().getHundreds()+" | \n"
						+ "Total - "+ATM.getCash().getTotal());
	}
	
	/**
	 * Change the status (the quantity) of the banknotes.
	 */
	public static void changeBanknoteStatus(){
		Scanner scan=new Scanner(System.in);
		int[] banknotesArray={10,20,50,100};//the banknotes the ATM has: 10$,20$,50$ and 100$
		int[] statusArray=new int[4];
		printBanknoteStatus();//print the current banknotes' status
		
		for(int i=0;i<banknotesArray.length;i++){
			boolean inputIsOk=false;//checks if the new inputed status for a banknote is ok
			while(!inputIsOk){
				try{
					//try to take the new status for each banknote
					//the new status cannot be more than 100 or less than 5
					
					System.out.println("Type new status for the "+banknotesArray[i]+"$:");
					int newStatus=Integer.parseInt(scan.next());
					if(newStatus>100){
						System.out.println("Cannot have more than 100 "+banknotesArray[i]+"$");
						inputIsOk=false;
					}else if(newStatus<5){
						System.out.println("Cannot have less than 5 "+banknotesArray[i]+"$");
						inputIsOk=false;
					}else{
						statusArray[i]=newStatus;
						inputIsOk=true;
					}
					
				}catch(Exception e){
					inputIsOk=false;
					System.out.println("Invalid status. Try again.");
					scan.nextLine();
				}
			}
		}
		//set the changes in the ATM's cashContainer
		ATM.getCash().setTens(statusArray[0]);
		ATM.getCash().setTwenties(statusArray[1]);
		ATM.getCash().setFifties(statusArray[2]);
		ATM.getCash().setHundreds(statusArray[3]);
		
		//write the updated data (in the ATM's cashContainer) cashstatus.csv file
		CSVFile.writeTo(ATM.CASH_CSV, ATM.getCash());
		printBanknoteStatus();//print the new status
	}
}
