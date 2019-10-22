package com.smartapps.super_pos.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartapps.super_pos.API.RetrofitAPIs;
import com.smartapps.super_pos.API.RetrofitClientInstance;
import com.smartapps.super_pos.Activities.OrderDetailActivity;
import com.smartapps.super_pos.Adapters.OrderAdapter;
import com.smartapps.super_pos.Items.Feed;
import com.smartapps.super_pos.Items.NavItem;
import com.smartapps.super_pos.Items.Tables.Order;
import com.smartapps.super_pos.R;
import com.smartapps.super_pos.Utils.Utils;
import com.smartapps.super_pos.Utils.Views.CustomTabLayout;
import com.smartapps.super_pos.Utils.Views.LoadView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CurrentOrderFragment extends ProjectFragment {
    OrderAdapter orderAdapter;
    RecyclerView driversRv;
    private int resultRequests = 9999;
    LoadView loadView;

    public CurrentOrderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        loadView = rootView.findViewById(R.id.load_view);
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

    public void requestData() {
        if(getProjectActivity() != null) {
            loadView.show();
        }
        RetrofitAPIs retrofitAPIs = RetrofitClientInstance.getRetrofitInstance().create(RetrofitAPIs.class);

        Call<Feed> call = retrofitAPIs.getFeed("current");

        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if (response.isSuccessful()) {
                    Log.v("respons ", response.body().toString());
                    //Toast.makeText(getActivity(), response.body().getOrders().size()+"", Toast.LENGTH_SHORT).show();
                    //OrderAdapter.orders = response.body().getOrders();
                    if (response.body().getOrders().size()>0){
                        orderAdapter.updateList(response.body().getOrders());
                        orderAdapter.notifyDataSetChanged();
                        loadView.hide();
                    } else{
                        loadView.showError("لا توجد بيانات حاليا");

                    }

                } else {
                    loadView.showError(Utils.getErrorMesage(response.code()));
                    return;
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                loadView.showError(new LoadView.OnErrorViewClickListener() {
                    @Override
                    public void onErrorViewClickListener() {
                        loadView.show();
                        requestData();
                    }
                });

            }

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == resultRequests) {
            if (resultCode == Activity.RESULT_OK) {
                Order editableOrder = (Order) data.getSerializableExtra("editableOrder");
                int index = data.getIntExtra("index", 0);
                //orderAdapter.updateOrder(editableOrder, index);
                orderAdapter.deleteOrder(index);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    //    requestData();

    }
    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onResumeFragment(NavItem navItem) {
        super.onResumeFragment(navItem);
        if(navItem.getTitle().equals(R.string.nav_current_order)) {
            requestData();
        }
    }
}
