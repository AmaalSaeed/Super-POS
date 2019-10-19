package com.smartapps.super_pos.Items.Tables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductItem implements Serializable {
    private int id;
    private Product product;
    @SerializedName("item_no")
    private int item_count;
    @SerializedName("min_qty")
    private int min_quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public int getMin_quantity() {
        return min_quantity;
    }

    public void setMin_quantity(int min_quantity) {
        this.min_quantity = min_quantity;
    }
}
