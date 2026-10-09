package files;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DataFromExternalJson {

	@Test
	public void addNewPlace() throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		// Add Place
		String placeAdded = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes
						(Paths.get("C:\\Users\\localadminuser\\Desktop\\API_Practice\\RestAssured\\PlaceData.json")))).when()
				.post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Content-Type", "application/json;charset=UTF-8").extract().response().asString();

		// Get place ID
		JsonPath json = ReusableCode.rawToJson(placeAdded);
		String placeID = json.getString("place_id");
		System.out.println(placeID);

		// Delete Book
		given().log().all().header("Content-Type", "application/json")
				.body("{\r\n"
						+ "    \r\n"
						+ "    \"place_id\": \""+placeID+"\"\r\n"
						+ "    \r\n"
						+ "}")

				.when().post("maps/api/place/delete/json").then().log().all().assertThat().statusCode(200)
				.body("status", equalTo("OK"));
	}

}
