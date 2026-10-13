package oAuth_GrantAccess;

import static io.restassured.RestAssured.given;

import java.util.List;

import io.restassured.path.json.JsonPath;
import pojoClasses.Courses;
import pojoClasses.GetCourseDetails;
import pojoClasses.webAutomation;

public class GetCourseDetails_POJO {

	public static void main(String[] args) {

		// Generate Access Token
		String tokenResponse = given().log().all()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").when()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").then().log().all().extract()
				.response().asString();

		// Get Access Token
		JsonPath json = new JsonPath(tokenResponse);
		String accessToken = json.getString("access_token");
		System.out.println(accessToken);

		// Get Course Details
		GetCourseDetails courseDetails = given().log().all().queryParam("access_token", accessToken)
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").then().log().all().extract().response()
				.as(GetCourseDetails.class);

		String linkedURL = courseDetails.getLinkedIn();
		System.out.println(linkedURL);

		String instructorName = courseDetails.getInstructor();
		System.out.println(instructorName);
		
		System.out.println("=====================================");

		// Get SOAP API Call
		List<webAutomation> webCourse = courseDetails.getCourses().getWebAutomation();
		for (int i = 0; i < webCourse.size(); i++)
			if (webCourse.get(i).getCourseTitle().contains("Selenium")) {
				String courseTitle = webCourse.get(i).getCourseTitle();
				String coursePrice = webCourse.get(i).getPrice();
				System.out.println(courseTitle + "=" + coursePrice);
			}
		
		System.out.println("=========================================================");

		// Get All course of WebAutomation
		for (int i = 0; i < webCourse.size(); i++) {
			String courseTitle = webCourse.get(i).getCourseTitle();
			String coursePrice = webCourse.get(i).getPrice();
			System.out.println(courseTitle + "=" + coursePrice);
		}
	}

}
