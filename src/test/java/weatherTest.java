import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.get;


public class weatherTest {

    private final String APIKEY = "485674da4e171e6b75889bc851e4d3c5";

    @Test
    public void getRequestGetWeather() throws JSONException {
        Response response = get("http://api.openweathermap.org/data/2.5/weather?q=London&units=metric&APPID=" + APIKEY);
        System.out.println(response.asString());
        JSONObject jsonResponse = new JSONObject(response.asString());
        int temperature = jsonResponse.getJSONObject("main").getInt("temp");
        System.out.println(temperature);
    }
}
