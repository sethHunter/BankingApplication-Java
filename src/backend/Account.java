package backend;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Account
{
    private final ArrayList<Transaction> transactions = new ArrayList<>();
    private double balance;
    private final String name;

    Account(String name)
    {
        this.name = name;
        balance = 0;
    }

    public void view()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n\n Transactions");
        System.out.println("What value would you like to sort by?");
        System.out.println("1. Date");
        System.out.println("2. User");
        System.out.println("3. Amount");
        int result = parseInt(scan.nextLine());

        switch (result) {
            case 1 -> transactions.sort(Transaction.sortByDate);
            case 2 -> transactions.sort(Transaction.sortByUser);
            default -> transactions.sort(Transaction.sortByAmount);
        }
        for (Transaction t:transactions)
        {
            t.display();
        }
    }

    public void transfer(String fromUser, int fromAccount)
    {
        Database database = Database.getDatabase();
        Scanner scan = new Scanner(System.in);
        System.out.println("How much are you wanting to send?");
        double amount = Double.parseDouble(scan.nextLine());

        System.out.println("Send to");
        System.out.println("1. Your accounts");
        System.out.println("2. Another user");
        int result = parseInt(scan.nextLine());

        switch (result)
        {
            case 1:
                balance = balance-amount;
                int toAccount = database.selectUserAccount(fromUser);
                Transaction t1 = new Transaction(fromUser, fromAccount, fromUser, toAccount, -(amount), balance);
                database.sendTransaction(fromUser, fromAccount, fromUser, toAccount, amount);
                transactions.add(t1);
                database.logTransaction(t1);
                break;

            case 2:
                System.out.println("Type the username of the person you are wanting to send money");
                String toUser = scan.nextLine();

                int toAccount1 = database.selectUserAccount(fromUser);
                balance = balance-amount;
                Transaction t2 = new Transaction(fromUser, fromAccount, toUser, toAccount1, -(amount), balance);
                database.sendTransaction(fromUser, fromAccount, toUser, toAccount1, amount);
                transactions.add(t2);
                database.logTransaction(t2);
                break;

            default:
                break;
        }
    }

    public void withdraw(String from, int fromAccount)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("How much would you like to withdraw?");
        double result = Double.parseDouble(scan.nextLine());

        balance = balance - result;

        Transaction t = new Transaction(from, fromAccount, from, fromAccount, result, balance);
        transactions.add(t);
        Database database = Database.getDatabase();
        database.logTransaction(t);

    }

    public void deposit(String from, int fromAccount)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("How much would you like to deposit?");
        double result = Double.parseDouble(scan.nextLine());

        balance = balance + result;

        Transaction t = new Transaction(from, fromAccount, from, fromAccount, result, balance);
        transactions.add(t);
        Database database = Database.getDatabase();
        database.logTransaction(t);
    }

    public void acceptTransaction(String fromUser, int fromAccount, String toUser, int toAccount, double amount)
    {
        balance = balance + amount;
        Transaction t = new Transaction(fromUser, fromAccount, toUser, toAccount, amount, balance);
        transactions.add(t);
        Database database = Database.getDatabase();
        database.logTransaction(t);
    }

    public String getName() {
        return name;
    }
}
