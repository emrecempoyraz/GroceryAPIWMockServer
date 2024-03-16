package com.grocery.store.api.applicationApi;


import io.restassured.response.Response;
import static com.grocery.store.api.applicationApi.SpecBuilder.getRequestSpec;
import static com.grocery.store.api.applicationApi.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class RestResource {

     public static Response get() {
         return given(getRequestSpec())
                 .when()
                 .get()
                 .then()
                 .spec(getResponseSpec())
                 .extract()
                 .response();
     }

     public static Response post(String path, Object requestData) {
         return given(getRequestSpec())
                 .body(requestData)
                 .when()
                 .post(path)
                 .then()
                 .spec(getResponseSpec())
                 .extract()
                 .response();
     }

    public static Response getProduct(String path) {
        return given(getRequestSpec())
                .when()
                .get(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }
}
