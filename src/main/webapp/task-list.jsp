<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Task List</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
</head>
<body>
	<jsp:include page="navbar.jsp"/>
	<div class = "pt-5 container">
		
		<h1>Task list</h1>
		<hr/>


		<p>
			<a class="btn btn-primary" href="${pageContext.request.contextPath}/tasks?action=create">Create task</a>
		</p>
		<table class = "table table-striped table-bordered table-hover">
			
			<tr class = "table-primary">
				<th>ID</th>
				<th>Title</th>
				<th>Description</th>
				<th>User</th>
				<th>Actions</th>
			</tr>
			
			<c:forEach items="${tasks}" var="task">
			
				<tr>
					<td>${task.id}</td>
					<td>${task.title}</td>
					<td>${task.description}</td>
					<td>${task.user.id}</td>
					<td>
						<a class="btn btn-info" href="${pageContext.request.contextPath}/tasks?action=update&id=${task.id}">Edit</a>
						<a class="btn btn-danger" href="${pageContext.request.contextPath}/tasks?action=delete&id=${task.id}">Delete</a>
					</td>
				</tr>
				
			</c:forEach>
			
		</table>
		
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>