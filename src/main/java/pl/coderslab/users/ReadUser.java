package pl.coderslab.users;

import pl.coderslab.utils.User;
import pl.coderslab.utils.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ReadUser", value = "/user/show")
public class ReadUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int UserId = Integer.parseInt(request.getParameter("UserId"));

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

        request.setAttribute("showUserID", user.getId());
        request.setAttribute("showUserUserName", user.getUserName());
        request.setAttribute("showUserEmail", user.getEmail());

        getServletContext().getRequestDispatcher("/users/read.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
