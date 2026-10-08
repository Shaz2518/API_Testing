package RestAssuredProject.RestAPITesting;

import org.testng.Assert;

import files.JSONCode;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath json = new JsonPath(JSONCode.courseDetails());
		
		//Print all course count
		int courseCount = json.getInt("courses.size()");
		System.out.println("Course Count: " + courseCount);
		
		//Purchase Amount
		int purchaseAmount = json.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount: " + purchaseAmount);

		//Title of the first course
		String firstCourseTitle = json.getString("courses[0].title");
		System.out.println("First Course Title: " + firstCourseTitle);
		
		//Get title and price of all course
		for(int i=0; i<courseCount; i++)
		{
			String title = json.get("courses["+i+"].title");
			int coursePrice = json.getInt("courses["+i+"].price");
			System.out.println(title + ": " + coursePrice);
		}
		
		//Get copies sold by RPA
		for(int i=0; i<courseCount; i++)
		{
			String title = json.get("courses["+i+"].title");
			if(title.equalsIgnoreCase("RPA"))
			{
				int courseCopies = json.getInt("courses["+i+"].copies");
				System.out.println("Course Copies Sold: " + title + ": " + courseCopies);
			}
					
		}
		
		//Get all course prices and compare with purchase Amount
		int totalCoursePrice =0;
		for(int i=0; i<courseCount; i++)
		{
			int coursePrice = json.getInt("courses["+i+"].price");
			int courseCopies = json.getInt("courses["+i+"].copies");
			totalCoursePrice = totalCoursePrice + coursePrice *courseCopies;
					
		}
		System.out.println("Total Course Price: " + totalCoursePrice);
		Assert.assertEquals(totalCoursePrice, purchaseAmount);
	}

}
