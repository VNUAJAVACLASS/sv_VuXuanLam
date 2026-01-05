package model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private long id;
    private Integer userId;
    private String fullname;
    private String address;
    private String phone;
    private double totalAmount;
    private String paymentMethod;
    private String paymentStatus;

    // KHỞI TẠO LIST ĐỂ TRÁNH NULL
    private List<OrderItem> items = new ArrayList<>();

    // --- GETTER & SETTER ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    // QUAN TRỌNG: TRẢ VỀ List, không bao giờ null
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items != null ? items : new ArrayList<>();
    }
}