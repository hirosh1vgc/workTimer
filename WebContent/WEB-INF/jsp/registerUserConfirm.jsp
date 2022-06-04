<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>新規ユーザー登録内容確認</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	 rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body>
<h1>workTimer</h1>
<p>以下の内容で新規ユーザーを登録します。よろしいですか？</p>

	<form action="/workTimer/UserRegisterServlet" method="post">
		<input type="hidden" name="action" value="Register">
		<ul style="list-style-type: none">
			<li>ユーザーID: ${user.getLoginId() }</li>
			<li>パスワード: ${user.getPassword() }</li>
			<li>ユーザー名: ${user.getUserName() }</li>
			<li>権限: 「
				<c:choose>
					<c:when test="${user.getUserLv() ==1}">ユーザー</c:when>
					<c:when test="${user.getUserLv() ==2}">管理者</c:when>
				</c:choose>」
			</li>
		</ul>
		<div class="button mb-3" style="margin-left: 2pc">
			<button class="btn btn-primary" type="submit">確定</button>
		</div>
	</form>

	<div class="a"  style="margin-left: 2pc">
		<a href="/workTimer/UserRegisterServlet">入力へ戻る</a><br>
		<a href="/workTimer/LogoutServlet">ログアウト</a>
	</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
 integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous">
</script>
</body>
</html>