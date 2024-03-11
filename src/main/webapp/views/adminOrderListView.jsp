<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="vnua.fita.bookstore.util.Constant" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
<script>
function onClickAdminOrderConfirm(orderId,confirmType,action){
	document.getElementById("orderIdOfAction").value = orderId;
	document.getElementById("confirmTypeOfAction").value = confirmType;
	document.getElementById("adminOrderForm").value = action.substring(1);
	document.getElementById("adminOrderForm").submit();
	
}</script>

<meta charset="UTF-8">
<title>Thông tin tài khoản đơn hàng</title>
</head>
<body>
	<jsp:include page="_header_backend.jsp"></jsp:include>
	<jsp:include page="_menu_backend.jsp"></jsp:include>
	<div align="center">
			<h3>Danh sach don hang ${listType}</h3>
			<form id="adminOrderForm" method="post" action="">
					<input type="hidden" name="orderId" id="orderIdOfAction"/>
					<input type="hidden" name="confirmType" id="confirmTypeOfAction"/>
			</form>
			<p style="color: red;">${errors}</p>
			<p style="color: blue;">${message}</p>
			<table border="1">
				<tr>
					<th>Mã hóa đơn</th>
					<th>Tên khách</th>
					<th>Số điện thoại</th>
					<th>Ngày đặt mua</th>
					<th>Ngày xác nhận</th>
					<th>Địa chỉ nhận sách</th>
					<th>Phương thức thanh toán</th>
					<th>Trạng thái đơn hàng</th>
					<th>Thao tác</th>
				</tr>
				<c:forEach items="${orderListOfCustomer}" var="orderOfCustomer">
					<tr>
						<td>${orderOfCustomer.orderNo}</td>
						<td>${orderOfCustomer.customer.fullname}</td>
						<td>${orderOfCustomer.customer.mobile}</td>
						<td><fmt:formatDate value="${orderOfCustomer.orderDate}"
								pattern="dd-MM-yyyy HH:mm" /></td>
						<td><fmt:formatDate
								value="${orderOfCustomer.orderApproveDate}"
								pattern="dd-MM-yyyy HH:mm" /></td>
						<td>${orderOfCustomer.deliveryAddress }</td>
						<td>${orderOfCustomer.paymentModeDescription}</td>
						<td>${orderOfCustomer.orderStatusDescription}</td>
						<td>
    <c:if test="${Constant.TRANSFER_PAYMENT_MODE.equals(orderOfCustomer.paymentMode)}">
    	<button onclick="document.getElementById('divImg${orderOfCustomer.orderId}').style.display='block'">Xem anhrt</button>
		<button onclick="document.getElementById('divImg${orderOfCustomer.orderId}').style.display='none'">Ẩn</button>
																				<br>
		<div id="divImg${orderOfCustomer.orderId}" style="display: none;padding-top: 5px;">
		<img alt="Transfer Image" src="${pageContext.request.contextPath}/${orderOfCustomer.paymentImagePath}"width="150">
		</div>
    </c:if>
</td>
						<td>
						
						${orderOfCustomer.orderStatusDescription}
																<c:if 
						test="${Constant.WAITING_CONFIRM_ORDER_STATUS != orderOfCustomer.orderStatus}">
						
																					&nbsp;-&nbsp;${orderOfCustomer.paymentStatusDescription }
						</c:if>
						</td>
						<td>
							<button onclick="document.getElementById('div${orderOfCustomer.orderId}').style.display='block'">Xem chi tiết</button>
							<button onclick="document.getElementById('div${orderOfCustomer.orderId}').style.display='none'">Ẩn</button>
							<div id="div${orderOfCustomer.orderId}" style="display: none;">
								<h3>thông tin chi tiết hóa đơnn</h3>
								<table border="1">
									<tr style="background-color: yellow;">
										<th>Tiêu đề</th>
										<th>Tác giả</th>
										<th>Giá tiền</th>
										<th>Số lượng mua</th>
										<th>Tổng thành phần</th>
									</tr>
									<c:forEach items="${orderOfCustomer.orderBookList }" var="cartItem">
										<td>${cartItem.selectedBook.title }</td>
										<td>${cartItem.selectedBook.author }</td>
										<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${cartItem.selectedBook.price }"/><sup>đ</sup></td>
										<td>${cartItem.quantity }</td>
										<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${cartItem.selectedBook.price*cartItem.quantity }"/><sup>đ</sup></td>
									</c:forEach>
								</table>
								<br/>
								Tổng số tiền
								<b>
									<span>
										<fmt:formatNumber type="number" maxFractionDigits="0" value="${orderOfCustomer.totalCost }"/>
									</span>
									<sup>đ</sup>
								</b>
								<c:if test="${Constant.WAITING_CONFIRM_ORDER_STATUS == orderOfCustomer.orderStatus}">
								&nbsp;-&nbsp;&nbsp;-&nbsp;
								<button onclick="onClickAdminOrderConfirm(${orderOfCustomer.orderId},${Constant.DELIVERING_ORDER_STATUS},'${Constant.WAITING_APPROVE_ACTION}');">Xác nhận</button>
								</c:if>
								
								<c:if test="${Constant.DELIVERING_ORDER_STATUS == orderOfCustomer.orderStatus}">
										<br><br>
										<button 
		onclick="onClickAdminOrderConfirm(${orderOfCustomer.orderId},${Constant.DELIVERED_ORDER_STATUS},'${Constant.DELIVERING_ACTION}');">Xác nhận đã giao hàng
										</button>		
									&nbsp;-&nbsp;
									<button 
		onclick="onClickAdminOrderConfirm(${orderOfCustomer.orderId},${Constant.REJECT_ORDER_STATUS},'${Constant.DELIVERING_ACTION}');">Xác nhận khách trả hàng
										</button>
									
								
								</c:if>
							</div>
						</td>
					</tr>
					<c:forEach items="${orderOfCustomer.orderBookList}" var="cartItem">
					</c:forEach>
				</c:forEach>
			</table>
		</div>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>