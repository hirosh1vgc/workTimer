<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ユーザー削除確認</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
 rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
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
<a href="/workTimer/AdminMainServlet">ユーザー管理画面へ戻る</a><br>
<a href="/workTimer/LogoutServlet">ログアウト</a>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
 integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous">
</script>
</body>
</html>