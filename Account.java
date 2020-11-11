import java.util.ArrayList;

//Class containing the main attributes and methods of an account
class Account {
	//Encapsulated data
	private int accountNO;
	private int pin;
	private double balance;
	private long phoneNumber;
	private boolean blocked;
	private ArrayList<String> miniStatement = new ArrayList<>();
	
	//Constructor
	Account(int accountNumber, int PIN, double Balance, long PhoneNumber,boolean Blocked){
		accountNO = accountNumber;
		pin = PIN;
		balance = Balance;
		phoneNumber = PhoneNumber;
		blocked = Blocked;
		miniStatement.add("");
		miniStatement.add("");
		miniStatement.add("");
	}
	//Method to check whether the entered pin matches with the correct pin 
	boolean validatePIN(int enteredPin) {
		if(enteredPin==pin)
			return true;
		else
			return false;
	}
	
	//Methods to get the private data
	int getAccountNumber() {
		return accountNO;
	}
	double getAvailableBalance() {
		return balance;
	}
	long getPhoneNumber() {
		return phoneNumber;
	}
	boolean getBlockedStatus() {
		return blocked;
	}
	//Adding money to the account
	void credit(double amount) {
		balance += amount;
		miniStatement.add(String.valueOf(amount)+" has been credited to your account." );
	}
	//Taking out money from the account
	void debit(double amount) {
		balance -= amount;
		miniStatement.add(String.valueOf(amount)+" has been debited from your account.");
	}
	//Set new PIN
	void setNewPin(int newPIN) {
		pin = newPIN;
	}
	//To block the account
	void setBlockedStatus(boolean userBlocked) {
		blocked = userBlocked;
	}
	void printMiniStatement() {
		for(int i=0;i<3;i++) {
			System.out.println(miniStatement.get(miniStatement.size()-i-1));
		}
	}
}

//Class which acts as a database for all the existing accounts
class Database{
	
	//Object array of all the existing accounts
	private Account accounts[];
	//Constructor which initiates all the accounts when called
	Database(){
		//All the pre-existing accounts
		accounts = new Account[10];
		accounts[0] = new Account(12345,54321,1000.0,9887565878l,false);
		accounts[1] = new Account(67890,98765,200.0,9987878812l,false );
		accounts[2] = new Account(78967,23457,9800.0,8666732719l,false);
		accounts[3] = new Account(43213,98769,5400.0,7647742761l,false);
		accounts[4] = new Account(99087,87554,100.0,6306327615l,false);
		accounts[5] = new Account(10298,87657,2660.0,9018274563l,false);
		accounts[6] = new Account(87554,89086,7600.0,9000959666l,false);
		accounts[7] = new Account(17065,45095,6000.0,8712341155l,false);
		accounts[8] = new Account(65379,43631,5743.0,6306727443l,false);
		accounts[9] = new Account(23456,76543,500.0,9785453237l,false);
	}
	
	//Method to verify the user using the ATM with the entered pin
	boolean verifyUser(int userAccountNumber, int userPIN) {
			Account userAccount = getAccount(userAccountNumber);
			if(userAccount != null)
				return userAccount.validatePIN(userPIN);
			else
				return false;
		}
	//Method to get the user's account
	private Account getAccount(int enteredAccountNumber) {
		for(int i=0;i<10;i++) {
			if(enteredAccountNumber == accounts[i].getAccountNumber())
				return accounts[i];
		}
		return null;
	}
	//Method to get the user's phone number
	long getPhoneNumber(int userAccountNumber) {
		return getAccount(userAccountNumber).getPhoneNumber()%10000;
	}
	//Method to retrieve private data
	double getAvailableBalance(int userAccountNumber) {
		return getAccount(userAccountNumber).getAvailableBalance();
	}
	
	//Methods to debit or credit money using account class
	void credit(int userAccountNumber, double amount) {
		getAccount(userAccountNumber).credit(amount);
	}
	void debit(int userAccountNumber, double amount) {
		getAccount(userAccountNumber).debit(amount);
	}
	//Method to change PIN
	void changePin(int userAccountNumber, int newPin) {
		getAccount(userAccountNumber).setNewPin(newPin);
	}
	//Method to transfer money
	void TransferAmount(int userAccountNumber,int userAccountToTransfer,double amount) {
		getAccount(userAccountNumber).debit(amount);
		getAccount(userAccountToTransfer).credit(amount);
	}
	//To block an account
	void setBlockStatus(int userAccountNumber) {
		getAccount(userAccountNumber).setBlockedStatus(true);
	}
	//Get the blocked status of the account
	boolean getBlockStatus(int userAccountNumber) {
		return getAccount(userAccountNumber).getBlockedStatus();
	}
	void printMiniStatement(int userAccountNumber) {
		getAccount(userAccountNumber).printMiniStatement();
	}
}
