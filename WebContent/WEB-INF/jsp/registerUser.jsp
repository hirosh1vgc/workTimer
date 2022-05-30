<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>新規ユーザー登録</title>
	<link rel="stylesheet" type="text/css" href="css/registerUser.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	 rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body>
<h1>新規ユーザー登録</h1>

<c:if test="${errorMsg.length()>0}">
<p style="color : red;">${errorMsg }</p>
</c:if>

<!-- 初回の初期値を設定 -->
<c:set var="inUserName" value=""/>
<c:set var="inPassword" value=""/>
<c:set var="inLoginid" value=""/>

<!-- データエラーが発生した場合 -->
  <c:if test="${not empty user.loginId }">
    <c:set var="inLoginid" value="${user.loginId }" />
  </c:if>
  <c:if test="${not empty user.password }">
    <c:set var="inPassword" value="${user.password }" />
  </c:if>
    <c:if test="${not empty user.userName }">
    <c:set var="inUserName" value="${user.userName }" />
  </c:if>

<form action="/workTimer/UserRegisterServlet" method="post">
	<input type="hidden" name="action" value="Confirm">
	<div class="form">
		<div class="mb-3">
			<label for="login_id" class="form-label">新規ログインID(半角英数字10文字以内)</label>
			<input type="text" name="login_id" class="form-control" value="<c:out value='${inLoginid }' />">
		</div>
		<div class="mb-3">
			<label for="password" class="form-label">新規パスワード(半角英数字10文字以内)</label>
			<input type="text" name="password" class="form-control" value="<c:out value='${inPassword }' />">
		</div>
		<div class="mb-3">
			<label for="user_name" class="form-label">新規ユーザー名(日本語)</label>
			<input type="text" name="user_name" class="form-control" value="<c:out value='${inUserName }' />">
		</div>
		<div class="form-check form-check-inline">
			<input type="radio" class="form-check-input" value="1" name="admin" checked>
			<label for="user" class="form-check-label">通常ユーザー</label>
		</div>
		<div class="form-check form-check-inline">
	 		<input type="radio" class="form-check-input" value="2" name="admin">
			<label for="admin" class="form-check-label">管理者</label>
		</div>
	</div>
	<div class="button">
		<button class="btn btn-primary" type="submit">確認</button>
	</div>
</form>
<a href="/workTimer/AdminMainServlet">ユーザー管理画面に戻る</a><br>
<a href="/workTimer/LogoutServlet">ログアウト</a>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
 integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous">
</script>
</body>
</html>