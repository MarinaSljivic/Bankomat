package atm.beans;

/**
 * ATM's User in accordance with the users.csv file
 * 
 * @author Marina Sljivic
 *
 */
public class User {
	private String username,password;//user's username and password
	private double principalAmount;//the amount of money the user has in the bank
	private boolean isAdmin, isBlocked;//isAdmin true if it's an administrator, false if it's a regular user
	//isBlocked true if the user in the current half an hour entered wrong his password 3 times
	private long timeBlocked;//the time in milliseconds when the user has been blocked
	
	/**
	 * Constructs a User given a username, password, amount of money in the bank,<br>
	 * true or false depending if it's an admin or not<br>
	 * and the time it was blocked in milliseconds.<br>
	 * @param username String
	 * @param password String
	 * @param principalAmount double
	 * @param isAdmin boolean
	 * @param timeBlocked long
	 */
	public User(String username,String password,double principalAmount,
			boolean isAdmin,long timeBlocked){
		setUsername(username);
		setPassword(password);
		setPrincipalAmount(principalAmount);
		setAdmin(isAdmin);
		setTimeBlocked(timeBlocked);
		setBlocked();//depending on the time, set is the user blocked or not
	}
	
	//setters and getters for the data fields
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public double getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(double principalAmount) {
		this.principalAmount = principalAmount;
	}
	
	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked() {
		//if the time it's been blocked is more than half an hour
		if(System.currentTimeMillis()-getTimeBlocked() > 30*60*1000){
			this.isBlocked = false;//is not blocked
		}else{//else it is
			this.isBlocked = true;
		}
	}

	public long getTimeBlocked() {
		return timeBlocked;
	}

	public void setTimeBlocked(long timeBlocked) {
		this.timeBlocked = timeBlocked;
	}


	@Override
	public String toString(){
		return getUsername()+","+getPassword()+","+getPrincipalAmount()
				+","+isAdmin()+","+getTimeBlocked();
	}
}
