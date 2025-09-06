<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <meta charset="UTF-8">
  <title>Đăng nhập</title>
</head>
<body>
  <h2>Đăng nhập</h2>

  <!-- Hiển thị lỗi -->
  <c:if test="${not empty error}">
    <p style="color:red">${error}</p>
  </c:if>

  <form method="post" action="${pageContext.request.contextPath}/login">
    Tên đăng nhập: <br/>
    <input type="text" name="username" value="${rememberedUser}" required/><br/><br/>

    Mật khẩu: <br/>
    <input type="password" name="password" required/><br/><br/>

    <label><input type="checkbox" name="remember" ${not empty rememberedUser ? 'checked' : ''}/> Ghi nhớ đăng nhập</label><br/><br/>

    <input type="submit" value="Đăng nhập"/>
  </form>
</body>
</html>
