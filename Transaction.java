
import java.util.Scanner;

//Transaction can be done in various ways like checking of balance, withdrawal, deposit and transferring money.
//Hence an abstract class has been taken 
public abstract class Transaction{
	
	private int accountNumber;
	private Database database;
	
	//constructor
	public Transaction(int userAccountNumber, Database atmDatabase){
		accountNumber = userAccountNumber;
		database = atmDatabase;
	}
	
	//Normal methods can also be used in case of abstract class
	int getAccountNumber() {
		return accountNumber;
	}
	Database getBankDatabase() {
		return database;
	}
	
	//Abstract method which will be inherited by the inherited classes
	abstract public void run();
}

//Class to check the balance in an account
class BalanceInquiry extends Transaction{
	
	public BalanceInquiry(int userAccountNumber,Database database) {
		//Super constructor 
		super( userAccountNumber, database);
	}
	//Method which will override the method in super class
	public void run() {
		Database bankDatabase = getBankDatabase();
		double availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
		System.out.println("\nAvailable Balance:");
		System.out.println(availableBalance);
	}
}

//Class to withdraw money with the help of a cash dispenser
class Withdrawal extends Transaction{
	private double amount;
	private CashDispenser cashDispenser;
	Scanner s = new Scanner(System.in);
	
	public Withdrawal(int userAccountNumber, Database atmBankDatabase, CashDispenser atmCashDispenser) {
		//Super Constructor
		super(userAccountNumber, atmBankDatabase);
		//Additional feature only pertaining to this class
		cashDispenser = atmCashDispenser;
	}
	
	//Method which will override the method on super class
	public void run() {
		boolean cashDispensed = false;
		double availableBalance;
		Database bankDatabase = getBankDatabase();
		while(!cashDispensed) {
			System.out.println("\nEnter the amount of money you want to withdraw:");
			amount = s.nextDouble();
			availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
			//Checking for available balance
			if(amount<=availableBalance) {
				//Checking for cash in the cash dispenser
				if(cashDispenser.isCashAvailable(amount)) {
					bankDatabase.debit(getAccountNumber(), amount);
					cashDispenser.dispenseCash(amount);
					cashDispensed = true;
					System.out.println("\nPlease collect the dispensed cash.");
				}
				else
					System.out.println("\nInsufficient cash available. choose smaller amount.");		
			}
			else
				System.out.println("\nInsufficient balance in your acccount.");
		}
	}
}

//Class to deposit money with the help of a deposit slot
class Deposit extends Transaction{
	private double amount;
	private DepositSlot depositSlot;
	Scanner s = new Scanner(System.in);
	
	public Deposit(int userAccountNumber, Database bankDatabase, DepositSlot atmDepositSlot) {
		//Super Constructor
		super(userAccountNumber, bankDatabase);
		//Additional feature pertaining only to this class
		depositSlot = atmDepositSlot;
	}
	
	//Method which will override the method in the super class
	public void run() {
		Database bankDatabase = getBankDatabase();
		System.out.println("\nEnter the amount you want to deposit:");
		amount = s.nextDouble();
		System.out.println("\n*Please deposit the amount in the Deposit Slot.*");
		boolean cashReceived = depositSlot.isCashDeposited();
		//Checking whether cash is received in the deposit slot
		if(cashReceived) {
			System.out.println("\nCash received and the amount will be added to your account.");
			bankDatabase.credit(getAccountNumber(), amount);
		}
		else
			System.out.println("\nCash not received.");
	}
}

//Class to change the pin of existing account
class ChangePIN extends Transaction{
	public ChangePIN(int userAccountNumber,Database database) {
		super(userAccountNumber,database);
	}
	
	public void run() {
		Scanner s = new Scanner(System.in);
		int newPIN;
		Database bankDatabase = getBankDatabase();
		System.out.println("Enter the new pin number");
		newPIN = s.nextInt();
		bankDatabase.changePin(getAccountNumber(),newPIN);
		System.out.println("\nYour PIN has been successfully changed");
	}
}

//Class to transfer amount to another account
class TransferMoney extends Transaction{
	public TransferMoney(int userAccountNumber,Database database) {
		super(userAccountNumber,database);
	}
	public void run() {
		Scanner s = new Scanner(System.in);
		boolean cashTransferred = false;
		double transferAmount;
		double availableBalance;
		int accountNumberToTransfer;
		Database bankDatabase = getBankDatabase();
		System.out.println("Enter the account number of the account you want to transfer the amount to:");
		accountNumberToTransfer = s.nextInt();
		while(!cashTransferred) {
			System.out.println("Enter the amount to be transferred:");
			transferAmount = s.nextDouble();
			availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
			if(transferAmount<=availableBalance) {
				bankDatabase.TransferAmount(getAccountNumber(),accountNumberToTransfer,transferAmount);
				System.out.println("\nAmount has been successfully transferred.");
				cashTransferred = true;
			}
			else
				System.out.println("Insufficient cash available in your account. Please enter a smaller amount");
		}
	}
}

//Class to block the account
class BlockAccount extends Transaction{
	public BlockAccount(int userAccountNumber,Database database) {
		super(userAccountNumber,database);
	}
	public void run() {
		Database bankDatabase = getBankDatabase();
		System.out.println("\nYour account is being blocked. Please wait..  ");
		bankDatabase.setBlockStatus(getAccountNumber());
		System.out.println("\nYour Account has been successfully blocked. No further transactions can take place once you exit.");
	}
}

class getMiniStatement extends Transaction{
	public getMiniStatement(int userAccountNumber,Database database) {
		super(userAccountNumber,database);
	}
	public void run() {
		Database bankDatabase = getBankDatabase();
		bankDatabase.printMiniStatement(getAccountNumber());
	}
}