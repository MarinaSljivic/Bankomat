package atm.atmstuff;

/**
 * ATM's container of cash (10$, 20$, 50$ and 100$) each banknote can't be set to more than 100.

 * @author Marina Sljivic
 *
 */
public class CashContainer {
	
	private int tens, twenties, fifties, hundreds;
	
	/**
	 * Constructs a cash container given the number of banknotes for 10$, 20$, 50$ and 100$.
	 * 
	 * @param tens int
	 * @param twenties int
	 * @param fifties int
	 * @param hundreds int
	 */
	public CashContainer(int tens, int twenties, int fifties, int hundreds){
		setTens(tens);
		setTwenties(twenties);
		setFifties(fifties);
		setHundreds(hundreds);
	}
	
	//getters and setters for the data fields (cannot set more than 100)
	public int getTens() {
		return tens;
	}
	public void setTens(int tens) {
		if(tens<=100){
			this.tens = tens;
		}
	}
	public int getTwenties() {
		return twenties;
	}
	public void setTwenties(int twenties) {
		if(twenties<=100){
			this.twenties = twenties;	
		}
	}
	public int getFifties() {
		return fifties;
	}
	public void setFifties(int fifties) {
		if(fifties<=100){
			this.fifties = fifties;
			
		}
	}
	public int getHundreds() {
		return hundreds;
	}
	public void setHundreds(int hundreds) {
		if(hundreds<=100){
			this.hundreds = hundreds;
		}
	}
	
	/**
	 * @return total amount of cash in the ATM's cash container
	 */
	public int getTotal() {
		return this.tens*10+this.twenties*20+this.fifties*50+this.hundreds*100;
	}
	
	
	@Override
	public String toString(){
		return getTens()+","+getTwenties()+","+getFifties()+","+getHundreds();
	}
	
}
