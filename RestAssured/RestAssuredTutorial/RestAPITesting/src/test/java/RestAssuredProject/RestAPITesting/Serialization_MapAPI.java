package RestAssuredProject.RestAPITesting;

import io.restassured.RestAssured;
import pojoClasses.POJO_Location;
import pojoClasses.POJO_MapAPI;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;
public class Serialization_MapAPI {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		POJO_MapAPI map = new POJO_MapAPI();
		
		POJO_Location loc = new POJO_Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		map.setLocation(loc);
		
		map.setAccuracy(50);
		map.setName("House of Angels");
		map.setPhone_number("(+91) 983 893 3937");
		map.setAddress("Mumbai,Maharashtra,India");
		
		List<String> arrayType = new ArrayList<String>();
		arrayType.add("Mobile Phone");
		arrayType.add("Booth Station");
		map.setTypes(arrayType);
		
		map.setWebsite("https://rahulshettyacademy.com/");
		map.setLanguage("Urdu-IN");
		
		
		
		String locationResponse = given().queryParam("key", "qaclick123")
		.body(map).when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println(locationResponse);
		
	}

}
