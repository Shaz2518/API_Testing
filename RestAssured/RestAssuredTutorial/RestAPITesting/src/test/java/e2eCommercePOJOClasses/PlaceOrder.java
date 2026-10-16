package e2eCommercePOJOClasses;

import java.util.List;

public class PlaceOrder {
	
	public List<String> getOrders() {
		return orders;
	}
	public void setOrders(List<String> orders) {
		this.orders = orders;
	}
	public List<String> getPlacproductOrderId() {
		return placproductOrderId;
	}
	public void setPlacproductOrderId(List<String> placproductOrderId) {
		this.placproductOrderId = placproductOrderId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	List<String> orders;
	List<String> placproductOrderId;
	String message;
	
	

}
