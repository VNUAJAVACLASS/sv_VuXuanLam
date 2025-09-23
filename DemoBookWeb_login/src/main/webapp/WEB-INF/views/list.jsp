<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Danh sách tin tức</title>
<style>
  :root{
    --primary:#1976d2;
    --primary-dark:#0d47a1;
    --bg:#f3f6fa;
    --text:#2c3e50;
    --muted:#6b7280;
    --danger:#e53935;
    --warning:#f59e0b;
    --radius:12px;
  }
  *{box-sizing:border-box;margin:0;padding:0}
  body{
    font-family:"Segoe UI",Tahoma,Arial,sans-serif;
    background:var(--bg);
    color:var(--text);
    min-height:100vh;
    display:flex;align-items:center;justify-content:center;
    padding:24px;
  }
  .page{
    width:100%;max-width:980px;
    background:#fff;border-radius:var(--radius);
    box-shadow:0 10px 24px rgba(0,0,0,.08);
    overflow:hidden;
  }
  .topbar{
    display:flex;align-items:center;justify-content:space-between;
    padding:16px 20px;background:#fff;border-bottom:1px solid #eef2f7;
  }
  .hello{font-weight:600}
  .hello small{color:var(--muted);font-weight:400}
  .link{color:var(--primary);text-decoration:none;font-weight:600}
  .link:hover{color:var(--primary-dark)}
  .header{
    padding:20px;
    display:flex;gap:12px;flex-wrap:wrap;align-items:center;justify-content:space-between;
  }
  h2{color:var(--primary)}
  .actions{display:flex;gap:10px;align-items:center;flex-wrap:wrap}
  .btn{
    display:inline-block;border:none;border-radius:10px;
    padding:10px 14px;font-weight:600;text-decoration:none;cursor:pointer;
    transition:.2s;
  }
  .btn-primary{background:var(--primary);color:#fff}
  .btn-primary:hover{background:var(--primary-dark);transform:translateY(-1px)}
  .btn-warning{background:var(--warning);color:#111}
  .btn-warning:hover{filter:brightness(.95);transform:translateY(-1px)}
  .btn-danger{background:var(--danger);color:#fff}
  .btn-danger:hover{filter:brightness(.95);transform:translateY(-1px)}
  .search{
    display:flex;gap:8px;align-items:center;
    background:#f8fafc;border:1px solid #e5e7eb;border-radius:10px;
    padding:8px 10px;
  }
  .search input{
    border:none;outline:none;background:transparent;min-width:220px;
  }
  .content{padding:0 20px 20px}
  .table-wrap{overflow:auto;border:1px solid #eef2f7;border-radius:10px}
  table{width:100%;border-collapse:collapse;min-width:560px;background:#fff}
  thead th{
    text-align:left;background:#f8fafc;color:#374151;
    padding:12px;border-bottom:1px solid #eef2f7;font-weight:700
  }
  tbody td{
    padding:12px;border-bottom:1px solid #f1f5f9;vertical-align:top
  }
  tbody tr:hover{background:#fafafa}
  .muted{color:var(--muted)}
  .empty{
    text-align:center;padding:28px;color:var(--muted)
  }
  .flash{
    margin:0 20px 16px;background:#ecfdf5;color:#065f46;
    border:1px solid #a7f3d0;padding:10px 12px;border-radius:10px;font-weight:600
  }
  @media (max-width:600px){
    .search input{min-width:140px}
    .btn{padding:9px 12px}
  }
</style>
</head>
<body>
<div class="page">

  <!-- Top bar -->
  <div class="topbar">
    <div class="hello">Xin chào: <b>${sessionScope.username}</b> <small>(Admin)</small></div>
    <a class="link" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
  </div>

  <!-- Header + actions -->
  <div class="header">
    <h2>Danh sách tin tức</h2>
    <div class="actions">
      <form class="search" method="get" action="${pageContext.request.contextPath}/adminHome">
        <input type="text" name="q" placeholder="Tìm theo tiêu đề..." value="${param.q}">
        <button class="btn btn-primary" type="submit">Tìm</button>
      </form>
      <a class="btn btn-primary" href="${pageContext.request.contextPath}/adminHome?action=create">+ Tạo tin mới</a>
    </div>
  </div>

  <!-- Flash message (nếu có) -->
  <c:if test="${not empty message}">
    <div class="flash">${message}</div>
  </c:if>

  <div class="content">
    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th style="width:80px">ID</th>
            <th>Tiêu đề</th>
            <th style="width:220px">Hành động</th>
          </tr>
        </thead>
        <tbody>
        <c:choose>
          <c:when test="${empty BookList}">
            <tr>
              <td colspan="3" class="empty">Chưa có tin nào. Nhấn <b>“+ Tạo tin mới”</b> để thêm nha.</td>
            </tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="Book" items="${BookList}">
              <tr>
                <td><span class="muted">#</span>${Book.id}</td>
                <td>
                  <a class="link"
                     href="${pageContext.request.contextPath}/adminHome?action=detail&id=${Book.id}">
                    ${Book.title}
                  </a>
                </td>
                <td>
                  <a class="btn btn-warning"
                     href="${pageContext.request.contextPath}/adminHome?action=edit&id=${Book.id}">Sửa</a>
                  <a class="btn btn-danger"
                     href="${pageContext.request.contextPath}/adminHome?action=delete&id=${Book.id}"
                     onclick="return confirm('Xóa tin này?');">Xóa</a>
                </td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
        </tbody>
      </table>
    </div>

    <!-- Phân trang (nếu backend set các biến page/totalPages) -->
    <c:if test="${not empty totalPages}">
      <div style="display:flex;gap:8px;justify-content:center;margin-top:16px;flex-wrap:wrap">
        <c:forEach var="i" begin="1" end="${totalPages}">
          <c:choose>
            <c:when test="${i == page}">
              <span class="btn" style="background:#e5e7eb;cursor:default">${i}</span>
            </c:when>
            <c:otherwise>
              <a class="btn"
                 style="background:#f3f4f6"
                 href="${pageContext.request.contextPath}/adminHome?page=${i}&q=${param.q}">
                 ${i}
              </a>
            </c:otherwise>
          </c:choose>
        </c:forEach>
      </div>
    </c:if>
  </div>

</div>
</body>
</html>
