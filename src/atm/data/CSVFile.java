package atm.data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import atm.beans.CashContainer;
import atm.beans.User;

/**
 * Reads and writes to the csv files.
 * 
 * @author Marina Sljivic
 *
 */
public class CSVFile {
	
	/**
	 * Writes to a filename.csv file the list of Users.
	 * @param filename String
	 * @param users List<User>
	 */
	public static void writeTo(String filename, List<User> users){
		PrintWriter pw=null;
		try{
			pw=new PrintWriter(new FileOutputStream(new File(filename),false));//false because don't append,
			for(User user : users){//it replaces all with new updated data
				pw.write(user+"\n");
			}			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			pw.close();
		}
	}
	/**
	 * Writes to a filename.csv file the values in the cashContainer.
	 * @param filename String
	 * @param container CashContainer
	 */
	public static void writeTo(String filename, CashContainer container){
		PrintWriter pw=null;
		try{
			pw=new PrintWriter(new FileOutputStream(new File(filename),false));
			pw.write(container+"\n");		
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			pw.close();
		}
	}
	/**
	 * @param filename String
	 * @return a list of users List<User> reading it from a filename.csv file.
	 * @throws IOException
	 */
	public static List<User> readUsers(String filename) throws IOException{
		BufferedReader br=null;
		List<User> listOfUsers=new ArrayList<>();
			try{
				br=new BufferedReader(new FileReader(filename));
				String line;
				while((line=br.readLine())!=null){
					String[] userArray=line.split(",");
					User u=new User(userArray[0],userArray[1],Double.parseDouble(userArray[2]),
							Boolean.valueOf(userArray[3]),Long.parseLong(userArray[4]));
					listOfUsers.add(u);
				}
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				br.close();
			}
		return listOfUsers;
	}
	/**
	 * @param filename String
	 * @return a CashContainer reading it from a filename.csv file.
	 * @throws IOException
	 */
	public static CashContainer readCashStatus(String filename) throws IOException{
		BufferedReader br=null;
		CashContainer container = null;
			try{
				br=new BufferedReader(new FileReader(filename));
				String line;
				while((line=br.readLine())!=null){
					String[] cash=line.split(",");
					container = new CashContainer(Integer.parseInt(cash[0]),Integer.parseInt(cash[1]),
							Integer.parseInt(cash[2]),Integer.parseInt(cash[3]));
				}
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				br.close();
			}
		return container;
	}
}
