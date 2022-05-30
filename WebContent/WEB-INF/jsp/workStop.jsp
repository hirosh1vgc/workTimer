<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate, java.time.format.DateTimeFormatter, model.WorkStart" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>勤務終了</title>
<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
</head>
<body>
<%
	WorkStart ws = (WorkStart)session.getAttribute("workstart");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("y年MM月dd日");
	LocalDate wd = ws.getWorkday();
	String workday = wd.format(dtf);
%>

<h1>workTimer</h1>
<h3>ようこそ、${logindata.VUserName }さん（ログインID: ${account.loginId }）</h3>
<p>${logindata.VUserName }さんは、<%= workday %>の勤務中です。<br>
	本日の勤務の開始時刻は、「${workstart.workstart }」です。<br>
	勤務が終了したら退勤ボタンをクリックしてください。</p>

<c:if test="${errorMsg.length()>0}">
	<p style="color : red;">${errorMsg}</p>
</c:if>

<form action="/workTimer/TimerMainServlet" method="post">
	<input type="hidden" name="action" value="workStop">
	<input type="hidden" name="userid" value="${account.userId }">
	<input type="hidden" name="userlv" value="${account.userLv }">
	<div class="form">
		<div class="mb-3">
			<label for="detail" class="form-label">勤務内容(50文字以内)</label>
			<input type="text" name="detail" class="form-control" value="<c:out value='${inDetail }' />">
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
<script src="js/bootstrap/bootstrap.min.js"></script>
</body>
</html>