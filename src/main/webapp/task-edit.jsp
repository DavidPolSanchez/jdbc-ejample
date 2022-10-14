<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Task edit</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
</head>
<body>
	<jsp:include page="navbar.jsp"/>
	<div class = "mt-5 mb-5 container">

		<c:choose>
			<c:when test="${empty task.id}">
				<h2>Create new task</h2>

			</c:when>
			<c:otherwise>
				<h2>Edit task ${task.id}</h2>
			</c:otherwise>
		</c:choose>
		<hr/>
		
		<div class = "row">
			<div class = "col-md-4">
				<form action = "${pageContext.request.contextPath}/tasks" method="POST">

					<div class="mb-3">
						<label for="title" class="form-label">Title</label>
						<input type="text" class="form-control" id="title" name="title" placeholder="Payment Gateway development" value="${task.title}">
					</div>

					<div class="mb-3">
						<label for="description" class="form-label">Description</label>
						<textarea class="form-control" id="description" name="description" rows="3">${task.description}</textarea>
					</div>

					<select name='user' class="form-select" required>
						<option value="${task.user.id}" selected>${task.user.firstName}</option>
						<c:forEach items="${users}" var="user">
							<c:if test="${user.id != task.user.id}">
								<option value="${user.id}">${user.firstName}</option>
							</c:if>
						</c:forEach>
					</select>


					<input type = "hidden" name = "id" value = "${task.id}"/>
				
					<button type = "submit" class = "btn btn-primary mt-3">Save</button>
				</form>
			</div>
		</div>
		<a href = "${pageContext.request.contextPath}/tasks">Back to List</a>
	</div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

</body>
</html>