<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="${pageContext.request.contextPath}/js/bookstore_script.js"></script>

<div style="background: #E0E0E0; height: 75px; padding: 15px;">
	<div style="float: left">
		<h2>Website cửa hàng sách</h2>
	</div>

	<div style="float: right; text-align: right;">
		<!-- User store in session with attribute: loginedUser -->

		<c:if test="${not empty loginedUser }">
	Xin chào <b>${loginedUser.username}</b>
	|
	<a href="${pageContext.request.contextPath}/customerOrderList">Thông tin
				tài khoản/đơn hàng</a>
	|
	<a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
	|
	<a href="${pageContext.request.contextPath}//cartBook/viewCart"><img
				alt="cart-icon"
				src="${pageContext.request.contextPath }/img/shopping-cart.png"
				width="20px" height="20px"></a>

		</c:if>

		<c:if test="${empty loginedUser }">
			<a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
		</c:if>

		<c:if test="${not empty cartofCusromer}">
			<a href="${pageContext.request.contextPath}/cartBook/viewCart"> <img
				alt="ShoppingCart" src="${pageContext.request.contextPath}/img/shopping-cart.png">
			</a>
		</c:if>
		<br>
		<br>
		Tìm sách &nbsp; <input name="search" onchange ="activeAsLink('${pageContext.request.contextPath}/clientHome?keyword='+this.value);">
	</div>
</div>