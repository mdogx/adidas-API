package tk.mdogx.adidas.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.get;


public class weatherTest {

    private final String API_KEY = "485674da4e171e6b75889bc851e4d3c5";
    private final String weatherEndpoint = "http://api.openweathermap.org/data/2.5/weather";

    private final String cityName = "London";
    private final String cityId = "2643743";

    private final String prefixWeatherRequestByCityName = "?q=";
    private final String prefixWeatherRequestByCityId = "?id=";
    private final String postfixRequest = "&units=metric&APPID=" + API_KEY;

    private final String mainObjectKey = "main";
    private final String temperatureKey = "temp";
    private final String cityIdKey = "id";
    private final String cityNameKey = "name";

    @Test
    public void getTemperatureByCityName() throws JSONException {

        // get weather by city name
        Response responseByName = get(weatherEndpoint + prefixWeatherRequestByCityName + cityName + postfixRequest);
        JSONObject jsonResponseByName = new JSONObject(responseByName.asString());
        int temperatureByName = jsonResponseByName.getJSONObject(mainObjectKey).getInt("temp");

        // check that the correct response is received
        Assert.assertEquals(jsonResponseByName.getString(cityNameKey), cityName, "The names of cities in the request and response do not match");

        // check data
        Assert.assertTrue(temperatureByName > -100 && temperatureByName < 100, "The data from different endpoints do not match");

        // log
        System.out.println("By city name: in " + cityName + " is " + temperatureByName);
    }

    @Test
    public void getTemperatureByCityId() throws JSONException {

        Response responseById = get(weatherEndpoint + prefixWeatherRequestByCityId + cityId + postfixRequest);
        JSONObject jsonResponseById = new JSONObject(responseById.asString());
        int temperatureById = jsonResponseById.getJSONObject(mainObjectKey).getInt(temperatureKey);

        // check that the correct response is received
        Assert.assertEquals(jsonResponseById.getInt(cityIdKey), Integer.parseInt(cityId), "The id's of cities in the request and response do not match");

        // check data
        Assert.assertTrue(temperatureById > -100 && temperatureById < 100, "The data from different endpoints do not match");

        // log
        System.out.println("By city ID: in " + cityName + " is " + temperatureById);
    }

    @Test
    public void compareWeatherFromDifferentEndpoints() throws JSONException {

        // get weather by city name
        int temperatureByName = new JSONObject(get(weatherEndpoint + prefixWeatherRequestByCityName + cityName + postfixRequest).asString())
                .getJSONObject(mainObjectKey)
                .getInt(temperatureKey);

        // get weather by city id
        int temperatureById = new JSONObject(get(weatherEndpoint + prefixWeatherRequestByCityId + cityId + postfixRequest).asString())
                .getJSONObject(mainObjectKey)
                .getInt(temperatureKey);

        // check that different endpoint give the same data
        Assert.assertEquals(temperatureById, temperatureByName, "The data from different endpoints do not match");
    }
}
