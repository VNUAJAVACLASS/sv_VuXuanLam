<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- giả sử AdminServlet đã setAttribute("Book", detail) --%>
<html>
<head><meta charset="UTF-8"><title>Chi tiết</title></head>
<body>
  <h2>Chi tiết tin</h2>
  <p><b>ID:</b> ${Book.id}</p>
  <p><b>Tiêu đề:</b> ${Book.title}</p>
  <p><b>Nội dung:</b> ${Book.content}</p>
  <a href="${pageContext.request.contextPath}/adminHome">Quay lại</a>
</body>
</html>
