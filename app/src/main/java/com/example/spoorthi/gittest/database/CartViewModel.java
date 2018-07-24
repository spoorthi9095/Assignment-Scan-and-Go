package com.example.spoorthi.gittest.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class CartViewModel extends AndroidViewModel
{
    private CartRepository mRepository;

    private LiveData<List<CartItem>> mAllCartItems;

    public CartViewModel (Application application) {
        super(application);
        mRepository = new CartRepository(application);
        mAllCartItems = mRepository.getmAllCartItems();
    }

    public LiveData<List<CartItem>> getmAllWords() { return mAllCartItems; }

    public void insert(CartItem cartItem) { mRepository.insert(cartItem); }
}
