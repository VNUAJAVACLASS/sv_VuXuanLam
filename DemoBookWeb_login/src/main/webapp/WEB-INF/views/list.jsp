<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="vi_VN" />

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Quản lý sách - Admin</title>

<style>
:root {
    --primary: #3b82f6;
    --primary-hover: #2563eb;
    --bg: #f0f4f8;
    --text: #1e293b;
    --muted: #64748b;
    --danger: #ef4444;
    --danger-hover: #dc2626;
    --warning: #facc15;
    --radius: 14px;
    --shadow: 0 8px 20px rgba(0, 0, 0, 0.06);
}

/* Animation */
@keyframes fadeIn {
    from { opacity: 0; transform: translateY(8px); }
    to { opacity: 1; transform: translateY(0); }
}

/* RESET */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    background: var(--bg);
    font-family: "Inter", sans-serif;
    color: var(--text);
}

/* ===== ADMIN SHELL + HEADER ===== */
.admin-shell {
    min-height: 100vh;
}

.admin-header {
    background: #0f172a;
    color: #e5e7eb;
    padding: 12px 32px;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.admin-brand {
    font-weight: 800;
    letter-spacing: .04em;
    font-size: 18px;
    text-transform: uppercase;
}

.admin-sub {
    font-size: 12px;
    color: #9ca3af;
}

.admin-brand-wrap {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.admin-nav {
    display: flex;
    align-items: center;
    gap: 18px;
    font-size: 14px;
}

.admin-nav a {
    color: #e5e7eb;
    text-decoration: none;
    opacity: .9;
}

.admin-nav a:hover {
    opacity: 1;
    text-decoration: underline;
}

.admin-nav .logout-link {
    padding: 6px 12px;
    border-radius: 999px;
    background: #ef4444;
    color: #fff;
    text-decoration: none;
    font-weight: 600;
}

.admin-nav .logout-link:hover {
    background: #dc2626;
}

/* PAGE WRAPPER */
.page {
    width: 100%;
    max-width: 1040px;
    background: #fff;
    border-radius: var(--radius);
    box-shadow: var(--shadow);
    overflow: hidden;
    animation: fadeIn .3s ease;
    margin: 24px auto 32px;
}

/* TOP BAR */
.topbar {
    padding: 16px 22px;
    border-bottom: 1px solid #e2e8f0;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.topbar b { color: var(--primary); }

/* BUTTONS */
.btn {
    padding: 10px 18px;
    border-radius: var(--radius);
    font-weight: 600;
    cursor: pointer;
    transition: 0.2s ease;
    text-decoration: none;
    border: none;
}

.btn-primary { background: var(--primary); color: white; }
.btn-primary:hover { background: var(--primary-hover); transform: translateY(-1px); }

.btn-warning { background: var(--warning); color: #111; }
.btn-warning:hover { filter: brightness(0.92); transform: translateY(-1px); }

.btn-danger { background: var(--danger); color: white; }
.btn-danger:hover { background: var(--danger-hover); transform: translateY(-1px); }

/* HEADER */
.header {
    padding: 22px 26px;
    border-bottom: 1px solid #e2e8f0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
}

h2 { font-size: 24px; font-weight: 700; }

.actions {
    display: flex;
    gap: 12px;
    flex-wrap: wrap;
}

/* SEARCH */
.search {
    display: flex;
    gap: 10px;
    background: #f8fafc;
    border-radius: var(--radius);
    border: 1px solid #e2e8f0;
    padding: 10px 12px;
}

.search input {
    background: transparent;
    border: none;
    outline: none;
    width: 240px;
}

/* TABLE */
.table-wrap {
    margin: 20px;
    border-radius: var(--radius);
    border: 1px solid #e2e8f0;
    overflow: auto;
}

table {
    width: 100%;
    min-width: 720px;
    border-collapse: collapse;
}

thead th {
    background: #f1f5f9;
    padding: 14px;
    font-weight: 700;
    border-bottom: 1px solid #e2e8f0;
}

tbody td {
    padding: 14px;
    border-bottom: 1px solid #f1f5f9;
}

tbody tr:hover {
    background: #f9fafb;
}

.muted { color: var(--muted); }

.empty {
    padding: 28px;
    text-align: center;
    color: var(--muted);
}

/* FLASH */
.flash {
    margin: 16px 24px;
    padding: 12px;
    background: #ecfdf5;
    border-left: 4px solid #10b981;
    color: #065f46;
    border-radius: var(--radius);
}

/* NÚT THÊM GIỎ */
.btn-cart {
    background: #22c55e;
    color: white;
    padding: 10px 18px;
    border-radius: 12px;
    font-weight: 600;
    cursor: pointer;
    transition: 0.25s ease;
    display: inline-flex;
    gap: 6px;
    align-items: center;
}

.btn-cart:hover {
    background: #16a34a;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(34, 197, 94, 0.35);
}

.btn-cart:active { transform: scale(0.97); }

/* INPUT SỐ LƯỢNG */
.qty-input {
    width: 80px;
    padding: 8px 10px;
    border-radius: 10px;
    border: 1px solid #d1d5db;
    outline: none;
    transition: .2s;
}

.qty-input:focus {
    border-color: var(--primary);
    box-shadow: 0 0 0 2px rgba(59,130,246,0.25);
}

/* PAGINATION */
.pagination {
    padding: 20px;
    display: flex;
    justify-content: center;
    gap: 8px;
}

.pagination .btn {
    padding: 8px 14px;
    border-radius: 999px;
    background: #f3f4f6;
}

.pagination .btn.active {
    background: var(--primary);
    color: #fff;
}

/* RESPONSIVE */

/* Tablet */
@media (max-width: 900px) {
    .header { flex-direction: column; gap: 16px; text-align: center; }
    .search input { width: 150px; }
    .admin-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 6px;
    }
}

/* Mobile */
@media (max-width: 600px) {
    .page { border-radius: 0; }
    .topbar { flex-direction: column; gap: 6px; text-align: center; }
    .search { width: 100%; }
    .search input { width: 100%; }
    .btn { width: 100%; text-align: center; }
    table { min-width: 600px; }
    .btn-cart { width: 100%; justify-content: center; padding: 14px; }
    .admin-header { padding-inline: 16px; }
}
</style>
</head>

<body>
<div class="admin-shell">

    <!-- HEADER ADMIN CHUNG -->
    <header class="admin-header">
        <div class="admin-brand-wrap">
            <div class="admin-brand">BOOK ADMIN</div>
            <div class="admin-sub">Quản lý hệ thống hiệu sách online</div>
        </div>
      <nav class="admin-nav">
    <a href="${pageContext.request.contextPath}/home" target="_blank">
        Xem trang khách
    </a>
    <a href="${pageContext.request.contextPath}/adminHome">
        Quản lý sách
    </a>
    <a href="${pageContext.request.contextPath}/admin/orders">
        Đơn hàng
    </a>
    <a href="${pageContext.request.contextPath}/cart">
        Giỏ hàng
    </a>
    <a href="${pageContext.request.contextPath}/logout" class="logout-link">
        Đăng xuất
    </a>
</nav>

    </header>

    <div class="page">

        <!-- TOP BAR -->
        <div class="topbar">
            <div>Xin chào: <b>${sessionScope.username}</b></div>
            <!-- Có thể thay bằng link tới "Đơn hàng" admin -->
            <a class="btn-primary btn" href="${pageContext.request.contextPath}/admin/orders">
                Xem đơn hàng
            </a>
        </div>

        <!-- HEADER -->
        <div class="header">
            <h2>Danh sách sách</h2>

            <div class="actions">
                <form class="search" method="get" action="${pageContext.request.contextPath}/adminHome">
                    <input type="text" name="q" placeholder="Tìm theo tiêu đề..." value="${param.q}">
                    <button class="btn btn-primary" type="submit">Tìm</button>
                </form>

                <a class="btn btn-primary"
                   href="${pageContext.request.contextPath}/adminHome?action=create">+ Tạo mới</a>
            </div>
        </div>

        <!-- FLASH -->
        <c:if test="${not empty message}">
            <div class="flash">${message}</div>
        </c:if>

        <!-- CONTENT -->
        <div class="content">
            <div class="table-wrap">
                <table>
                    <thead>
                    <tr>
                        <th style="width: 80px">ID</th>
                        <th>Tiêu đề</th>
                        <th style="width: 140px">Giá</th>
                        <th style="width: 220px">Hành động</th>
                        <th style="width: 220px">Giỏ hàng</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:choose>
                        <c:when test="${empty BookList}">
                            <tr><td colspan="5" class="empty">Chưa có dữ liệu.</td></tr>
                        </c:when>

                        <c:otherwise>
                            <c:forEach var="book" items="${BookList}">
                                <tr>
                                    <td><span class="muted">#</span>${book.id}</td>

                                    <td>
                                        <a class="btn-link"
                                           href="${pageContext.request.contextPath}/adminHome?action=detail&id=${book.id}">
                                            ${book.title}
                                        </a>
                                    </td>

                                    <td><fmt:formatNumber value="${book.price}" type="number"/> đ</td>

                                    <td>
                                        <a class="btn btn-warning"
                                           href="${pageContext.request.contextPath}/adminHome?action=edit&id=${book.id}">
                                            Sửa
                                        </a>

                                        <a class="btn btn-danger"
                                           href="${pageContext.request.contextPath}/adminHome?action=delete&id=${book.id}"
                                           onclick="return confirm('Xóa sách này?');">
                                            Xóa
                                        </a>
                                    </td>

                                    <td>
                                        <form action="${pageContext.request.contextPath}/cart" method="post" style="display:flex; gap:10px;">
                                            <input type="hidden" name="action" value="add">
                                            <input type="hidden" name="id" value="${book.id}">
                                            <input class="qty-input" type="number" name="qty" value="1" min="1">
                                            <button class="btn-cart" type="submit">🛒 Thêm vào giỏ</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>

                </table>
            </div>

            <!-- PAGINATION -->
            <c:if test="${not empty totalPages}">
                <div class="pagination">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <c:choose>
                            <c:when test="${i == page}">
                                <span class="btn active">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a class="btn"
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
</div>
</body>
</html>
