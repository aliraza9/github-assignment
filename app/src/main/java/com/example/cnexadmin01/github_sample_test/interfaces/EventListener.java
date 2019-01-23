package com.example.cnexadmin01.github_sample_test.interfaces;

import com.example.cnexadmin01.github_sample_test.models.Followers;
import com.example.cnexadmin01.github_sample_test.models.ServerResponse;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by Ali on 11/6/2018.
 */

public interface EventListener {

    void success(ServerResponse response);

    void success(List<Followers> response);

    void failure(String msg);
}
