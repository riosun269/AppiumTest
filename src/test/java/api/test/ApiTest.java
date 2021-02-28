package api.test;

import api.utils.ApiUtils;
import api.utils.TestConstant;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static api.utils.TestConstant.*;

public class ApiTest extends BaseSetup {


    public void login() {
        JSONObject requestBody = new JSONObject();
        requestBody.put(TestConstant.PHONE_KEY, TestConstant.PHONE_NUMBER);
        response = postRequest(LOGIN_ENDPOINT, HEADERS, ContentType.JSON, requestBody.toJSONString());
        ApiUtils.validateCode(response, HTTP_CODE_SUCCESS);
    }

}
