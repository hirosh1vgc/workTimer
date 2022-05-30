<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザーメイン画面</title>
<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
</head>
<body style="margin-left: 2em">
<h1>workTimer</h1>
<h3>ようこそ、${account.userName }さん（ログインID: ${account.loginId }）</h3>
	<c:if test="${scMsg.length()>0}">
		<p style="color : blue;">${scMsg}<br></p>
	</c:if>
	<a href="/workTimer/TimerMainServlet">勤怠記録へ</a>
	<ul style="list-style-type: none">
		<li>通常勤務時間: 「${wd.wakeup }」～「${wd.sleep }」</li>
		<li>休憩時間「${wd.rest }分」</li>
	</ul>

<c:if test="${not empty vmonths }">
	<form action="/workTimer/UserMainServlet" method="post">
	<input type="hidden" name="userid" value="${account.userId }">
	<input type="hidden" name="action" value="userMain">
	<h5>過去の勤怠記録を確認する</h5>
	<div class="mb-3">
		<label for="monthSelect" class="form-label">過去の勤務年月を選択</label>
		<select class="form-select" name="monthSelect" style="width : 9em" required>
			<c:forEach var="item" items="${vmonths }">
				<option value="${item.workDay }">${item.smonth }</option>
			</c:forEach>
		</select>
	</div>
	<button class="btn btn-primary" type="submit">検索</button>
	</form>
</c:if>

<a href="/workTimer/LogoutServlet">ログアウト</a>
<script src="js/bootstrap/bootstrap.min.js"></script>
</body>
</html>