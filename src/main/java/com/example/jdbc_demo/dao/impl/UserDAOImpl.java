package com.example.jdbc_demo.dao.impl;

import com.example.jdbc_demo.dao.DirectionDAO;
import com.example.jdbc_demo.dao.TaskDAO;
import com.example.jdbc_demo.dao.UserDAO;
import com.example.jdbc_demo.model.Direction;
import com.example.jdbc_demo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String URL_DATABASE = "jdbc:mysql://localhost:3300/todoappdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";
    private DirectionDAO directionDAO = new DirectionDAOImpl();
    private TaskDAO taskDAO = new TaskDAOImpl();

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<User> users = new ArrayList<>();
        try {

            // 1 - Crear conexion
            connection = DriverManager.getConnection(
                    URL_DATABASE,
                    USER,
                    PASSWORD);

            // 2 - Crear y ejecutar sentencia
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users;");

            // 3 - Procesar resultados
            while (resultSet.next()) {

                // obtenemos los valores en base al nombre de la columna
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                Integer age = resultSet.getInt("age");
                String phone = resultSet.getString("email");

                // creamos un objeto con los datos obtenidos
                User user = new User(id, firstName, lastName, email, age, phone);
                users.add(user);

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
        return users;
    }

    @Override
    public User findById(Long id) {
        if (id == null)
            return null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        User user = null;

        try {

            // 1 - Crear conexion
            connection = DriverManager.getConnection(
                    URL_DATABASE,
                    USER,
                    PASSWORD);

            // 2 - Crear y ejecutar sentencia
            String sql = """
                            SELECT 
                            users.id as id, 
                            users.first_name as first_name,  
                            users.last_name as last_name,
                            users.email as email, 
                            users.age as age, 
                            users.phone as phone, 
                            directions.id as id_direction, 
                            directions.street as street, 
                            directions.postal_code as postal_code, 
                            directions.province as province, 
                            directions.country as country 
                            FROM users
                            LEFT JOIN directions ON users.id_direction = directions.id 
                            WHERE users.id = ?;""";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            // 3 - Procesar resultados
            while (resultSet.next()) {

                // user
                Long idUser = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                Integer age = resultSet.getInt("age");
                String phone = resultSet.getString("phone");

                // direction
                Long idDirection = resultSet.getLong("id_direction");
                String street = resultSet.getString("street");
                String postalCode = resultSet.getString("postal_code");
                String province = resultSet.getString("province");
                String country = resultSet.getString("country");

                Direction direction = new Direction(idDirection,street,postalCode,province,country);
                user = new User(idUser,firstName,lastName,email,age,phone);
                user.setDirection(direction);

            }


        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }

        // Obtener tasks:
        if (user != null)
            user.setTasks(taskDAO.findByUserId(user.getId()));

        return user;
    }

    @Override
    public Long create(User user) {

        // Si es nulo o ya existe entonces no se crea
        if (user == null || user.getId() != null)
            return null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            // 1 - Crear conexion
            connection = DriverManager.getConnection(
                    URL_DATABASE,
                    USER,
                    PASSWORD);


            // Crear direction
            Long idDirection = directionDAO.create(user.getDirection());


            // Crear usuario
            // 2 - Create Statement
            String sql = "INSERT INTO users "
                    + "(first_name,last_name,email,age,phone,id_direction) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";


            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getAge());
            statement.setString(5, user.getPhone());
            statement.setLong(6, idDirection);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs != null && rs.next())
                return rs.getLong(1); // obtiene la primary key generada por la base de datos

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
        return null;
    }

    @Override
    public void update(User user) {

        // Si es nulo o no tiene id entonces no se puede actualizar
        if (user == null || user.getId() == null)
            return;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            // 1 - Crear conexion
            connection = DriverManager.getConnection(
                    URL_DATABASE,
                    USER,
                    PASSWORD);

            // Update direction
            directionDAO.update(user.getDirection());

            // Update user
            String sql = """
                    UPDATE users
                    SET first_name = ?, last_name = ?, email = ?, age = ?, phone = ?
                    WHERE id = ? """; // (Java 13-15 Text Blocks Feature)


            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getAge());
            statement.setString(5, user.getPhone());
            statement.setLong(6, user.getId()); // Pasar el id para filtrar y no actualizar todos
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }

    }

    @Override
    public void delete(Long id) {

        // Si es nulo o no tiene id entonces no se puede actualizar
        if (id == null)
            return;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;


        User user = this.findById(1L);
        Long idDirection = user.getDirection().getId();
        try {

            // 1 - Crear conexion
            connection = DriverManager.getConnection(
                    URL_DATABASE,
                    USER,
                    PASSWORD);


            // 2 - Create Statement
            String sql = """
                    DELETE FROM users
                    WHERE id = ? """; // (Java 13-15 Text Blocks Feature)


            statement = connection.prepareStatement(sql);
            statement.setLong(1, id); // Pasar el id para filtrar y no borrar todos
            statement.executeUpdate();


        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }

        // Borrar la dirección
        directionDAO.delete(idDirection);
    }
}