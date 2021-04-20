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

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.sendRedirect("/users/login.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        String loginAdminEmail = request.getParameter("loginAdminEmail");
        String loginAdminPassword = request.getParameter("loginAdminPassword");

        UserDao.findAllAdmins();

        User[] admins = UserDao.admins;

        // logged in admin has two attributes present!
            // adminEmailConfirmed
            // adminPasswordConfirmed

        for (User user : admins) {
            if (loginAdminEmail.equals(user.getEmail())) {
                session.setAttribute("adminEmailConfirmed", "true");
                session.setAttribute("adminEmail", user.getEmail());
                session.removeAttribute("emailIncorrect");
                if (loginAdminPassword.equals(user.getRawPassword())) {
                    session.setAttribute("adminPasswordConfirmed", "true");
                    response.sendRedirect("/user/list");
                } else {
                    System.out.println("user pass incorrect");
                    session.setAttribute("passwordIncorrect", "true");
                    getServletContext().getRequestDispatcher("/users/login.jsp").forward(request, response);
                }
            } else {
                System.out.println("user name incorrect");
                session.setAttribute("emailIncorrect", "true");
                getServletContext().getRequestDispatcher("/users/login.jsp").forward(request, response);
            }
        }
    }
}
