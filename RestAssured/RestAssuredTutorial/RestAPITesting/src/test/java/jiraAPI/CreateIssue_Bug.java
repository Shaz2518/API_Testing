package jiraAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
public class CreateIssue_Bug {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://shaziyashaikh2518-1791730051584.atlassian.net";
		String createIssue = given().log().all()
		.header("Content-Type","application/json")
		.header("Authorization","Basic c2hheml5YXNoYWlraDI1MThAZ21haWwuY29tOkFUQVRUM3hGZkdGMDBCeG9URV9DaEVTWjhON01fTUlBOFFza1RoYkNlZklaRVFZaEJWN1ExdThMY2JBT0VNSFZ5MTlVNU56eFNfazlONVp1Q1VNdmF3WFNRTXMwSVBQU0lZM2ZYOUltZ3JEZmxMTk5CLUZpb1h4ODRZUktZVEN4Zk9vSC1CRmdycWMzclRfZi1HcEVvdEdoeTUtU1ZUSm5tUFVqMjhKTFF5bDB4R1lLZnY2V0xIQT0yMzI4NkNCMQ==")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"SCRUM\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"Pages are not working\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}\r\n"
				+ "")
		.when().post("rest/api/3/issue")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath json = new JsonPath(createIssue);
		String issueID = json.getString("id");
		System.out.println(issueID);
		
		//Add attachment
		String attachmentAdded = given().log().all().pathParam("key", issueID)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic c2hheml5YXNoYWlraDI1MThAZ21haWwuY29tOkFUQVRUM3hGZkdGMDBCeG9URV9DaEVTWjhON01fTUlBOFFza1RoYkNlZklaRVFZaEJWN1ExdThMY2JBT0VNSFZ5MTlVNU56eFNfazlONVp1Q1VNdmF3WFNRTXMwSVBQU0lZM2ZYOUltZ3JEZmxMTk5CLUZpb1h4ODRZUktZVEN4Zk9vSC1CRmdycWMzclRfZi1HcEVvdEdoeTUtU1ZUSm5tUFVqMjhKTFF5bDB4R1lLZnY2V0xIQT0yMzI4NkNCMQ==")
		.multiPart("file",new File("C:\\Users\\localadminuser\\Downloads\\musa-ortac-4jAhqZRzsVI-unsplash.jpg"))
		.when().post("rest/api/3/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath attach = new JsonPath(attachmentAdded);
		String fileName = attach.getString("filename");
		System.out.println("File Name:" + fileName);
				
	}

}
