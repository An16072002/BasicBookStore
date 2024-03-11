<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="${pageContext.request.contextPath}/js/bookstore_script.js"></script>
<title></title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
	<jsp:include page="_menu.jsp"></jsp:include>
	<div align="center">
		<h3>CHI TIẾT HÓA ĐƠN</h3>
		<table border="1">
			<tr>
				<th align="left">Tài khoản</th>
				<td>${loginedUser.username }</td>
				<c:if
					test="${Constant.WAITING_CONFIRM_ORDER_STATUS == orderOfCustomer.orderStatus }">
					<td rowspan="9"><img alt="Transfer Image"
						src="${orderOfCustomer.paymentImagePath}" width="150"></td>
				</c:if>
				<td rowspan="9"><img alt="Transfer Image"
						src="${orderOfCustomer.paymentImagePath}" width="150"></td>
			</tr>
			<tr>
				<th align="left">Họ tên:</th>
				<td>${loginedUser.fullname }</td>
			</tr>
			<tr>
				<th align="left">Số di động</th>
				<td>${loginedUser.mobile }</td>
			</tr>
			<tr>
				<th align="left">Mã hóa đơn</th>
				<td>${orderOfCustomer.orderNo }</td>
			</tr>
			<tr>
				<th align="left">Ngày đặt mua</th>
				<td><fmt:formatDate value="${orderOfCustomer.orderDate }"
						pattern="dd-MM-yyyy HH:mm" /></td>
			</tr>
			<tr>
				<th align="left">Địa chỉ nhận sách</th>
				<td>${orderOfCustomer.deliveryAddress }</td>
			</tr>
			<tr>
				<th align="left">Phương thức thanh toán:</th>
				<td>${orderOfCustomer.paymentModeDescription }</td>
			</tr>
			<tr>
				<th align="left">Trạng thái đơn hàng:</th>
				<td>${orderOfCustomer.orderStatusDescription }<c:if
						test="${Constant.WAITING_CONFIRM_ORDER_STATUS != orderOfCustomer.orderStatus }">
							&nbsp;-&nbsp;${orderOfCustomer.paymentStatusDescription }
						</c:if>
				</td>
			</tr>
		</table>
	</div>

	<div align="center">
		<h3>Các cuốn sách trong hóa đơn</h3>
		<table border="1">
			<tr>
				<th>Tiêu đề</th>
				<th>Tác giả</th>
				<th>Giá tiền</th>
				<th>Số lượng mua</th>
				<th>Tổng thành phần</th>
			</tr>
			<c:forEach items="${cartOfCustomer.cartItemList }" var="entry">
				<tr>
					<td>${entry.value.selectedBook.title }</td>
					<td>${entry.value.selectedBook.author }</td>
					<td><fmt:formatNumber type="number" maxFractionDigits="0"
							value="${entry.value.selectedBook.price }" /><sup>đ</sup></td>
					<td>${entry.value.quantity }</td>
					<td><fmt:formatNumber type="number" maxFractionDigits="0"
							value="${entry.value.selectedBook.price * entry.value.quantity }" /><sup>đ</sup>
					</td>
				</tr>

			</c:forEach>
		</table>
		
		<br>
		Tổng số tiền:
		<b>
			<span id="total">
				<fmt:formatNumber type="number" maxFractionDigits="0"
							value="${cartOfCustomer.totalCost }" />
			</span>
			<sup>đ</sup>
		</b>
	</div>
</body>
</html>