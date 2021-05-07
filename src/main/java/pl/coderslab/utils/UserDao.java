package pl.coderslab.utils;

import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
    private static final String UPDATE_USER_DATA_QUERY = "update users set email = ?, username = ?, password = ? where id = ?;";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * from users";

    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users where email = ?";
    private static final String SELECT_CURRENT_IDS = "SELECT id FROM users ORDER BY id ASC;";

    private static final String SELECT_ALL_ADMINS = "SELECT * from admins";

    private static final String FETCH_FINAL_USERS = "SELECT * from users order by id limit ?, 10";

    private static final String FETCH_FINAL_USERS_FROM_SEARCH = "select * from users where username like concat('%',?,'%') or email like concat('%',?,'%') order by id asc limit ?, 10";
    private static final String FIND_ALL_USERS_FROM_SEARCH = "select * from users where username like concat('%',?,'%') or email like concat('%',?,'%') order by id";

    private static final String RETURN_NUMBER_OF_USERS = "select count(id) from users";
    private static final String RETURN_NUMBER_OF_USERS_FROM_SEARCH = "select count(id) from users where username like concat('%',?,'%') or email like concat('%',?,'%') order by id";




    // hint - fetching user ID (due to its AUTO INCREMENT)
//    PreparedStatement preStmt = DBUtil.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

    // CREATE METHOD where we pass a User. This method should be in the User (master?) constructor, which will be initiated in Main (or similiar)
    public static User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(2, user.getUserName());
            statement.setString(1, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();

            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            System.out.println("User created.");
            findAllUsers();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Issue with accessing database when CREATING USER");
            return null;
        }
    }

    // READ USER method - pass in the id of the user you want to print in the console
    public static User read(int userId){
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement readUser = conn.prepareStatement(SELECT_USER_BY_ID);
            readUser.setString(1, (String.valueOf(userId)));
            ResultSet readUserValue = readUser.executeQuery();
            if(readUserValue.next()){
                int userID = readUserValue.getInt(1);
                String userEmail = readUserValue.getString(2);
                String userName = readUserValue.getString(3);
                String userPass = readUserValue.getString(4);
                System.out.println("Your user data is: id #" + userID + " | " + userEmail + " | " + userName + " | " + userPass);
                return new User(readUserValue.getInt(1), readUserValue.getString(2), readUserValue.getString(3), readUserValue.getString(4));
            } else {
                System.out.println("<ERROR> user with id: " + userId + " does not exist");
                return null;
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("issue with accessing database when trying to READ");
            return null;
        }
    }

    // UPDATE USER method - pass in user and grant it updated attributes
    public static void update(User user, int id){
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement readUser = conn.prepareStatement(UPDATE_USER_DATA_QUERY);
            readUser.setString(1, user.getEmail());
            readUser.setString(2, user.getUserName());
            readUser.setString(3, user.getPassword());
            readUser.setString(4, String.valueOf(id));
            readUser.executeUpdate();
            if(readUser.executeUpdate() == 1){
                System.out.println( "update SUCCESSFUL");
                System.out.println("updated data as follows:");
                System.out.println("new email is: " + user.getEmail());
                System.out.println("new username is: " + user.getUserName());
                System.out.println("new hashed password is: " + user.getPassword());
            } else {
                System.out.println("<ERROR> failed to update your data");
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("issue with accessing database when trying to UPDATE");
        }
    }

    // DELETE USER method - pass in user it to delete it
    public static void delete(int userId) {
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement deleteUser = conn.prepareStatement(DELETE_USER_BY_ID);
            deleteUser.setInt(1, userId);
            if(deleteUser.executeUpdate() == 1){
                System.out.println("Deleting user with id #" + userId + " was successful");
                System.out.println("Current list of users below:");
                PreparedStatement listAllUsers = conn.prepareStatement(SELECT_ALL_USERS);
                ResultSet resultListOfUsers = listAllUsers.executeQuery();
                while(resultListOfUsers.next()){
                    int idOfUser = resultListOfUsers.getInt(1);
                    String emailOfUser = resultListOfUsers.getString(2);
                    String usernameOfUser = resultListOfUsers.getString(3);
                    String passwordOfUser = resultListOfUsers.getString(4);
                    System.out.println("#" + idOfUser + " | " + emailOfUser + " | " + usernameOfUser + " | " + passwordOfUser);
                }
            } else {
                System.out.println("<ERROR> failed to delete your user - did you provide the proper ID?");
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("issue with accessing database when trying to DELETE");
        }
    }

    // FIND ALL USERS WHEN SEARCH IS FILLED OUT
    public static User[] findAllUsers(String searchedParam) {
        User[] users = new User[0];
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement fetchDisplayedUsers = conn.prepareStatement(FIND_ALL_USERS_FROM_SEARCH);
            fetchDisplayedUsers.setString(1, searchedParam);
            fetchDisplayedUsers.setString(2, searchedParam);
            ResultSet resultListOfUsers = fetchDisplayedUsers.executeQuery();
            users = new User[0];
            while (resultListOfUsers.next()) {
                int idOfUser = resultListOfUsers.getInt(1);
                String emailOfUser = resultListOfUsers.getString(2);
                String usernameOfUser = resultListOfUsers.getString(3);
                String passwordOfUser = resultListOfUsers.getString(4);
                User addUserToArray = new User(idOfUser, emailOfUser, usernameOfUser, passwordOfUser);
                users = addToArray(addUserToArray, users);
            }
            if(users.length == 0){
                System.out.println("There are no users in the database from this search");
            } else {
                System.out.println("Your SEARCH from the database consists of the below users:");
                for(User user : users){
                    System.out.println(("#" + user.getId() + " | " + user.getEmail() + " | " + user.getUserName()));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("issue with accessing database when trying to LIST ALL USERS FROM SEARCH");
        }
        return users;
    }

    // Find all users and return all in one array
    public static User[] findAllUsers() {
        User[] users = new User[0];
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement listAllUsers = conn.prepareStatement(SELECT_ALL_USERS);
            ResultSet resultListOfUsers = listAllUsers.executeQuery();
            users = new User[0];
            while (resultListOfUsers.next()) {
                int idOfUser = resultListOfUsers.getInt(1);
                String emailOfUser = resultListOfUsers.getString(2);
                String usernameOfUser = resultListOfUsers.getString(3);
                String passwordOfUser = resultListOfUsers.getString(4);
                User addUserToArray = new User(idOfUser, emailOfUser, usernameOfUser, passwordOfUser);
                users = addToArray(addUserToArray, users);
            }
            if(users.length == 0){
                System.out.println("There are no users in the database");
            } else {
                System.out.println("Your database consists of the below users:");
                for(User user : users){
                    System.out.println(("#" + user.getId() + " | " + user.getEmail() + " | " + user.getUserName()));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("issue with accessing database when trying to LIST ALL USERS");
        }
        return users;
    }

    // FIND ALL ADMINS and return all in one array
    public static User[] findAllAdmins() {
        User[] admins = new User[0];
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement listAllAdmins = conn.prepareStatement(SELECT_ALL_ADMINS);
            ResultSet resultListOfAdmins = listAllAdmins.executeQuery();
            admins = new User[0];
            while (resultListOfAdmins.next()) {
                int idOfAdmin = resultListOfAdmins.getInt(1);
                String emailOfAdmin = resultListOfAdmins.getString(2);
                String usernameOfAdmin = resultListOfAdmins.getString(3);
                String passwordOfAdmin = resultListOfAdmins.getString(4);
                User addAdminToArray = new User(emailOfAdmin, usernameOfAdmin, passwordOfAdmin);
                admins = addToArray(addAdminToArray, admins);
            }
            if(admins.length == 0){
                System.out.println("There are no admins in the database");
            } else {
                System.out.println("Your database consists of the below admins:");
                for(User user : admins){
                    System.out.println(("#" + user.getId() + " | " + user.getEmail() + " | " + user.getUserName()));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("issue with accessing database when trying to LIST ALL ADMINS");
        }
        return admins;
    }

    // FETCH SEARCHED USERS and return array - page included
    public static User[] fetchDisplayedUserArray (int pageNumber, String searchedParam) {
        User[] extractedUsers = new User[0];
        try(Connection conn = DbUtil.getConnection()) {
            PreparedStatement fetchDisplayedUsers = conn.prepareStatement(FETCH_FINAL_USERS_FROM_SEARCH);
            fetchDisplayedUsers.setString(1, searchedParam);
            fetchDisplayedUsers.setString(2, searchedParam);
            fetchDisplayedUsers.setInt(3, (pageNumber - 1) * 10);
            ResultSet resultFetchDisplayedUsers = fetchDisplayedUsers.executeQuery();
            extractedUsers = new User[0];
            while(resultFetchDisplayedUsers.next()) {
                int idOfUser = resultFetchDisplayedUsers.getInt(1);
                String emailOfUser = resultFetchDisplayedUsers.getString(2);
                String usernameOfUser = resultFetchDisplayedUsers.getString(3);
                String passwordOfUser = resultFetchDisplayedUsers.getString(4);
                User addUserToArray = new User(idOfUser, emailOfUser, usernameOfUser, passwordOfUser);
                extractedUsers = addToArray(addUserToArray, extractedUsers);
            }
            if(extractedUsers.length == 0){
                System.out.println("There are no users in the database");
            } else {
                System.out.println("Your list of users to preview consists of the below users:");
                for(User user : extractedUsers){
                    System.out.println(("#" + user.getId() + " | " + user.getEmail() + " | " + user.getUserName()));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("issue with accessing database when trying to FETCH TABLES WITH USERS TO BE VIEWED");
        }
        return extractedUsers;
    }

    // EXPORT TABLE WITH USERS TO BE VIEWED - WITHOUT SEARCH FILLED OUT
    public static User[] fetchDisplayedUserArray (int pageNumber) {
        User[] extractedUsers = new User[0];
        try(Connection conn = DbUtil.getConnection()) {
            PreparedStatement fetchDisplayedUsers = conn.prepareStatement(FETCH_FINAL_USERS);
            fetchDisplayedUsers.setInt(1, (pageNumber - 1) * 10);
            ResultSet resultFetchDisplayedUsers = fetchDisplayedUsers.executeQuery();
            extractedUsers = new User[0];
            while(resultFetchDisplayedUsers.next()) {
                int idOfUser = resultFetchDisplayedUsers.getInt(1);
                String emailOfUser = resultFetchDisplayedUsers.getString(2);
                String usernameOfUser = resultFetchDisplayedUsers.getString(3);
                String passwordOfUser = resultFetchDisplayedUsers.getString(4);
                User addUserToArray = new User(idOfUser, emailOfUser, usernameOfUser, passwordOfUser);
                extractedUsers = addToArray(addUserToArray, extractedUsers);
            }
            if(extractedUsers.length == 0){
                System.out.println("There are no users in the database");
            } else {
                System.out.println("Your list of users to preview consists of the below users:");
                for(User user : extractedUsers){
                    System.out.println(("#" + user.getId() + " | " + user.getEmail() + " | " + user.getUserName()));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("issue with accessing database when trying to FETCH TABLES WITH USERS TO BE VIEWED");
        }
        return extractedUsers;
    }

    //RETURN TOTAL NUMBER OF USERS
    public static int returnNumberOfUsers() {
        int numberOfUsers = 0;
        try(Connection conn = DbUtil.getConnection()) {
            PreparedStatement returnNumberOfUsers = conn.prepareStatement(RETURN_NUMBER_OF_USERS);
            ResultSet rs = returnNumberOfUsers.executeQuery();
            while(rs.next()) {
                numberOfUsers = rs.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("issue with accessing database when trying to RETURN NUMBER OF USERS");
        }
        System.out.println("final number of users in database is: " + numberOfUsers);
        return numberOfUsers;
    }

    // RETURN TOTAL NUMBER OF USERS WHEN SEARCHED IS USED

    public static Integer returnNumberOfUsers(String searchedParam) {
        Integer numberOfUsers = 0;
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement returnNumberOfUsersWithSearch = conn.prepareStatement(RETURN_NUMBER_OF_USERS_FROM_SEARCH);
            returnNumberOfUsersWithSearch.setString(1, searchedParam);
            returnNumberOfUsersWithSearch.setString(2, searchedParam);
            ResultSet rs = returnNumberOfUsersWithSearch.executeQuery();
            while (rs.next()) {
                numberOfUsers = rs.getInt(1);
            }
            if(numberOfUsers.equals(null)){
                System.out.println("There are no users in the database from this search");
            } else {
                System.out.println("final number of users using search is: " + numberOfUsers);
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("issue with accessing database when trying to LIST ALL USERS FROM SEARCH");
        }
        return numberOfUsers;
    }


    // auxilary method - validate if inputed id reflect an id in the database
    public static boolean validateID (int id) {
        try (Connection connection = DbUtil.getConnection()) {
            int[] arrayOfIDs = new int[0];
            int IDcounter = 0;
            PreparedStatement listAllIDs = connection.prepareStatement(SELECT_CURRENT_IDS);
            ResultSet listOfAllIDs = listAllIDs.executeQuery();
            while(listOfAllIDs.next()){
                arrayOfIDs = Arrays.copyOf(arrayOfIDs, arrayOfIDs.length + 1);
                arrayOfIDs[IDcounter] = listOfAllIDs.getInt(1);
                IDcounter++;
            }
            for (int IDsInList : arrayOfIDs){
                if(IDsInList == id){
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("issue with accessing database when trying to VALIDATE INPUTED ID");
            return true;
        }
    }

    // auxilary method - validate email construction
    public static boolean validateEmail(String email){
        EmailValidator emailValidator = EmailValidator.getInstance(); // SINGLETON refference https://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/EmailValidator.html#getInstance--
        if(emailValidator.isValid(email) == true){
            return true;
        } else {
            return false;
        }
    }

    // auxilary method - validate if email is already used in the data base
    public static boolean validateEmailOccurence(String email) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement validateEmail = connection.prepareStatement(SELECT_USER_BY_EMAIL);
            validateEmail.setString(1, email);
            ResultSet validateEmailDone = validateEmail.executeQuery();
            if(validateEmailDone.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("issue with accessing database when VALIDATING EMAIL");
            return true;
        }
    }

    // auxilary method - copy table with users for findAll method
    public static User[] addToArray(User u, User[] users) {
        users = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        users[users.length - 1] = u; // Dodajemy obiekt na ostatniej pozycji.
        return users; // Zwracamy nową tablicę.
    }

    // auxilary method - password hashing
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
