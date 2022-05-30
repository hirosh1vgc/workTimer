<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>workTimer</title>
<link rel="stylesheet" type="text/css" href="css/login.css">
<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
</head>

<body>
<h1>workTimer</h1>

<form action="/workTimer/LoginServlet" method="post" class="form">
<div class="mb-3">
	<label for="user_id" class="form-label">user_id</label>
	<input type="text" name="user_id" class="form-control">
</div>
<div class="mb-3">
	<label for="password" class="form-label">password</label>
	<input type="password" name="password" class="form-control">
</div>
<button class="btn btn-primary" type="submit">ログイン</button>
</form>

<div class="form-footer">
	<h5 style="color: red">${loginMsg }</h5>
</div>

<script src="js/bootstrap/bootstrap.min.js"></script>
</body>
</html>