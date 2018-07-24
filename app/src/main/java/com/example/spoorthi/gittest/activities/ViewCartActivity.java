package com.example.spoorthi.gittest.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spoorthi.gittest.R;
import com.example.spoorthi.gittest.adapters.CartItemAdapter;
import com.example.spoorthi.gittest.beanclass.CartItem;
import com.example.spoorthi.gittest.database.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewCartActivity extends AppCompatActivity {

    private Context context = ViewCartActivity.this;

    RecyclerView cartList;

    TextView cartTotal;

    Button checkoutBtn;

    List<CartItem> cartItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        setLightStatusBar(getWindow().getDecorView(),this);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#dcdcdc")));
        }

        initViews();

        initListeners();
    }

    private void initViews()
    {
        cartList = (RecyclerView)findViewById(R.id.rv_cart_list);
        cartTotal = (TextView)findViewById(R.id.tv_total_amount);
        checkoutBtn = (Button)findViewById(R.id.btn_checkout);

        cartList.setHasFixedSize(true);
        cartList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        int size = getIntent().getIntExtra("count",0);

        for(int i=0;i<size;i++)
        {
            int count = i+1;
            CartItem cartItem = new CartItem(i,"scanned item "+count,100,1);
            cartItemList.add(cartItem);
        }

        CartItemAdapter cartItemAdapter = new CartItemAdapter(context,cartItemList);

        cartList.setAdapter(cartItemAdapter);

        if(cartItemList!=null && cartItemList.size()>0)
        {
            cartTotal.setText(""+cartItemList.size()*100);
        }
    }

    private void initListeners()
    {
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartItemList.size()>0)
                {
                    Intent intent = new Intent(context,PaymentActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(context,"No item in cart.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public static void setLightStatusBar(View view, Activity activity){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.parseColor("#dcdcdc"));
        }
    }
}
