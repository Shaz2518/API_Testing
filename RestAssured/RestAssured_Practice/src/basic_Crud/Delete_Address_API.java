package basic_Crud;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import files.JSON_Body;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Delete_Address_API {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(JSON_Body.JSONBody())

				.when().post("/maps/api/place/add/json")

				.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		// This will parse JSON and get place id
				JsonPath js = new JsonPath(response);
				String pid = js.getString("place_id");
				System.out.println("PlaceID = " + pid);

				// Delete Address
				given().queryParam("key", "qaclickacademy")
						.body("{\r\n"
								+ " \"place_id\":\""+pid+"\"\r\n"
								+ "")

						.when().delete("/maps/api/place/delete/json").then().log().all().
						assertThat().statusCode(200).body("status", equalTo("OK"));
						
				
	}

}
