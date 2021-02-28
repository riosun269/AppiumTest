package api.test;

import api.BaseRest;
import api.utils.TestConstant;
import io.restassured.response.Response;

public abstract class BaseSetup extends BaseRest {
    protected String endpoint = TestConstant.URL;
    protected Response response;
}
