package e2eECommerce;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import e2eCommercePOJOClasses.LoginAPI;
import e2eCommercePOJOClasses.LoginToken;
import e2eCommercePOJOClasses.OrderDetails;
import e2eCommercePOJOClasses.Orders;
import e2eCommercePOJOClasses.PlaceOrder;

public class E2E_CommerceAPI_Copy {

	public static void main(String[] args) {

		// Login and Token Generation
		RequestSpecification loginReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		LoginAPI login = new LoginAPI();
		login.setUserEmail("demonslayer@gmail.com");
		login.setUserPassword("DemonSlayer123");

		RequestSpecification loginRequest = given().spec(loginReq).body(login);
		LoginToken loginResp = loginRequest.when().post("api/ecom/auth/login").then().extract().response()
				.as(LoginToken.class);
		String prodToken = loginResp.getToken();
		String UserID = loginResp.getUserId();
		System.out.println("UserID: " + UserID + "\n" + "ProdToken: " +  prodToken);

		// Create a Product
		RequestSpecification prodBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", prodToken).build();
		RequestSpecification prodReq = given().spec(prodBaseReq).param("productName", "Jeans")
				.param("productAddedBy", UserID).param("productCategory", "Fashion")
				.param("productSubCategory", "Pants").param("productPrice", "799")
				.param("productDescription", "Blue Denim Jeans").param("productFor", "Women").multiPart("productImage",
						new File("C:\\Users\\localadminuser\\Downloads\\510uTHyDqGL._AC_UF1000,1000_QL80_.jpg"));
		String prodResp = prodReq.when().post("api/ecom/product/add-product").then().extract().response()
				.asString();

		JsonPath prodDetails = new JsonPath(prodResp);
		String productID = prodDetails.get("productId");
		System.out.println("ProductID: " + productID);

		// Add Product to Cart
		RequestSpecification cartBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", prodToken).setContentType(ContentType.JSON).build();

		OrderDetails orderDetail = new OrderDetails();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productID);

		List<OrderDetails> orderValue = new ArrayList<OrderDetails>();
		orderValue.add(orderDetail);
		Orders order = new Orders();
		order.setOrders(orderValue);

		RequestSpecification placeOrder = given().spec(cartBaseReq).body(order);
		PlaceOrder placeOrderResp = placeOrder.when().post("api/ecom/order/create-order").then().extract().response()
				.as(PlaceOrder.class);
		List<PlaceOrder> placeOrderDetails = new ArrayList<PlaceOrder>();
		placeOrderDetails.add(placeOrderResp);
		String orderID = placeOrderResp.getOrders().get(0);
		System.out.println("OrderID: " + orderID);

		// View Product
		RequestSpecification viewProdBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", prodToken).setContentType(ContentType.JSON).build();
		RequestSpecification viewProdReq = given().spec(viewProdBaseReq).queryParam("id", orderID);
		String viewOrderDetails = viewProdReq.when().get("api/ecom/order/get-orders-details").then()
				.extract().response().asString();
		JsonPath viewOrder = new JsonPath(viewOrderDetails);
		String viewOrderMsg = viewOrder.get("message");
		System.out.println("View Order Message: " + viewOrderMsg);
		
		// Delete Product
		RequestSpecification deleteBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", prodToken).setContentType(ContentType.JSON).build();
		RequestSpecification deleteProduct = given().spec(deleteBaseReq).pathParam("productId", productID);
		String deleteResp = deleteProduct.when().delete("api/ecom/product/delete-product/{productId}").then().extract().response().asString();

		JsonPath deleteDetails = new JsonPath(deleteResp);
		String deleteMsg = deleteDetails.get("message");
		System.out.println("Product Delete Message: " + deleteMsg);
		Assert.assertEquals(deleteMsg, "Product Deleted Successfully");

		// Delete Order
		RequestSpecification deleteOrderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", prodToken).setContentType(ContentType.JSON).build();
		RequestSpecification deleteOrder = given().spec(deleteOrderReq).pathParam("orderID", orderID);
		String deleteOrderResp = deleteOrder.when().delete("api/ecom/order/delete-order/{orderID}").then()
				.extract().response().asString();
		JsonPath deleteOrderDetails = new JsonPath(deleteOrderResp);
		String deleteOrderMsg = deleteOrderDetails.get("message");
		System.out.println("Order Delete Message: " + deleteOrderMsg);
	}

}
