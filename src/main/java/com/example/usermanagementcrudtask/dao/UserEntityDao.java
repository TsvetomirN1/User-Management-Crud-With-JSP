package com.example.usermanagementcrudtask.dao;


import com.example.usermanagementcrudtask.model.UserEntity;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserEntityDao {

    private String jdbcURL = "jdbc:mysql://localhost:3306/user_management_crud?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";

    private static final String INSERT_USERS_SQL = "INSERT INTO users (first_name,last_name,date_of_birth,phone_number,email) VALUES  (?,?,?,?,?);";
    private static final String SELECT_USER_BY_ID = "select * from users where id =?;";
    private static final String SELECT_ALL_USERS = "select * from users;";
    private static final String SORT_ALL_USERS_BY_LASTNAME = "select * from users order by last_name desc;";
    private static final String SORT_ALL_USERS_BY_BirthDate = "select * from users order by date_of_birth desc;";

    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set first_name = ?, last_name = ?, date_of_birth = ?, phone_number = ?, email= ? where id = ?;";




    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertUser(UserEntity userEntity) throws SQLException {


        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, userEntity.getFirstName());
            preparedStatement.setString(2, userEntity.getLastName());
            preparedStatement.setString(3, userEntity.getDateOfBirth());
            preparedStatement.setString(4, userEntity.getPhoneNumber());
            preparedStatement.setString(5, userEntity.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public UserEntity selectUser(int id) {
        UserEntity userEntity = null;

        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String dateOfBirth = resultSet.getString("date_of_birth");
                String phoneNumber = resultSet.getString("phone_number");
                String email = resultSet.getString("email");

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                 formatter.format(dateOfBirth);
                userEntity = new UserEntity(id, firstName, lastName, dateOfBirth, phoneNumber, email);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return userEntity;
    }

    public List<UserEntity> selectAllUsers() {

        List<UserEntity> users = new ArrayList<>();

        try (Connection connection = getConnection();


             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String dateOfBirth =resultSet.getString("date_of_birth");
                String phoneNumber = resultSet.getString("phone_number");
                String email = resultSet.getString("email");

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                formatter.format(dateOfBirth);
                users.add(new UserEntity(id, firstName, lastName, dateOfBirth, phoneNumber, email));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }


        return users;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(UserEntity userEntity) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL)) {
            statement.setString(1, userEntity.getFirstName());
            statement.setString(2, userEntity.getLastName());
            statement.setString(3, String.valueOf(userEntity.getDateOfBirth()));
            statement.setString(4, userEntity.getPhoneNumber());
            statement.setString(5, userEntity.getEmail());
            statement.setInt(6, userEntity.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public List<UserEntity> selectAllUsersByLastName() {

        return selectAllUsers()
                .stream().sorted(Comparator.comparing(UserEntity::getLastName))
                .collect(Collectors.toList());


//        try (Connection connection = getConnection();
//
//             PreparedStatement preparedStatement = connection.prepareStatement(SORT_ALL_USERS_BY_LASTNAME);) {
//
//            ResultSet resultSet = preparedStatement.executeQuery();


    }
    public List<UserEntity> selectAllUsersByDateOfBirth() {

        return selectAllUsers()
                .stream().sorted(Comparator.comparing(UserEntity::getDateOfBirth))
                .collect(Collectors.toList());


//        try (Connection connection = getConnection();
//
//             PreparedStatement preparedStatement = connection.prepareStatement(SORT_ALL_USERS_BY_BirthDate);) {
//
//            ResultSet resultSet = preparedStatement.executeQuery();


    }
}
