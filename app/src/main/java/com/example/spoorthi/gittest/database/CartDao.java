package com.example.spoorthi.gittest.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CartDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCartItem(CartItem cartItem);

    @Query("DELETE FROM cart_table")
    void deleteAll();

    @Query("SELECT * from cart_table ORDER BY product_id ASC")
    LiveData<List<CartItem>> getAllCartItems();
}
