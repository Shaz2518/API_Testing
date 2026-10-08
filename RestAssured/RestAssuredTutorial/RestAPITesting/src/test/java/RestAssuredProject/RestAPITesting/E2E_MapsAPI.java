package RestAssuredProject.RestAPITesting;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import files.JSONCode;
import files.ReusableCode;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class E2E_MapsAPI {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		// Add Place
		String placeAdded = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(JSONCode.addPlaceJSON()).when()
				.post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Content-Type", "application/json;charset=UTF-8").extract().response().asString();

		// Get place ID
		JsonPath json = ReusableCode.rawToJson(placeAdded);
		String placeID = json.getString("place_id");
		System.out.println(placeID);

		// Update Place
		String newAddress = "South Bombay, Mumbai";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeID + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "")
				.when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		String addressJSON = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID).when()
				.get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath newJSON = ReusableCode.rawToJson(addressJSON);
		String actualAddress = newJSON.getString("address");
		System.out.println(actualAddress);

		// Compare Address if its changed
		Assert.assertEquals(newAddress, actualAddress);
	}

}
