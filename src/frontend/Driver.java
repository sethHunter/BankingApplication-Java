package frontend;

import backend.*;
import java.util.Locale;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args)
    {
        Database database = Database.getDatabase();
        database.load();

        do {
            String username = null;
            String password = null;
            String firstName = null;
            String lastName = null;
            String employeeS = null;
            boolean employee = false;

            Scanner scan = new Scanner(System.in);

            System.out.println("\nWelcome!");
            System.out.println("Please enter your username");
            System.out.println("If you do not have a account one will be made for you\n");


            username = scan.nextLine();
            if(database.findUser(username))
            {
                System.out.println("\nPlease enter your password");
                password = scan.nextLine();

                if (!database.checkPassword(username, password))
                {
                    int trys = 3;

                    do {
                        if (trys == 0)
                            break;
                        trys--;

                        System.out.println("\nIncorrect password please try again. You have " + trys + " attempts remaining ");
                        password = scan.nextLine();

                    } while(!database.checkPassword(username, password));
                }



            } else
            {

                System.out.println("\nWhat is your first name?");
                firstName = scan.nextLine();
                System.out.println();

                System.out.println("What is your last name?");
                lastName = scan.nextLine();
                System.out.println();

                System.out.println("What do you want your password to be?");
                password = scan.nextLine();
                System.out.println();

                System.out.println("Are you a employee?");
                System.out.println("Type yes or no");
                employeeS = scan.nextLine();

                if(employeeS.toUpperCase(Locale.ROOT).equals("YES"))
                {
                    employee = true;

                } else
                {
                    employee = false;
                }

                database.add(firstName, lastName, username, password, employee);
            }
        } while(database.start());

        database.save();
    }
}
