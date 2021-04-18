package pl.coderslab.users;

import pl.coderslab.utils.User;
import pl.coderslab.utils.UserDao;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {

//    RETURN CURRENT CONTENTS OF DATABASE

    public static User returnUsersFromDatabase (HttpServletRequest request) {
    UserDao.findAll();
    final User[] usersArray = UserDao.users;
    int userToUpdateId = Integer.parseInt(request.getParameter("UserId"));
    User user = new User();
        for (int i = 0; i < usersArray.length; i++)
            if (userToUpdateId == usersArray[i].getId()) {
                user = UserDao.users[i];
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

//    validate inputed passwords
    public static void validatePasswords(String newUserEmail, HttpServletRequest request, String newUserName, String newUserPasswordConfirm, String newUserPassword) {
        if ((!(newUserPasswordConfirm).equals(newUserPassword)) || ("").equals(newUserPassword) || ("").equals(newUserPasswordConfirm)) {
            request.setAttribute("userName", newUserName);
            request.setAttribute("userEmail", newUserEmail);
            request.setAttribute("passwordsDifferent", "true");
        }
    }
}
