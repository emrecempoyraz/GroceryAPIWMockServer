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

public class TestMelon {

    @Test(description = "get all grocery",priority = 1)
    public void getGroceryList () {
        Response response = GroceryAPI.get();
        assertThat(response.getStatusCode(),equalTo(200));
        assertThat(response.getBody().path("data.name[9]"),equalTo("melon"));
    }

    @Test(description = "get only Melon",priority = 2,dependsOnMethods = "getGroceryList")
    public void getOrange () {
        Response response = GroceryAPI.getProductByName(Routes.CUCUMBER);
        assertThat(response.getStatusCode(),equalTo(200));
        assertThat(response.getBody().path("data.name[9]"),equalTo("melon"));
        assertThat(response.getBody().path("data.price[9]"),equalTo(9));
    }

    @Test(description = "add more Melons to inventory",priority = 3)
    public void changeMelonPrice() {
        RequestData requestData = requestDataBuilder(IProducts.CUCUMBER_ID,IProducts.CUCUMBER,IProducts.CUCUMBER_PRICE,IProducts.CUCUMBER_STOCK);
        Response response = GroceryAPI.post(requestData);
        assertThat(response.getStatusCode(),equalTo(201));
        assertThat(response.getBody().path("message"),equalTo("updated successfuly"));
    }



    @Test(description = "try to add Melons with wrong name",priority = 4)
    public void dontAddWithWrongNameAndId () {
        RequestData requestData = requestDataBuilder(IProducts.ORANGE_ID, IProducts.APPLE,IProducts.MELON_PRICE,IProducts.MELON_STOCK);
        Response response = GroceryAPI.post(requestData);
        assertThat(response.getStatusCode(),equalTo(400));
        assertThat(response.getBody().path("message"),equalTo("bad request"));
    }
}
