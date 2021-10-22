package com.ashish.kawaspace.network;

import com.ashish.kawaspace.model.UserInfoModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiInterface {


    @GET("/api/?inc=gender,name,nat,location,picture,email&results=20")
    Call<UserInfoModel> getProfile();

}
