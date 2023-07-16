import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean verifyPin(String pin) {
        return userPin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        Transaction depositTransaction = new Transaction("Deposit", amount);
        transactionHistory.add(depositTransaction);
        System.out.println("Deposit successful. Amount: " + amount);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            Transaction withdrawalTransaction = new Transaction("Withdrawal", amount);
            transactionHistory.add(withdrawalTransaction);
            System.out.println("Withdrawal successful. Amount: " + amount);
        } else {
            System.out.println("Insufficient balance. Withdrawal failed.");
        }
    }

    public void transfer(double amount, Account recipientAccount) {
        if (balance >= amount) {
            balance -= amount;
            recipientAccount.deposit(amount);
            Transaction transferTransaction = new Transaction("Transfer", amount);
            transactionHistory.add(transferTransaction);
            System.out.println("Transfer successful. Amount: " + amount);
        } else {
            System.out.println("Insufficient balance. Transfer failed.");
        }
    }

    public void displayTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println("Type: " + transaction.getType() + ", Amount: " + transaction.getAmount());
        }
    }
}

public class ATMSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create an account
        Account account = new Account("user123", "1234");

        // Prompt user for user id and pin
        System.out.println("Welcome to the ATM System!");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter User PIN: ");
        String userPin = scanner.nextLine();

        // Verify user id and pin
        if (userId.equals(account.getUserId()) && account.verifyPin(userPin)) {
            System.out.println("User ID and PIN verified. ATM functionalities unlocked.");
            boolean quit = false;

            while (!quit) {
                System.out.println("\nATM Menu:");
                System.out.println("1. View Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        account.displayTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        scanner.nextLine();
                        account.withdraw(withdrawalAmount);
                        break;
                    case 3:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine();
                        account.deposit(depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Enter recipient User ID: ");
                        String recipientUserId = scanner.nextLine();
                        Account recipientAccount = new Account(recipientUserId, "");
                        account.transfer(transferAmount, recipientAccount);
                        break;
                    case 5:
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid User ID or PIN. ATM functionalities locked.");
        }

        scanner.close();
    }
}