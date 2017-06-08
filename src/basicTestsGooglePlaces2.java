import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import resources.commonMethods;
import resources.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class basicTestsGooglePlaces2 {
    Properties properties = new Properties();
    @BeforeTest
    public void getData() throws IOException{
        FileInputStream fis = new
                FileInputStream("C:\\Users\\KFES\\Documents\\Projects\\DemoRestAssured\\src\\resources\\env.properties");
        properties.load(fis);
    }

    @Test
    public void getGooglePlace() {
        //BaseURL or Host
        RestAssured.baseURI = "https://maps.googleapis.com";

        String key = properties.getProperty("KEY");
        String resource = resources.getGetData();

        Response response = given().
                param("location","-33.8670522,151.1957362").
                param("radius","50").
                param("key",key).log().all().
                when().
                get(resource).
                then().assertThat().statusCode(200).and().contentType("application/json").and().
                body("results[0].name",equalTo("Pirrama Road")).and().
                body("results[0].place_id",equalTo("ChIJ4cWusjeuEmsRB6Ng3fFLvaA")).and().
                header("Server","pablo").log().all()
                .extract().response();

        printPlaceNames(response);
    }

    public void printPlaceNames(Response response){
        JsonPath jsonPath = commonMethods.rawToJson(response);
        int resultsSize = jsonPath.get("results.size()");

        for (int i =0;i<resultsSize;i++) {
            String name = jsonPath.get(String.format("results[%s].name",i));
            System.out.println(name);
        }
    }
}
