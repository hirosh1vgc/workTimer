<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>月選択</title>
	<link rel="stylesheet" type="text/css" href="css/admin.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	 rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

</head>
<body>
<h1>workTimer</h1>

<c:choose>
	<c:when test="${!empty vmonths }">
	<h3>ユーザー名「${memoryuser.userName }」の過去の勤務月を選択してください。</h3>
		<form action="/workTimer/UserMainServlet" method="post">
			<input type="hidden" name="userid" value="${memoryuser.userId }">
			<input type="hidden" name="action" value="adminMemory">
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
	</c:when>
	<c:otherwise>ユーザー名「${memoryuser.userName }」の過去の勤務履歴はありません。<br></c:otherwise>
</c:choose>

<a href="/workTimer/AdminMainServlet">管理者メイン画面へ戻る</a><br>
<a href="/workTimer/LogoutServlet">ログアウト</a>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
 integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous">
</script>
</body>
</html>