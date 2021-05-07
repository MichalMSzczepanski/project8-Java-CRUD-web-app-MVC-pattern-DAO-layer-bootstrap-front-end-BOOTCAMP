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

@WebServlet(name = "ReadUser", value = "/user/show")
public class ReadUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int UserId = Integer.parseInt(request.getParameter("UserId"));
        User user = UserDao.read(UserId);

        HttpSession sess = request.getSession();
        sess.setAttribute("showUserID", user.getId());
        sess.setAttribute("showUserUserName", user.getUserName());
        sess.setAttribute("showUserEmail", user.getEmail());

        getServletContext().getRequestDispatcher("/users/read.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
