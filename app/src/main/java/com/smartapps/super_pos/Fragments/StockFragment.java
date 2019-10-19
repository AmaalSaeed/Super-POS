package com.smartapps.super_pos.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartapps.super_pos.API.RetrofitAPIs;
import com.smartapps.super_pos.API.RetrofitClientInstance;
import com.smartapps.super_pos.Adapters.StockAdapter;
import com.smartapps.super_pos.Items.NavItem;
import com.smartapps.super_pos.Items.Tables.Stock;
import com.smartapps.super_pos.R;
import com.smartapps.super_pos.Utils.Utils;
import com.smartapps.super_pos.Utils.Views.LoadView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockFragment extends ProjectFragment  {
    RecyclerView stockRv;
    StockAdapter stockAdapter;

    public StockFragment() {
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

        stockRv = rootView.findViewById(R.id.drivers_rv);
        stockAdapter = new StockAdapter(getActivity(), new StockAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int product_id) {

                getProjectActivity().showEditQuntity(new LoadView.OnEditQuntityViewClickListener() {
                    @Override
                    public void onEditQuntityViewClickListener(int quntity) {
                        int min_quntity = quntity;
                        Toast.makeText(getProjectActivity(), min_quntity, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        stockRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        stockRv.setAdapter(stockAdapter);

        return rootView;
    }

    private void requestData() {
        getProjectActivity().show();
        RetrofitAPIs retrofitAPIs = RetrofitClientInstance.getRetrofitInstance().create(RetrofitAPIs.class);

        Call<Stock> call = retrofitAPIs.getStock("normal");

        call.enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                getProjectActivity().hide();
                if (response.isSuccessful()) {
                    getProjectActivity().hide();
                    Log.v("respons ", response.body().toString());
                    //StockAdapter.productItems = response.body().getProductItems();
                    stockAdapter.updateList(response.body().getProductItems());
                    stockAdapter.notifyDataSetChanged();

                } else {
                    getProjectActivity().showError(Utils.getErrorMesage(response.code()));
                    return;
                }
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
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

    @Override
    public void onResume() {
        super.onResume();
        requestData();

    }
    @Override
    public void onResumeFragment(NavItem navItem) {

        if(navItem.getTitle().equals(R.string.nav_stock)) {
            requestData();
        }
    }
}
