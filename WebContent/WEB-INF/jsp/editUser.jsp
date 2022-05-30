<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.V_User, java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% V_User editUser = (V_User)session.getAttribute("editUser"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>ユーザー情報編集</title>
	<link rel="stylesheet" type="text/css" href="css/registerUser.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	 rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body>
<p>ログインユーザーID: <%=editUser.getVLoginId() %>さん(在籍状況: <%=editUser.getVEnrollmentLv() %>)の情報を編集します。</p>

<c:if test="${errorMsg.length()>0}">
<p style="color : red;">${errorMsg}</p>
</c:if>

<form action="/workTimer/UserEditServlet" method="post">
	<input type="hidden" name="action" value="EditConfirm">
	<input type="hidden" name="login_id" value="<%=editUser.getVLoginId() %>">
	<div class="form">

		<div class="mb-3">
			<label for="password" class="form-label">新規パスワード(半角英数字10文字以内)</label>
			<input type="text" name="password" value="<%=editUser.getVPassword()  %>" class="form-control">
		</div>
		<div class="mb-3">
			<label for="user_name" class="form-label">新規ユーザー名(日本語)</label>
			<input type="text" name="user_name" value="<%=editUser.getVUserName()  %>" class="form-control">
		</div>
		<div>
		ユーザー権限：　
			<div class="form-check form-check-inline">
				<input type="radio" class="form-check-input" value="1" name="userlv" checked>
				<label for="enrollment" class="form-check-label">ユーザー</label>
			</div>
			<div class="form-check form-check-inline">
		 		<input type="radio" class="form-check-input" value="2" name="userlv">
				<label for="retire" class="form-check-label">管理者</label>
			</div>
		</div>
		<div>
			在籍状況：　
			<div class="form-check form-check-inline">
				<input type="radio" class="form-check-input" value="1" name="enrollment" checked>
				<label for="enrollment" class="form-check-label">在籍中</label>
			</div>
			<div class="form-check form-check-inline">
		 		<input type="radio" class="form-check-input" value="2" name="enrollment">
				<label for="retire" class="form-check-label">退社済</label>
			</div>
		</div>
	</div>
	<div class="button">
		<button class="btn btn-primary" type="submit">確認</button>
	</div>
</form>

<a href="/workTimer/AdminMainServlet">ユーザー一覧に戻る</a><br>
<a href="/workTimer/LogoutServlet">ログアウト</a><br>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
 integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous">
</script>
</body>
</html>