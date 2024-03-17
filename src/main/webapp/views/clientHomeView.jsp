<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/bookstore_style.css">
    <title>Website Cửa Hàng Sách</title>
</head>
<body>
    <jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
    <div align="center">
        <h3>Danh sách các cuốn sách</h3>
        <p style="color: red;">${errors}</p>
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
                    <td><fmt:setLocale value="en_US" /> <fmt:formatNumber
                            type="number" value="${book.price}" /><sup>đ</sup></td>
                    <td align="center">${book.quantityInStock}</td>
                    <!-- gọi đến servlet có tên liên kết detailBook, truyền thông tin bookId để xem thông tin chi tiết cuốn sách -->
                    <td><a href="detailBook?bookId=${book.bookId}">Xem chi
                            tiết</a></td>
                </tr>
            </c:forEach>
        </table>

     
        <c:choose>
            <c:when test="${empty keyword}">
                <div style="margin-top: 5px">
                    <!-- Link previous chỉ xuất hiện khi trang hiện tại lớn hơn 1 -->
                    <c:if test="${currentPage gt 1}">
                        <a href="clientHome?page=${currentPage - 1}">Previous</a>&nbsp;
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
                    <!-- Link Next chỉ xuất hiện khi trang hiện tại nhỏ hơn tổng số trang -->
                    <c:if test="${currentPage lt noOfPages}">
                        &nbsp;<a href="clientHome?page=${currentPage + 1}">Next</a>
                    </c:if>
                </div>
            </c:when>
           
            <c:otherwise>
                <div style="margin-top: 5px">
                    <c:if test="${currentPage gt 1}">
                        <a href="clientHome?page=${currentPage - 1}&keyword=${keyword}">Previous</a>&nbsp;
                    </c:if>
                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                &nbsp;${i}&nbsp;
                            </c:when>
                            <c:otherwise>
                                &nbsp;<a href="clientHome?page=${i}&keyword=${keyword}">${i}</a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${currentPage lt noOfPages}">
                        &nbsp;<a href="clientHome?page=${currentPage + 1}&keyword=${keyword}">Next</a>
                    </c:if>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
