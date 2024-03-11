<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Website Cửa Hàng Sách</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>
	<div align="center">
		<h3>Danh sách các cuốn sách</h3>
		<p style="color: red;">${error }</p>
		<table border="1">
			<tr>
				<th>Tiêu đề</th>
				<th>Tác giả</th>
				<th>Giá tiền</th>
				<th>Số lượng sẵn có</th>
				<th>Thao tác</th>
			</tr>
			<c:forEach items="${bookList}" var="book">
				<tr>
					<td>${book.title}</td>
					<td>${book.author}</td>
					<td><fmt:formatNumber maxFractionDigits="0" type="number"
							value="${book.price }" /><sup>đ</sup></td>
					<td align="center">${book.quantityInStock}</td>
					<td align="center"><button type="button"
							onclick="activeAsLink('detailBook?bookId=${book.bookId}')">Xem
							chi tiết</button></td>
				</tr>
			</c:forEach>
		</table>
		
		<c:if test="${empty keyword }">
			<div style="margin-top: 5px;">
				<c:if test="${currentPage gt 1 }">
				<a href="clientHome?page=${currentPage-1}">Previous</a>&nbsp;
				</c:if>
			<c:forEach begin="1" end="${noOfPages}" var="i">
				<c:choose>
					<c:when test="${currentPage eq i}">
						&nbsp;${i}&nbsp;
					</c:when>
					<c:otherwise>
						&nbsp;<a href="clientHome?page=${i}">${i}</a>&nbsp;
					</c:otherwise>	
				</c:choose>
			</c:forEach>
			
			<c:if test="${currentPage lt noOfPages}">&nbsp;<a href="clientHome?page=${currentPage + 1}">Next</a> </c:if>
			</div>
			
		</c:if>
		
		<c:if test="${not empty keyword}">
			<div style="margin-top: 5px;">
				<c:if test="${currentPage gt 1}">
				<a href="clientHome?page=${currentPage-1}&keyword=${keyword}">Previous</a> &nbsp;
				</c:if>
				
				<c:forEach begin="1" end="${noOfPages}" var="i">
					<c:choose>
						<c:when test="${currentPage eq i }">
						&nbsp;${i}&nbsp;
						</c:when>
						<c:otherwise>
							&nbsp;<a href="clientHome?page=${i}&keyword=${keyword}">${i}</a>&nbsp;
						</c:otherwise>
					</c:choose>
				</c:forEach>
						<c:if test="${currentPage lt noOfPages}">
							&nbsp;<a href="clientHome?page=${currentPage +1}&keyword=${keyword}">Next</a>
						</c:if>
			</div>
		</c:if>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>