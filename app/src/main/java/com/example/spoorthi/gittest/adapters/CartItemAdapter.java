package com.example.spoorthi.gittest.adapters;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spoorthi.gittest.R;
import com.example.spoorthi.gittest.beanclass.CartItem;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>
{
    Context context;
    List<CartItem> cartItemList;

    public CartItemAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = (List<CartItem>) cartItemList;


    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartItemViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {

        CartItem cartItem = cartItemList.get(position);

        Log.e("cartItem ",""+cartItem);

        holder.productName.setText(cartItem.getProductName());

        holder.productPrice.setText("100");

        holder.productCount.setText("1");

    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public List<CartItem> getCartItemList() {
        return this.cartItemList;
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView productName,productPrice,productCount;
        ImageView incrementQty,decrementQty;

        public CartItemViewHolder(View itemView) {
            super(itemView);

            productName = (TextView)itemView.findViewById(R.id.tv_product_name);
            productPrice = (TextView)itemView.findViewById(R.id.tv_product_price);
            productCount = (TextView)itemView.findViewById(R.id.tv_product_count);

            incrementQty = (ImageView)itemView.findViewById(R.id.iv_increment_item);

            decrementQty = (ImageView)itemView.findViewById(R.id.iv_decrement_item);


        }
    }
}
