package dao;

import db.ConnectDB;
import model.Invoice;
import model.Order;
import model.OrderItem;

import java.sql.*;
import java.util.List;

public class OrderDAO {

  public static class Result {
    public final int orderId;
    public final int invoiceId;
    public Result(int orderId, int invoiceId) { this.orderId = orderId; this.invoiceId = invoiceId; }
  }

  public Result createOrderWithItemsAndInvoice(Order order, Invoice invoice) throws SQLException {
    String sqlOrder = "INSERT INTO orders(user_id, fullname, phone, address, total_amount, payment_method, payment_status) " +
                      "VALUES(?,?,?,?,?,?,?)";
    String sqlItem  = "INSERT INTO order_items(order_id, product_id, product_name, unit_price, quantity, line_total) " +
                      "VALUES(?,?,?,?,?,?)";
    String sqlInv   = "INSERT INTO invoice(order_id, amount, method, status) VALUES(?,?,?,?)";

    try (Connection conn = ConnectDB.getConnection()) {
      conn.setAutoCommit(false);
      int orderId = 0;
      int invoiceId = 0;

      // 1) orders
      try (PreparedStatement ps = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
        ps.setObject(1, order.getUserId(), Types.INTEGER);
        ps.setString(2, order.getFullname());
        ps.setString(3, order.getPhone());
        ps.setString(4, order.getAddress());
        ps.setDouble(5, order.getTotalAmount());
        ps.setString(6, order.getPaymentMethod());   // COD | ZaloPay
        ps.setString(7, order.getPaymentStatus());   // PENDING | PAID
        ps.executeUpdate();
        try (ResultSet rs = ps.getGeneratedKeys()) {
          if (rs.next()) orderId = rs.getInt(1);
        }
      }

      // 2) order_items
      List<OrderItem> items = order.getItems();
      if (items != null && !items.isEmpty()) {
        try (PreparedStatement ps = conn.prepareStatement(sqlItem)) {
          for (OrderItem it : items) {
            ps.setInt(1, orderId);
            ps.setInt(2, it.getProductId());
            ps.setString(3, it.getProductName());
            ps.setDouble(4, it.getUnitPrice());
            ps.setInt(5, it.getQuantity());
            ps.setDouble(6, it.getLineTotal());
            ps.addBatch();
          }
          ps.executeBatch();
        }
      }

      // 3) invoice
      try (PreparedStatement ps = conn.prepareStatement(sqlInv, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, orderId);
        ps.setDouble(2, invoice.getAmount());
        ps.setString(3, invoice.getMethod());  // COD | ZaloPay
        ps.setString(4, invoice.getStatus());  // PENDING | SUCCESS
        ps.executeUpdate();
        try (ResultSet rs = ps.getGeneratedKeys()) {
          if (rs.next()) invoiceId = rs.getInt(1);
        }
      }

      conn.commit();
      return new Result(orderId, invoiceId);

    } catch (SQLException ex) {
      // rollback if error
      // Note: need a new connection or catch outside; here simplified:
      throw ex;
    }
  }

  public void updatePaymentStatus(int orderId, String orderStatus, int invoiceId, String invoiceStatus) throws SQLException {
    String upOrder = "UPDATE orders SET payment_status=? WHERE id=?";
    String upInv   = "UPDATE invoice SET status=? WHERE id=?";
    try (Connection conn = ConnectDB.getConnection()) {
      conn.setAutoCommit(false);
      try (PreparedStatement p1 = conn.prepareStatement(upOrder);
           PreparedStatement p2 = conn.prepareStatement(upInv)) {
        p1.setString(1, orderStatus);
        p1.setInt(2, orderId);
        p1.executeUpdate();

        p2.setString(1, invoiceStatus);
        p2.setInt(2, invoiceId);
        p2.executeUpdate();

        conn.commit();
      } catch (SQLException e) {
        conn.rollback();
        throw e;
      }
    }
  }
}
