package com.smartapps.super_pos.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static final String BASE_URL = "https://yesuper.market/api/";
    private static Retrofit retrofit;
    private static Gson gson;
    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(4, TimeUnit.MINUTES)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(/*getGsonInstance()*/))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /*public static Gson getGsonInstance() {

        if (gson == null) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd kk:mm:ss").
                    registerTypeAdapter( DateItem.class, new RetrofitClientInstance.DateSerializer()).
                    registerTypeAdapter( DateItem.class, new RetrofitClientInstance.DateDeSerializer()).
                    create();
        }
        return gson;
    }


    public static class DateDeSerializer implements JsonDeserializer<DateItem> {

        public DateDeSerializer() {
            super();
        }

        @Override
        public DateItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            DateItem DateItem;
            DateItem = new DateItem(context.deserialize(json, String.class).toString());
            return DateItem;
        }
    }



    public static class DateSerializer implements JsonSerializer<DateItem> {

        public DateSerializer() {
            super();
        }

        @Override
        public JsonElement serialize(DateItem src, Type typeOfSrc, JsonSerializationContext context) {
            String date = src.toString();
            return context.serialize(date);
        }


    }*/

}
