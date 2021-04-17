package pl.coderslab.users;

import pl.coderslab.utils.User;
import pl.coderslab.utils.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateUser", value = "/user/add")
public class CreateUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("initialVisit", "initialVisitValue");
        getServletContext().getRequestDispatcher("/users/create.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        extract data from form
        String newUserName = request.getParameter("newUserName");
        String newUserEmail = request.getParameter("newUserEmail");
        String newUserPassword = request.getParameter("newUserPassWord");
        String newUserPasswordConfirm = request.getParameter("newUserPassWordConfirm");

//       check if email was used before
        UserDao userDao = new UserDao();
        userDao.findAll();
        final User[] usersArray = UserDao.users;
        for (int i = 0; i < usersArray.length; i++) {
            while (newUserEmail.equals(usersArray[i].getEmail())) {
                request.setAttribute("userName", newUserName);
                request.setAttribute("userEmailOccupied", "true");
                getServletContext().removeAttribute("passwordsDifferent");
                getServletContext().getRequestDispatcher("/users/create.jsp").forward(request, response);
            }
        }
        
//        check if user submitted empty values or mixed up passwords
        while( (("").equals(newUserName) || ("").equals(newUserEmail) || ("").equals(newUserPassword) || ("").equals(newUserPasswordConfirm) || (!(newUserPasswordConfirm).equals(newUserPassword)))) {
            if ("".equals(newUserName)) {
                request.setAttribute("userEmail", newUserEmail);
                request.setAttribute("userUserNameMissing", "true");
                getServletContext().removeAttribute("passwordsDifferent");
            }
            if ("".equals(newUserEmail)) {
                request.setAttribute("userName", newUserName);
                request.setAttribute("userEmailMissing", "true");
                getServletContext().removeAttribute("passwordsDifferent");
            }
            if ((!(newUserPasswordConfirm).equals(newUserPassword)) || ("").equals(newUserPassword) || ("").equals(newUserPasswordConfirm)) {
                request.setAttribute("userName", newUserName);
                request.setAttribute("userEmail", newUserEmail);
                request.setAttribute("passwordsDifferent", "true");
            }
            getServletContext().getRequestDispatcher("/users/create.jsp").forward(request, response);
        }

        User user = new User(newUserEmail, newUserName, newUserPassword);

        userDao.create(user);

        response.sendRedirect("/user/list");


    }
}
