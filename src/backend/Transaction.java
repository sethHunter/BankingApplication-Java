package backend;

import java.util.Comparator;
import java.util.Date;

public class Transaction
{
    private final Date date;
    private final String fromUser;
    private final String toUser;
    private final int toAccount;
    private final int fromAccount;
    private final double amount;
    private final double newBalance;

    public Transaction(String fromUser, int fromAccount, String toUser, int toAccount, double amount, double newBalance)
    {
        this.date = new Date();
        this.fromUser = fromUser;
        this.fromAccount = fromAccount;
        this.toUser = toUser;
        this.toAccount = toAccount;
        this.amount = amount;
        this.newBalance = newBalance;
    }

    public void display()
    {
        System.out.println(date + "             " + fromUser + " Account " + fromAccount + 1 + "             " + toUser + " Account " + toAccount + 1 + "             "+ amount + "             " + newBalance);
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getToUser() {
        return toUser;
    }

    public static Comparator<Transaction> sortByDate = new Comparator<Transaction>() {

        @Override
        public int compare(Transaction obj1, Transaction obj2) {

            //sort in ascending order
            return obj1.getDate().compareTo(obj2.getDate());
        }
    };

    public static Comparator<Transaction> sortByUser = new Comparator<Transaction>() {

        @Override
        public int compare(Transaction obj1, Transaction obj2) {

            //sort in ascending order
            return obj1.getToUser().compareTo(obj2.getToUser());
        }
    };

    public static Comparator<Transaction> sortByAmount = new Comparator<Transaction>() {

        @Override
        public int compare(Transaction obj1, Transaction obj2) {

            //sort in ascending order
            return (int) (obj2.getAmount() - obj1.getAmount());
        }
    };
}
