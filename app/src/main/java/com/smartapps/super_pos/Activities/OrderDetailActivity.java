package com.smartapps.super_pos.Activities;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smartapps.super_pos.API.RetrofitAPIs;
import com.smartapps.super_pos.API.RetrofitClientInstance;
import com.smartapps.super_pos.Adapters.OrderDetailAdapter;
import com.smartapps.super_pos.ContainerActivity;
import com.smartapps.super_pos.Items.Feed;
import com.smartapps.super_pos.Items.Tables.Driver;
import com.smartapps.super_pos.Items.Tables.Order;
import com.smartapps.super_pos.Items.Tables.ProductItem;
import com.smartapps.super_pos.R;
import com.smartapps.super_pos.Utils.Utils;
import com.smartapps.super_pos.Utils.Views.LoadView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends ContainerActivity {
    Order order;
    Driver driver;
    ArrayList<ProductItem> productItems;
    TextView orderTime;
    TextView reciveTime;
    TextView driverName;
    TextView orderNo;
    TextView totalPrice;
    ImageView driverImage;
    ProgressBar driverImageProgressBr;
    Button stateBtn;
    RecyclerView productsRv;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        if (getIntent().getExtras() != null && getIntent().hasExtra("order")){
            order = (Order) getIntent().getExtras().getSerializable("order");
            index = getIntent().getExtras().getInt("index");
        } else {
            Toast.makeText(this, "OrderIntentExtrasError", Toast.LENGTH_SHORT).show();
        }

        orderTime = findViewById(R.id.order_time);
        reciveTime = findViewById(R.id.recive_time);
        driverName = findViewById(R.id.driver_name);
        orderNo = findViewById(R.id.order_no);
        totalPrice = findViewById(R.id.total_price);
        driverImage = findViewById(R.id.item_image_view);
        driverImageProgressBr = findViewById(R.id.item_image_progressBr);
        stateBtn = findViewById(R.id.do_order);
        productsRv = findViewById(R.id.products_recyclerv);

        driver = order.getDriver();
        productItems = order.getProductItems();

        orderTime.setText(order.getCreated_at());
        reciveTime.setText(order.getUpdated_at());
        driverName.setText(driver.getName());
        orderNo.setText(String.valueOf(order.getId()));
        totalPrice.setText(order.getTotal_price() + "  ريال");

        if (!TextUtils.isEmpty(driver.getImageUrl())) {
            Picasso.get().load(driver.getImageUrl()).into(driverImage);
            driverImageProgressBr.setVisibility(View.GONE);
        } else {
            Picasso.get().load(driver.getImageUrl()).into(driverImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    driverImageProgressBr.setVisibility(View.GONE);

                }

                @Override
                public void onError(Exception e) {
                }
            });
        }

        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(this, productItems);
        productsRv.setLayoutManager(new LinearLayoutManager(OrderDetailActivity.this));
        productsRv.setAdapter(orderDetailAdapter);

        if (order.getStatus() <3){
            stateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getApplicationContext(), "insideButn", Toast.LENGTH_SHORT).show();
                    show();
                    requestData();
                }
            });
        }else {
            stateBtn.setVisibility(View.GONE);
        }


    }

    private void requestData(){
        RetrofitAPIs retrofitAPIs = RetrofitClientInstance.getRetrofitInstance().create(RetrofitAPIs.class);

        Call<Feed> call = retrofitAPIs.changeOrderStatus(order.getId(), "market");
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {

                //Toast.makeText(getApplicationContext(), "insideRequest", Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(), "statusChange: " + response.body().getOrders().get(0).getStatus(), Toast.LENGTH_SHORT).show();
                    hide();
                    Order editableOrder = response.body().getOrders().get(0);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("editableOrder",editableOrder);
                    returnIntent.putExtra("index", index);
                    setResult(Activity.RESULT_OK,returnIntent);

                    /*stateBtn.setText("إنهاء");
                    stateBtn.setBackgroundColor(getResources().getColor(R.color.holder_color));
                    stateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });*/
                } else{
                    //Toast.makeText(getApplicationContext(), "statusNotChange: ", Toast.LENGTH_SHORT).show();
                    showError(Utils.getErrorMesage(response.code()));
                    return;
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "requestFail " + t.getMessage(), Toast.LENGTH_SHORT).show();
                showInternetError(new LoadView.OnErrorViewClickListener() {
                    @Override
                    public void onErrorViewClickListener() {
                        requestData();
                    }
                });
            }
        });
    }
}
