<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate, java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>勤務時間再確認</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	 rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body>

<h1>workTimer</h1>
<h3>ようこそ、${logindata.VUserName }さん（ログインID: ${account.loginId }）</h3>
<p>${logindata.VUserName }さんは、「${workstart.workday }」の「${workstart.workstart }」に開始した勤務を終了していません。<br>
	勤務終了時間と勤務内容を入力し、勤怠記録を確定させてください。
</p>

<c:if test="${errorMsg.length() >0}">
	<p style="color : red;">${errorMsg }</p>
</c:if>

<form action="/workTimer/TimerMainServlet" method="post">
	<input type="hidden" name="action" value="workStopConfirm">
	<input type="hidden" name="userid" value="${account.userId }">
		<input type="hidden" name="userlv" value="${account.userLv }">
	<div class="time">
		<label for="time">勤務終了時刻を入力してください。</label>
		<input type="time" name="workstop"  min="${workstart.workstart }" required>
	</div>
	<div class="form">
		<div class="mb-3">
			<label for="detail" class="form-label">勤務内容(50文字以内)</label>
			<input type="text" name="detail" class="form-control">
		</div>
	</div>
	<div class="button">
		<button class="btn btn-primary" type="submit">退勤</button>
	</div>
</form>

<c:if test="${account.userLv == 1 }">
	<a href="/workTimer/UserMainServlet">ユーザーメイン画面へ戻る</a><br>
</c:if>
<c:if test="${account.userLv == 2 }">
	<a href="/workTimer/AdminMainServlet">管理者メイン画面へ戻る</a><br>
</c:if>
<a href="/workTimer/LogoutServlet">ログアウト</a>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
 integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous">
</script>
</body>
</html>