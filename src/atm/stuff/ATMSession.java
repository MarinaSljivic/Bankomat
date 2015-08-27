package atm.stuff;
import java.util.Scanner;

import atm.data.CSVFile;
import atm.user.options.AdminUser;
import atm.user.options.RegularUser;
import atm.user.options.User;


/**
 * @author Marina Sljivic
 *
 */
public class ATMSession {
	
	private static User user;
	
	/**
	 * @return User object, the current user that is logged in. 
	 */
	public static User getUser() {
		return user;
	}
	/**
	 * Sets the current user that is logged in.
	 * @param user User object
	 */
	public static void setUser(User user) {
		ATMSession.user = user;
	}	

	/**
	 * Tries to login to a specific account with the inputed username and a password.<br>
	 * If the user inputs a wrong password for 3 times it will be blocked for half an hour.<br>
	 * If the user was blocked and it's not passed half an hour it will logout.<br>
	 * If the username and password are valid(a User with that username exists in the ATM's usersList),<br>
	 * it sets the current session User.
	 */
	public static void login(){
		//welcome message and take the username
		Scanner scan = new Scanner(System.in);
		System.out.println(	"*******************\n"
				  + "*     WELCOME     *\n"
				  + "*******************\n");
		System.out.println("Please enter your username: ");
		String username=scan.nextLine();
		
		//while there isn't a User with that usernamein the userList, its index is -1
		while(getIndexOfUser(username) == -1){ 
			System.out.println("Wrong username. Please enter your username again: ");
			username=scan.nextLine();
		}
		
		setUser(ATM.getUsersList().get(getIndexOfUser(username)));//set the current session user
		getUser().setBlocked();//set if it's blocked
		
		if(!getUser().isBlocked()){//if the User isn't blocked try to take the password
			
			System.out.println("Enter your password: ");
			String password=scan.nextLine();
			int countAttempts = 1; //the first attempt
			
			//while the password isn't correct and the number of attempts is less than 3 ask the password again
			while(!password.equals(getUser().getPassword()) && countAttempts<3){
				System.out.println("Wrong password. Enter your password again: ");
				password=scan.nextLine();
				countAttempts++;
			}
			
			//if the entered password is ok in no more than 3 attempts the login passed
			if(password.equals(getUser().getPassword())){
				System.out.println("*************************************************************");
				System.out.println("Logged in successfully!\n");
				
				if(getUser().isAdmin()){ //if it is an administrator user display the options for it
					AdminUser.displayOptions();
				}else{ //else display options for a regular user
					RegularUser.displayOptions();
				}
				
			}else{//if the password is wrong after 3 attempts, block the user
				getUser().setTimeBlocked(System.currentTimeMillis());//set the time when it's blocked
				getUser().setBlocked();//and that it is blocked

				//write the updated data (ATM's usersList) in users.csv file
				CSVFile.writeTo(ATM.USER_CSV, ATM.getUsersList());
				
				//print the message and logout()
				System.out.println("Sorry, wrong password entered 3 times, you have been blocked.\n");
				logout();
			}
		}else{//if the User is blocked, print it and the time it has to wait to be activated
			System.out.println("Sorry, you are blocked. "
					+"You will be activated in "
					//from half an hour subtract the minutes the user has been blocked (30min - (currentMin-blockedMin))
					+((30*60*1000 - System.currentTimeMillis() + getUser().getTimeBlocked())/(1000*60) %60 )+" minutes and "
					//same to find how many seconds rests
					+((30*60*1000 - System.currentTimeMillis() + getUser().getTimeBlocked())/1000 %60)+" seconds\n");
			logout(); //and logout
		}
	}

	/**
	 * @param username String
	 * @return the index of the User with that username in the ATM's usersList,<br>
	 * 		 -1 if such User doesn't exist.
	 */
	public static int getIndexOfUser(String username){
		int rez=-1;
		for(int i=0;i<ATM.getUsersList().size();i++){
			if(ATM.getUsersList().get(i).getUsername().equals(username)){
				rez=i;
			}
		}
		return rez;
	}
	
	/**
	 * Prints a goodbye message, sets the current user that<br>
	 * was logged in to null and calls login() again.
	 */
	public static void logout(){
				
		System.out.println("*************************************************************");
		System.out.println("Goodbye. Have a nice day.");
		System.out.println("*************************************************************");
		
		setUser(null); //"delete" the current session user
		
		login(); //and try to login() with another user
	}
	

	
	
}
