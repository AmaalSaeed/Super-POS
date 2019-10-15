package com.smartapps.super_pos.API;

import com.smartapps.super_pos.Items.Feed;
import com.smartapps.super_pos.Items.Post;
import com.smartapps.super_pos.Items.Tables.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPIs {

    @FormUrlEncoded
    @POST("market/orders")
    Call<Feed> getFeed(@Field("order_type") String order_type);

    @GET("posts")
    Call<List<Post>> getPosts();

    @FormUrlEncoded
    @POST("customers/orders/market/status")
    Call<Feed> changeOrderStatus(@Field("order_id") int order_id, @Field("user") String user);
}
