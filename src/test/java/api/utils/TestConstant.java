package api.utils;

import java.util.HashMap;
import java.util.Map;

public class TestConstant {
    public static final String URL = "https://uat-golending-api.gobear.com/ph";
    public static final String CONTENT_TYPE = "";
    public static final Map<String,String> HEADERS = new HashMap<String, String>();
    public static final Integer HTTP_CODE_SUCCESS = 200;
    public static final String PHONE_NUMBER = "+639781255597";
    public static final String LOGIN_ENDPOINT = URL + "/api/v2/sessions";

    /** Key object */
    public static final String PHONE_KEY = "phoneNumber";

}
