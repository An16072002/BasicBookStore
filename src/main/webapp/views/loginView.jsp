<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<h3>Trang đăng nhập</h3>
		<p style="color: red;">${errors}</p>
		<form action="login" method="post">
			<table border="0">
				<tr>
					<td>Tài khoản</td>
					<td><input type="text" name="username" id="username"
						value="${loginForm.username }" /></td>
				<tr>
					<td>Mật khẩu</td>
					<td><input type="password" name="password" id="password"
						value="${loginForm.password }"></td>
				</tr>
				<tr>
					<td>Ghi nhớ</td>
					<td><input type="checkbox" name="rememberMe" value="Y" ${loginForm.rememberMe}/></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Đăng nhập">&nbsp;&nbsp;
						<a href="${pageContext.request.contextPath}/">Bỏ qua</a></td>

				</tr>
			</table>
		</form>
	</div>
</body>
</html>