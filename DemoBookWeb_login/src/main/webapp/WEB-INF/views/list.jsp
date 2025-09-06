<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>Danh sách tin tức</title>
</head>
<body>
	<h2>Danh sách tin tức</h2>
	<p>
		Xin chào: ${sessionScope.username} | <a
			href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
	</p>

	<hr />

	<a href="${pageContext.request.contextPath}/adminHome?action=create">Tạo
		tin mới</a>
	<br>
	<br>

	<table border="1" cellpadding="5">
		<tr>
			<th>ID</th>
			<th>Tiêu đề</th>
			<th>Hành động</th>
		</tr>
		<c:forEach var="Book" items="${BookList}">
			<tr>
				<td>${Book.id}</td>
				<td><a
					href="${pageContext.request.contextPath}/adminHome?action=detail&id=${Book.id}">
						${Book.title} </a></td>
				<td><a
					href="${pageContext.request.contextPath}/adminHome?action=edit&id=${Book.id}">Sua</a>
					| <a
					href="${pageContext.request.contextPath}/adminHome?action=delete&id=${Book.id}"
					onclick="return confirm('Xoá tin này?');">Xoá</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
