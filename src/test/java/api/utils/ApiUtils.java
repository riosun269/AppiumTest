package api.utils;

import io.restassured.response.Response;
import org.testng.Assert;

public class ApiUtils {
    public static void validateCode (Response response, int expectedCode) {
        Assert.assertEquals(response.getStatusCode(), expectedCode
                , String.format("Actual code is %d, expected code is %d", response.getStatusCode(), expectedCode));
    }
}
