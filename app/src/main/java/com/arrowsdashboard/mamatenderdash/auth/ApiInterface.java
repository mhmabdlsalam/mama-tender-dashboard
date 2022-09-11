package com.arrowsdashboard.mamatenderdash.auth;


import com.arrowsdashboard.mamatenderdash.auth.Models.Request.Post;
import com.arrowsdashboard.mamatenderdash.auth.Models.Responce.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    String SERVER_KEY = "AAAAcbvEiWQ:APA91bEGKlgVtduNTzTGWW23nDgyhjvzFI17F-cvHwm4ug8cN22W5ikxZmmi4jmZglKAMDinF7Hiof77aleIx4EUqqCkWyUjmrmO2CL2PaoWBNBznxgvUvLigWZb8T-mZy8CpkrXtS4Y";

    @Headers({"Content-Type: application/json",
            "Authorization:key=AAAAcbvEiWQ:APA91bEGKlgVtduNTzTGWW23nDgyhjvzFI17F-cvHwm4ug8cN22W5ikxZmmi4jmZglKAMDinF7Hiof77aleIx4EUqqCkWyUjmrmO2CL2PaoWBNBznxgvUvLigWZb8T-mZy8CpkrXtS4Y"})
    @POST("send")
    Call<Response> sendNotification(@Body Post notificationModel);
}
