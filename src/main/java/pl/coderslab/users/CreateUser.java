package pl.coderslab.users;

import pl.coderslab.utils.User;
import pl.coderslab.utils.UserDao;
import pl.coderslab.utils.UserUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CreateUser", value = "/user/add")
public class CreateUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sess = request.getSession();
        sess.removeAttribute("userName");
        sess.removeAttribute("userEmail");
        getServletContext().getRequestDispatcher("/users/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        extract data from form
        String newUserName = request.getParameter("newUserName");
        String newUserEmail = request.getParameter("newUserEmail");
        String newUserPassword = request.getParameter("newUserPassWord");
        String newUserPasswordConfirm = request.getParameter("newUserPassWordConfirm");

//        set and clear session for new input validation
        HttpSession session = request.getSession();
        session.removeAttribute("userEmailOccupied");
        session.removeAttribute("userEmailMissing");
        session.removeAttribute("userUserNameMissing");
        session.removeAttribute("passwordsDifferent");

//        set boolean flag if email was used before BUT it can be the email that's being updated
        boolean repeatedEmail = UserDao.validateOverallUserEmailOccurences(newUserEmail);

//        check all potential errors
        while ((("").equals(newUserName)
                || ("").equals(newUserEmail)
                || ("").equals(newUserPassword)
                || ("").equals(newUserPasswordConfirm)
                || (!(newUserPasswordConfirm).equals(newUserPassword)))
                || repeatedEmail) {
            // if email was repeated
            UserUtil.validateEmailRepeat(repeatedEmail, session);
            // if email was empty
            UserUtil.validateEmailisEmpty(newUserEmail, session);
            // if user name was empty
            UserUtil.validateUserName(newUserName, session);
            // if one of the passwords was empty or they didn't match
            UserUtil.validatePasswords(newUserPasswordConfirm, newUserPassword, session);
            doGet(request, response);
        }

        User user = new User(newUserEmail, newUserName, newUserPassword);

        UserDao.create(user);

        response.sendRedirect("/user/list");

    }
}
