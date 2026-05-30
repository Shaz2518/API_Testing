package basic_Crud;

import files.JSON_Body;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Assertion_Check {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
				
			String response = 	given().log().all()				
					.queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body(JSON_Body.JSONBody())
				
				.when().post("/maps/api/place/add/json")
				
				.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
			
			System.out.println(response);
			
	}

}
