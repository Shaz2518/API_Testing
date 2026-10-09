package files;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class ParametrizedJson {

	@Test(dataProvider = "books")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";

		String bookAddResp = given().log().all().header("Content-Type", "application/json")
				.body(JSONCode.addBookJson(isbn, aisle)).when().post("Library/Addbook.php").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();

		JsonPath id = ReusableCode.rawToJson(bookAddResp);
		String bookID = id.getString("ID");
		System.out.println(bookID);

		// Delete Book
		given().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"ID\": \"" + bookID + "\"\r\n" + "}")

				.when().post("Library/DeleteBook.php").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("book is successfully deleted"));

	}

	@DataProvider(name = "books")
	public Object[][] getBookData() {
		Object[][] bookDetails = new Object[][] { { "john", "123" }, { "steve", "980" }, { "bob", "450" } };
		return bookDetails;
	}
}
