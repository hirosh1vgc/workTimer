<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>管理者メイン画面</title>
	<link rel="stylesheet" type="text/css "href="css/admin.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	 rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body>
	<h1>workTimer</h1>
	<c:if test="${scMsg.length()>0}">
		<p style="color : blue;">${scMsg }<br></p>
	</c:if>
	<div class="work">
		<a href="/workTimer/TimerMainServlet">勤怠記録へ</a>
		<ul style="list-style-type: none">
			<li>通常勤務時間: 「${wd.wakeup }」～「${wd.sleep }」</li>
			<li>休憩時間「${wd.rest }分」</li>
		</ul>
	</div>
	<h2>管理者用メニュー</h2>
	<div class="table-responsive">
	<table class="table caption-top">
		<caption>登録済全ユーザー一覧</caption>
		<thead>
			<tr class="table-active">
				<th  scope="col" style="width : auto">ID</th>
				<th>名前</th>
				<th>ログインID</th>
				<th>パスワード</th>
				<th>権限</th>
				<th>在籍状況</th>
				<th>勤務履歴・csv出力</th>
				<th colspan="2" style="width : auto">ユーザー登録情報管理</th>
			</tr>
		</thead>
		<tfoot>
			<c:forEach var="item" items="${alluser }">
				<tr>
					<td>${item.VUserId }</td>
					<td>${item.VUserName }</td>
					<td>${item.VLoginId }</td>
					<td>${item.VPassword }</td>
					<td>${item.VUserLv }</td>
					<td>${item.VEnrollmentLv }</td>
					<td>
					<form action="/workTimer/UserMainServlet" method="post">
						<button class="btn btn-info" type="submit">勤務履歴</button>
						<input type="hidden" name="userid" value="${item.VUserId }">
						<input type="hidden" name="action" value="adminMain">
					</form>
					</td>
					<td>
						<c:if test="${item.VUserId > 2}">
							<form action="/workTimer/UserEditServlet" method="post">
								<input type="hidden" name="editUser" value="${item.VLoginId }">
								<input type="hidden" name="action" value="EditInput">
								<button class="btn btn-primary" type="submit">編集</button>
							</form>
						</c:if>
					</td>
					<td>
						<c:if test="${item.VUserId > 2}">
							<form action="/workTimer/UserDeleteServlet" method="post">
								<input type="hidden" name="deleteUser" value="${item.VLoginId }">
								<input type="hidden" name="action" value="DeleteConfirm">
								<button class="btn btn-danger" type="submit">削除</button>
							</form>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tfoot>
	</table>
</div>

	<a href="/workTimer/UserRegisterServlet">新規ユーザー登録</a><br>
	<a href="/workTimer/LogoutServlet">ログアウト</a><br>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
	 integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous">
	</script>
</body>
</html>