package com.reactlibrary.Api;

import com.reactlibrary.Models.Cosmos;
import com.reactlibrary.Models.Version;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

    @GET("api/v1/cosmos")
    Call<Cosmos> getCosmos();

    @GET("api/v1/mobile/version/{application_id}/{package_string}")
    Call<Version> getLastVersion(@Path("application_id") String application_id,
                                 @Path("package_string") String package_string);


}
