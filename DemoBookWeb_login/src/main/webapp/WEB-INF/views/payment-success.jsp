<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thanh toán thành công</title>
</head>
<body
	style="font-family: Segoe UI; padding: 48px; background: #f7f8fa; text-align: center">
	<div
		style="display: inline-block; background: #fff; padding: 36px 56px; border-radius: 12px; box-shadow: 0 3px 10px rgba(0, 0, 0, .1)">
		<h2>✅ Thanh toán thành công!</h2>
		<p>
			Cảm ơn <b>${sessionScope.checkout_name}</b> đã đặt hàng.
		</p>
		<p>
			Phương thức: <b>${sessionScope.checkout_method}</b>
		</p>
		<p>
			Tổng tiền: <b>${sessionScope.checkout_amount} đ</b>
		</p>
		<a href="${pageContext.request.contextPath}/adminHome">⬅Về trang
			chính</a>
	</div>
</body>
</html>
