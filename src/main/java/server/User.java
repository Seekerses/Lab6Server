package server;

public class User {
    private String username;
    private String password;

    public User(){

    }

    public User(String string, String string1) {
        username = string;
        password = string1;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
