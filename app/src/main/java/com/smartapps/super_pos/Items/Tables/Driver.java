package com.smartapps.super_pos.Items.Tables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Driver implements Serializable {

private String name;
@SerializedName("image")
private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
