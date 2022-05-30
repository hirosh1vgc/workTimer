<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>勤務記録表示</title>
<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">

</head>
<body>
<h1>workTimer</h1>
<c:choose>
	<c:when test="${!empty memoryuser }">
		<c:set var="userid" value="${memoryuser.userId }" />
		<h3>${memoryuser.userName }さん（ログインID: ${memoryuser.loginId }）の、${memorymonth.smonth }の勤務記録一覧を表示します。</h3>
	</c:when>
	<c:otherwise>
		<c:set var="userid" value="${account.userId }" />
		<h3>${account.userName }さん（ログインID: ${account.loginId }）の、${memorymonth.smonth }の勤務記録一覧を表示します。</h3>
	</c:otherwise>
</c:choose>

<div class="table-responsive">
<table class="table caption-top" style="width : 70%; margin-left : 2em" >
	<caption>通常勤務時間: 「${wd.wakeup }」～「${wd.sleep }」・休憩時間「${wd.rest }分」</caption>
 	<thead>
		<tr class="table-active">
			<th scope="col">勤務日</th>
			<th>勤務開始</th>
			<th>勤務終了</th>
			<th>勤務時間</th>
			<th>残業時間</th>
			<th>勤務内容</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="item" items="${wmemory }">
	<tr>
		<td>${item.workDay }</td>
		<td>${item.workStart }</td>
		<td>${item.workStop }</td>
		<td>${item.workLength }</td>
		<td>${item.workOver }</td>
		<td>${item.workDetail }</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
</div>
<ul style="list-style-type: none">
	<li>${memorymonth.smonth }の勤務日数: ${wmemory.size() }日</li>
	<li>${lengthMsg }</li>
</ul>

<form action="/workTimer/csvExportServlet" method="post">
	<input type="submit" class="btn btn-primary" value="CSVダウンロード">
	<input type= "hidden" name="userid" value="<c:out value='${userid }' />">
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