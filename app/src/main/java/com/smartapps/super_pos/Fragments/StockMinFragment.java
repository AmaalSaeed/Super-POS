package com.smartapps.super_pos.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartapps.super_pos.API.RetrofitAPIs;
import com.smartapps.super_pos.API.RetrofitClientInstance;
import com.smartapps.super_pos.Activities.OrderDetailActivity;
import com.smartapps.super_pos.Adapters.StockAdapter;
import com.smartapps.super_pos.Items.NavItem;
import com.smartapps.super_pos.Items.Tables.ProductItem;
import com.smartapps.super_pos.Items.Tables.Stock;
import com.smartapps.super_pos.R;
import com.smartapps.super_pos.Utils.Utils;
import com.smartapps.super_pos.Utils.Views.CustomTabLayout;
import com.smartapps.super_pos.Utils.Views.LoadView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockMinFragment extends ProjectFragment {
    RecyclerView stockRv;
    StockAdapter stockAdapter;
    int min_quntity;
    int productId;
    LoadView loadView;

    public void setChangeStockMinQuntityViewClickListener1(ChangeStockMinQuntityViewClickListener1 changeStockMinQuntityViewClickListener) {
        changeStockMinQuntityViewClickListener1 = changeStockMinQuntityViewClickListener;
    }

    private ChangeStockMinQuntityViewClickListener1 changeStockMinQuntityViewClickListener1;

    public StockMinFragment() {
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
        stockRv = rootView.findViewById(R.id.drivers_rv);
        stockAdapter = new StockAdapter(getActivity(), new StockAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int product_id) {
                productId = product_id;

                loadView.showEditQuntity(new LoadView.OnEditQuntityViewClickListener() {
                    @Override
                    public void onEditQuntityViewClickListener(int quntity) {
                        min_quntity = quntity;
                        //Toast.makeText(getProjectActivity(),"qty: " + min_quntity + "product_id: " + product_id, Toast.LENGTH_SHORT).show();
                        changeStockMinQuntity(product_id, min_quntity);
                    }
                });
            }
        }, this);
        stockRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        stockRv.setAdapter(stockAdapter);

        return rootView;
    }

    private void requestData() {
        loadView.show();
        RetrofitAPIs retrofitAPIs = RetrofitClientInstance.getRetrofitInstance().create(RetrofitAPIs.class);

        Call<Stock> call = retrofitAPIs.getStock("min");

        call.enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                loadView.hide();
                if (response.isSuccessful()) {
                    loadView.hide();
                    Log.v("respons ", response.body().toString());
                    //StockAdapter.productItems = response.body().getProductItems();
                    stockAdapter.updateList(response.body().getProductItems());
                } else {
                    loadView.showError(Utils.getErrorMesage(response.code()));
                    return;
                }
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
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

    public void changeStockMinQuntity(int product_id, int min_qty){
        loadView.show();
        RetrofitAPIs retrofitAPIs = RetrofitClientInstance.getRetrofitInstance().create(RetrofitAPIs.class);

        Call<Stock> call = retrofitAPIs.changeStorkMinQtY(product_id, min_qty);

        call.enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                loadView.hide();
                if (response.isSuccessful()) {
                    loadView.hide();
                    Log.v("respons ", response.body().toString());
                    int editableMin_qty = response.body().getProductItems().get(0).getMin_quantity();
                    int product_id = response.body().getProductItems().get(0).getId();
                    //Toast.makeText(getContext(), "editableMin: "+ editableMin_qty + "product_id: "+ product_id, Toast.LENGTH_LONG).show();
                    if(changeStockMinQuntityViewClickListener1 != null)
                        changeStockMinQuntityViewClickListener1.changeStockMinQuntityViewClickListener1(product_id, editableMin_qty);
                    stockAdapter.notifyDataSetChanged();

                } else {
                    loadView.showError(Utils.getErrorMesage(response.code()));
                    return;
                }
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
                getProjectActivity().showInternetError(new LoadView.OnErrorViewClickListener() {
                    @Override
                    public void onErrorViewClickListener() {
                        loadView.show();
                        changeStockMinQuntity(productId, min_quntity);
                    }
                });

            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
       // requestData();


    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResumeFragment(NavItem navItem) {
        super.onResumeFragment(navItem);
        if(navItem.getTitle().equals(R.string.nav_min_stock)) {
            requestData();
        }
    }

    public interface ChangeStockMinQuntityViewClickListener1{
        void changeStockMinQuntityViewClickListener1(int product_id, int min_qty);
    }
}
