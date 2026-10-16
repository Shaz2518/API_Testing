package e2eECommerce;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;

import e2eCommercePOJOClasses.LoginAPI;
import e2eCommercePOJOClasses.LoginToken;

public class E2E_CommerceAPI {

	public static void main(String[] args) {

		// Login and Token Generation
		RequestSpecification loginReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		LoginAPI login = new LoginAPI();
		login.setUserEmail("demonslayer@gmail.com");
		login.setUserPassword("DemonSlayer123");

		RequestSpecification loginRequest = given().log().all().spec(loginReq).body(login);
		LoginToken loginResp = loginRequest.when().post("api/ecom/auth/login").then().log().all().extract().response()
				.as(LoginToken.class);
		String prodToken = loginResp.getToken();
		String UserID = loginResp.getUserId();
		System.out.println(UserID + "\n" + prodToken);

		// Create a Product
		RequestSpecification prodBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", prodToken).build();
		RequestSpecification prodReq = given().log().all().spec(prodBaseReq).param("productName", "Jeans")
				.param("productAddedBy", "UserID").param("productCategory", "Fashion")
				.param("productSubCategory", "Pants").param("productPrice", "799")
				.param("productDescription", "Blue Denim Jeans").param("productFor", "Women").multiPart("productImage",
						new File("C:\\Users\\localadminuser\\Downloads\\510uTHyDqGL._AC_UF1000,1000_QL80_.jpg"));
		String prodResp = prodReq.when().post("api/ecom/product/add-product").then().log().all().extract().response()
				.asString();

		JsonPath prodDetails = new JsonPath(prodResp);
		String productID = prodDetails.get("productId");
		System.out.println("ProductID: " + productID);
	}

}
