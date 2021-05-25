package pl.coderslab.utils;

import org.mindrot.jbcrypt.BCrypt;

public class User {

    private int id;
    private String email;
    private String userName;
    private String password;
    private int user_views;

    // constructor for initializing user in UPDATE method
    public User(){
    };

    // constructor for final user to create a profile
    public User(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.user_views = 0;
    }

    // constructor for UserDao methods
    public User(int id, String email, String userName, String password) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.user_views = 0;
        setPassword(password);
    }

    public int getId() {
        return id;
    }

    // the USER should not be able to set their id - it's on AUTO INCREMENT
    protected void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getRawPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public int getUser_views() {
        return user_views;
    }

    public void setUser_views(int user_views) {
        this.user_views = user_views;
    }

    public String getUserDisplayName() {
        String[] temp = this.userName.split(" ");
        return temp[0].toLowerCase() + "-" + temp[1].toLowerCase();
    }
}
