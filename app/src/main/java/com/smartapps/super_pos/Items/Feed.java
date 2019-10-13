package com.smartapps.super_pos.Items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smartapps.super_pos.Items.Tables.Order;

import java.util.ArrayList;

public class Feed {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<Order> orders;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "status='" + status + '\'' +
                ", orders=" + orders +
                '}';
    }
}
