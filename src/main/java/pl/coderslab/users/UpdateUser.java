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

        int UserId = Integer.parseInt(request.getParameter("UserId"));
        User user = UserDao.read(UserId);

        HttpSession sess = request.getSession();
        sess.setAttribute("userId", user.getId());
        sess.setAttribute("userName", user.getUserName());
        sess.setAttribute("userEmail", user.getEmail());
        getServletContext().getRequestDispatcher("/users/update.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        extract data from form
        String newUserName = request.getParameter("newUserName");
        String newUserEmail = request.getParameter("newUserEmail");
        String newUserPassword = request.getParameter("newUserPassword");
        String newUserPasswordConfirm = request.getParameter("newUserPasswordConfirm");
        int id = Integer.valueOf(request.getParameter("UserId"));

//        set and clear session for new input validation
        HttpSession session = request.getSession();
        session.removeAttribute("userEmailOccupied");
        session.removeAttribute("userEmailMissing");
        session.removeAttribute("userUserNameMissing");
        session.removeAttribute("passwordsDifferent");

//        set boolean flag if email was used before BUT it can be the email that's being updated
        boolean repeatedEmail = UserDao.validateUserEmailOccurences(newUserEmail, id);

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

            String finalUserName = (("").equals(newUserName)) ? (String) session.getAttribute("userName") : newUserName;
            String finalUserEmail = (("").equals(newUserEmail)) ? (String) session.getAttribute("userEmail") : newUserEmail;
            String finalUserPassword = (("").equals(newUserPassword)) ? (String) session.getAttribute("userPassword") : newUserEmail;

            User userReplacement = new User(finalUserEmail, finalUserName, finalUserPassword);

            int userId = (Integer) session.getAttribute("userId");

            UserDao.update(userReplacement, userId);

            response.sendRedirect("/user/list?pageNumber=1");

        }
    }

