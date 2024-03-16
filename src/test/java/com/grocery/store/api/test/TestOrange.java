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

public class TestOrange {

    @Test(description = "get all grocery",priority = 1)
    public void getGroceryList () {
        Response response = GroceryAPI.get();
        assertThat(response.getStatusCode(),equalTo(200));
        assertThat(response.getBody().path("data.name[2]"),equalTo("orange"));
    }

    @Test(description = "get only Orange",priority = 2,dependsOnMethods = "getGroceryList")
    public void getOrange () {
        Response response = GroceryAPI.getProductByName(Routes.ORANGE);
        assertThat(response.getStatusCode(),equalTo(200));
        assertThat(response.getBody().path("data.name[2]"),equalTo("orange"));
        assertThat(response.getBody().path("data.price[2]"),equalTo(6));
    }

    @Test(description = "add more Oranges to inventory",priority = 3)
    public void changeOrangeStock() {
        RequestData requestData = requestDataBuilder(IProducts.ORANGE_ID,IProducts.ORANGE,IProducts.ORANGE_PRICE,IProducts.ORANGE_STOCK);
        Response response = GroceryAPI.post(requestData);
        assertThat(response.getStatusCode(),equalTo(201));
        assertThat(response.getBody().path("message"),equalTo("added successfuly"));
    }



    @Test(description = "try to add Oranges with wrong name",priority = 4)
    public void dontAddWithWrongName () {
        RequestData requestData = requestDataBuilder(IProducts.ORANGE_ID, IProducts.APPLE,IProducts.ORANGE_PRICE,IProducts.ORANGE_STOCK);
        Response response = GroceryAPI.post(requestData);
        assertThat(response.getStatusCode(),equalTo(400));
        assertThat(response.getBody().path("message"),equalTo("bad request"));
    }
}
