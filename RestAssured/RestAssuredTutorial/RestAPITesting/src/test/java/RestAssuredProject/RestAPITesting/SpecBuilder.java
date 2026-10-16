package RestAssuredProject.RestAPITesting;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoClasses.POJO_Location;
import pojoClasses.POJO_MapAPI;
import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;

public class SpecBuilder {

	public static void main(String[] args) {

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

		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		ResponseSpecification respSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		RequestSpecification locationResponse = given().spec(reqSpec).body(map);

		String responseString = locationResponse.when().post("/maps/api/place/add/json").then().spec(respSpec).extract()
				.response().asString();
		System.out.println(responseString);

	}

}
