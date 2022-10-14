package com.example.jdbc_demo.controller;

import com.example.jdbc_demo.dao.UserDAO;
import com.example.jdbc_demo.dao.impl.UserDAOImpl;
import com.example.jdbc_demo.model.Direction;
import com.example.jdbc_demo.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "userServlet", value = "/users")
public class UserController extends HttpServlet {

    private final UserDAO userDAO = new UserDAOImpl();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "";


        switch (action) { // Switch Expression Java SE 14
            // mostrar pantalla edición
            case "update" -> showEdit(request, response);
            // borrar
            case "delete" -> delete(request, response);
            // mostrar listado
            default -> retrieve(request, response);
        }
    }

    /**
     * Muestra los usuarios
     */
    private void retrieve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("users", userDAO.findAll());
        request.getRequestDispatcher("user-list.jsp").forward(request, response);
    }

    /**
     * Borra un usuario
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extraer parámetro de empresa a borrar
        String id = request.getParameter("id");
        // Borrar empresa
        userDAO.delete(Long.valueOf(id));
        // Volver al listado
        retrieve(request, response);
    }

    /**
     * Muestra la pantalla edición
     */
    private void showEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extraer id de la url
        String id = request.getParameter("id");
        // Recuperar el elemento por su id
        User user = userDAO.findById(Long.valueOf(id));
        // Cargar el elemento en la request para cargar sus datos en el formulario para permitir editarlo
        request.setAttribute("user", user);
        // redirigir a la vista edición
        request.getRequestDispatcher("user-edit.jsp").forward(request, response);
    }


    /**
     * Método para recibir peticiones de tipo HTTP POST.
     * Permite CREAR y ACTUALIZAR
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extraer parametros
        String id = request.getParameter("id");
        Long idUser = null;
        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty())
            idUser = Long.valueOf(request.getParameter("id"));

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        Integer age = null;
        if (request.getParameter("age") != null && !request.getParameter("age").isEmpty())
            age = Integer.valueOf(request.getParameter("age"));

        String phone = request.getParameter("phone");

        // direction
        String street = request.getParameter("street");
        String postalCode = request.getParameter("postalCode");
        String province = request.getParameter("province");
        String country = request.getParameter("country");

        Long idDirection = null;
        if (request.getParameter("idDirection") != null && !request.getParameter("idDirection").isEmpty())
            idDirection = Long.valueOf(request.getParameter("idDirection"));

        // Asignar datos:
        Direction direction = new Direction(idDirection, street, postalCode, province, country);
        User user = new User(idUser, firstName, lastName, email, age, phone);
        user.setDirection(direction);

        // Si hay id es actualización
        if (user.getId() != null)
            userDAO.update(user);
        else
            userDAO.create(user);

        // Volver al listado
        retrieve(request, response);
    }

}
