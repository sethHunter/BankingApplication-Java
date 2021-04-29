// BY: Seth Hunter 2021

package backend;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class Database
{
    //singleton
    private final ArrayList<Employee> employees;
    private final ArrayList<Customer> customers;
    private final Stack<Transaction> transactions;
    private boolean employee;
    private boolean lock;
    private int index;

    private static final Database database = new Database();

    private Database()
    {
        this.employees = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.transactions = new Stack<>();
        employee = false;
    }

    public boolean start()
    {
        if (!lock)
        {
            BankLogger bankLogger = BankLogger.getBankLogger();
            if(employee)
            {
                bankLogger.log(employees.get(index).getUsername() + " has logged in. " + new Date());
                return employees.get(index).menu();
            } else {
                bankLogger.log(customers.get(index).getUsername() + " has logged in. " + new Date());
                return customers.get(index).menu();
            }
        }

        return false;
    }

    public boolean checkPassword(String username, String password)
    {
        for (Employee e : employees) {
            if (e.getUsername().equals(username)) {
                if (e.getPassword().equals(password)) {
                    lock = false;
                    employee = true;
                    return true;
                }
            }
        }

        for (Customer c : customers) {
            if (c.getUsername().equals(username)) {
                if (c.getPassword().equals(password)) {
                    lock = false;
                    employee = false;
                    return true;
                }
            }
        }

        return false;
    }

    public boolean findUser(String username)
    {
        if(!employees.isEmpty())
        {
            for (int i = 0; i < employees.size(); ++i)
            {
                if(employees.get(i).getUsername().equals(username))
                {
                    employee = true;
                    index = i;
                    return true;
                }
            }
        }

        if (!customers.isEmpty())
        {
            for (int i = 0; i < customers.size(); ++i)
            {
                if(customers.get(i).getUsername().equals(username))
                {
                    employee = false;
                    index = i;
                    return true;
                }
            }
        }

        return false;
    }

    public int selectUserAccount(String username)
    {
        if (!customers.isEmpty())
        {
            for (Customer customer : customers) {
                if (customer.getUsername().equals(username)) {
                    return customer.accountMenu();
                }
            }
        }

        return -1;
    }

    public int selectUser(String username)
    {
        if (!customers.isEmpty())
        {
            for (int i = 0; i < customers.size(); ++i)
            {
                if(customers.get(i).getUsername().equals(username))
                {
                    return i;
                }
            }
        }

        return -1;
    }

    public void deleteEmployee(String username)
    {
        if(findUser(username))
        {
            employees.remove(index);
            index = 0;
            employee = false;
        }
    }

    public void deleteCustomer(String username)
    {
        if (findUser(username))
            customers.remove(index);
    }

    public void approveCustomer(String username)
    {
        if (findUser(username))
            customers.get(index).setApproved(true);
    }

    public void logTransaction(Transaction t)
    {
        transactions.add(t);
    }

    public void viewAll()
    {
        for(Transaction t:transactions)
        {
            t.display();
        }
    }

    public void sendTransaction(String fromUser, int fromAccount, String toUser, int toAccount, double amount)
    {
        customers.get(selectUser(toUser)).intakeTransaction(fromUser, fromAccount, toUser, toAccount, amount);
    }

    public void add(String firstName,String lastName, String username, String password, boolean employee)
    {
        if (employee)
        {
            Employee user = new Employee(firstName, lastName, username, password);
            employees.add(user);
            index = employees.size() - 1;
            this.employee = employee;
        } else
        {
            Customer user = new Customer(firstName, lastName, username, password);
            customers.add(user);
            index = customers.size() - 1;
            this.employee = employee;
        }
        checkPassword(username, password);
    }

    public void load()
    {
        // Load from file
    }

    public void save()
    {
        // Save to file
    }

    public static Database getDatabase()
    {
        return database;
    }
}
