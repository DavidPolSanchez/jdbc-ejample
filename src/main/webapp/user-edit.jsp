<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User edit</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
</head>
<body>
	<jsp:include page="navbar.jsp"/>
	<div class = "mt-5 mb-5 container">

		<c:choose>
			<c:when test="${empty user.id}">
				<h2>Create new user</h2>

			</c:when>
			<c:otherwise>
				<h2>Edit user ${user.id}</h2>
			</c:otherwise>
		</c:choose>
		<hr/>
		
		<div class = "row">
			<div class = "col-md-4">
				<form action = "${pageContext.request.contextPath}/users" method="POST">

					<div class="mb-3">
						<label for="firstName" class="form-label">First Name</label>
						<input type="text" required class="form-control" id="firstName" name="firstName" placeholder="Juan" value="${user.firstName}">
					</div>

					<div class="mb-3">
						<label for="lastName" class="form-label">Last Name</label>
						<input type="text" class="form-control" id="lastName" name="lastName" placeholder="García" value="${user.lastName}">
					</div>

					<div class="mb-3">
						<label for="email" class="form-label">Email</label>
						<input type="email" required class="form-control" id="email" name="email" placeholder="juan@company.com" value="${user.email}">
					</div>

					<div class="mb-3">
						<label for="age" class="form-label">Age</label>
						<input type="number" required class="form-control" id="age" name="age" placeholder="33" value="${user.age}">
					</div>

					<div class="mb-3">
						<label for="phone" class="form-label">Phone</label>
						<input type="text" class="form-control" id="phone" name="phone" placeholder="666555444" value="${user.phone}">
					</div>

					<div class="mb-3">
						<label for="street" class="form-label">Street</label>
						<input type="text" required class="form-control" id="street" name="street" placeholder="Calle falsa" value="${user.direction.street}">
					</div>

					<div class="mb-3">
						<label for="postalCode" class="form-label">Postal Code</label>
						<input type="text" class="form-control" id="postalCode" name="postalCode" placeholder="44050" value="${user.direction.postalCode}">
					</div>

					<div class="mb-3">
						<label for="province" class="form-label">Province</label>
						<input type="text" class="form-control" id="province" name="province" placeholder="Madrid" value="${user.direction.province}">
					</div>

					<div class="mb-3">
						<label for="country" class="form-label">Country</label>
						<input type="text" class="form-control" id="country" name="country" placeholder="Spain" value="${user.direction.country}">
					</div>

					<input type = "hidden" name = "idDirection" value = "${user.direction.id}"/>
					<input type = "hidden" name = "id" value = "${user.id}"/>
				
					<button type = "submit" class = "btn btn-primary mt-3">Save</button>
				</form>
			</div>
		</div>
		<a href = "${pageContext.request.contextPath}/users">Back to List</a>
	</div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

</body>
</html>