package com.smartapps.super_pos.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smartapps.super_pos.Items.Tables.Product;
import com.smartapps.super_pos.Items.Tables.ProductItem;
import com.smartapps.super_pos.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private static final String TAG = OrderDetailAdapter.class.getSimpleName();
    private Context mContext;
    private List<ProductItem> productItems = new ArrayList<>();

    public OrderDetailAdapter(Context context, List<ProductItem> productItems) {
        this.mContext = context;
        this.productItems = productItems;
    }

    public void updateList(ArrayList<ProductItem> productItems){
        this.productItems = productItems;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.order_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductItem productItem = productItems.get(position);
        final Product product = productItem.getProduct();

        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice()+ " ريال" + " * " + productItem.getItem_count());
        holder.productsSumPrice.setText(product.getPrice() * productItem.getItem_count() + " ريال");
        if (!TextUtils.isEmpty(product.getUrl())) {
            Picasso.get().load(product.getUrl()).into(holder.productImage);
            holder.productImageBr.setVisibility(View.GONE);
        } else
            Picasso.get().load(product.getUrl()).into(holder.productImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    holder.productImageBr.setVisibility(View.GONE);

                }

                @Override
                public void onError(Exception e) {
                }
            });

    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        ProgressBar productImageBr;
        TextView productName;
        TextView productPrice;
        TextView productsSumPrice;

        //TODO Bind views
        public ViewHolder(View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.item_image_view);
            productImageBr = itemView.findViewById(R.id.item_image_progressBr);
            productName = itemView.findViewById(R.id.item_name);
            productPrice = itemView.findViewById(R.id.item_price);
            productsSumPrice = itemView.findViewById(R.id.items_price);


        }
    }
}