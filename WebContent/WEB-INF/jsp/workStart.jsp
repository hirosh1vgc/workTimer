<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate, java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>勤務開始</title>
<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
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
	<button class="btn btn-primary" type="submit">勤務開始</button>
</form>

<c:if test="${account.userLv == 1 }">
	<a href="/workTimer/UserMainServlet">ユーザーメイン画面へ戻る</a><br>
</c:if>
<c:if test="${account.userLv == 2 }">
	<a href="/workTimer/AdminMainServlet">管理者メイン画面へ戻る</a><br>
</c:if>
<a href="/workTimer/LogoutServlet">ログアウト</a>
<script src="js/bootstrap/bootstrap.min.js"></script>

</body>
</html>