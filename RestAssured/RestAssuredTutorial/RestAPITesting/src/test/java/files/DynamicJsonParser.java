package files;

import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJsonParser {
	
	@Test
	public void addBook()
	{
		RestAssured.baseURI = "http://216.10.245.166";
		
		String bookAddResp = given().log().all().header("Content-Type","application/json")
		.body(JSONCode.addBookJson("pop","567"))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath id = ReusableCode.rawToJson(bookAddResp);
		String bookID = id.getString("ID");
		System.out.println(bookID);
		
		//Delete Book
		given().log().all().header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"ID\": \""+bookID+"\"\r\n"
				+ "}")
		
		.when().post("Library/DeleteBook.php").then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("book is successfully deleted"));
		
	
	}

}
