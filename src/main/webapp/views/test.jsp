<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/js/bookstore_script.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết cuốn sách</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
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
        }
         table td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            border-right: 1px solid #ddd; /* Thêm đường kẻ dọc */
        }
        table td:last-child {
            border-right: none; /* Loại bỏ đường kẻ dọc ở cột cuối cùng */
        }
        .book-details {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .book-details img {
            max-width: 200px;
            height: auto;
            display: block;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <body>
    <div class="container">
        <table>
            <tr>
                <td>Tiêu đề:</td>
                <td>Harry Potter và Hòn Đá Phù Thủy</td>
            </tr>
            <tr>
                <td>Tác giả:</td>
                <td>J.K. Rowling</td>
            </tr>
            <tr>
                <td>Giá tiền:</td>
                <td>$10.99</td>
            </tr>
        </table>
        <div class="book-details">
        	 <img src="harry_potter.jpg" alt="Harry Potter và Hòn Đá Phù Thủy">
            <div>
                <h2>Chi tiết cuốn sách</h2>
                <p>
                    Harry Potter và Hòn Đá Phù Thủy là cuốn sách đầu tiên trong loạt truyện
                    nổi tiếng về Harry Potter của nhà văn J.K. Rowling. Cuốn sách kể về cuộc
                    phiêu lưu của cậu bé phù thủy Harry Potter và những người bạn ở trường phù
                    thủy Hogwarts.
                </p>
            </div>
        </div>
    </div>
</body>
</body>
</html>