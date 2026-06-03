package basic_Crud;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import files.JSON_Body;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class CRUD_API {

	public static void main(String[] args) {

		// Add API
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "  \"location\": {\r\n" + "    \"lat\": -38.383494,\r\n" + "    \"lng\": 33.427362\r\n"
						+ "  },\r\n" + "  \"accuracy\": 50,\r\n" + "  \"name\": \"Frontline house\",\r\n"
						+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
						+ "  \"address\": \"29, side layout, cohen 09\",\r\n" + "  \"types\": [\r\n"
						+ "    \"shoe park\",\r\n" + "    \"shop\"\r\n" + "  ],\r\n"
						+ "  \"website\": \"http://google.com\",\r\n" + "  \"language\": \"French-IN\"\r\n" + "}\r\n"
						+ "\r\n" + "")

				.when().post("/maps/api/place/add/json")

				.then().assertThat().statusCode(200);

		// Assert Check
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(JSON_Body.JSONBody())

				.when().post("/maps/api/place/add/json")

				.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		System.out.println(response);

		// Update API
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

		// Delete Address
		given().queryParam("key", "qaclickacademy").body("{\r\n" + " \"place_id\":\"" + pid + "\"\r\n" + "")

				.when().delete("/maps/api/place/delete/json").then().log().all().assertThat().statusCode(200)
				.body("status", equalTo("OK"));

	}

}
