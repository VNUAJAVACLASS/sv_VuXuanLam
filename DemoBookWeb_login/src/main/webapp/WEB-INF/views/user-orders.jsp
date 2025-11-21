<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="vi_VN" />

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8" />
<title>Đơn hàng của tôi</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/book.css">
</head>
<body>
	<div class="site">

		<!-- HEADER -->
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
					<a href="${pageContext.request.contextPath}/home" class="active">Trang
						chủ</a> <a href="${pageContext.request.contextPath}/products">Tất cả
						sách</a> <a href="${pageContext.request.contextPath}/cart">Giỏ 
						hàng</a> <a href="${pageContext.request.contextPath}/my-orders">Đơn
						hàng</a> <a href="${pageContext.request.contextPath}/user/profile">Tài
						khoản</a>
				</nav>

				<div class="header-right">
					<c:if test="${not empty sessionScope.username}">
						<div class="user-pill">
							<span>👋</span> <span>Xin chào, <b>${sessionScope.username}</b></span>
						</div>
					</c:if>
					<a class="btn-outline"
						href="${pageContext.request.contextPath}/cart">🛒 Giỏ hàng</a> <a
						class="btn-primary"
						href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
				</div>
			</div>
		</header>

		<!-- MAIN -->
		<main class="main">
			<section class="content">
				<div class="content-header">
					<div class="content-header-left">
						<h2>Đơn hàng của tôi</h2>
						<p>
							<c:choose>
								<c:when test="${empty orders}">
                                Anh chưa có đơn nào. Đặt thử vài cuốn xem sao ✨
                            </c:when>
								<c:otherwise>
                                Danh sách các đơn hàng đã đặt gần đây.
                            </c:otherwise>
							</c:choose>
						</p>
					</div>
				</div>

				<div class="cart-wrapper">
					<div class="cart-card">
						<c:choose>
							<c:when test="${empty orders}">
								<div class="empty">
									Chưa có đơn hàng nào. <br /> <a
										href="${pageContext.request.contextPath}/books"
										class="btn-primary"
										style="margin-top: 12px; display: inline-flex; align-items: center; gap: 6px;">
										➕ Mua sách ngay </a>
								</div>
							</c:when>

							<c:otherwise>
								<table class="cart-table">
									<thead>
										<tr>
											<th>Mã đơn</th>
											<th>Người nhận</th>
											<th>Tổng tiền</th>
											<th>Thanh toán</th>
											<th>Trạng thái</th>
											<th>Hóa đơn</th>
											<!-- ✅ Cột mới -->
										</tr>
									</thead>

									<tbody>
										<c:forEach var="o" items="${orders}">
											<tr>
												<td>#${o.id}</td>
												<td>
													<div class="cart-book-title">${o.fullname}</div>
													<div class="cart-book-meta">${o.address} – ${o.phone}
													</div>
												</td>
												<td><fmt:formatNumber value="${o.totalAmount}"
														type="number" /> đ</td>
												<td>${o.paymentMethod}</td>
												<td>${o.paymentStatus}</td>

												<!-- ✅ Nút xem hóa đơn -->
												<td><a
													href="${pageContext.request.contextPath}/invoice?orderId=${o.id}"
													class="btn-outline"
													style="padding: 6px 10px; font-size: 13px; border-radius: 999px; text-decoration: none;">
														Xem hoá đơn </a></td>
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

		<footer class="footer">
			<div class="footer-inner">
				<div>
					©
					<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" />
					Bookly. Made with ❤️.
				</div>
				<div class="footer-links">
					<a href="#">Điều khoản sử dụng</a> <a href="#">Chính sách bảo
						mật</a> <a href="#">Hỗ trợ</a>
				</div>
			</div>
		</footer>

	</div>
</body>
</html>
