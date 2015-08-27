package atm.user.options;

import java.util.Scanner;

import atm.data.CSVFile;
import atm.stuff.ATM;
import atm.stuff.ATMSession;

/**
 * This class contains all the options for a regular user.
 * 
 * @author Marina Sljivic
 *
 */
public class RegularUser {

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
			System.out.println("1. Raise money\n"
					+ "2. Check account balance\n"
					+ "3. Log off\n"
					+"*************************************************************");
			System.out.println("Enter the number near the option: ");

			boolean inputIsOk=false;//checks the user input if it's 1,2,3 or 4
			do{
				try{
					inputIsOk=true;//suppose it's ok
					option=Integer.parseInt(scan.next());//take the option choice
					switch (option){
						case 1:
							raiseMoney();
							break;
						case 2:
							checkAccountBalance();
							break;
						case 3:
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
		}while(option!=3);
		
	}

	/**
	 * Raise money from the User account in the bank if it's possible.
	 */
	public static void raiseMoney(){
		
		Scanner scan=new Scanner(System.in);
		boolean inputIsOk=false;//checks if the input for the cash to withdraw is ok
		int howMuchCash=0;
		while(!inputIsOk){//while it's not
			try{
				//take how much cash the User wants to withdraw
				System.out.println("Enter how much do you want to raise:");
				howMuchCash=Integer.parseInt(scan.next());
				
				if(howMuchCash>ATMSession.getUser().getPrincipalAmount()){//if it's more than the User has
					System.out.println("Sorry, your account balance is lower than that.");
				}
				else if(howMuchCash>ATM.getCash().getTotal()){//if it's more than the ATM's cashContainer has
					System.out.println("Sorry, the cash machine hasn't got enough cash. Please go to the bank.");
				}
				else if(howMuchCash%10!=0){//if it's an invalid value as 25
					System.out.println("Sorry, you can't raise that amount");
				}						
				else{//if it's ok calcolate how much the ATM's to give of each banknote
					calculate(howMuchCash);
					
					//save the changes in the users.csv and cashstatus.csv files
					CSVFile.writeTo(ATM.CASH_CSV, ATM.getCash());
					CSVFile.writeTo(ATM.USER_CSV, ATM.getUsersList());

					inputIsOk=true;
				}

			}catch(Exception e){//if the input isn't an integer
				inputIsOk=false;
				System.out.println("Wrong syntax. Try again.");
			}
		}
	}
	/**
	 * Calculates how much banknotes of 10$,20$,50$ and 100$ the ATM has to give the user if it's possible.<br>
	 * Taking care of the quantity of each banknote (if some is less than 5 try to avoid it).
	 * 
	 * DON'T LIKE THIS METHOD, COULD BE BETTER.
	 * 
	 * @param howMuchCash int
	 */
	public static void calculate(int howMuchCash){
		int[] outBill={0,0,0,0};//the quantity for each banknote the ATM has to give the user
		//the indexes correspond to the banknote's array indexes
		int[] banknote={10,20,50,100};
		
		int withdrawMoney = howMuchCash;//remember the cash to withdraw
		
		//set the quantity of the ATM's cashContainer
		int tens=ATM.getCash().getTens();
		int twenties=ATM.getCash().getTwenties();
		int fifties=ATM.getCash().getFifties();
		int hundreds=ATM.getCash().getHundreds();
		
		//first round: try to take the money passing through every bancnote that is not less than 5
		while(hundreds>5 
				&& howMuchCash!=0 && (howMuchCash-100)>=0){//as long as we can take a banknote
			outBill[3]++;//increase its counter
			howMuchCash-=100;//and subtract it from the howMuchCash
			hundreds--;
		}
	
		while(fifties>5
				&& howMuchCash!=0 && (howMuchCash-50)>=0){//as long as we can take a banknote
			outBill[2]++;//increase its counter
			howMuchCash-=50;//and subtract it from the howMuchCash
			fifties--;
		}
		
		while(twenties>5
				&& howMuchCash!=0 && (howMuchCash-20)>=0){//as long as we can take a banknote
			outBill[1]++;//increase its counter
			howMuchCash-=20;//and subtract it from the howMuchCash
			twenties--;
		}
		
		while(tens>5
				&& howMuchCash!=0 && (howMuchCash-10)>=0){//as long as we can take a banknote
			outBill[0]++;//increase its counter
			howMuchCash-=10;//and subtract it from the howMuchCash
			tens--;
		}
		
		//second round pass through every no matter what
		while(hundreds>0 
				&& howMuchCash!=0 && (howMuchCash-100)>=0){//as long as we can take a banknote
			outBill[3]++;//increase its counter
			howMuchCash-=100;//and subtract it from the howMuchCash
			hundreds--;
		}
	
		while(fifties>0
				&& howMuchCash!=0 && (howMuchCash-50)>=0){//as long as we can take a banknote
			outBill[2]++;//increase its counter
			howMuchCash-=50;//and subtract it from the howMuchCash
			fifties--;
		}
		
		while(twenties>0
				&& howMuchCash!=0 && (howMuchCash-20)>=0){//as long as we can take a banknote
			outBill[1]++;//increase its counter
			howMuchCash-=20;//and subtract it from the howMuchCash
			twenties--;
		}
		
		while(tens>0
				&& howMuchCash!=0 && (howMuchCash-10)>=0){//as long as we can take a banknote
			outBill[0]++;//increase its counter
			howMuchCash-=10;//and subtract it from the howMuchCash
			tens--;
		}
		
		if(howMuchCash == 0){//if there is no more money to give
			//set the new status in the ATM's cashContainer
			ATM.getCash().setTens(ATM.getCash().getTens()-outBill[0]);
			ATM.getCash().setTwenties(ATM.getCash().getTwenties()-outBill[1]);
			ATM.getCash().setFifties(ATM.getCash().getFifties()-outBill[2]);
			ATM.getCash().setHundreds(ATM.getCash().getHundreds()-outBill[3]);
			
			//set the new status of the User's principalAmount
			ATMSession.getUser().setPrincipalAmount(ATMSession.getUser().getPrincipalAmount() - withdrawMoney);
			
			//print how much the user has got of every banknote
			System.out.println("You got:");
			for(int i=0;i<4;i++){
				System.out.print(outBill[i]+"*"+banknote[i]+" | ");
			}
		}else{
			System.out.println("Sorry, withdraw failed.");
		}
		System.out.println();
	}
	
	/**
	 * Check out the principalAmount of the User.
	 */
	public static void checkAccountBalance(){
		System.out.println("Your account balance is: "+ATMSession.getUser().getPrincipalAmount());
	}
	

}
