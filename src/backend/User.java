package backend;

public class User
{

    private final String username;
    private final String password;
    private String firstName;
    private String lastName;


    User(String username, String password, String firstName, String lastName)
    {
        this.username  = username;
        this.password  = password;
        this.firstName = firstName;
        this.lastName  = lastName;
    }

    //Menu for the user. Returns false if they want to shutdown or true if they want to go back to the main menu
    public boolean menu() { return false;}

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
