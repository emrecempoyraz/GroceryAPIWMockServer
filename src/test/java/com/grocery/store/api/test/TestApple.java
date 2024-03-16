package com.grocery.store.api.test;

import com.grocery.store.api.applicationApi.GroceryAPI;
import com.grocery.store.api.applicationApi.Routes;
import com.grocery.store.api.pojo.RequestData;
import com.grocery.store.api.data.IProducts;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestApple extends Routes {

    @Test(description = "get all grocery",priority = 1)
    public void getGroceryList () {
        Response response = GroceryAPI.get();
        assertThat(response.getStatusCode(),equalTo(200));
        assertThat(response.getBody().path("data.name[0]"),equalTo("apple"));

    }

    @Test(description = "get only apple",priority = 2,dependsOnMethods = "getGroceryList")
    public void getApple () {
        Response response = GroceryAPI.getProductByName(Routes.APPLE);
        assertThat(response.getStatusCode(),equalTo(200));
        assertThat(response.getBody().path("data.id[0]"),equalTo(1));
        assertThat(response.getBody().path("data.stock[0]"),equalTo(100));
    }

    @Test(description = "add more apples to inventory",priority = 3,dependsOnMethods = "getApple")
    public void changeAppleStock() {
        RequestData requestData = requestDataBuilder(IProducts.APPLE_ID,IProducts.APPLE,IProducts.APPLE_PRICE,IProducts.APPLE_STOCK);
        Response response = GroceryAPI.post(requestData);
        assertThat(response.getStatusCode(),equalTo(201));
        assertThat(response.getBody().path("message"),equalTo("added successfuly"));
    }

    @Test(description = "try to add apples with wrong id",priority = 4)
    public void dontAddWithWrongID () {
        RequestData requestData = requestDataBuilder(3,IProducts.APPLE,IProducts.APPLE_PRICE,IProducts.APPLE_STOCK);
        Response response = GroceryAPI.post(requestData);
        assertThat(response.getStatusCode(),equalTo(400));
        assertThat(response.getBody().path("message"),equalTo("bad request id"));
    }


    public static RequestData requestDataBuilder (int id ,String name,int price,int stock){
        RequestData requestData = new RequestData();
        requestData.setId(id);
        requestData.setName(name);
        requestData.setPrice(price);
        requestData.setStock(stock);
        return requestData;
    }
}
