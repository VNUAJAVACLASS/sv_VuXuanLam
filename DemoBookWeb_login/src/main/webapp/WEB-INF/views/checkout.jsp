<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh toán</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f7f8fa; padding: 40px; }
        .container { max-width: 600px; margin: auto; background: white; border-radius: 12px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #1976d2; margin-bottom: 20px; }
        label { display: block; margin: 15px 0 5px; font-weight: 600; }
        input, select { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 8px; }
        .total { font-weight: bold; font-size: 1.2em; margin: 15px 0; color: #d32f2f; text-align: right; }
        button { width: 100%; padding: 14px; background: #1976d2; color: white; border: none; border-radius: 8px; font-size: 16px; margin-top: 20px; cursor: pointer; }
        button:hover { background: #0d47a1; }
    </style>
</head>
<body>
<div class="container">
    <h2>Thanh toán đơn hàng</h2>

    <c:if test="${empty sessionScope.cart}">
        <p style="text-align:center; color:red;">Giỏ hàng trống!</p>
        <a href="${pageContext.request.contextPath}/adminHome">Quay lại mua sắm</a>
    </c:if>

    <c:if test="${not empty sessionScope.cart}">
        <form action="${pageContext.request.contextPath}/checkout" method="post">
            <label>Họ và tên</label>
            <input type="text" name="fullname" required>

            <label>Địa chỉ giao hàng</label>
            <input type="text" name="address" required>

            <label>Số điện thoại</label>
            <input type="text" name="phone" pattern="\d{10,11}" required>

            <label>Phương thức thanh toán</label>
            <select name="paymentMethod" required>
                <option value="COD">Thanh toán khi nhận hàng (COD)</option>
                <option value="VNPAY">Thanh toán qua VNPay</option>
            </select>

            <div class="total">
                Tổng tiền: <fmt:formatNumber value="${sessionScope.totalPrice}" type="currency"/> VND
            </div>

            <!-- Không để amount ở đây để tránh fake -->
            <input type="hidden" name="amount" value="${sessionScope.totalPrice}">

            <button type="submit">Xác nhận thanh toán</button>
        </form>
    </c:if>
</div>
</body>
</html>