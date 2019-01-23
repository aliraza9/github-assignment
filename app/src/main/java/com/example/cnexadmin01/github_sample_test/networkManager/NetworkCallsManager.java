package com.example.cnexadmin01.github_sample_test.networkManager;

import android.support.annotation.NonNull;

import com.example.cnexadmin01.github_sample_test.interfaces.APIInterface;
import com.example.cnexadmin01.github_sample_test.interfaces.EventListener;
import com.example.cnexadmin01.github_sample_test.models.Followers;
import com.example.cnexadmin01.github_sample_test.models.ServerResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Ali on 11/6/2018
 */
public class NetworkCallsManager implements EventListener {

    private static NetworkCallsManager networkCallsManager;
    private EventListener eventListener;

    public static NetworkCallsManager getNetworkCallsManager() {
        if (networkCallsManager == null)
            networkCallsManager = new NetworkCallsManager();

        return networkCallsManager;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * @param email
     */
    public void searchUserOnGitHub(String email) {

        Retrofit retrofit = ApiClientRetrofit.getClient();
        APIInterface apiInterfaceRetrofit = retrofit.create(APIInterface.class);

        final Call<ServerResponse> response = apiInterfaceRetrofit.checkUser(email);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {

                if (response.body() != null) {
                    eventListener.success(response.body());
                } else if (response.errorBody() != null) {
                    eventListener.failure("Something went wrong !!!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                eventListener.failure(t.getMessage());
            }
        });
    }

    /**
     * @param username
     */
    public void getfollowers(String username) {

        Retrofit retrofit = ApiClientRetrofit.getClient();
        APIInterface apiInterfaceRetrofit = retrofit.create(APIInterface.class);

        final Call<List<Followers>> response = apiInterfaceRetrofit.getFollowers(username);

        response.enqueue(new Callback<List<Followers>>() {
            @Override
            public void onResponse(@NonNull Call<List<Followers>> call, @NonNull Response<List<Followers>> response) {

                if (response.body() != null) {
                    eventListener.success((List<Followers>) response.body());
                } else if (response.errorBody() != null) {
                    eventListener.failure("Something went wrong !!!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Followers>> call, @NonNull Throwable t) {
                eventListener.failure(t.getMessage());
            }
        });
    }

    @Override
    public void success(ServerResponse response) {

    }

    @Override
    public void success(List<Followers> response) {

    }

    @Override
    public void failure(String title) {

    }
}
