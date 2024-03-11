<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết cuốn sách</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table td {
            padding: 10px;
            border-bottom: 1px solid #dee2e6;
            border-right: 1px solid #dee2e6; /* Thêm đường kẻ dọc */
        }
        table td:last-child {
            border-right: none; /* Loại bỏ đường kẻ dọc ở cột cuối cùng */
        }
        .book-details {
            display: flex;
            align-items: center;
            margin-top: 20px;
        }
        .book-details img {
            max-width: 200px;
            height: auto;
            margin-right: 20px;
        }
        .book-details h2 {
            color: #007bff;
        }
        .book-details p {
            line-height: 1.6;
        }
        /* Responsive design */
        @media (max-width: 768px) {
            .container {
                width: 90%;
            }
            .book-details {
                flex-direction: column;
            }
            .book-details img {
                margin-right: 0;
                margin-bottom: 20px;
            }
        }
    </style>
    <script>
        function plusValue(elementId, maxQuantity) {
            let quantity = parseInt(document.getElementById(elementId).value);
            if (quantity + 1 <= maxQuantity) {
                document.getElementById(elementId).value = quantity + 1;
            } else {
                alert('Giá trị không được vượt quá: ' + maxQuantity);
            }
        }

        function minusValue(elementId) {
            let quantity = parseInt(document.getElementById(elementId).value);
            if (quantity - 1 >= 1) {
                document.getElementById(elementId).value = quantity - 1;
            }
        }

    </script>
</head>
<body>
    <div align="center">
        <h3>Xem chi tiết thông tin sách</h3>
        <p style="color: red">${errors }</p>
        <c:if test="${not empty book}">
            <div class="container">
                <form id="detailBookForm" action="cartBook/addToCart" method="POST">
                    <input type="hidden" name="bookId" value="${book.bookId }" />
                    <table>
                        <tr>
                            <td><strong>Tiêu đề</strong></td>
                            <td>${book.title }</td>
                        </tr>
                        <tr>
                            <td><strong>Tác giả</strong></td>
                            <td>${book.author }</td>
                        </tr>
                        <tr>
                            <td><strong>Giá tiền</strong></td>
                            <td><fmt:formatNumber type="number" value="${book.price }" /><sup>đ</sup></td>
                        </tr>
                        <tr>
                            <td><strong>Số lượng có trong kho</strong></td>
                            <td>${book.quantityInStock }</td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div class="book-details">
                                    <img id="bookImage" alt="BookImage" src="${book.imagePath}" width="150">
                                    <div>
                                        <h2>Chi tiết cuốn sách</h2>
                                        <p id="bookText">${book.detail}</p>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr align="center">
                            <td colspan="2"><a href="adminHome">Bỏ qua</a></td>
                        </tr>
                    </table>
                    <div style="margin-top: 20px;">
                        <img src="img/icon-remove.png" onclick="minusValue('quantity${book.bookId}')" width="20px" height="20px" />
                        <input type="text" value="1" size="2" style="line-height: 20px" onchange="validateValue(this,${book.quantityInStock})" id="quantity${book.bookId}" name="quatityPurchased" />
                        <img src="img/icon-add.png" onclick="plusValue('quantity${book.bookId}',${book.quantityInStock})" width="20px" height="20px" />
                        <c:if test="${not empty loginedUser }">
                            <button onclick="checkQuantityAndSubmit('quantity${book.bookId}',${book.quantityInStock})">Thêm vào giỏ hàng</button>
                        </c:if>
                        <c:if test="${empty loginedUser }">
                            <button type="button" onclick="alert('Bạn cần đăng nhập')">Thêm vào giỏ hàng</button>
                        </c:if>
                        <a href="clientHome">Tiếp tục mua sách</a>
                    </div>
                </form>
            </div>
        </c:if>
    </div>
</body>
</html>
