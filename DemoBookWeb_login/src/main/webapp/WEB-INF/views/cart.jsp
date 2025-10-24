<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Giỏ hàng của bạn 💕</title>
    <style>
        body { font-family: "Segoe UI"; background: #f3f6fa; }
        table { width: 80%; margin: 50px auto; border-collapse: collapse; }
        th, td { padding: 12px; border-bottom: 1px solid #ccc; text-align: center; }
        th { background: #8ac6d1; color: white; }
        a { text-decoration: none; color: #3498db; }
    </style>
</head>
<body>
    <h2 style="text-align:center;">🛍️ Giỏ hàng của bạn</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Tên sách</th>
            <th>Nội dung</th>
            <th>Số lượng</th>
        </tr>
        <%
            java.util.List<model.CartItem> cart = (java.util.List<model.CartItem>) request.getAttribute("cart");
            if (cart != null && !cart.isEmpty()) {
                for (model.CartItem item : cart) {
        %>
        <tr>
            <td><%= item.getBook().getId() %></td>
            <td><%= item.getBook().getTitle() %></td>
            <td><%= item.getBook().getContent() %></td>
            <td><%= item.getQuantity() %></td>
        </tr>
        <% }} else { %>
        <tr><td colspan="4">💨 Giỏ hàng trống!</td></tr>
        <% } %>
    </table>
    <div style="text-align:center;"><a href="books">⬅️ Tiếp tục mua sắm</a></div>
</body>
</html>
