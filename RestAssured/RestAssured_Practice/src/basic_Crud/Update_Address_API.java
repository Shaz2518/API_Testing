package basic_Crud;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import files.JSON_Body;

public class Update_Address_API {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(JSON_Body.JSONBody())

				.when().post("/maps/api/place/add/json")

				.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		// System.out.println(response);

		// This will parse JSON and get place id
		JsonPath js = new JsonPath(response);
		String pid = js.getString("place_id");
		System.out.println("PlaceID = " + pid);

		// Update Address
		String newAddress = "Mumbai,Maharashtra,India";
		given().queryParam("key", "qaclickacademy")
				.body("{\r\n" + "\"place_id\":\"" + pid + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("/maps/api/place/update/json").then().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// Get place address
		String getDetails = given().queryParam("key", "qaclick123").queryParam("place_id", pid).when()
				.get("/maps/api/place/get/json").then().assertThat().statusCode(200).extract().response().asString();

		System.out.println(getDetails);
		JsonPath getAddress = new JsonPath(getDetails);
		String actualAddress = getAddress.getString("address");
		System.out.println("Address:  " + actualAddress);

	}

}
