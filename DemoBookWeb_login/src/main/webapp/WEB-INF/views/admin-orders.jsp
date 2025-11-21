<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="vi_VN" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <title>Quản lý đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/book.css">
</head>
<body>
<div class="site">
    <header class="header">
        <div class="header-inner">
            <div class="logo-wrap">
                <div class="logo-icon">B</div>
                <div>
                    <div class="logo-text-main">BOOKLY ADMIN</div>
                    <div class="logo-text-sub">Quản lý đơn hàng</div>
                </div>
            </div>

            <nav class="nav">
                <a href="${pageContext.request.contextPath}/adminHome">Sách</a>
                <a href="${pageContext.request.contextPath}/admin/orders" class="active">Đơn hàng</a>
            </nav>

            <div class="header-right">
                <a class="btn-primary" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </div>
        </div>
    </header>

    <main class="main">
        <section class="content">
            <div class="content-header">
                <div class="content-header-left">
                    <h2>Danh sách đơn hàng</h2>
                    <p>Quản lý các đơn hàng của khách.</p>
                </div>
            </div>

            <div class="cart-wrapper">
                <div class="cart-card">
                    <c:choose>
                        <c:when test="${empty orders}">
                            <div class="empty">Chưa có đơn hàng nào.</div>
                        </c:when>
                        <c:otherwise>
                            <table class="cart-table">
                                <thead>
                                <tr>
                                    <th>Mã đơn</th>
                                    <th>Người nhận</th>
                                    <th>SĐT</th>
                                    <th>Tổng tiền</th>
                                    <th>Thanh toán</th>
                                    <th>Trạng thái</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="o" items="${orders}">
                                    <tr>
                                        <td>#${o.id}</td>
                                        <td>${o.fullname}</td>
                                        <td>${o.phone}</td>
                                        <td><fmt:formatNumber value="${o.totalAmount}" type="number"/> đ</td>
                                        <td>${o.paymentMethod}</td>
                                        <td>${o.paymentStatus}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </section>
    </main>
</div>
</body>
</html>
