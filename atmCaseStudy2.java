//Class to know whether cash is available and dispense if needed
class CashDispenser{
	//variables
	private final static double INITIALAMOUNT = 500000;
	private double moneyAvailable;
	
	//Constructor when invoked loads INITIALAMOUNT into the dispenser
	//It is called every time the system starts
	CashDispenser(){
		moneyAvailable = INITIALAMOUNT;
	}
	//Method to dispense cash and decrease money in the dispenser
	void dispenseCash(double amount) {
		double moneyRequired = amount;
		moneyAvailable -= moneyRequired;
	}
	//Method to check how much cash is available in the dispenser
	boolean isCashAvailable(double amount) {
		double moneyRequired = amount;
		if(moneyAvailable>=moneyRequired)
			return true;
		else
			return false;
	}
}

//Class to just return a true value to confirm that cash has been deposited 
class DepositSlot{
	boolean isCashDeposited() {
		return true;
	}
}

//Class to start the ATM
public class atmCaseStudy2 {
	
	public static void main (String[] args) {
		ATMCaseStudy atm = new ATMCaseStudy();
		atm.start();
	}
}
#testing
