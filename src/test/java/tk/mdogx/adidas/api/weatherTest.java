package tk.mdogx.adidas.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

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

        String weatherByCityNameURL = weatherEndpoint + prefixWeatherRequestByCityName + cityName + postfixRequest;
        JSONObject jsonResponse = new JSONObject(get(weatherByCityNameURL).asString());
        int temperature = jsonResponse.getJSONObject(mainObjectKey).getInt(temperatureKey);

        // check that the correct response is received
        Assert.assertEquals(jsonResponse.getString(cityNameKey), cityName, "City names in the request and response do not match");

        // check data
        Assert.assertTrue(temperature > -100 && temperature < 100, "The data is out of range");

        // log
        System.out.println("By city name: in " + cityName + " is " + temperature);
    }

    @Test
    public void getTemperatureByCityId() throws JSONException {

        String weatherByCityIdURL = weatherEndpoint + prefixWeatherRequestByCityId + cityId + postfixRequest;
        JSONObject jsonResponse = new JSONObject(get(weatherByCityIdURL).asString());
        int temperature = jsonResponse.getJSONObject(mainObjectKey).getInt(temperatureKey);

        // check that the correct response is received
        Assert.assertEquals(jsonResponse.getInt(cityIdKey), Integer.parseInt(cityId), "City id's in the request and response do not match");

        // check data
        Assert.assertTrue(temperature > -100 && temperature < 100, "The data is out of range");

        // log
        System.out.println("By city ID: in " + cityName + " is " + temperature);
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
