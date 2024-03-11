<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa thông tin sách</title>
    <style>
        /* Bổ sung CSS cho trang chỉnh sửa sách tại đây */
    </style>
</head>
<body>
    <div align="center">
        <h3>Chỉnh sửa thông tin sách</h3>
        <p style="color: red">${errors}</p>
        <c:if test="${not empty book}">
            <div class="container">
                <form id="editBookForm" action="editBook" method="POST" enctype="multipart/form-data">
                    <input type="hidden" name="bookId" value="${book.bookId}" />
                    <!-- Các trường thông tin sách -->
                    <label for="title">Tiêu đề:</label>
                    <input type="text" id="title" name="title" value="${book.title}" required><br><br>

                    <label for="author">Tác giả:</label>
                    <input type="text" id="author" name="author" value="${book.author}" required><br><br>

                    <label for="price">Giá tiền:</label>
                    <input type="text" id="price" name="price" value="${book.price}" required><br><br>

                    <label for="quantityInStock">Số lượng trong kho:</label>
                    <input type="text" id="quantityInStock" name="quantityInStock" value="${book.quantityInStock}" required><br><br>

                    <label for="detail">Chi tiết:</label><br>
                    <textarea id="detail" name="detail">${book.detail}</textarea><br><br>

                    <label for="image">Chọn ảnh mới:</label>
                    <input type="file" id="image" name="file"><br><br>

                    <input type="submit" value="Cập nhật">
                    <a href="adminHome">Quay lại</a>
                </form>
            </div>
        </c:if>
    </div>
</body>
</html>
