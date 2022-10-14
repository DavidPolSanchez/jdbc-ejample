<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>User List</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
</head>
<body>
	<jsp:include page="navbar.jsp"/>
	<div class = "pt-5 container">
		
		<h1>User list</h1>
		<hr/>


		<p>
			<button class = "btn btn-primary" onclick="window.location.href = 'user-edit.jsp'">Create user</button>
		</p>
		<table class = "table table-striped table-bordered table-hover">
			
			<tr class = "table-primary">
				<th>ID</th>
				<th>FirstName</th>
				<th>LastName</th>
				<th>Email</th>
				<th>Age</th>
				<th>Phone</th>
				<th>Actions</th>
			</tr>
			
			<c:forEach items="${users}" var="user">
			
				<tr>
					<td>${user.id}</td>
					<td>${user.firstName}</td>
					<td>${user.lastName}</td>
					<td>${user.email}</td>
					<td>${user.age}</td>
					<td>${user.phone}</td>
					<td>
						<a class="btn btn-info" href="${pageContext.request.contextPath}/users?action=update&id=${user.id}">Edit</a>
						<a class="btn btn-danger" href="${pageContext.request.contextPath}/users?action=delete&id=${user.id}">Delete</a>
					</td>
				</tr>
				
			</c:forEach>
			
		</table>
		
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>