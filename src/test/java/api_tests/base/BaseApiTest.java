package api_tests.base;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class BaseApiTest {

    @Test
    void firstApiTest()
    {

        String petReq = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"doggie\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        RestAssured.
                given()
                    .log().all()
                .when()
                    .contentType(ContentType.JSON)
                    .body(petReq)
                    .post("https://petstore.swagger.io/v2/pet")
                .then()
                    .statusCode(200)
                    .assertThat().body(matchesJsonSchemaInClasspath("json_validators/PetResJsonScheme.json"))
                    .log().all();
    }
}
