<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh toán đơn hàng</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, sans-serif;
            background-color: #f7f8fa;
            padding: 40px;
        }
        .checkout-container {
            max-width: 600px;
            margin: auto;
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            padding: 30px;
        }
        h2 { color: #333; text-align: center; }
        label { display: block; margin-top: 12px; color: #444; }
        input, select {
            width: 100%; padding: 10px; margin-top: 6px;
            border: 1px solid #ccc; border-radius: 8px;
        }
        button {
            margin-top: 20px; width: 100%; padding: 12px;
            background: #1976d2; color: white;
            border: none; border-radius: 8px;
            cursor: pointer; font-size: 16px;
        }
        button:hover { background: #0d47a1; }
    </style>
</head>
<body>
<div class="checkout-container">
    <h2>💳 Thanh toán đơn hàng</h2>
    <form action="${pageContext.request.contextPath}/checkout" method="post">
        <label>Họ và tên</label>
        <input type="text" name="fullname" required>

        <label>Địa chỉ giao hàng</label>
        <input type="text" name="address" required>

        <label>Số điện thoại</label>
        <input type="text" name="phone" required>

        <label>Phương thức thanh toán</label>
        <select name="paymentMethod">
            <option value="COD">Thanh toán khi nhận hàng (COD)</option>
            <option value="ZaloPay">ZaloPay</option>
        </select>

        <label>Tổng tiền</label>
        <input type="number" name="amount" min="0" value="100000" required readonly>

        <button type="submit">Thanh toán ngay</button>
    </form>
</div>
</body>
</html>
