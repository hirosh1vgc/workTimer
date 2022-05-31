<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate, java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>勤務開始</title>
	<link rel="stylesheet" type="text/css" href="css/work.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	 rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body>
<% LocalDate now = LocalDate.now();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("y年MM月dd日");
	String today = now.format(dtf);
%>
<h1>workTimer</h1>
<h3>ようこそ、${account.userName }さん（ログインID: ${account.loginId }）</h3>
<p><%= today %>の勤務を開始します。勤務開始ボタンをクリックしてください。</p>

<form action="/workTimer/TimerMainServlet" method="post">
	<input type="hidden" name="action" value="workStart">
	<input type="hidden" name="userid" value="${logindata.VUserId }">
	<input type="hidden" name="userlv" value="${account.userLv }">
	<button class="btn btn-primary" type="submit" id="start">勤務開始</button>
</form>

<div class="back">
	<c:if test="${account.userLv == 1 }">
		<a href="/workTimer/UserMainServlet">ユーザーメイン画面へ戻る</a><br>
	</c:if>
	<c:if test="${account.userLv == 2 }">
		<a href="/workTimer/AdminMainServlet">管理者メイン画面へ戻る</a><br>
	</c:if>
	<a href="/workTimer/LogoutServlet">ログアウト</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
 integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous">
</script>
</body>
</html>