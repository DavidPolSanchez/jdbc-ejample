package com.example.jdbc_demo.dao.impl;

import com.example.jdbc_demo.dao.DirectionDAO;
import com.example.jdbc_demo.model.Direction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DirectionDAOImpl implements DirectionDAO {

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
    public List<Direction> findAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<Direction> directions = new ArrayList<>();
        try {

            // 1 - Crear conexion
            connection = DriverManager.getConnection(
                    URL_DATABASE,
                    USER,
                    PASSWORD);

            // 2 - Crear y ejecutar sentencia
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM directions;");

            // 3 - Procesar resultados
            while (resultSet.next()) {

                // obtenemos los valores en base al nombre de la columna
                Long id = resultSet.getLong("id");
                String street = resultSet.getString("street");
                String postalCode = resultSet.getString("postal_code");
                String province = resultSet.getString("province");
                String country = resultSet.getString("country");
                // creamos un objeto con los datos obtenidos
                Direction direction = new Direction(id, street, postalCode, province, country);
                directions.add(direction);

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
        return directions;
    }

    @Override
    public Direction findById(Long id) {
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
            String sql = "SELECT * FROM directions where id = ?;";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            // 3 - Procesar resultados
            while (resultSet.next()) {

                // obtenemos los valores en base al nombre de la columna
                Long idDirection = resultSet.getLong("id");
                String street = resultSet.getString("street");
                String postalCode = resultSet.getString("postal_code");
                String province = resultSet.getString("province");
                String country = resultSet.getString("country");
                // creamos un objeto con los datos obtenidos

                return new Direction(idDirection, street, postalCode, province, country);

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
    public Long create(Direction direction) {

        // Si es nulo o ya existe entonces no se crea
        if (direction == null || direction.getId() != null)
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

            // 2 - Create Statement
            String sql = "INSERT INTO directions "
                    + "(street,postal_code,province,country) "
                    + "VALUES (?, ?, ?, ?)";


            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, direction.getStreet());
            statement.setString(2, direction.getPostalCode());
            statement.setString(3, direction.getProvince());
            statement.setString(4, direction.getCountry());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs != null && rs.next())
                return rs.getLong(1);


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
    public void update(Direction direction) {

        // Si es nulo o no tiene id entonces no se puede actualizar
        if (direction == null || direction.getId() == null)
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

            // 2 - Create Statement
            String sql = """
                    UPDATE directions
                    SET street = ?, postal_code = ?, province = ?, country = ?  
                    WHERE id = ? """; // (Java 13-15 Text Blocks Feature)


            statement = connection.prepareStatement(sql);
            statement.setString(1, direction.getStreet());
            statement.setString(2, direction.getPostalCode());
            statement.setString(3, direction.getProvince());
            statement.setString(4, direction.getCountry());
            statement.setLong(5, direction.getId()); // Pasar el id para filtrar y no actualizar todos
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

            // 2 - Create Statement
            String sql = """
                    DELETE FROM directions
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
    }

    @Override
    public void deleteByUserId(Long id) {


    }
}