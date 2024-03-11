<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="padding: 5px;"align="center">
			<c:if test="${empty loginedUser}">
				<a
				href="${pageContext.request.contextPath}/clientHome">Trang chủ
				</a>
			</c:if>
			<c:if test="${not empty loginedUser}">
					<c:if test="${loginedUser.role == 0 }"><!-- Khách -->
						<a
					href="${pageContext.request.contextPath}/clientHome">Trang chủ</a>
					</c:if>
					
					<c:if test="${loginedUser.role == 1 }"><!-- Admin -->
						<a
					href="${pageContext.request.contextPath}/adminHome">Trang chủ</a>
					</c:if>
			</c:if>
			|
			<a href="">Sách phổ biến</a>
			|
			<a href="">Sách bán chạy</a>
			|
			<a href="">Sách mới</a>
			|
			<a href="">Sách thấp đến cao</a>
			|
			<a href="">Sách cao đến thấp</a>
</div>