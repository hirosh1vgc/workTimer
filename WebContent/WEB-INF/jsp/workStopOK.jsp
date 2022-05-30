<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDate, java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>退勤済</title>
</head>
<body>

<h1>workTimer</h1>
<p>${logindata.VUserName }さん（ログインID: ${account.loginId }）の、${workstart.workday }の勤務は終了しました。</p>
<p>${workstart.workday }の勤務の開始時刻は「${workstart.workstart }」、終了時刻は「${workstop.stopTime }」でした。<br>
	勤務時間は「${workstop.workLength }」、残業時間は「${workstop.workOver }」でした。
<p>おつかれさまでした。</p>

<c:if test="${account.userLv == 1 }">
	<a href="/workTimer/UserMainServlet">ユーザーメイン画面へ戻る</a><br>
</c:if>
<c:if test="${account.userLv == 2 }">
	<a href="/workTimer/AdminMainServlet">管理者メイン画面へ戻る</a><br>
</c:if>
<a href="/workTimer/LogoutServlet">ログアウト</a>

</body>
</html>