<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="vi_VN" />

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8" />
<title>Giỏ hàng</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
	integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLMDJd/rBKYNHIILk7vWAJ+Y3DGf6a7x7QxGZkF+6/tK54B3lFzE6T3f2l7f2n0w=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/book.css">
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
					<a href="${pageContext.request.contextPath}/home" class="active">Trang
						chủ</a> <a href="${pageContext.request.contextPath}/products">Tất
						cả sách</a> <a href="${pageContext.request.contextPath}/cart">Giỏ
						hàng</a> <a href="${pageContext.request.contextPath}/my-orders">Đơn
						hàng</a> <a href="${pageContext.request.contextPath}/user/profile">Tài
						khoản</a>
				</nav>


				<div class="header-right">
					<c:if test="${not empty sessionScope.username}">
						<div class="user-pill">
							<span><i class="fas fa-hand-wave"></i></span> <span>Xin
								chào, <b>${sessionScope.username}</b>
							</span>
						</div>
					</c:if>
					<a class="btn-outline"
						href="${pageContext.request.contextPath}/books"><i
						class="fas fa-arrow-left"></i> Tiếp tục mua</a> <a class="btn-primary"
						href="${pageContext.request.contextPath}/logout">Đăng xuất</a> <label
						for="navCheckbox" class="nav-toggle"> <span></span>
					</label>
				</div>
			</div>

			<input type="checkbox" id="navCheckbox" />
			<div class="nav-mobile">
				<div class="nav-mobile-inner">
					<a href="${pageContext.request.contextPath}/home" class="active">Trang
						chủ</a> <a href="${pageContext.request.contextPath}/products">Tất
						cả sách</a> <a href="${pageContext.request.contextPath}/cart">Giỏ
						hàng</a> <a href="${pageContext.request.contextPath}/my-orders">Đơn
						hàng</a> <a href="${pageContext.request.contextPath}/user/profile">Tài
						khoản</a>
				</div>
			</div>
		</header>

		<main class="main">
			<section class="hero" style="margin-bottom: 0;">
				<div class="hero-text">
					<h1>
						Giỏ hàng của bạn <i class="fas fa-shopping-cart"></i>
					</h1>
					<p>Kiểm tra lại các tựa sách trước khi tiến hành thanh toán
						nhé.</p>
					<div class="hero-badges">
						<span class="badge">Miễn phí ship từ 300k</span> <span
							class="badge secondary">Hoàn hàng dễ dàng</span>
					</div>
				</div>
			</section>

			<section class="content">
				<div class="content-header">
					<div class="content-header-left">
						<h2>Tóm tắt giỏ hàng</h2>
						<p>
							<c:choose>
								<c:when test="${empty sessionScope.cart}">
                                Hiện tại giỏ hàng chưa có cuốn nào.
                            </c:when>
								<c:otherwise>
                                Kiểm tra số lượng từng tựa sách, sau đó nhấn thanh toán.
                            </c:otherwise>
							</c:choose>
						</p>
					</div>

					<div class="content-header-right">
						<a href="${pageContext.request.contextPath}/books"
							class="btn-link"> <i class="fas fa-arrow-left"></i> Tiếp tục
							chọn sách
						</a>
					</div>
				</div>

				<div class="cart-wrapper">
					<div class="cart-card">
						<c:choose>
							<c:when test="${empty sessionScope.cart}">
								<div class="empty">
									Giỏ hàng trống. <br /> <a
										href="${pageContext.request.contextPath}/books"
										class="btn-primary"
										style="margin-top: 12px; display: inline-flex; align-items: center; gap: 6px;">
										<i class="fas fa-plus"></i> Thêm sách vào giỏ
									</a>
								</div>
							</c:when>

							<c:otherwise>
								<table class="cart-table">
									<thead>
										<tr>
											<th>Sách</th>
											<th>Mã</th>
											<th>Đơn giá</th>
											<th>Số lượng</th>
											<th>Thành tiền</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:set var="total" value="0" />
										<c:forEach var="it" items="${sessionScope.cart}">
											<c:set var="line" value="${it.priceSnapshot * it.quantity}" />
											<c:set var="total" value="${total + line}" />

											<tr>
												<td>
													<div class="cart-book-title">${it.book.title}</div>
													<div class="cart-book-meta">${it.book.content}</div>
												</td>
												<td>#${it.book.id}</td>
												<td><fmt:formatNumber value="${it.priceSnapshot}"
														type="number" /> đ</td>
												<td>
													<form class="row-actions"
														action="${pageContext.request.contextPath}/cart"
														method="post">
														<input type="hidden" name="action" value="update" /> <input
															type="hidden" name="id" value="${it.book.id}" /> <input
															class="cart-qty-input" type="number" name="qty" min="1"
															value="${it.quantity}" />
														<button class="btn-primary" type="submit"
															style="border-radius: 999px; padding-inline: 12px; font-size: 12px;">
															<i class="fas fa-sync-alt"></i> Cập nhật
														</button>
													</form>
												</td>
												<td><fmt:formatNumber value="${line}" type="number" />
													đ</td>
												<td>
													<form action="${pageContext.request.contextPath}/cart"
														method="post"
														onsubmit="return confirm('Xoá mục này khỏi giỏ?')">
														<input type="hidden" name="action" value="remove" /> <input
															type="hidden" name="id" value="${it.book.id}" />
														<button class="btn-cart-remove" type="submit"
															style="border: none; background: none; color: #e11d48; cursor: pointer; font-size: 13px;">
															<i class="fas fa-times"></i> Xoá
														</button>
													</form>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

								<div class="cart-actions-row" style="margin-top: 10px;">
									<form action="${pageContext.request.contextPath}/cart"
										method="post"
										onsubmit="return confirm('Xoá toàn bộ giỏ hàng?')">
										<input type="hidden" name="action" value="clear" />
										<button class="btn-outline" type="submit"
											style="border-color: #fecaca; color: #b91c1c;">
											<i class="fas fa-trash-alt"></i> Xoá toàn bộ giỏ
										</button>
									</form>
								</div>
							</c:otherwise>
						</c:choose>
					</div>

					<c:if test="${not empty sessionScope.cart}">
						<div class="cart-summary">
							<div class="cart-summary-title">Tổng tiền</div>

							<div class="cart-summary-row">
								<span>Tạm tính</span> <span><fmt:formatNumber
										value="${total}" type="number" /> đ</span>
							</div>

							<div class="cart-summary-row">
								<span>Phí vận chuyển</span> <span> <c:choose>
										<c:when test="${total >= 300000}">
                                        0 đ
                                    </c:when>
										<c:otherwise>
                                        25.000 đ
                                    </c:otherwise>
									</c:choose>
								</span>
							</div>

							<div class="cart-summary-row total">
								<span>Thành tiền</span> <span> <c:choose>
										<c:when test="${total >= 300000}">
											<fmt:formatNumber value="${total}" type="number" /> đ
                                    </c:when>
										<c:otherwise>
											<c:set var="finalTotal" value="${total + 25000}" />
											<fmt:formatNumber value="${finalTotal}" type="number" /> đ
                                    </c:otherwise>
									</c:choose>
								</span>
							</div>

							<c:set var="totalPrice" value="${total}" scope="session" />
							<c:if test="${total < 300000}">
								<c:set var="totalPrice" value="${total + 25000}" scope="session" />
							</c:if>

							<div class="cart-summary-note">Miễn phí ship cho đơn từ
								300.000đ. Vui lòng kiểm tra lại số lượng trước khi thanh toán.</div>

							<a href="${pageContext.request.contextPath}/checkout"
								class="btn-primary"
								style="width: 100%; justify-content: center; display: inline-flex; text-align: center;">
								<i class="fas fa-money-check-alt"></i> Nhập địa chỉ & thanh toán
							</a>

						</div>
					</c:if>
				</div>
			</section>
		</main>

		<footer class="footer">
			<div class="footer-inner">
				<div>
					©
					<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" />
					Bookly. Made with <i class="fas fa-heart"></i>.
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