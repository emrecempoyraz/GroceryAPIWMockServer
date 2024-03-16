package com.grocery.store.api.test;

import com.grocery.store.api.applicationApi.GroceryAPI;
import com.grocery.store.api.applicationApi.Routes;
import com.grocery.store.api.pojo.RequestData;
import com.grocery.store.api.data.IProducts;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.grocery.store.api.test.TestApple.requestDataBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestCucumber {

    @Test(description = "get all grocery",priority = 1)
    public void getGroceryList () {
        Response response = GroceryAPI.get();
        assertThat(response.getStatusCode(),equalTo(200));
        assertThat(response.getBody().path("data.name[4]"),equalTo("cucumber"));
    }

    @Test(description = "get only Cucumber",priority = 2,dependsOnMethods = "getGroceryList")
    public void getOrange () {
        Response response = GroceryAPI.getProductByName(Routes.CUCUMBER);
        assertThat(response.getStatusCode(),equalTo(200));
        assertThat(response.getBody().path("data.name[4]"),equalTo("cucumber"));
        assertThat(response.getBody().path("data.price[4]"),equalTo(9));
    }

    @Test(description = "add more Cucumbers to inventory",priority = 3)
    public void changeCucumberPrice() {
        RequestData requestData = requestDataBuilder(IProducts.CUCUMBER_ID,IProducts.CUCUMBER,IProducts.CUCUMBER_PRICE,IProducts.CUCUMBER_STOCK);
        Response response = GroceryAPI.post(requestData);
        assertThat(response.getStatusCode(),equalTo(201));
        assertThat(response.getBody().path("message"),equalTo("updated successfuly"));
    }



    @Test(description = "try to add Cucumbers with wrong name/id",priority = 4)
    public void dontAddWithWrongNameAndId () {
        RequestData requestData = requestDataBuilder(IProducts.ORANGE_ID, IProducts.APPLE,IProducts.CUCUMBER_PRICE,IProducts.CUCUMBER_STOCK);
        Response response = GroceryAPI.post(requestData);
        assertThat(response.getStatusCode(),equalTo(400));
        assertThat(response.getBody().path("message"),equalTo("bad request"));
    }
}
