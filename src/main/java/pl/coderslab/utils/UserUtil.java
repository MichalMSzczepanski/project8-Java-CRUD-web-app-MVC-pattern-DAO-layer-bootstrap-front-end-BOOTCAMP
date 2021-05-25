package pl.coderslab.utils;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {

//    RETURN SEARCHED USER BY ID FROM GET

    public static User returnUserFromDatabase (int id) {
        User user = new User();
        User[] usersArray = UserDao.findAllUsers();
        for (int i = 0; i < usersArray.length; i++)
            if (id == usersArray[i].getId()) {
                user = usersArray[i];
            }
        return user;
    }

    public static User returnUsersFromDatabase (HttpServletRequest request) {
    final User[] usersArray = UserDao.findAllUsers();
    int userToUpdateId = Integer.parseInt(request.getParameter("UserId"));
    User user = new User();
        for (int i = 0; i < usersArray.length; i++)
            if (userToUpdateId == usersArray[i].getId()) {
                user = UserDao.findAllUsers()[i];
            }
        return user;
    }


//    USER INPUT VALIDATION

//    check if email was repeated
    public static void validateEmailRepeat(boolean repeatedEmail, HttpServletRequest request, String newUserName) {
        if (repeatedEmail) {
            request.setAttribute("userName", newUserName);
            request.setAttribute("userEmailOccupied", "true");
            request.getServletContext().removeAttribute("passwordsDifferent");
        }
    }
//   check if email was empty
public static void validateEmailisEmpty (String newUserEmail, HttpServletRequest request, String newUserName) {
    if ("".equals(newUserEmail)) {
        request.setAttribute("userName", newUserName);
        request.setAttribute("userEmailMissing", "true");
        request.getServletContext().removeAttribute("passwordsDifferent");
    }
}

//    check if user name is empty
    public static void validateUserName(String newUserEmail, HttpServletRequest request, String newUserName) {
        if ("".equals(newUserName)) {
            request.setAttribute("userEmail", newUserEmail);
            request.setAttribute("userUserNameMissing", "true");
            request.getServletContext().removeAttribute("passwordsDifferent");
        }
    }

//    validate inputted passwords
    public static void validatePasswords(String newUserEmail, HttpServletRequest request, String newUserName, String newUserPasswordConfirm, String newUserPassword) {
        if ((!(newUserPasswordConfirm).equals(newUserPassword)) || ("").equals(newUserPassword) || ("").equals(newUserPasswordConfirm)) {
            request.setAttribute("userName", newUserName);
            request.setAttribute("userEmail", newUserEmail);
            request.setAttribute("passwordsDifferent", "true");
        }
    }

//    check if password has at least 5 characters, a digit a capital letter
}
