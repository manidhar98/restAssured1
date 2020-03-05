import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Testing {
    public static String KEY = "Zw7vv1fapCIZgAJZKDeWL4CLUbg7SsYW";


    static public Response getJson(String endpoint){
            RestAssured.defaultParser = Parser.JSON;

            return
                    given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                            when().get(endpoint).
                            then().contentType(ContentType.JSON).extract().response();
        }

    @Test
    public void before(){
        String URL = "http://www.mapquestapi.com/geocoding/v1/address?key="+KEY+"&location=1600+Pennsylvania+Ave+NW,Washington,DC,20500";
        Response response = getJson(URL);
        JsonPath jsonPath = response.jsonPath();
        String lat = jsonPath.getString("results[0].locations[0].latLng.lat");
        String Long = jsonPath.getString("results[0].locations[0].latLng.lng");
        String URL2 = "https://api.darksky.net/forecast/8ce891b3792f3f55f0ba960acf5db037/"+lat+","+Long;
        response =getJson(URL2);
        jsonPath = response.jsonPath();
        String weather = jsonPath.getString("daily.data[0].summary");
        System.out.println(weather);
    }
}
