<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang kết quả</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>
	<div align="center">
		<h3>Xin chào: ${loginedUser.fullname }</h3>
		<table>
			<tr>
				<td>Tài khoản: </td>
				<td>${loginedUser.username }</td>
			</tr>
			<tr>
				<td>Số điện thoại: </td>
				<td>${loginedUser.mobile }</td>
			</tr>
			<tr>
				<td>Email: </td>
				<td>${loginedUser.email }</td>
			</tr>
			<tr>
				<td>Địa chỉ: </td>
				<td>${loginedUser.address }</td>
			</tr>
	</table>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>