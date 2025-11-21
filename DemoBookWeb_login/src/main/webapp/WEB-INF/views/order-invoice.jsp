<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="vi_VN" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Hóa đơn đơn hàng #${order.id}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/book.css">
    <style>
        .invoice-page {
            max-width: 900px;
            margin: 24px auto;
            background: #fff;
            border-radius: 14px;
            padding: 24px 28px;
            box-shadow: 0 8px 24px rgba(15,23,42,.08);
        }
        .invoice-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            gap: 16px;
            border-bottom: 1px solid #e5e7eb;
            padding-bottom: 16px;
            margin-bottom: 20px;
        }
        .invoice-title {
            font-size: 22px;
            font-weight: 700;
        }
        .invoice-meta {
            font-size: 13px;
            color: #6b7280;
            line-height: 1.6;
        }
        .badge-status {
            display: inline-flex;
            padding: 4px 10px;
            border-radius: 999px;
            font-size: 12px;
            font-weight: 600;
        }
        .badge-paid { background:#dcfce7; color:#15803d; }
        .badge-pending { background:#fef9c3; color:#854d0e; }
        .badge-failed { background:#fee2e2; color:#b91c1c; }

        .info-grid {
            display: grid;
            grid-template-columns: 1.2fr 1fr;
            gap: 16px;
            margin-bottom: 20px;
        }
        .info-box {
            padding: 14px 16px;
            border-radius: 12px;
            background: #f9fafb;
            border: 1px solid #e5e7eb;
            font-size: 14px;
        }
        .info-box h4 {
            margin-bottom: 8px;
            font-size: 14px;
            font-weight: 700;
        }

        .invoice-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        .invoice-table th,
        .invoice-table td {
            padding: 10px 8px;
            border-bottom: 1px solid #e5e7eb;
            font-size: 14px;
        }
        .invoice-table th {
            background: #f1f5f9;
            font-weight: 600;
        }

        .invoice-summary {
            margin-top: 18px;
            max-width: 320px;
            margin-left: auto;
            border-radius: 12px;
            border: 1px solid #e5e7eb;
            padding: 12px 14px;
            background: #f9fafb;
            font-size: 14px;
        }
        .summary-row {
            display:flex;
            justify-content:space-between;
            margin-bottom:6px;
        }
        .summary-row.total {
            font-weight:700;
            border-top:1px dashed #d4d4d8;
            padding-top:8px;
            margin-top:8px;
        }

        .invoice-actions {
            margin-top: 20px;
            display:flex;
            justify-content:space-between;
            flex-wrap:wrap;
            gap:10px;
        }
        .btn {
            display:inline-flex;
            align-items:center;
            justify-content:center;
            gap:6px;
            padding:10px 14px;
            border-radius:999px;
            font-size:14px;
            font-weight:600;
            text-decoration:none;
            border:none;
            cursor:pointer;
        }
        .btn-primary {
            background:#3b82f6;
            color:#fff;
        }
        .btn-outline {
            border:1px solid #d4d4d8;
            color:#111827;
            background:#fff;
        }

        @media (max-width: 720px) {
            .invoice-page { margin:12px; padding:18px; }
            .info-grid { grid-template-columns: 1fr; }
        }
    </style>
</head>
<body>
<div class="site">

    <!-- HEADER TÁI DÙNG BOOKLY -->
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
						chủ</a> <a href="${pageContext.request.contextPath}/books">Tất cả
						sách</a> <a href="${pageContext.request.contextPath}/cart">Giỏ
						hàng</a> <a href="${pageContext.request.contextPath}/my-orders">Đơn
						hàng</a> <a href="${pageContext.request.contextPath}/user/profile">Tài
						khoản</a>
				</nav>

            <div class="header-right">
                <c:if test="${not empty sessionScope.username}">
                    <div class="user-pill">
                        <span>👋</span>
                        <span>Xin chào, <b>${sessionScope.username}</b></span>
                    </div>
                </c:if>
                <a class="btn-outline" href="${pageContext.request.contextPath}/cart">🛒 Giỏ hàng</a>
                <a class="btn-primary" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </div>
        </div>
    </header>

    <main class="main">
        <div class="invoice-page">
            <!-- HEADER HÓA ĐƠN -->
            <div class="invoice-header">
                <div>
                    <div class="invoice-title">Hóa đơn đơn hàng #${order.id}</div>
                    <div class="invoice-meta">
                        Ngày in: <fmt:formatDate value="<%= new java.util.Date() %>" pattern="dd/MM/yyyy HH:mm"/> <br/>
                        Người đặt: ${order.fullname}
                    </div>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${order.paymentStatus == 'PAID' || invoice.status == 'SUCCESS'}">
                            <span class="badge-status badge-paid">Đã thanh toán</span>
                        </c:when>
                        <c:when test="${order.paymentStatus == 'FAILED' || invoice.status == 'FAILED'}">
                            <span class="badge-status badge-failed">Thanh toán thất bại</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge-status badge-pending">Chờ thanh toán</span>
                        </c:otherwise>
                    </c:choose>
                    <div class="invoice-meta" style="margin-top:6px;">
                        Phương thức: ${order.paymentMethod}
                    </div>
                </div>
            </div>

            <!-- INFO -->
            <div class="info-grid">
                <div class="info-box">
                    <h4>Thông tin giao hàng</h4>
                    <div><b>${order.fullname}</b></div>
                    <div>${order.address}</div>
                    <div>Điện thoại: ${order.phone}</div>
                </div>

                <div class="info-box">
                    <h4>Thông tin hóa đơn</h4>
                    <c:if test="${not empty invoice}">
                        <div>Mã hóa đơn: #${invoice.id}</div>
                        <div>Số tiền hóa đơn:
                            <b><fmt:formatNumber value="${invoice.amount}" type="number"/> đ</b>
                        </div>
                        <div>Phương thức: ${invoice.method}</div>
                        <div>Trạng thái: ${invoice.status}</div>
                    </c:if>
                    <c:if test="${empty invoice}">
                        <div>Chưa có hóa đơn ghi nhận cho đơn này.</div>
                    </c:if>
                </div>
            </div>

            <!-- BẢNG SẢN PHẨM -->
            <table class="invoice-table">
                <thead>
                <tr>
                    <th style="width: 50px;">#</th>
                    <th>Sản phẩm</th>
                    <th style="width: 110px;">Đơn giá</th>
                    <th style="width: 80px;">SL</th>
                    <th style="width: 130px;">Thành tiền</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="it" items="${items}" varStatus="st">
                    <tr>
                        <td>${st.index + 1}</td>
                        <td>${it.productName}</td>
                        <td><fmt:formatNumber value="${it.unitPrice}" type="number"/> đ</td>
                        <td>${it.quantity}</td>
                        <td><fmt:formatNumber value="${it.lineTotal}" type="number"/> đ</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <!-- TÓM TẮT TIỀN -->
            <div class="invoice-summary">
                <div class="summary-row">
                    <span>Tổng tiền hàng</span>
                    <span><fmt:formatNumber value="${order.totalAmount}" type="number"/> đ</span>
                </div>
                <!-- Nếu anh có thêm phí ship / giảm giá thì cộng ở đây -->
                <div class="summary-row total">
                    <span>Thành tiền</span>
                    <span><fmt:formatNumber value="${order.totalAmount}" type="number"/> đ</span>
                </div>
            </div>

            <!-- ACTION -->
            <div class="invoice-actions">
                <a href="${pageContext.request.contextPath}/my-orders" class="btn btn-outline">
                    ← Quay lại danh sách đơn
                </a>
                <button class="btn btn-primary" type="button" onclick="window.print();">
                    🖨 In hóa đơn
                </button>
            </div>
        </div>
    </main>

    <footer class="footer">
        <div class="footer-inner">
            <div>© <fmt:formatDate value="<%= new java.util.Date() %>" pattern="yyyy"/> Bookly. Made with ❤️.</div>
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
