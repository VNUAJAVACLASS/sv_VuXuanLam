<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="vi_VN"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Tất cả sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/book.css">
</head>

<body>
<div class="site">

    <header class="header">
        <div class="header-inner">
            <div class="logo-wrap">
                <div class="logo-icon">B</div>
                <div>
                    <div class="logo-text-main">BOOKLY</div>
                    <div class="logo-text-sub">Hiệu sách online của bạn</div>
                </div>
            </div>

            <nav class="nav">
                <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
                <a href="${pageContext.request.contextPath}/products" class="active">Tất cả sách</a>
                <a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a>
                <a href="${pageContext.request.contextPath}/my-orders">Đơn hàng</a>
                <a href="${pageContext.request.contextPath}/user/profile">Tài khoản</a>
            </nav>
        </div>
    </header>

    <main class="main">
        <section class="content">

            <div class="content-header">
                <h2>Tất cả sản phẩm</h2>
                <p>Duyệt toàn bộ sách đang có trong cửa hàng</p>
            </div>

            <c:choose>
                <c:when test="${empty BookList}">
                    <div class="empty">Không có sách nào.</div>
                </c:when>

                <c:otherwise>
                    <div class="book-grid">

                        <c:forEach var="b" items="${BookList}">
                            <div class="book-card">

                                <div class="book-thumb">Bìa sách</div>

                                <div class="book-card-main">
                                    <div>
                                        <div class="book-title">${b.title}</div>
                                        <div class="book-meta">Mã sách #${b.id}</div>
                                        <div class="book-price">
                                            <fmt:formatNumber value="${b.price}" type="number"/> đ
                                        </div>
                                    </div>

                                    <div class="book-actions">
                                        <a class="btn-link"
                                           href="${pageContext.request.contextPath}/bookDetail?id=${b.id}">
                                            Xem chi tiết →
                                        </a>

                                        <form action="${pageContext.request.contextPath}/cart" method="post"
                                              style="display:flex; align-items:center; gap:6px;">
                                            <input type="hidden" name="action" value="add">
                                            <input type="hidden" name="id" value="${b.id}">
                                            <input class="qty-input" type="number" name="qty" value="1" min="1">
                                            <button class="btn-cart" type="submit">
                                                + Thêm
                                            </button>
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </c:forEach>

                    </div>
                </c:otherwise>
            </c:choose>

            <!-- PHÂN TRANG -->
            <c:if test="${totalPages > 1}">
                <div class="pagination">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <c:choose>
                            <c:when test="${i == page}">
                                <span class="page-btn active">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a class="page-btn"
                                   href="${pageContext.request.contextPath}/products?page=${i}">
                                    ${i}
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:if>

        </section>
    </main>

</div>
</body>
</html>
