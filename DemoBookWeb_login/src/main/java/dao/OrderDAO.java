package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import db.ConnectDB;
import model.Invoice;
import model.Order;
import model.OrderItem;

public class OrderDAO {

	public static class Result {
		public final int orderId;
		public final int invoiceId;

		public Result(int orderId, int invoiceId) {
			this.orderId = orderId;
			this.invoiceId = invoiceId;
		}
	}

	public Result createOrderWithItemsAndInvoice(Order order, Invoice invoice) throws SQLException {
		String sqlOrder = "INSERT INTO orders(user_id, fullname, phone, address, total_amount, payment_method, payment_status) "
				+ "VALUES(?,?,?,?,?,?,?)";
		String sqlItem = "INSERT INTO order_items(order_id, product_id, product_name, unit_price, quantity, line_total) "
				+ "VALUES(?,?,?,?,?,?)";
		String sqlInv = "INSERT INTO invoice(order_id, amount, method, status) VALUES(?,?,?,?)";

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
				ps.setString(6, order.getPaymentMethod()); // COD | ZaloPay
				ps.setString(7, order.getPaymentStatus()); // PENDING | PAID
				ps.executeUpdate();
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next())
						orderId = rs.getInt(1);
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
				ps.setString(3, invoice.getMethod()); // COD | ZaloPay
				ps.setString(4, invoice.getStatus()); // PENDING | SUCCESS
				ps.executeUpdate();
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next())
						invoiceId = rs.getInt(1);
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

	public void updatePaymentStatus(int orderId, String orderStatus, int invoiceId, String invoiceStatus)
			throws SQLException {
		String upOrder = "UPDATE orders SET payment_status=? WHERE id=?";
		String upInv = "UPDATE invoice SET status=? WHERE id=?";
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

//lấy list đơn theo user
	public List<Order> findByUserId(int userId) throws SQLException {
		String sql = "SELECT id, user_id, fullname, phone, address, total_amount, "
				+ "payment_method, payment_status, created_at "
				+ "FROM orders WHERE user_id = ? ORDER BY created_at DESC";

		List<Order> list = new ArrayList<>();

		try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Order o = new Order();
					o.setId(rs.getInt("id"));
					o.setUserId((Integer) rs.getObject("user_id"));
					o.setFullname(rs.getString("fullname"));
					o.setPhone(rs.getString("phone"));
					o.setAddress(rs.getString("address"));
					o.setTotalAmount(rs.getDouble("total_amount"));
					o.setPaymentMethod(rs.getString("payment_method"));
					o.setPaymentStatus(rs.getString("payment_status"));
					// nếu Order có field createdAt thì set thêm
					list.add(o);
				}
			}
		}
		return list;
	}

//lấy ALL đơn cho admin
	public List<Order> findAll() throws SQLException {
		String sql = "SELECT id, user_id, fullname, phone, address, total_amount, "
				+ "payment_method, payment_status, created_at " + "FROM orders ORDER BY created_at DESC";

		List<Order> list = new ArrayList<>();

		try (Connection conn = ConnectDB.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Order o = new Order();
				o.setId(rs.getInt("id"));
				o.setUserId((Integer) rs.getObject("user_id"));
				o.setFullname(rs.getString("fullname"));
				o.setPhone(rs.getString("phone"));
				o.setAddress(rs.getString("address"));
				o.setTotalAmount(rs.getDouble("total_amount"));
				o.setPaymentMethod(rs.getString("payment_method"));
				o.setPaymentStatus(rs.getString("payment_status"));
				list.add(o);
			}
		}
		return list;
	}

//lấy 1 đơn + item (nếu muốn trang chi tiết đơn)
	public Order findByIdWithItems(int orderId) throws SQLException {
		String orderSql = "SELECT id, user_id, fullname, phone, address, total_amount, "
				+ "payment_method, payment_status, created_at " + "FROM orders WHERE id = ?";
		String itemSql = "SELECT id, product_id, product_name, unit_price, quantity, line_total "
				+ "FROM order_items WHERE order_id = ?";

		try (Connection conn = ConnectDB.getConnection()) {
			Order o = null;

			// lấy thông tin order
			try (PreparedStatement ps = conn.prepareStatement(orderSql)) {
				ps.setInt(1, orderId);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						o = new Order();
						o.setId(rs.getInt("id"));
						o.setUserId((Integer) rs.getObject("user_id"));
						o.setFullname(rs.getString("fullname"));
						o.setPhone(rs.getString("phone"));
						o.setAddress(rs.getString("address"));
						o.setTotalAmount(rs.getDouble("total_amount"));
						o.setPaymentMethod(rs.getString("payment_method"));
						o.setPaymentStatus(rs.getString("payment_status"));
					}
				}
			}

			if (o == null)
				return null;

			// lấy items
			List<OrderItem> items = new ArrayList<>();
			try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
				ps.setInt(1, orderId);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						OrderItem it = new OrderItem();
						it.setId(rs.getInt("id"));
						it.setOrderId(orderId);
						it.setProductId(rs.getInt("product_id"));
						it.setProductName(rs.getString("product_name"));
						it.setUnitPrice(rs.getDouble("unit_price"));
						it.setQuantity(rs.getInt("quantity"));
						it.setLineTotal(rs.getDouble("line_total"));
						items.add(it);
					}
				}
			}
			o.setItems(items);
			return o;
		}
	}

	public List<Order> getAllOrders() throws SQLException {
		String sql = "SELECT * FROM orders ORDER BY id DESC";
		List<Order> list = new ArrayList<>();

		try (Connection conn = ConnectDB.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Order o = new Order();
				o.setId(rs.getInt("id"));
				o.setUserId(rs.getInt("user_id"));
				o.setFullname(rs.getString("fullname"));
				o.setPhone(rs.getString("phone"));
				o.setAddress(rs.getString("address"));
				o.setTotalAmount(rs.getDouble("total_amount"));
				o.setPaymentMethod(rs.getString("payment_method"));
				o.setPaymentStatus(rs.getString("payment_status"));

				list.add(o);
			}
		}
		return list;
	}

	public Order findById(int id) throws Exception {
		String sql = "SELECT * FROM orders WHERE id = ?";
		try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Order o = new Order();
				o.setId(rs.getInt("id"));
				o.setUserId(rs.getInt("user_id"));
				o.setFullname(rs.getString("fullname"));
				o.setPhone(rs.getString("phone"));
				o.setAddress(rs.getString("address"));
				o.setTotalAmount(rs.getDouble("total_amount"));
				o.setPaymentMethod(rs.getString("payment_method"));
				o.setPaymentStatus(rs.getString("payment_status"));
				return o;
			}
			return null;
		}
	}

	public List<OrderItem> getOrderItems(int orderId) throws Exception {
		String sql = "SELECT id, order_id, product_id, product_name, " + "       unit_price, quantity, line_total "
				+ "FROM order_items " + "WHERE order_id = ?";

		List<OrderItem> list = new ArrayList<>();

		try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OrderItem it = new OrderItem();

				it.setId(rs.getInt("id"));
				it.setOrderId(rs.getInt("order_id"));
				it.setProductId(rs.getInt("product_id"));
				it.setProductName(rs.getString("product_name"));
				it.setUnitPrice(rs.getDouble("unit_price"));
				it.setQuantity(rs.getInt("quantity"));
				it.setLineTotal(rs.getDouble("line_total"));

				list.add(it);
			}
		}
		return list;
	}

	public Invoice findInvoiceByOrderId(int orderId) throws SQLException {

		String sql = "SELECT id, order_id, amount, method, status "
				+ "FROM invoice WHERE order_id = ? ORDER BY id DESC LIMIT 1";

		try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, orderId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Invoice inv = new Invoice();
					inv.setId(rs.getInt("id"));
					inv.setOrderId(rs.getInt("order_id"));
					inv.setAmount(rs.getDouble("amount"));
					inv.setMethod(rs.getString("method"));
					inv.setStatus(rs.getString("status"));
					return inv;
				}
			}
		}
		return null;
	}

}
