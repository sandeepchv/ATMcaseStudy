import java.util.Random;
import java.util.Scanner;

//Class to perform all the functions of an ATM
class ATMCaseStudy {
	
	//variables
	private boolean verified;
	private int accountNumber;
	private CashDispenser cashDispenser;
	private DepositSlot depositSlot;
	private Database database;
	
	//Options appearing in the main menu
	private static final int CHECKBALANCE = 1;
	private static final int WITHDRAWAL = 2;
	private static final int DEPOSIT = 3;
	private static final int CHANGEPIN = 4;
	private static final int TRANSFERAMOUNT = 5;
	private static final int BLOCKACCOUNT = 6;
	private static final int MINISTATEMENT = 7;
	private static final int EXIT = 8;
	
	Scanner s = new Scanner(System.in);
	
	//Constructor to allot a cash dispenser, deposit slot and provide database
	ATMCaseStudy(){
		verified = false;
		accountNumber = 0;
		cashDispenser = new CashDispenser();
		depositSlot = new DepositSlot();
		database = new Database();
	}
	
	//Method for performing the tasks
	public void start() {
		while(true) {
			while(!verified) {
				System.out.println("\nWelcome to the Trust Us Bank!");
				checkVerifiedStatus();
			}
			if(BlockStatus()) {
				System.out.println("\nThis Account has been blocked. No further transactions can take place.");
			}
			else {
				if(checkOTP()) {
					performTransactions();
				}
			}
			verified = false;
			accountNumber = 0;
			System.out.println("\nThank you for using our bank!");
		}
	}
	
	//Method to confirm given pin matches with their account number
	private void checkVerifiedStatus() {
		System.out.println("\nEnter your Account Number:");
		int acc = s.nextInt();
		System.out.println("\nEnter your pin:");
		int pin = s.nextInt();
		//Get status by checking from the database
		verified = database.verifyUser(acc,pin);
		
		if(verified)
			accountNumber = acc;//assigning account number
		else 
			System.out.println("Invalid Input. Please Try Again\n");
	}
	//Method to generate an OTP and send it to the user's phone number
	//OTP entered by the user is checked with the randomly generated OTP
	private boolean checkOTP() {
		Random rand = new Random();
		int randNO = rand.nextInt(1000000);
		long lastFourPNO = database.getPhoneNumber(accountNumber);
		System.out.println("\nThe OTP sent to your phone number ending with "+lastFourPNO+" is "+randNO);
		System.out.println("Enter the OTP");
		int userOTP = s.nextInt();
		if(userOTP==randNO)
			return true;
		else {
			System.out.println("Invalid input. Please Try Again");
			return false;
		}	
	}
	//To block the account
	private boolean BlockStatus() {
		boolean op = database.getBlockStatus(accountNumber);
		return op;
	}
	//Method to display main menu and take the input
	private int displayMainMenu() {
		System.out.println("\nMain Menu:");
		System.out.println("1.Check Balance");
		System.out.println("2.Withdrawal");
		System.out.println("3.Deposit");
		System.out.println("4.Change PIN");
		System.out.println("5.Transfer Amount To Another Account");
		System.out.println("6.Block your Account");
		System.out.println("7.Print mini statement");
		System.out.println("8.Exit");
		System.out.println("\nEnter an option:");
		int temp = s.nextInt();
		return temp;
	}
	
	//Method to choose the type of transaction
	private void performTransactions() {
		Transaction tr = null;
		boolean exited = false;
		
		while(!exited) {
			int choice = displayMainMenu();
			
			switch(choice) {
			
			case CHECKBALANCE :
			case WITHDRAWAL :
			case DEPOSIT :
			case CHANGEPIN :
			case TRANSFERAMOUNT :
			case BLOCKACCOUNT :
			case MINISTATEMENT :
				tr = startTransaction(choice);//Create the transaction
				tr.run();//Run the transaction
				break;
			case EXIT :
				System.out.println("\nReturning to the start");
				exited = true;
				break;
			default :
				System.out.println("\nYou did not enter a valid input");
				break;
			}
		}
	}
	
	//Method to start the transaction
	private Transaction startTransaction (int type) {
		Transaction temp = null;
		
		switch(type) {
		case CHECKBALANCE :
			temp = new BalanceInquiry(accountNumber, database);
			break;
		case WITHDRAWAL:
			temp = new Withdrawal ( accountNumber, database,cashDispenser);
			break;
		case DEPOSIT:
			temp = new Deposit( accountNumber,database, depositSlot);
			break;
		case CHANGEPIN :
			temp = new ChangePIN(accountNumber, database);
			break;
		case TRANSFERAMOUNT :
			temp = new TransferMoney(accountNumber,database);
			break;
		case BLOCKACCOUNT :
			temp = new BlockAccount(accountNumber,database);
			break;
		case MINISTATEMENT :
			temp = new getMiniStatement(accountNumber,database);
			break;
		}
		return temp;
	}
}
//testing
