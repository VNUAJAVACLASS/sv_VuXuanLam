package model;

public class Invoice {
  private Integer id;
  private Integer orderId;
  private double  amount;
  private String  method;  
  private String  status;
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
  public double getAmount() {
	return amount;
  }
  public void setAmount(double amount) {
	this.amount = amount;
  }
  public String getMethod() {
	return method;
  }
  public void setMethod(String method) {
	this.method = method;
  }
  public String getStatus() {
	return status;
  }
  public void setStatus(String status) {
	this.status = status;
  }  

 
}
