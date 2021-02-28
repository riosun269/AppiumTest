package api;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.Collections;
import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class BaseRest {

    public Response postRequest(String url, Map<String,String> headers, ContentType contentType, String body) {
        ExtractableResponse<Response> response = RestAssured.given()
                .headers(headers)
                .contentType(contentType)
                .body(body)
                .when()
                .post(url)
                .then()
                .log().ifError()
                .extract();
        return response.response();
    }

    public Response getRequestWithParam(String url, Map<String, String> headersToken) {
        ExtractableResponse<Response> response = RestAssured.given()
                .headers(headersToken)
                .when()
                .get(url)
                .then()
                .log().ifError()
                .extract();
        return response.response();
    }
}
