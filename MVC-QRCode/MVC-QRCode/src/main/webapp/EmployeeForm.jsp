<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

<script>
	var loadFile = function(event) {
		var output = document.getElementById('output');
		output.src = URL.createObjectURL(event.target.files[0]);
		output.onload = function() {
			URL.revokeObjectURL(output.src) // free memory
		}
	};
</script>
</head>
<body>

	<div class="container">

		<h1>Employee Directory</h1>
		<hr />


		<div class="row">
			<div class="col-md-4">

				<form action="QRCodeController?action=SAVE" method="POST"
					enctype="multipart/form-data">
					<div class="form-group">
						<input type="text" class="form-control" name="id"
							placeholder="Enter Id" value="${employee.id}" />
					</div>
					<div class="form-group">
						<input type="text" class="form-control" name="name"
							placeholder="Enter Name" value="${employee.name}" />
					</div>
					<div class="form-group">
						Select Profile Picture to upload: <input type="file"
							id="profile_pic" name="profile_pic" accept=".jpg, .jpeg, .png"
							onchange="loadFile(event)">
					</div>

					<c:set var="profile" value="${employee.profile_pic}" />
					<c:if test="${profile!=null}">
						<div id="div_profile" class="form-group">
							<img width="200" id="output"
								src="data:image/png;base64,${employee.profile_pic}" />
						</div>
					</c:if>
					<div class="form-group">
						<img width="200" id="output" />
					</div>

					<div class="form-group">
						<input type="date" class="form-control" name="dob"
							placeholder="Enter DOB" value="${employee.dob}" />
					</div>

					<div class="form-group">
						<input type="text" class="form-control" name="department"
							placeholder="Enter Department" value="${employee.department}" />
					</div>
					<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
		<a
			href=${pageContext.request.contextPath}/QRCodeController?action=LIST>Back
			to List</a>
	</div>

</body>