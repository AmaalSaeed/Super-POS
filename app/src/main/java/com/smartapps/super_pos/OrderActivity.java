package com.smartapps.super_pos;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.smartapps.super_pos.API.RetrofitAPIs;
import com.smartapps.super_pos.API.RetrofitClientInstance;
import com.smartapps.super_pos.Activities.OrderDetailActivity;
import com.smartapps.super_pos.Adapters.OrderAdapter;
import com.smartapps.super_pos.Items.Feed;
import com.smartapps.super_pos.Items.Tables.Order;
import com.smartapps.super_pos.Utils.Utils;
import com.smartapps.super_pos.Utils.Views.LoadView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends ContainerActivity {
    RecyclerView driversRv;
    OrderAdapter orderAdapter;
    private int resultRequests = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order);

        //Start service:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("Default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("DefaultDesc");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        //Get FirebaseToken for this app
        FirebaseApp.initializeApp(this);
        getFirebaseAppToken();

        requestData();

        driversRv = findViewById(R.id.drivers_rv);
        orderAdapter = new OrderAdapter(this, new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order item, int position) {
                Intent intent = new Intent(OrderActivity.this, OrderDetailActivity.class);
                intent.putExtra("index", position);
                intent.putExtra("order", item);
                startActivityForResult(intent, resultRequests);
            }
        });
        driversRv.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
        driversRv.setAdapter(orderAdapter);
    }

    private void requestData() {
        show();
        RetrofitAPIs retrofitAPIs = RetrofitClientInstance.getRetrofitInstance().create(RetrofitAPIs.class);

        Call<Feed> call = retrofitAPIs.getFeed("all");

        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                hide();
                if (response.isSuccessful()) {
                    hide();
                    //Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                    Log.v("respons ", response.body().toString());
                    //List<Order> Orders = response.body();
                    //Toast.makeText(getApplicationContext(), orders.size(), Toast.LENGTH_LONG).show();
                    //OrderAdapter.orders = response.body().getOrders();
                    orderAdapter.updateList(response.body().getOrders());
               /*

                for (int i=0; i<orders.size(); i++){
                    Toast.makeText(getApplicationContext(), orders.toString(), Toast.LENGTH_LONG).show();
                    Log.v("order: ","id: " + orders.get(i).getId() + "\n" +
                            "status: " +orders.get(i).getStatus() + "\n" +
                                    "created_at: " +orders.get(i).getCreated_at() + "\n" +
                                    "updated_at: " +orders.get(i).getUpdated_at() + "\n" +
                                    "total: " +orders.get(i).getTotal_price() + "\n" +
                                    "driver: " +orders.get(i).getDriver().toString() + "\n" +
                                    "items: " +orders.get(i).getProductItems().toString() + "\n" );

                }*/
                    orderAdapter.notifyDataSetChanged();

                } else {
                    //Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                    showError(Utils.getErrorMesage(response.code()));
                    return;
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                showInternetError(new LoadView.OnErrorViewClickListener() {
                    @Override
                    public void onErrorViewClickListener() {
                        show();
                        requestData();
                    }
                });

            }


        });

    }

    public void getFirebaseAppToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult().getToken();
                            //Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("firebaseToken", task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == resultRequests) {
            if (resultCode == Activity.RESULT_OK) {
                Order editableOrder = (Order) data.getSerializableExtra("editableOrder");
                int index = data.getIntExtra("index", 0);
                orderAdapter.updateOrder(editableOrder, index);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
