package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.seleniumScripts.SeleniumScripts;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FetchFitnessData extends BaseTest {
	SeleniumScripts selScript = new SeleniumScripts();
	String accessToken;

	@Test
	public void GetData() throws IOException {
		String authorizationCode = "";

		authorizationCode = selScript.getAuthorizationCodeForActivites();
		RequestSpecification res = RestAssured.given().when().contentType(ContentType.JSON);

		String payLoad = "https://www.strava.com/oauth/token?client_id=" + configProperties.getProperty("client_id")
				+ "&client_secret=" + configProperties.getProperty("client_secret") + "&code=" + authorizationCode
				+ "&grant_type=authorization_code";

		System.out.println("Successfully recieved authorization code: " + authorizationCode);
		System.out.println("Sending authorization code to post request to get access token");
		// get access token using authorization code as access token keeps changing
		// after every six hours
		Response responseForAccessToken = res.post(payLoad).then().extract().response();
		accessToken = responseForAccessToken.jsonPath().getString("access_token");

		// using the access token to get the fitness activites of a user
		System.out.println("Using the access token to get the fitness activites of a user");
		Response response = res.queryParam("access_token", accessToken).get("/api/v3/athlete/activities").then().log()
				.all().extract().response();

		Assert.assertEquals(response.statusCode(), 200);
		System.out.println("Successfully Fetched Activity Data for a strava user.!!!");

	}

}
