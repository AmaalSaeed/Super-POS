package com.smartapps.super_pos.Items.Tables;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.smartapps.super_pos.Items.PathDate;

@Entity
public class Price {
    @PrimaryKey
    @NonNull
    private int id;
    private double price;
    private PathDate date;
    private int market_id;
    private int product_id;

    public int getMarket_id() {
        return market_id;
    }

    public void setMarket_id(int market_id) {
        this.market_id = market_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price product = (Price) o;
        return id == product.id;
    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PathDate getDate() {
        return date;
    }

    public void setDate(PathDate date) {
        this.date = date;
    }





}
