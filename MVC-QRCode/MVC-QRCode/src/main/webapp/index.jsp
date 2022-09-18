<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

form {
	margin: 0 auto;
	border: 3px solid #f1f1f1;
}

input[type=text], input[type=password] {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	box-sizing: border-box;
}

button {
	background-color: #4CAF50;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 100%;
}

button:hover {
	opacity: 0.8;
}

.imgcontainer {
	text-align: center;
	margin: 12px 0 6px 0;
}

.container {
	padding: 16px;
}

span.psw {
	float: right;
	padding-top: 16px;
}

}
</style>
</head>
<body>
	<p></p>
	<p style="color: #b50e0e" align=center>${NOTIFICATION}</p>
	<form style="max-width: 480px" action="QRCodeController" method="POST">
		<div>
			<h2 align=center>Employee Management Systems</h2>
		</div>
		<div class="imgcontainer">
			<img height="200"
				src="<c:url value="https://hrdailyadvisor.blr.com/app/uploads/sites/3/2017/02/Productive-Happy-Workers.jpg"/>" />
		</div>

		<div class="container">
			<label for="uname"><b>Username</b></label> <input type="text"
				placeholder="Enter Username" name="uname" required> <label
				for="psw"><b>Password</b></label> <input type="password"
				placeholder="Enter Password" name="psw">

			<button type="submit" class="btn btn-success">LOGIN</button>

		</div>
	</form>

</body>
</html>