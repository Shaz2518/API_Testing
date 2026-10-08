package files;

import io.restassured.path.json.JsonPath;

public class ReusableCode {

	public static JsonPath rawToJson(String response)
	{
		JsonPath jsonValue = new JsonPath(response);
		return jsonValue;
	}
}
