<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bookstore_style.css">
<script
	src="https://cdn.ckeditor.com/ckeditor5/41.1.0/classic/ckeditor.js"></script>
<title>Trang chủ quản trị</title>
</head>
<body>
	<div align="center">
		<h3>Thêm sách mới</h3>
		<p style="color: red">${errors}</p>
		<form action="createBook" method="post" enctype="multipart/form-data">
			<table border="0">
				<tr>
					<td>Tiêu đề</td>
					<td><input type="text" name="title" value=${book.title}></td>
				</tr>
				<tr>
					<td>Tác giả</td>
					<td><input type="text" name="author" value=${book.author}></td>
				</tr>
				<tr>
					<td>Giá tiền</td>
					<td><input type="text" name="price" value=${book.price}>&nbsp;&nbsp;
						(vnđ)</td>
				</tr>
				<tr>
					<td>Số lượng có trong kho</td>
					<td><input type="text" name="quantityInStock"
						value=${book.quantityInStock}></td>
				</tr>
				<tr>
					<td>Ảnh bìa sách</td>
					<td><img id="bookImage" width="150"> &nbsp; <input
						type="file" name="file" accept="image/*"
						onchange="loadImage(event)" /></td>
				</tr>
				<tr>
					<td>Giới thiệu sách</td>
					<td><textarea name="detail" id="editor" cols="10" rows="20">${book.detail }</textarea>
						<script>
							ClassicEditor.create(document
									.querySelector('#editor'));
						</script>
					</td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Thêm mới">&nbsp;&nbsp;
						<a href="adminHome">Bỏ qua</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>