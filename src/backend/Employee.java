package backend;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Employee extends User
{
    Employee(String username, String password, String firstName, String lastName)
    {
        super(username, password, firstName, lastName);

    }

    public boolean menu()
    {
        boolean notDone = true;
        boolean exit = false;

        do {
            Database database = Database.getDatabase();
            Scanner scan = new Scanner(System.in);

            System.out.println("\nHello " + getUsername() + "! \n Select an option from below and enter int he appropriate number to continue");
            System.out.println("1. View transaction log");
            System.out.println("2. Approve a customer's account");
            System.out.println("3. Delete a customer");
            System.out.println("4. Delete your account");
            System.out.println("5. Log out");
            System.out.println("6. Quit Application");
            int result = parseInt(scan.nextLine());

            switch (result) {
                case 1 -> database.viewAll();
                case 2 -> {
                    System.out.println("Enter the username of the customer you want to approve");
                    database.approveCustomer(scan.nextLine());
                }
                case 3 -> {
                    System.out.println("Enter the username of the customer you want to delete");
                    database.deleteCustomer(scan.nextLine());
                }
                case 4 -> database.deleteEmployee(getUsername());
                case 5 -> notDone = false;
                default -> {
                    notDone = false;
                    exit = true;
                }
            }
        } while (notDone);

        return !exit;
    }
}
