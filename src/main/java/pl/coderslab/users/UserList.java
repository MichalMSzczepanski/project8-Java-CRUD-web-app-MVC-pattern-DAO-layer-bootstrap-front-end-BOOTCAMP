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

@WebServlet(name = "UserList", value = "/user/list")
public class UserList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // fetch data from SEARCH BAR if exists(located in header.jspf)
        String searchedParam = request.getParameter("search");
        int numberOfUsers = (searchedParam == null) ? UserDao.returnNumberOfUsers() : UserDao.returnNumberOfUsers(searchedParam);

        // fetching number of pages (two scenarios - search param null or provided)
        double totalNumberOfPages = numberOfUsers/10 + 1 ;

        // validate proper page number in url
        try {
            int currentPageNumber = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));

            // redirect to first page if user changes url
            if (currentPageNumber > totalNumberOfPages || currentPageNumber < 1) {
                currentPageNumber = 1;
            }

            // fetch most viewed user

            User user = UserDao.fetchMostViewedUser();
            HttpSession session = request.getSession();
            session.setAttribute("mostViewedUser", user);

            // return table dedicated with users - dedicated to search and specific page provided above
            User[] finalUserExtracted;
            if (searchedParam == null) {
                finalUserExtracted = UserDao.fetchDisplayedUserArray(currentPageNumber);
            } else {
                finalUserExtracted = UserDao.fetchDisplayedUserArray(currentPageNumber, searchedParam);
            }
            request.setAttribute("extractedUsers", finalUserExtracted);
            request.setAttribute("pageNumber", currentPageNumber);
            request.setAttribute("totalNumberOfPages", totalNumberOfPages);
            getServletContext().getRequestDispatcher("/users/list.jsp").forward(request, response);

        } catch (NumberFormatException e){
            System.out.println("user tried to type in a non-digit character");
        response.sendRedirect("/users/list.jsp/pageNumber=1");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
