<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Giả sử AdminServlet đã setAttribute("Book", detail) --%>
<html>
<head>
  <meta charset="UTF-8">
  <title>Chi tiết tin</title>
  <style>
    body {
      font-family: "Segoe UI", Tahoma, sans-serif;
      background: #f3f6fa;
      margin: 0;
      padding: 20px;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
    }

    .container {
      background: #fff;
      padding: 30px;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      max-width: 600px;
      width: 100%;
    }

    h2 {
      color: #1976d2;
      margin-bottom: 20px;
      text-align: center;
    }

    p {
      margin: 12px 0;
      font-size: 1rem;
      line-height: 1.5;
    }

    b {
      color: #333;
    }

    a {
      display: inline-block;
      margin-top: 20px;
      text-decoration: none;
      padding: 10px 18px;
      border-radius: 8px;
      background: #1976d2;
      color: #fff;
      font-weight: bold;
      transition: 0.3s ease;
    }

    a:hover {
      background: #0d47a1;
      transform: translateY(-2px);
    }
  </style>
</head>
<body>
  <div class="container">
    <h2>Chi tiết tin</h2>
    <p><b>ID:</b> ${Book.id}</p>
    <p><b>Tiêu đề:</b> ${Book.title}</p>
    <p><b>Nội dung:</b> ${Book.content}</p>
    <a href="${pageContext.request.contextPath}/adminHome">⬅ Quay lại</a>
  </div>
</body>
</html>
