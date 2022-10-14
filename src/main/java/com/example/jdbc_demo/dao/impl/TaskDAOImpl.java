package com.example.jdbc_demo.dao.impl;

import com.example.jdbc_demo.dao.DirectionDAO;
import com.example.jdbc_demo.dao.TaskDAO;
import com.example.jdbc_demo.model.Direction;
import com.example.jdbc_demo.model.Task;
import com.example.jdbc_demo.model.Task;
import com.example.jdbc_demo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {

    private static final String URL_DATABASE = "jdbc:mysql://localhost:3300/todoappdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> findAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<Task> tasks = new ArrayList<>();
        try {

            // 1 - Crear conexion
            connection = DriverManager.getConnection(
                    URL_DATABASE,
                    USER,
                    PASSWORD);

            // 2 - Crear y ejecutar sentencia
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tasks;");

            // 3 - Procesar resultados
            while (resultSet.next()) {

                // obtenemos los valores en base al nombre de la columna
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Long idUser = resultSet.getLong("id_user");

                Task task = new Task(id, title, description);

                // si tiene usuario lo asignamos
                if(idUser != 0){
                    User user = new User();
                    user.setId(idUser);
                    task.setUser(user);
                }

                tasks.add(task);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
        return tasks;
    }

    @Override
    public Task findById(Long id) {
        if (id == null)
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

            // 2 - Crear y ejecutar sentencia
            String sql = """
                            SELECT 
                            t.id as id, 
                            t.title as title,  
                            t.description as description,
                            t.id_user as id_user,
                            u.first_name as first_name
                            FROM tasks t
                            LEFT JOIN users u on u.id = t.id_user
                            WHERE t.id = ?;""";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            // 3 - Procesar resultados
            Task task = null;
            while (resultSet.next()) {

                // task
                Long idTask = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");

                // user
                Long idUser = resultSet.getLong("id_user");
                String firstName = resultSet.getString("first_name");


                task = new Task(idTask,title,description);
                if (idUser != 0)
                    task.setUser(new User(idUser, firstName));

                return task;
            }

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
    public List<Task> findByUserId(Long id) {
        if (id == null)
            return null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Task> tasks = new ArrayList<>();

        try {

            // 1 - Crear conexion
            connection = DriverManager.getConnection(
                    URL_DATABASE,
                    USER,
                    PASSWORD);

            // 2 - Crear y ejecutar sentencia
            String sql = """
                            SELECT 
                            t.id as id, 
                            t.title as title,  
                            t.description as description,
                            t.id_user as id_user
                            FROM tasks t 
                            WHERE id_user = ?;""";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            // 3 - Procesar resultados
            Task task = null;
            while (resultSet.next()) {

                // task
                Long idTask = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Long idUser = resultSet.getLong("id_user");

                task = new Task(idTask,title,description);
                task.setUser(new User(idUser));
                tasks.add(task);

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }

        return tasks;
    }

    @Override
    public void create(Task task) {

        // Si es nulo o ya existe entonces no se crea
        if (task == null || task.getId() != null)
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


            String sql = """
                        INSERT INTO tasks
                        (title,description,id_user)
                        VALUES (?, ?, ?)
                        """;


            statement = connection.prepareStatement(sql);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setLong(3, task.getUser() != null ? task.getUser().getId() : null);
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
    public void update(Task task) {

        // Si es nulo o no tiene id entonces no se puede actualizar
        if (task == null || task.getId() == null)
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

            // Update user
            String sql = """
                    UPDATE tasks
                    SET title = ?, description = ?, id_user = ?
                    WHERE id = ? """;


            statement = connection.prepareStatement(sql);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setLong(3, task.getUser() != null ? task.getUser().getId() : null);
            statement.setLong(4, task.getId()); // Pasar el id para filtrar y no actualizar todos
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

        try {

            // 1 - Crear conexion
            connection = DriverManager.getConnection(
                    URL_DATABASE,
                    USER,
                    PASSWORD);

            // Borrar la dirección

            // 2 - Create Statement
            String sql = """
                    DELETE FROM tasks
                    WHERE id = ? """;


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
    }
}