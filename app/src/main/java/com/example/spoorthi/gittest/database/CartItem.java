package com.example.spoorthi.gittest.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "cart_table")
public class CartItem
{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "product_id")
    int productID;

    @ColumnInfo(name = "product_name")
    String productName;

    @ColumnInfo(name = "product_amount")
    int amount;

    @ColumnInfo(name = "product_quantity")
    int quantity;

    public CartItem(@NonNull int productID, String productName, int amount, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.amount = amount;
        this.quantity = quantity;
    }

    @NonNull
    public int getProductID() {
        return productID;
    }

    public void setProductID(@NonNull int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
