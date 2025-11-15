package model;

import java.util.List;

public class Order {
  private Integer id;
  private Integer userId;
  private String fullname, phone, address;
  private double totalAmount;
  private String paymentMethod, paymentStatus;
  private List<OrderItem> items;
  public Integer getId() {
	return id;
  }
  public void setId(Integer id) {
	this.id = id;
  }
  public Integer getUserId() {
	return userId;
  }
  public void setUserId(Integer userId) {
	this.userId = userId;
  }
  public String getFullname() {
	return fullname;
  }
  public void setFullname(String fullname) {
	this.fullname = fullname;
  }
  public String getPhone() {
	return phone;
  }
  public void setPhone(String phone) {
	this.phone = phone;
  }
  public String getAddress() {
	return address;
  }
  public void setAddress(String address) {
	this.address = address;
  }
  public double getTotalAmount() {
	return totalAmount;
  }
  public void setTotalAmount(double totalAmount) {
	this.totalAmount = totalAmount;
  }
  public String getPaymentMethod() {
	return paymentMethod;
  }
  public void setPaymentMethod(String paymentMethod) {
	this.paymentMethod = paymentMethod;
  }
  public String getPaymentStatus() {
	return paymentStatus;
  }
  public void setPaymentStatus(String paymentStatus) {
	this.paymentStatus = paymentStatus;
  }
  public List<OrderItem> getItems() {
	return items;
  }
  public void setItems(List<OrderItem> items) {
	this.items = items;
  }


}
