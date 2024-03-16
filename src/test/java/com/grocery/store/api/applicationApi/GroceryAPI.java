package com.grocery.store.api.applicationApi;

import com.grocery.store.api.pojo.RequestData;
import io.restassured.response.Response;

public class GroceryAPI extends Routes {

    public static Response get() {
        return RestResource.get();
    }

    public static Response getProductByName (String productName){
        return RestResource.getProduct(productName);
    }

    public static Response post(RequestData requestData) {
        return RestResource.post(POST_PATH,requestData);
    }
}
