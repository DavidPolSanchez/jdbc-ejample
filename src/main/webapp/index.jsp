<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Únicamente redirige al servlet para recuperar datos y mostrar el listado --%>
<% response.sendRedirect(request.getContextPath() + "/users"); %>