package oAuth_GrantAccess;
import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;
public class OAuth_ClientCredentials {

	public static void main(String[] args) {
		
		//Generate Access Token
		String tokenResponse = given().log().all()
		.formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParams("grant_type", "client_credentials").formParams("scope", "trust")
		.when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.then().log().all().extract().response().asString();
		
		//Get Access Token
		JsonPath json = new JsonPath(tokenResponse);
		String accessToken = json.getString("access_token");
		System.out.println(accessToken);
		
		//Get Course Details
		String courseDetails = given().log().all().queryParam("access_token", accessToken)
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.then().log().all().extract().response().asString();
		System.out.println("========CourseDetails===============" + "\n" + courseDetails);
	}

}
