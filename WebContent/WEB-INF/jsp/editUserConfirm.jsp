<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>ユーザー編集内容確認</title>
	<link rel="stylesheet" type="text/css" href="css/editUser.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	 rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body>
	<h1>workTimer</h1>
	<p>ログインID:「${edituser.loginId }」さんの情報を以下の通り編集します。よろしいですか？</p>
	<form action="/workTimer/UserEditServlet" method="post">
	<input type="hidden" name="action" value="Edit">
	<ul>
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
		<div class="buttons">
			<button class="btn btn-success" type="submit">確定</button>
		</div>
	</form>

	<a href="/workTimer/UserEditServlet">入力へ戻る</a><br>
	<a href="/workTimer/AdminMainServlet">ユーザー情報一覧へ戻る</a><br>
	<a href="/workTimer/LogoutServlet">ログアウト</a>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
	 integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous">
	</script>
</body>
</html>