<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>勤務記録表示</title>
	<link rel="stylesheet" type="text/css" href="css/admin.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	 rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body>
<h1>workTimer</h1>
<c:choose>
	<c:when test="${!empty memoryuser }">
		<c:set var="userid" value="${memoryuser.userId }" />
		<h5>${memoryuser.userName }さん（ログインID: ${memoryuser.loginId }）の、${memorymonth.smonth }の勤務記録一覧を表示します。</h5>
	</c:when>
	<c:otherwise>
		<c:set var="userid" value="${account.userId }" />
		<h5>${account.userName }さん（ログインID: ${account.loginId }）の、${memorymonth.smonth }の勤務記録一覧を表示します。</h5>
	</c:otherwise>
</c:choose>

<div class="table-responsive">
<table class="table caption-top">
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
	<input type="submit" class="btn btn-light" value="CSV出力" style="margin-left: 2pc;">
	<input type= "hidden" name="userid" value="<c:out value='${userid }' />">
</form>

<div class="a">
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