<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="vi_VN"/>

<html lang="vi">
<head>
<meta charset="UTF-8" />
<title>Giỏ hàng</title>
<style>
:root{
  --primary:#1976d2; --primary-dark:#0d47a1; --bg:#f3f6fa; --text:#2c3e50;
  --muted:#6b7280; --danger:#e53935; --radius:12px;
}
*{box-sizing:border-box;margin:0;padding:0}
body{
  font-family:"Segoe UI",Tahoma,Arial,sans-serif; background:var(--bg); color:var(--text);
  min-height:100vh; display:flex; align-items:center; justify-content:center; padding:24px;
}
.page{ width:100%; max-width:980px; background:#fff; border-radius:var(--radius);
  box-shadow:0 10px 24px rgba(0,0,0,.08); overflow:hidden; }
.topbar{ display:flex; align-items:center; justify-content:space-between;
  padding:16px 20px; background:#fff; border-bottom:1px solid #eef2f7; }
.link{ color:var(--primary); text-decoration:none; font-weight:600 }
.link:hover{ color:var(--primary-dark) }
.header{ padding:20px; display:flex; gap:12px; flex-wrap:wrap; align-items:center; justify-content:space-between; }
h2{ color:var(--primary) }
.actions{ display:flex; gap:10px; align-items:center; flex-wrap:wrap }
.btn{
  display:inline-block; border:none; border-radius:10px; padding:10px 14px;
  font-weight:600; text-decoration:none; cursor:pointer; transition:.2s;
}
.btn-primary{ background:var(--primary); color:#fff }
.btn-primary:hover{ background:var(--primary-dark); transform:translateY(-1px) }
.btn-danger{ background:var(--danger); color:#fff }
.btn-danger:hover{ filter:brightness(.95); transform:translateY(-1px) }
.content{ padding:0 20px 20px }
.table-wrap{ overflow:auto; border:1px solid #eef2f7; border-radius:10px }
table{ width:100%; border-collapse:collapse; min-width:760px; background:#fff }
thead th{
  text-align:left; background:#f8fafc; color:#374151; padding:12px; border-bottom:1px solid #eef2f7; font-weight:700
}
tbody td{ padding:12px; border-bottom:1px solid #f1f5f9; vertical-align:top }
tbody tr:hover{ background:#fafafa }
tfoot td{ padding:12px; background:#fafafa; font-weight:700 }
.muted{ color:var(--muted) }
.empty{ text-align:center; padding:28px; color:var(--muted) }
.qty-input{
  width:86px; border:1px solid #e5e7eb; border-radius:8px; padding:8px 10px; outline:none;
}
.row-actions{ display:flex; gap:8px; flex-wrap:wrap; align-items:center }
.total-bar{ display:flex; justify-content:space-between; align-items:center; margin-top:16px }
@media (max-width:600px){ .btn{ padding:9px 12px } }
</style>
</head>
<body>
  <div class="page">
    <!-- Top bar -->
    <div class="topbar">
      <div class="hello">Giỏ hàng</div>
      <a class="link" href="${pageContext.request.contextPath}/adminHome">⬅️ Tiếp tục mua</a>
    </div>

    <!-- Header -->
    <div class="header">
      <h2>🛒 Giỏ hàng của bạn</h2>
      <div class="actions">
        <form action="${pageContext.request.contextPath}/cart" method="post">
          <input type="hidden" name="action" value="clear"/>
          <button class="btn btn-danger" type="submit" onclick="return confirm('Xoá toàn bộ giỏ hàng?')">Xoá giỏ</button>
        </form>
      </div>
    </div>

    <div class="content">
      <div class="table-wrap">
        <table>
          <thead>
            <tr>
              <th style="width:90px">ID</th>
              <th>Tiêu đề</th>
              <th style="width:140px">Giá</th>
              <th style="width:120px">Số lượng</th>
              <th style="width:160px">Thành tiền</th>
              <th style="width:200px">Hành động</th>
            </tr>
          </thead>
          <tbody>
            <c:choose>
              <c:when test="${empty sessionScope.cart}">
                <tr>
                  <td colspan="6" class="empty">💨 Giỏ hàng trống rồi anh iu ơi~ thêm vài cuốn đi nè!</td>
                </tr>
              </c:when>
              <c:otherwise>
                <c:set var="total" value="0"/>
                <c:forEach var="it" items="${sessionScope.cart}">
                  <c:set var="line" value="${it.priceSnapshot * it.quantity}"/>
                  <c:set var="total" value="${total + line}"/>
                  <tr>
                    <td><span class="muted">#</span>${it.book.id}</td>
                    <td>
                      <div style="font-weight:600">${it.book.title}</div>
                      <div class="muted" style="font-size:13px; margin-top:4px">${it.book.content}</div>
                    </td>
                    <td><fmt:formatNumber value="${it.priceSnapshot}" type="number"/> đ</td>
                    <td>
                      <form class="row-actions" action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name="action" value="update"/>
                        <input type="hidden" name="id" value="${it.book.id}"/>
                        <input class="qty-input" type="number" name="qty" min="1" value="${it.quantity}"/>
                        <button class="btn btn-primary" type="submit">Cập nhật</button>
                      </form>
                    </td>
                    <td><fmt:formatNumber value="${line}" type="number"/> đ</td>
                    <td>
                      <form class="row-actions" action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name="action" value="remove"/>
                        <input type="hidden" name="id" value="${it.book.id}"/>
                        <button class="btn btn-danger" type="submit" onclick="return confirm('Xoá mục này?')">Xoá</button>
                      </form>
                    </td>
                  </tr>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </tbody>

          <c:if test="${not empty sessionScope.cart}">
            <tfoot>
              <tr>
                <td colspan="4" style="text-align:right">Tổng cộng:</td>
                <td colspan="2"><fmt:formatNumber value="${total}" type="number"/> đ</td>
              </tr>
            </tfoot>
          </c:if>
        </table>
      </div>

      <c:if test="${not empty sessionScope.cart}">
        <div class="total-bar">
          <div class="muted">Anh iu nhớ kiểm tra lại số lượng trước khi thanh toán nha 😘</div>
          <form action="${pageContext.request.contextPath}/checkout" method="post">
            <button class="btn btn-primary" type="submit">Thanh toán</button>
          </form>
        </div>
      </c:if>
    </div>
  </div>
</body>
</html>
