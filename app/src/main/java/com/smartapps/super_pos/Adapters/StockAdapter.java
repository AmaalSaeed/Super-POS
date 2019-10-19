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

import com.smartapps.super_pos.Fragments.StockMinFragment;
import com.smartapps.super_pos.Items.Tables.Product;
import com.smartapps.super_pos.Items.Tables.ProductItem;
import com.smartapps.super_pos.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> implements StockMinFragment.ChangeStockMinQuntityViewClickListener {
    private static final String TAG = OrderAdapter.class.getSimpleName();
    private Context mContext;
    public static ArrayList<ProductItem> productItems = new ArrayList<>();
    private StockAdapter.OnItemClickListener orderlistener;

    public StockAdapter(Context context, StockAdapter.OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.orderlistener = onItemClickListener;
        StockMinFragment.setChangeStockMinQuntityViewClickListener(this);
    }

    public void updateList(ArrayList<ProductItem> productItems) {
        StockAdapter.productItems.clear();
        StockAdapter.productItems = productItems;
        notifyDataSetChanged();
    }

    @Override
    public StockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.stock_item, parent, false);
        return new StockAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StockAdapter.ViewHolder holder, int position) {
        ProductItem productItem = productItems.get(position);
        Product product = productItems.get(position).getProduct();

        holder.productName.setText(product.getName());
        holder.productQuntity.setText("المتوفر   " + productItem.getItem_count());
        holder.minQuntity.setText("الحد الأدنى   " + productItem.getMin_quantity());
        if (!TextUtils.isEmpty(product.getUrl())) {
            Picasso.get().load(product.getUrl()).into(holder.produtImage);
            holder.productImageBr.setVisibility(View.GONE);
        } else {
            Picasso.get().load(product.getUrl()).into(holder.produtImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    holder.productImageBr.setVisibility(View.GONE);

                }

                @Override
                public void onError(Exception e) {
                }
            });
        }

        //TODO setup views

        holder.itemView.setOnClickListener(v -> {

            if (orderlistener != null)
                orderlistener.onItemClick(productItem.getId());

        });
    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }
    public int getPosition(int product_id){
        int id = 0;
        for(ProductItem productItem : productItems){
            if(productItem.getId() == product_id){
               id = productItems.indexOf(productItem);
               break;
            }
        }
             return id;
    }
    @Override
    public void changeStockMinQuntityViewClickListener(int product_id, int min_qty) {
        int position = getPosition(product_id);
        productItems.get(position).setMin_quantity(min_qty);
        notifyItemChanged(position);

    }

    public interface OnItemClickListener {
        void onItemClick(int product_id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView productQuntity;
        private TextView minQuntity;
        private ImageView produtImage;
        private ProgressBar productImageBr;

        //TODO Bind views
        public ViewHolder(View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.item_name);
            productQuntity = itemView.findViewById(R.id.quantity_no);
            minQuntity = itemView.findViewById(R.id.min_qty);
            produtImage = itemView.findViewById(R.id.item_image_view);
            productImageBr = itemView.findViewById(R.id.item_image_progressBr);

        }
    }
}
