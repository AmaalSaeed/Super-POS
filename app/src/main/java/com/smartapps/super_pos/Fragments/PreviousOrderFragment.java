package com.smartapps.super_pos.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartapps.super_pos.API.RetrofitAPIs;
import com.smartapps.super_pos.API.RetrofitClientInstance;
import com.smartapps.super_pos.Activities.OrderDetailActivity;
import com.smartapps.super_pos.Adapters.OrderAdapter;
import com.smartapps.super_pos.Items.Feed;
import com.smartapps.super_pos.Items.Tables.Order;
import com.smartapps.super_pos.R;
import com.smartapps.super_pos.Utils.Utils;
import com.smartapps.super_pos.Utils.Views.LoadView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousOrderFragment extends ProjectFragment {
    RecyclerView driversRv;
    OrderAdapter orderAdapter;
    private int resultRequests = 9999;

    public PreviousOrderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        driversRv = rootView.findViewById(R.id.drivers_rv);
        orderAdapter = new OrderAdapter(getActivity(), new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order item, int position) {
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("index", position);
                intent.putExtra("order", item);
                startActivityForResult(intent, resultRequests);
            }
        });
        driversRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        driversRv.setAdapter(orderAdapter);


        return rootView;
    }

    private void requestData() {
        getProjectActivity().show();
        RetrofitAPIs retrofitAPIs = RetrofitClientInstance.getRetrofitInstance().create(RetrofitAPIs.class);

        Call<Feed> call = retrofitAPIs.getFeed("previous");

        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                getProjectActivity().hide();
                if (response.isSuccessful()) {
                    getProjectActivity().hide();
                    //Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                    Log.v("respons ", response.body().toString());
                    //List<Order> Orders = response.body();
                    //Toast.makeText(getApplicationContext(), orders.size(), Toast.LENGTH_LONG).show();
                    OrderAdapter.orders = response.body().getOrders();
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
                    getProjectActivity().showError(Utils.getErrorMesage(response.code()));
                    return;
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                getProjectActivity().showInternetError(new LoadView.OnErrorViewClickListener() {
                    @Override
                    public void onErrorViewClickListener() {
                        getProjectActivity().show();
                        requestData();
                    }
                });

            }


        });

    }
}
