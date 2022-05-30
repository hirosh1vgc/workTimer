<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー削除確認</title>
<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
</head>
<body>
<h1>workTimer</h1>
<h3>ユーザー名「${deleteUser.VUserName }」を削除しますか？</h3>
<ul>
	<li>ユーザーID: ${deleteUser.VUserId }</li>
	<li>ログインID: ${deleteUser.VLoginId }</li>
	<li>パスワード: ${deleteUser.VPassword }</li>
	<li>権限: ${deleteUser.VUserLv }</li>
	<li>在籍状況: ${deleteUser.VEnrollmentLv }</li>
</ul>

<form action="/workTimer/UserDeleteServlet" method="post">
	<input type="hidden" value="${deleteUser.VLoginId }" name="loginid">
	<input type="hidden" name="action" value="Delete">
	<input type="submit" class="btn btn-danger" value="確定">
</form>
<a href="/workTimer/UserSearchServlet">ユーザー管理画面へ戻る</a><br>
<a href="/workTimer/LogoutServlet">ログアウト</a>
<script src="js/bootstrap/bootstrap.min.js"></script>
</body>
</html>