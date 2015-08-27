package atm.stuff;
import java.io.IOException;
import java.util.List;

import atm.data.CSVFile;
import atm.user.options.User;

/**
 * @author Marina Sljivic
 *
 */
public class ATM {
	private static CashContainer cashContainer;//ATM's container of cash (10$, 20$, 50$ and 100$)
	private static List<User> usersList;//list of all ATM's Users
	public static final String USER_CSV = "users.csv";//the file from which we read the Users
	public static final String CASH_CSV = "cashstatus.csv";//the file from which we read the cash status
		
	//setters and getters of the data fields
	public static void setCash(CashContainer cash) {
		ATM.cashContainer = cash;
	}
	
	public static CashContainer getCash() {
		return cashContainer;
	}

	public static void setUsersList(List<User> usersList) {
		ATM.usersList = usersList;
	}

	public static List<User> getUsersList() {
		return usersList;
	}

	public static void on() throws IOException{
		
		//set the usersList reading it from a csv file and set the cash from the csv file
		setUsersList(CSVFile.readUsers(USER_CSV));
		setCash(CSVFile.readCashStatus(CASH_CSV));
		
		ATMSession.login();//create an ATM session and start with the first login()
		
						
	}




	
	
}
