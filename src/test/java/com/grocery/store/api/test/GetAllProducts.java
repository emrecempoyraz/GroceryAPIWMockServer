package com.grocery.store.api.test;

import com.grocery.store.api.applicationApi.GroceryAPI;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetAllProducts {

    @Test
    public void getAll () {
        Response response = GroceryAPI.get();
        assertThat(response.getStatusCode(),equalTo(200));
        assertThat(response.contentType(),equalTo("application/json; charset=utf-8"));
        assertThat(response.getBody().path("data.name[1]"),equalTo("grapes"));
    }
}
