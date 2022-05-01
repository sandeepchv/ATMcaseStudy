# ATMcaseStudy
Object oriented thinking has been used to create a system which acts as an ATM. The ATM can help the user to perform functions such as withdrawing money from the cash dispenser, depositing money in the deposition slot, checking the remaining balance in their account and transferring money from one account to another.
		The user is asked to enter the account number associated with their account. After that, their pin must be entered and the system will check whether the entered pin matches with original pin. If so, an OTP is generated and sent to the user’s phone. The user will be proceeded into the main menu if the OTP entered is right.
		In the main menu the user can choose the action they want to perform(withdraw, deposit, check balance, get mini statement, transfer to another account, change pin, block account). After entering the option to check the balance, the amount remaining in their account will be displayed on the console. If the withdraw option is chosen, amount of money to be withdrawn will be asked and the required amount will be dispensed by the cash dispenser. Cash dispensed will be deducted from the user’s account. If the option to deposit is chosen, the user will be prompted to drop the cash in the deposit slot and the amount will be added to their account. This amount will not go into the cash dispenser. User can also change their pin by entering a new one. Amount can also be transferred to another account from the user’s when the other account number is provided. Amount is deducted from the user’s account and will be added to the other account. User can also block their account. Once they exit after blocking the account, they can no longer use the account. All the recent transactions appear when the option to display mini statement is chosen.
		User can perform as many transactions as they wish until they choose the exit option. Then thank you message will be displayed. This means the user has logged out of their account and can leave.


Account class will contain all the private data of the user such as account number, pin, balance and phone number. Database class is used to create all the pre-existing accounts with an object array. Additional layers of encapsulation are also added. ATMCaseStudy class is used to verify the pin and perform the option from the main menu. DepositSlot class is just present to ensure cash has been deposited and always returns a true value. CashDispenser class initiates with a fixed amount once the system runs. Transaction is an abstract class which is inherited by BalanceInquiry, Withdrawal, Deposit, ChangePIN, TransferMoney, BlockAccount and getMiniStatement to perform their respective tasks.