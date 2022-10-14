package com.example.jdbc_demo.controller;

import com.example.jdbc_demo.dao.TaskDAO;
import com.example.jdbc_demo.dao.UserDAO;
import com.example.jdbc_demo.dao.impl.TaskDAOImpl;
import com.example.jdbc_demo.dao.impl.UserDAOImpl;
import com.example.jdbc_demo.model.Direction;
import com.example.jdbc_demo.model.Task;
import com.example.jdbc_demo.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "taskServlet", value = "/tasks")
public class TaskController extends HttpServlet {

    private final TaskDAO taskDAO = new TaskDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "";

        switch (action) { // Switch Expression Java SE 14
            // mostrar pantalla creación
            case "create" -> showCreate(request, response);
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
        request.setAttribute("tasks", taskDAO.findAll());
        request.getRequestDispatcher("task-list.jsp").forward(request, response);
    }

    /**
     * Borra un usuario
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extraer parámetro de empresa a borrar
        String id = request.getParameter("id");
        // Borrar empresa
        taskDAO.delete(Long.valueOf(id));
        // Volver al listado
        retrieve(request, response);
    }


    /**
     * Muestra la pantalla creacion
     */
    private void showCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("users", userDAO.findAll());
        // redirigir a la vista edición
        request.getRequestDispatcher("task-edit.jsp").forward(request, response);
    }

    /**
     * Muestra la pantalla edición
     */
    private void showEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extraer id de la url
        String id = request.getParameter("id");
        // Recuperar el elemento por su id
        Task task = taskDAO.findById(Long.valueOf(id));
        // Cargar el elemento en la request para cargar sus datos en el formulario para permitir editarlo
        request.setAttribute("task", task);
        request.setAttribute("users", userDAO.findAll());
        // redirigir a la vista edición
        request.getRequestDispatcher("task-edit.jsp").forward(request, response);
    }


    /**
     * Método para recibir peticiones de tipo HTTP POST.
     * Permite CREAR y ACTUALIZAR
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extraer parametros
        String id = request.getParameter("id");
        Long idTask = null;
        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty())
            idTask = Long.valueOf(request.getParameter("id"));

        String title = request.getParameter("title");
        String description = request.getParameter("description");

        Long idUser = null;
        if (request.getParameter("user") != null && !request.getParameter("user").isEmpty())
            idUser = Long.valueOf(request.getParameter("user"));

        // Asignar datos:
        User user = new User(idUser);
        Task task = new Task(idTask, title, description, user);

        // Si hay id es actualización
        if (task.getId() != null)
            taskDAO.update(task);
        else
            taskDAO.create(task);

        // Volver al listado
        retrieve(request, response);
    }

}
