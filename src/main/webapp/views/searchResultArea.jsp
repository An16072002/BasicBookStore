<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Books Store Application</title>
</head>
<body>

	<table border="1" cellpadding="5">
	<caption>
	<b>${keyword}</b>
	</caption>
		<caption>
			<h2>List of Books from Ajax</h2>
		</caption>
		<tr>
			<th>ID</th>
			<th>Title</th>
			<th>Author</th>
			<th>Price</th>
			<th>Actions</th>
		</tr>
		<c:forEach var="book" items="${searchListBook}">
			<tr>
				<td><c:out value="${book.bookId}" /></td>
				<td><c:out value="${book.title}" /></td>

				<td><c:out value="${book.author}" /></td>
				<td><c:out value="${book.price}" /></td>
				<td><a href="editBook?bookId=<c:out value='${book.bookId}'/>">Edit</a>

					&nbsp;&nbsp;&nbsp;&nbsp; <a
					href="deleteBook?bookId=<c:out value='${book.bookId}'/>">Delete</a></td>

			</tr>
		</c:forEach>
	</table>

</body>
</html>