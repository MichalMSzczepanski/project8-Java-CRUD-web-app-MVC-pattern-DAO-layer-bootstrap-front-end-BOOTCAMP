package pl.coderslab.utils;

import javax.servlet.http.HttpSession;

public class UserUtil {

//    USER INPUT VALIDATION

//    check if email was repeated
    public static void validateEmailRepeat(boolean repeatedEmail, HttpSession session) {
        if (repeatedEmail) {
            System.out.println("validate email repeate");
            session.setAttribute("userEmailOccupied", "true");
        }
    }
//   check if email was empty
public static void validateEmailisEmpty (String newUserEmail, HttpSession session ) {
    if ("".equals(newUserEmail)) {
        System.out.println("validate email is empty");
        session.setAttribute("userEmailMissing", "true");
    }
}

//    check if user name is empty
    public static void validateUserName(String newUserName, HttpSession session) {
        if ("".equals(newUserName)) {
            System.out.println("validate user name is empty");
            session.setAttribute("userUserNameMissing", "true");
        }
    }

//    validate inputted passwords
    public static void validatePasswords(String newUserPasswordConfirm, String newUserPassword, HttpSession session) {
        if ((!(newUserPasswordConfirm).equals(newUserPassword)) || ("").equals(newUserPassword) || ("").equals(newUserPasswordConfirm)) {
            session.setAttribute("passwordsDifferent", "true");
        }
        System.out.println("validate pass method");
    }

//    check if password has at least 5 characters, a digit a capital letter
}
