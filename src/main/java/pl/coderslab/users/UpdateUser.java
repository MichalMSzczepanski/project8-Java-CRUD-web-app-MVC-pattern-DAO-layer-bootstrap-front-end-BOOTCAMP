package pl.coderslab.users;

import pl.coderslab.utils.User;
import pl.coderslab.utils.UserDao;

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

        UserDao userDao = new UserDao();
        userDao.findAll();
        final User[] usersArray = UserDao.users;
        int userToUpdateId = Integer.parseInt(request.getParameter("UserId"));
        User user = new User();
        for (int i = 0; i < usersArray.length; i++) {
            if (userToUpdateId == usersArray[i].getId()) {
                user = UserDao.users[i];
            }
        }

        HttpSession sess = request.getSession();
        sess.setAttribute("userId", user.getId());
        sess.setAttribute("userName", user.getUserName());
        sess.setAttribute("userEmail", user.getEmail());

        getServletContext().getRequestDispatcher("/users/update.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String newUserName = request.getParameter("newUserName");
        String newUserEmail = request.getParameter("newUserEmail");
        String newUserPassword = request.getParameter("newUserPassword");

        HttpSession sess = request.getSession();

        String finalUserName = ( ("").equals(newUserName)) ? (String) sess.getAttribute("userName") : newUserName;
        String finalUserEmail = ( ("").equals(newUserEmail)) ? (String) sess.getAttribute("userEmail") : newUserEmail;
        String finalUserPassword = ( ("").equals(newUserPassword)) ? (String) sess.getAttribute("userPassword") : newUserEmail;

        User userReplacement = new User (finalUserEmail, finalUserName, finalUserPassword);

        int userId = (Integer) sess.getAttribute("userId");

        UserDao.update(userReplacement, userId);

        response.sendRedirect("/user/list");

    }
}
