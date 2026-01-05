package model;

public class OrderItem {
  private Integer id;
  private Integer orderId;
  private Integer productId;
  private String  productName;
  private double  unitPrice;
  private int     quantity;
  private double  lineTotal;
  public Integer getId() {
	return id;
  }
  public void setId(Integer id) {
	this.id = id;
  }
  public Integer getOrderId() {
	return orderId;
  }
  public void setOrderId(Integer orderId) {
	this.orderId = orderId;
  }
  public Integer getProductId() {
	return productId;
  }
  public void setProductId(Integer productId) {
	this.productId = productId;
  }
  public String getProductName() {
	return productName;
  }
  public void setProductName(String productName) {
	this.productName = productName;
  }
  public double getUnitPrice() {
	return unitPrice;
  }
  public void setUnitPrice(double unitPrice) {
	this.unitPrice = unitPrice;
  }
  public int getQuantity() {
	return quantity;
  }
  public void setQuantity(int quantity) {
	this.quantity = quantity;
  }
  public double getLineTotal() {
	return lineTotal;
  }
  public void setLineTotal(double lineTotal) {
	this.lineTotal = lineTotal;
  }


}
