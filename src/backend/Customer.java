package backend;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Customer extends User
{
    private final ArrayList<Account> accounts = new ArrayList<>();
    private boolean approved;

    Customer(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
        approved = false;
    }

    public boolean menu()
    {
        boolean notDone = true;
        boolean exit = false;

        do {
            int accountNumber;
            Database database = Database.getDatabase();
            Scanner scan = new Scanner(System.in);
            System.out.println("\nHello " + getUsername() + "! \n Select an option from below and enter int he appropriate number to continue");
            System.out.println("1. View account(s)");
            System.out.println("2. Make transfer");
            System.out.println("3. Make deposit");
            System.out.println("4. Make withdraw");
            System.out.println("5. Make account");
            System.out.println("6. Log out");
            System.out.println("7. Quit Application");
            int result = parseInt(scan.nextLine());


            if (approved)
            {
                switch (result) {
                    case 1 -> {
                        accountNumber = accountMenu();
                        accounts.get(accountNumber).view();
                    }
                    case 2 -> {
                        accountNumber = accountMenu();
                        accounts.get(accountNumber).transfer(getUsername(), accountNumber);
                    }
                    case 3 -> {
                        accountNumber = accountMenu();
                        accounts.get(accountNumber).deposit(getUsername(), accountNumber);
                    }
                    case 4 -> {
                        accountNumber = accountMenu();
                        accounts.get(accountNumber).withdraw(getUsername(), accountNumber);
                    }
                    case 5 -> {
                        System.out.println("What name do you want for this account?");
                        accounts.add(new Account(scan.nextLine()));
                    }
                    case 6 -> notDone = false;
                    default -> {
                        notDone = false;
                        exit = true;
                    }
                }
            } else {
                System.out.println("Your account is not approved");
                notDone = false;
            }
        } while (notDone);

        return !exit;
    }

    public int accountMenu()
    {
        System.out.println("Please select the account that you would like to use accounts");
        for(int i = 0; i < accounts.size(); ++i)
        {
            System.out.println(i+1 + ". " + accounts.get(i).getName());
        }

        System.out.println();

        Scanner scan = new Scanner(System.in);

        return parseInt(scan.nextLine()) - 1;
    }

    public void intakeTransaction(String fromUser, int fromAccount, String toUser, int toAccount, double amount)
    {
        accounts.get(toAccount).acceptTransaction(fromUser, fromAccount, toUser, toAccount, amount);
    }

    public void setApproved(boolean approved)
    {
        this.approved = approved;
    }
}
