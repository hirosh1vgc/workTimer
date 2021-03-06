<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>workTimer</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
 rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>

<body>
<div class="container">
	<h1>workTimer</h1>
	<h5 style="color: red">${loginMsg }</h5>
	<div class="row">
		<form action="/workTimer/LoginServlet" method="post">
			<div class="col-md-5 w-75">
				<label for="user_id" class="form-label">user_id</label>
				<input type="text" name="user_id" class="form-control">
			</div>
			<div class="col-md-5 w-75">
				<label for="password" class="form-label">password</label>
				<input type="password" name="password" class="form-control">
			</div>
			<div class="mt-md-2">
				<button class="btn btn-primary" type="submit">ログイン</button>
			</div>
		</form>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
 integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>