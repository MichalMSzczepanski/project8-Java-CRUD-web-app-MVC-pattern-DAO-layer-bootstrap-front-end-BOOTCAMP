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

@WebServlet(name = "UpdateUser", value = "/user/edit")
public class UpdateUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserUtil.returnUsersFromDatabase (request);

        HttpSession sess = request.getSession();
        sess.setAttribute("userId", UserUtil.returnUsersFromDatabase (request).getId());
        sess.setAttribute("userName", UserUtil.returnUsersFromDatabase (request).getUserName());
        sess.setAttribute("userEmail", UserUtil.returnUsersFromDatabase (request).getEmail());
        getServletContext().getRequestDispatcher("/users/update.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String newUserName = request.getParameter("newUserName");
        String newUserEmail = request.getParameter("newUserEmail");
        String newUserPassword = request.getParameter("newUserPassword");
        String newUserPasswordConfirm = request.getParameter("newUserPasswordConfirm");

//        set boolean flag if email was used before BUT it can be the email that's being updated
        UserDao userDao = new UserDao();
        userDao.findAllUsers();
        final User[] usersArray = UserDao.users;
        boolean repeatedEmail = false;
        for (int i = 0; i < usersArray.length; i++) {
            if (newUserEmail.equals(usersArray[i].getEmail()) && (!UserUtil.returnUsersFromDatabase(request).getEmail().equals(usersArray[i].getEmail()))) {
                repeatedEmail = true;
            }
        }

//        check all potential errors
            while ((("").equals(newUserName) || ("").equals(newUserEmail) || ("").equals(newUserPassword) || ("").equals(newUserPasswordConfirm) || (!(newUserPasswordConfirm).equals(newUserPassword))) || repeatedEmail) {
                // if email was repeated
                UserUtil.validateEmailRepeat(repeatedEmail, request, newUserName);
                // if user name was missing
                UserUtil.validateUserName(newUserEmail, request, newUserName);
                // if email was missing
                UserUtil.validateEmailisEmpty(newUserEmail, request, newUserName);
                // if one of the passwords was empty or they didn't match
                UserUtil.validatePasswords(newUserEmail, request, newUserName, newUserPasswordConfirm, newUserPassword);
                getServletContext().getRequestDispatcher("/users/update.jsp").forward(request, response);
            }

            HttpSession sess = request.getSession();

            String finalUserName = (("").equals(newUserName)) ? (String) sess.getAttribute("userName") : newUserName;
            String finalUserEmail = (("").equals(newUserEmail)) ? (String) sess.getAttribute("userEmail") : newUserEmail;
            String finalUserPassword = (("").equals(newUserPassword)) ? (String) sess.getAttribute("userPassword") : newUserEmail;

            User userReplacement = new User(finalUserEmail, finalUserName, finalUserPassword);

            int userId = (Integer) sess.getAttribute("userId");

            UserDao.update(userReplacement, userId);

            response.sendRedirect("/user/list");

        }
    }

