<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- isEdit = true nếu trong request có attribute "Book" --%>
<c:set var="isEdit" value="${not empty Book}" />

<html>
<head>
  <meta charset="UTF-8" />
  <title><c:choose>
    <c:when test="${isEdit}">Sửa tin tức</c:when>
    <c:otherwise>Tạo tin tức</c:otherwise>
  </c:choose></title>
</head>
<body>
  <h2>
    <c:choose>
      <c:when test="${isEdit}">Sửa tin tức</c:when>
      <c:otherwise>Tạo tin tức</c:otherwise>
    </c:choose>
  </h2>

  <form method="post" action="${pageContext.request.contextPath}/adminHome">
    <c:if test="${isEdit}">
      <input type="hidden" name="id" value="${Book.id}" />
    </c:if>

    <label>Tiêu đề:</label><br/>
    <input type="text" name="title" value="${isEdit ? Book.title : ''}" required /><br/><br/>

    <label>Nội dung:</label><br/>
    <textarea name="content" rows="5" cols="40" required>${isEdit ? Book.content : ''}</textarea><br/><br/>

    <input type="submit" value="${isEdit ? 'Cập nhật' : 'Tạo mới'}" />
  </form>

  <br/>
  <a href="${pageContext.request.contextPath}/adminHome">Quay lại danh sách</a>
</body>
</html>
