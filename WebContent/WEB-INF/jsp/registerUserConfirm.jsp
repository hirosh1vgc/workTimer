<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規ユーザー登録内容確認</title>
<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
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
	<input type="submit" class="btn btn-primary" value="確定">
</form>

<a href="/workTimer/UserRegisterServlet">入力へ戻る</a><br>
<a href="/workTimer/LogoutServlet">ログアウト</a>
<script src="js/bootstrap/bootstrap.min.js"></script>
</body>
</html>