<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー編集内容確認</title>
<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
</head>
<body>
<h1>workTimer</h1>
<p>ログインID:「${edituser.loginId }」さんの情報を以下の通り編集します。よろしいですか？</p>
<form action="/workTimer/UserEditServlet" method="post">
<input type="hidden" name="action" value="Edit">
<ul style="list-style-type: none">
	<li>パスワード: ${edituser.password }</li>
	<li>ユーザー名: ${edituser.userName }</li>
	<li>権限レベル: 「
		<c:choose>
			<c:when test="${edituser.userLv ==1}">ユーザー</c:when>
			<c:when test="${edituser.userLv ==2}">管理者</c:when>
		</c:choose>」
	</li>
	<li>在籍状況: 「
		<c:choose>
			<c:when test="${edituser.enrollmentLv ==1}">在籍中</c:when>
			<c:when test="${edituser.enrollmentLv ==2}">退社済</c:when>
		</c:choose>」
	</li>
</ul>
<input type="submit" class="btn btn-primary" value="確定">
</form>

<a href="/workTimer/UserEditServlet">入力へ戻る</a><br>
<a href="/workTimer/AdminMainServlet">ユーザー情報一覧へ戻る</a><br>
<a href="/workTimer/LogoutServlet">ログアウト</a>
<script src="js/bootstrap/bootstrap.min.js"></script>
</body>
</html>