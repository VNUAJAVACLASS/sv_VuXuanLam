<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="isEdit" value="${not empty Book}" />

<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>
    <c:choose>
      <c:when test="${isEdit}">Sửa tin tức</c:when>
      <c:otherwise>Tạo tin tức</c:otherwise>
    </c:choose>
  </title>

  <style>
    :root {
      --primary: #1976d2;
      --primary-dark: #0d47a1;
      --bg: #f3f6fa;
      --text: #2c3e50;
      --muted: #6b7280;
      --danger: #e53935;
      --radius: 12px;
    }
    * { box-sizing: border-box; margin: 0; padding: 0 }
    body {
      font-family: "Segoe UI", Tahoma, Arial, sans-serif;
      background: var(--bg); color: var(--text);
      min-height: 100vh; display: flex; align-items: center; justify-content: center; padding: 24px;
    }
    .page { width:100%; max-width:720px; background:#fff; border-radius:var(--radius);
      box-shadow:0 10px 24px rgba(0,0,0,.08); overflow:hidden; }
    .topbar { display:flex; align-items:center; justify-content:space-between;
      padding:16px 20px; border-bottom:1px solid #eef2f7; }
    .link { color:var(--primary); text-decoration:none; font-weight:600 }
    .link:hover { color:var(--primary-dark) }
    .wrap { padding:20px }
    h2 { color:var(--primary); margin-bottom:12px }
    .form { display:grid; gap:14px; }
    .field { display:grid; gap:6px }
    label { font-weight:600 }
    .hint { color:var(--muted); font-size:13px }
    input[type="text"], textarea, input[type="number"] {
      width:100%; border:1px solid #e5e7eb; border-radius:10px; padding:10px 12px; outline:none;
    }
    textarea { min-height:140px; resize:vertical }
    .actions { display:flex; gap:10px; margin-top:8px }
    .btn { display:inline-block; border:none; border-radius:10px; padding:10px 14px; font-weight:700; cursor:pointer; transition:.2s; }
    .btn-primary { background:var(--primary); color:#fff }
    .btn-primary:hover { background:var(--primary-dark); transform:translateY(-1px) }
    .btn-ghost { background:#f3f4f6; color:#111; text-decoration:none; }
    .btn-ghost:hover { filter:brightness(.97) }
    .danger { color:var(--danger); font-size:14px; }
  </style>
</head>
<body>
  <div class="page">
    <div class="topbar">
      <div>
        <b>
          <c:choose>
            <c:when test="${isEdit}">Sửa tin tức</c:when>
            <c:otherwise>Tạo tin tức</c:otherwise>
          </c:choose>
        </b>
      </div>
      <a class="link" href="${pageContext.request.contextPath}/adminHome">⬅️ Quay lại danh sách</a>
    </div>

    <div class="wrap">
      <form class="form" method="post" action="${pageContext.request.contextPath}/adminHome">
        <c:if test="${isEdit}">
          <input type="hidden" name="id" value="${Book.id}" />
        </c:if>

        <div class="field">
          <label for="title">Tiêu đề</label>
          <input id="title" type="text" name="title"
                 value="${isEdit ? Book.title : ''}" required maxlength="255"
                 placeholder="Nhập tiêu đề bài viết…" />
        </div>

        <div class="field">
          <label for="content">Nội dung</label>
          <textarea id="content" name="content" required
                    placeholder="Nhập nội dung chi tiết…">${isEdit ? Book.content : ''}</textarea>
          <div class="hint">Bạn có thể dán văn bản thuần; nếu cần rich-text thì mình nâng cấp sau nha.</div>
        </div>

        <!-- 🆕 Giá (VND) -->
        <div class="field">
          <label for="price">Giá (VND)</label>
          <input id="price" type="number" name="price" min="0" step="1"
                 value="${isEdit && Book.price != null ? Book.price : 0}"
                 placeholder="Ví dụ: 150000" />
          <div class="hint">Đơn vị: VNĐ. Để 0 nếu bài không bán hoặc chưa có giá.</div>
        </div>

        <div class="actions">
          <button class="btn btn-primary" type="submit">
            <c:choose>
              <c:when test="${isEdit}">💾 Cập nhật</c:when>
              <c:otherwise>➕ Tạo mới</c:otherwise>
            </c:choose>
          </button>
          <a class="btn btn-ghost" href="${pageContext.request.contextPath}/adminHome">Hủy</a>
        </div>
      </form>
    </div>
  </div>
</body>
</html>
