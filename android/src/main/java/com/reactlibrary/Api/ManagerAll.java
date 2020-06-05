package com.reactlibrary.Api;

import com.reactlibrary.Models.Cosmos;
import com.reactlibrary.Models.Version;

import retrofit2.Call;

public class ManagerAll extends BaseManager {
    private static ManagerAll ourManagerAll = new ManagerAll();

    public static synchronized ManagerAll getInstance() {
        return ourManagerAll;
    }

    public Call<Cosmos> getCosmosInfo() {
        Call<Cosmos> call = getRestApiClient().getCosmos();
        return call;
    }

    public Call<Version> getVersionInfo(String application_id,String package_string){
        Call<Version> call = getRestApiClient().getLastVersion(application_id,package_string);
        return call;
    }

}
