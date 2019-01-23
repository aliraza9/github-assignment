package com.example.cnexadmin01.github_sample_test.interfaces;

import com.example.cnexadmin01.github_sample_test.models.Followers;
import com.example.cnexadmin01.github_sample_test.models.ServerResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ali on 11/6/2018.
 */

public interface APIInterface {

    @GET("search/users")
    Call<ServerResponse> checkUser(
            @Query("q") String email);

    @GET("users/{user_name}/followers")
    Call<List<Followers>> getFollowers(
            @Path(value = "user_name", encoded = true) String username);
}
