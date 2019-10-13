package com.smartapps.super_pos.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.smartapps.super_pos.Items.Tables.Driver;
import com.smartapps.super_pos.Items.Tables.Order;
import com.smartapps.super_pos.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private static final String TAG = OrderAdapter.class.getSimpleName();
    private Context mContext;
    public static ArrayList<Order> orders = new ArrayList<>();
    private OnItemClickListener ordersener;

    public OrderAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.ordersener = onItemClickListener;
    }

    public void updateOrder(Order order, int index){
        OrderAdapter.orders.set(index, order);
        notifyItemChanged(index);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Order order = orders.get(position);
        Driver driver = orders.get(position).getDriver();

        holder.driverName.setText(driver.getName());
        holder.orderNo.setText(String.valueOf(order.getId()));
        if (!TextUtils.isEmpty(driver.getImageUrl())) {
            Picasso.get().load(driver.getImageUrl()).into(holder.driverImage);
            holder.driverImageBr.setVisibility(View.GONE);
        } else {
            Picasso.get().load(driver.getImageUrl()).into(holder.driverImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    holder.driverImageBr.setVisibility(View.GONE);

                }

                @Override
                public void onError(Exception e) {
                }
            });
        }

        //TODO setup views

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordersener.onItemClick(order, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Order item, int index);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView driverName;
        private TextView orderNo;
        private ImageView driverImage;
        private ProgressBar driverImageBr;

        //TODO Bind views
        public ViewHolder(View itemView) {
            super(itemView);

            driverName = itemView.findViewById(R.id.item_name);
            orderNo = itemView.findViewById(R.id.order_no);
            driverImage = itemView.findViewById(R.id.item_image_view);
            driverImageBr = itemView.findViewById(R.id.item_image_progressBr);

        }
    }
}