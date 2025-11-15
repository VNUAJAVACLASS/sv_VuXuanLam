<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đăng nhập</title>
  <style>
    :root {
      --primary:#1976d2; --primary-dark:#0d47a1;
      --bg:#f3f6fa; --text:#2c3e50; --muted:#6b7280;
      --danger:#e53935; --radius:12px;
    }
    *{box-sizing:border-box;margin:0;padding:0}
    body{
      font-family:"Segoe UI",Tahoma,Arial,sans-serif;
      background:var(--bg); color:var(--text);
      min-height:100vh; display:flex; align-items:center; justify-content:center; padding:24px;
    }
    .card{
      width:100%; max-width:420px; background:#fff; border-radius:var(--radius);
      box-shadow:0 10px 24px rgba(0,0,0,.08); overflow:hidden;
    }
    .header{padding:18px 20px; border-bottom:1px solid #eef2f7}
    .title{color:var(--primary); font-size:20px; font-weight:700}
    .body{padding:20px; display:grid; gap:14px}
    label{font-weight:600}
    .hint{color:var(--muted); font-size:13px}
    input[type="text"], input[type="password"]{
      width:100%; border:1px solid #e5e7eb; border-radius:10px; padding:10px 12px; outline:none;
    }
    .row{display:flex; align-items:center; justify-content:space-between; gap:8px}
    .btn{
      display:inline-block; border:none; border-radius:10px; padding:10px 14px;
      font-weight:700; cursor:pointer; transition:.2s;
    }
    .btn-primary{background:var(--primary); color:#fff}
    .btn-primary:hover{background:var(--primary-dark); transform:translateY(-1px)}
    .error{
      background:#fee2e2; color:#991b1b; border:1px solid #fecaca;
      padding:10px 12px; border-radius:10px; font-weight:600;
    }
    .footer{padding:16px 20px; border-top:1px solid #eef2f7; text-align:center; color:var(--muted); font-size:13px}
    a{color:var(--primary); text-decoration:none}
    a:hover{color:var(--primary-dark)}
  </style>
</head>
<body>
  <div class="card">
    <div class="header">
      <div class="title">🔐 Đăng nhập</div>
    </div>

    <div class="body">
      <!-- Thông báo lỗi (nếu có) -->
      <c:if test="${not empty error}">
        <div class="error">${error}</div>
      </c:if>

      <form method="post" action="${pageContext.request.contextPath}/login" autocomplete="on">
        <div style="display:grid; gap:6px; margin-bottom:8px;">
          <label for="username">Tên đăng nhập</label>
          <input id="username" type="text" name="username"
                 value="${rememberedUser}" required maxlength="100" autofocus
                 placeholder="Nhập tên đăng nhập của anh nè~"/>
        </div>

        <div style="display:grid; gap:6px; margin-bottom:8px;">
          <label for="password">Mật khẩu</label>
          <input id="password" type="password" name="password" required
                 placeholder="••••••••"/>
          <div class="hint"></div>
        </div>

        <div class="row" style="margin:6px 0 12px;">
          <label style="display:flex; align-items:center; gap:8px; cursor:pointer;">
            <input type="checkbox" name="remember"
              <c:if test="${not empty rememberedUser}">checked</c:if> />
            Ghi nhớ đăng nhập
          </label>
          <!-- Nếu có quên mật khẩu thì để link ở đây -->
          <!-- <a href="${pageContext.request.contextPath}/forgot">Quên mật khẩu?</a> -->
        </div>

        <button class="btn btn-primary" type="submit">Đăng nhập</button>
      </form>
    </div>

    <div class="footer">
      <span>© ${pageContext.request.serverName}</span>
    </div>
  </div>
</body>
</html>
