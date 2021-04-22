package pl.coderslab.users;

import pl.coderslab.utils.User;
import pl.coderslab.utils.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "UserList", value = "/user/list")
public class UserList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // fetch all users from database, pass into table
        UserDao userDao = new UserDao();
        userDao.findAllUsers();
        final User[] usersArray = UserDao.users;

        // define variables needed for displaying exact list of users per called in paragm page
        int numberOfUsers = usersArray.length;
        double totalNumbersOfPagesTemp = numberOfUsers / (double) 10;
        double totalNumberOfPages = Math.ceil(totalNumbersOfPagesTemp);
        int pageNumber = 0;
        int pageNumberPrevious = 0;
        int pageNumberNext = 0;
        int start = 0;
        int end = 0;
        User[] extractedUsers = new User[0];
        int extractedUserCounter = 0;


        // check if page number param is passed in url
        if (request.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        } else {
            pageNumber = 1; // default page numer in case of first visit or no parameter passed
        }

        // check if there should be displayed more numbers than 1 page number
        if (pageNumber > 1) {
            pageNumberPrevious = pageNumber - 1;
            pageNumberNext = pageNumber + 1;
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("pageNumberPrevious", pageNumberPrevious);
            request.setAttribute("pageNumberNext", pageNumberNext);
        } else {
            request.setAttribute("pageNumber", pageNumber);
        }

        // iterate through the master array and pass into temp array extractedUsers
        end = pageNumber * 10 - 1;
        start = end - 9;
        // if user wants to display final page
        if (pageNumber == totalNumberOfPages) {
            int numberOfUsersOnPage = numberOfUsers - (pageNumberPrevious * 10);
            extractedUsers = Arrays.copyOf(extractedUsers, numberOfUsersOnPage);
            for (int j = 0; j < numberOfUsersOnPage; j++) {
                extractedUsers[j] = usersArray[start];
                start++;
            }
            // if user wants to display any other page that the final one
        } else {
            for (int j = start; j <= end; j++) {
                extractedUsers = Arrays.copyOf(extractedUsers, extractedUsers.length + 1);
                extractedUsers[extractedUserCounter] = usersArray[j];
                extractedUserCounter++;
            }
        }

            request.setAttribute("extractedUsers", extractedUsers);

            getServletContext().getRequestDispatcher("/users/list.jsp").forward(request, response);

        }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
