<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee Directory</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<h1>Employee Directory</h1>

		<p style="color: #b50e0e">${NOTIFICATION}</p>
		<hr>
		<p>
			<button class="btn btn-success"
				onclick="window.location.href = '/MVC-QRCode/EmployeeForm.jsp'"
				value="save">Add Employee</button>
		</p>
		<table class="table table-striped table-bordered">
			<tr class="thead-dark">
				<th>Id</th>
				<th>Name</th>
				<th>Profile Pic </th>
				<th>Department</th>
				<th>Date of birth</th>
				<th>QR Code</th>
				<th>Actions</th>
			</tr>

			<c:forEach items="${employee}" var="employee">

				<tr>
					<td>${employee.id}</td>
					<td>${employee.name}</td>
					<td><img width=200 src="data:image/png;base64,${employee.profile_pic}"></td>
					<td>${employee.department}</td>
					<td>${employee.dob}</td>
					<td><img src="data:image/png;base64,${employee.base64Image}"></td>
					<td><a
						href="${pageContext.request.contextPath}/QRCodeController?action=EDIT&id=${employee.id}">Edit |</a>
						<a
						href="${pageContext.request.contextPath}/QRCodeController?action=DELETE&id=${employee.id}"
						onclick="return confirm('Are you sure you want to delete this item');">Delete</a></td>
				</tr>

			</c:forEach>
		</table>
	</div>
</body>
</html>