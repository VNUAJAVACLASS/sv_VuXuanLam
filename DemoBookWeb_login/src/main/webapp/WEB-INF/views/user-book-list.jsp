<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="vi_VN" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Hiệu sách online</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLMDJd/rBKYNHIILk7vWAJ+Y3DGf6a7x7QxGZkF+6/tK54B3lFzE6T3f2l7f2n0w=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/book.css">
</head>
<body>
<div class="site">

    <%-- TÍNH SỐ LƯỢNG VÀ TỔNG TIỀN GIỎ HÀNG --%>
    <c:set var="cartCount" value="0" />
    <c:set var="cartTotal" value="0" />
    <c:if test="${not empty sessionScope.cart}">
        <c:forEach var="it" items="${sessionScope.cart}">
            <c:set var="cartCount" value="${cartCount + it.quantity}" />
            <c:set var="cartTotal" value="${cartTotal + (it.priceSnapshot * it.quantity)}" />
        </c:forEach>
    </c:if>

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
    <a href="${pageContext.request.contextPath}/home" class="active">Trang chủ</a>
    <a href="${pageContext.request.contextPath}/products">Tất cả sách</a>
    <a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a>
    <a href="${pageContext.request.contextPath}/my-orders">Đơn hàng</a>
    <a href="${pageContext.request.contextPath}/user/profile">Tài khoản</a>
</nav>


            <div class="header-right">
                <c:if test="${not empty sessionScope.username}">
                    <div class="user-pill">
                        <span><i class="fas fa-hand-wave"></i></span>
                        <span>Xin chào, <b>${sessionScope.username}</b></span>
                    </div>
                </c:if>

                <%-- NÚT GIỎ HÀNG CÓ SỐ LƯỢNG --%>
                <a class="btn-outline" href="${pageContext.request.contextPath}/cart"
                   title="Tổng tiền: <fmt:formatNumber value='${cartTotal}' type='number'/> đ">
                    <i class="fas fa-shopping-cart"></i>
                    Giỏ hàng
                    <c:if test="${cartCount > 0}">
                        (<span>${cartCount}</span>)
                    </c:if>
                </a>

                <a class="btn-primary" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>

                <label for="navCheckbox" class="nav-toggle">
                    <span></span>
                </label>
            </div>
        </div>

        <input type="checkbox" id="navCheckbox" />
        <div class="nav-mobile">
            <div class="nav-mobile-inner">
               
    <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
    <a href="${pageContext.request.contextPath}/books">Tất cả sách</a>
    <a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a>
    <a href="${pageContext.request.contextPath}/my-orders">Đơn hàng</a>
    <a href="${pageContext.request.contextPath}/user/profile">Tài khoản</a>


            </div>
        </div>
    </header>

    <main class="main">
        <section class="hero">
            <div class="hero-text">
                <h1>Khám phá những cuốn sách phù hợp cho hôm nay <i class="fas fa-book-open"></i></h1>
                <p>Chọn một cuốn sách yêu thích, thêm vào giỏ và hoàn thành đơn hàng chỉ trong vài bước đơn giản.</p>
                <div class="hero-badges">
                    <span class="badge">Miễn phí vận chuyển đơn từ 300k</span>
                    <span class="badge secondary">Ưu đãi 10% cho sinh viên</span>
                    <span class="badge neutral">Đa dạng thể loại &amp; chủ đề</span>
                </div>
            </div>

            <div class="hero-card">
                <div class="hero-search-title">Tìm kiếm nhanh</div>
                <form method="get" action="${pageContext.request.contextPath}/books">
                    <div class="search-bar">
                        <span style="font-size:16px;"><i class="fas fa-search"></i></span>
                        <input type="text" name="q" placeholder="Nhập tiêu đề sách bạn muốn tìm..."
                               value="${param.q}">
                        <button class="btn-primary" type="submit">
                            Tìm sách
                        </button>
                    </div>

                    <div class="chip-row">
                        <span class="chip"><i class="fas fa-lightbulb"></i> Kỹ năng sống</span>
                        <span class="chip"><i class="fas fa-briefcase"></i> Kinh tế</span>
                        <span class="chip"><i class="fas fa-feather-alt"></i> Văn học</span>
                        <span class="chip"><i class="fas fa-brain"></i> Tâm lý</span>
                    </div>
                </form>
            </div>
        </section>

        <section class="content">

            <div class="content-header">
                <div class="content-header-left">
                    <h2>Danh sách sách</h2>
                    <p>
                        <c:choose>
                            <c:when test="${not empty param.q}">
                                Kết quả cho từ khóa "<b>${param.q}</b>"
                                <c:if test="${not empty totalResults}">
                                    &nbsp;– tìm được <b>${totalResults}</b> tựa sách.
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                Gợi ý các tựa sách đang được quan tâm nhiều.
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>

                <div class="content-header-right">
                    <%-- Ở đây anh có thể thêm filter/ sort, em giữ nguyên --%>
                    <form method="get" action="${pageContext.request.contextPath}/books"
                          style="display:flex; gap:8px; align-items:center;">
                        <c:if test="${not empty categoryList}">
                            <select name="categoryId" class="select" onchange="this.form.submit()">
                                <option value="">Tất cả thể loại</option>
                                <c:forEach var="cat" items="${categoryList}">
                                    <option value="${cat.id}"
                                            ${param.categoryId == cat.id ? 'selected' : ''}>
                                        ${cat.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </c:if>

                        <select name="sort" class="select" onchange="this.form.submit()">
                            <option value="">Sắp xếp</option>
                            <option value="priceAsc"  ${param.sort == 'priceAsc'  ? 'selected' : ''}>Giá thấp → cao</option>
                            <option value="priceDesc" ${param.sort == 'priceDesc' ? 'selected' : ''}>Giá cao → thấp</option>
                            <option value="new"       ${param.sort == 'new'       ? 'selected' : ''}>Mới nhất</option>
                        </select>

                        <input type="hidden" name="q" value="${param.q}"/>
                    </form>
                </div>
            </div>

            <c:if test="${not empty message}">
                <div class="flash">${message}</div>
            </c:if>

            <c:choose>
                <c:when test="${empty BookList}">
                    <div class="empty">
                        Hiện chưa có sách nào phù hợp với tìm kiếm.
                        Vui lòng thử từ khóa khác hoặc thay đổi bộ lọc.
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="book-grid">
                        <c:forEach var="book" items="${BookList}">
                            <div class="book-card">
                                <c:if test="${book.price lt 50000}">
                                    <div class="book-tag">Giá hạt dẻ</div>
                                </c:if>

                                <div class="book-thumb">
                                    Bìa sách
                                </div>

                                <div class="book-card-main">
                                    <div>
                                        <div class="book-title">${book.title}</div>
                                        <div class="book-meta">Mã sách #${book.id}</div>
                                        <div class="book-price">
                                            <fmt:formatNumber value="${book.price}" type="number"/> đ
                                        </div>
                                    </div>

                                    <div class="book-actions">
                                        <a class="btn-link"
                                           href="${pageContext.request.contextPath}/bookDetail?id=${book.id}">
                                            Xem chi tiết <i class="fas fa-arrow-right"></i>
                                        </a>

                                        <form action="${pageContext.request.contextPath}/cart" method="post"
                                              style="display:flex; align-items:center; gap:6px;">
                                            <input type="hidden" name="action" value="add">
                                            <input type="hidden" name="id" value="${book.id}">
                                            <input class="qty-input" type="number" name="qty" value="1" min="1">
                                            <button class="btn-cart" type="submit">
                                                <i class="fas fa-cart-plus"></i> Thêm
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>

            <c:if test="${not empty totalPages}">
                <div class="pagination">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <c:choose>
                            <c:when test="${i == page}">
                                <span class="page-btn active">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a class="page-btn"
                                   href="${pageContext.request.contextPath}/books?page=${i}
                                        &q=${param.q}
                                        &sort=${param.sort}
                                        &categoryId=${param.categoryId}">
                                    ${i}
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:if>

            <%-- MINI TÓM TẮT GIỎ HÀNG NGAY DƯỚI DANH SÁCH --%>
            <c:if test="${cartCount > 0}">
                <div class="cart-summary-home"
                     style="margin-top:24px; padding:16px 20px; border-radius:12px; background:#f8fafc; border:1px solid #e5e7eb;">
                    <div style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:8px;">
                        <div>
                            <div style="font-weight:600; margin-bottom:4px;">
                                Giỏ hàng hiện tại: <span>${cartCount}</span> sản phẩm
                            </div>
                            <div style="font-size:14px; color:#6b7280;">
                                Tạm tính:
                                <b><fmt:formatNumber value="${cartTotal}" type="number"/> đ</b>
                            </div>
                        </div>
                        <div style="display:flex; gap:10px; flex-wrap:wrap;">
                            <a href="${pageContext.request.contextPath}/cart"
                               class="btn-primary"
                               style="text-decoration:none; display:inline-flex; align-items:center; gap:6px;">
                                <i class="fas fa-shopping-cart"></i> Xem giỏ hàng
                            </a>
                            <a href="${pageContext.request.contextPath}/checkout"
                               class="btn-outline"
                               style="text-decoration:none; display:inline-flex; align-items:center; gap:6px;">
                                <i class="fas fa-money-check-alt"></i> Thanh toán nhanh
                            </a>
                        </div>
                    </div>
                </div>
            </c:if>

        </section>
    </main>

    <footer class="footer">
        <div class="footer-inner">
            <div>© <fmt:formatDate value="<%= new java.util.Date() %>" pattern="yyyy"/> Bookly. Made with <i class="fas fa-heart"></i>.</div>
            <div class="footer-links">
                <a href="#">Điều khoản sử dụng</a>
                <a href="#">Chính sách bảo mật</a>
                <a href="#">Hỗ trợ</a>
            </div>
        </div>
    </footer>
</div>
</body>
</html>
