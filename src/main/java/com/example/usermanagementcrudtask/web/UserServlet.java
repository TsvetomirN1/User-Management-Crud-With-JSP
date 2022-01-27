package com.example.usermanagementcrudtask.web;

import com.example.usermanagementcrudtask.dao.UserEntityDao;
import com.example.usermanagementcrudtask.model.UserEntity;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/")
public class UserServlet extends HttpServlet {

    private UserEntityDao userEntityDao;

    public UserServlet() {
        this.userEntityDao = new UserEntityDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertUser(request, response);
                    break;
                case "/delete":
                    deleteUser(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateUser(request, response);
                    break;
                case "/sortByLastName"   :
                    sortUsersByLastName(request, response);
                    break;
                case "/sortByDateOfBirth"   :
                    sortByDateOfBirth(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }

    }

    private void sortByDateOfBirth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<UserEntity> usersByDateOfBirth = new ArrayList<>();
        usersByDateOfBirth = userEntityDao.selectAllUsersByDateOfBirth();
        request.setAttribute("sortByDateOfBirth", usersByDateOfBirth);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }

    private void sortUsersByLastName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserEntity> usersByLastName = new ArrayList<>();
        usersByLastName = userEntityDao.selectAllUsersByLastName();

        request.setAttribute("sortByLastName", usersByLastName);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }


    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserEntity existingUser = userEntityDao.selectUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);

    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<UserEntity> users = userEntityDao.selectAllUsers();
        request.setAttribute("listUser", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String dateOfBirth = request.getParameter("date_of_birth");
        String phoneNumber = request.getParameter("phone_number");
        String email = request.getParameter("email");

        UserEntity newUser = new UserEntity(firstName, lastName, dateOfBirth, phoneNumber, email);
        userEntityDao.insertUser(newUser);
        response.sendRedirect("list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userEntityDao.deleteUser(id);
        response.sendRedirect("list");

    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String dateOfBirth = request.getParameter("date_of_birth");
        String phoneNumber = request.getParameter("phone_number");
        String email = request.getParameter("email");

        UserEntity userEntity = new UserEntity(firstName, lastName, dateOfBirth, phoneNumber, email);
        userEntityDao.updateUser(userEntity);
        response.sendRedirect("list");
    }
}
