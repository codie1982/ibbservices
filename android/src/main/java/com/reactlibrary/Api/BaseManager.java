package com.reactlibrary.Api;

public class BaseManager {

    protected RestApi getRestApiClient(){
        ReastApiClient reastApiClient = new ReastApiClient(BaseUrl.COSMOSURL);
        return  reastApiClient.getRestApi();
    }


}
