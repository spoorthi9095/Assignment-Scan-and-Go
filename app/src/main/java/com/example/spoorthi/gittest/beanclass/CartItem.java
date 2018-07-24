package com.example.spoorthi.gittest.beanclass;

public class CartItem
{
    int productId;
    String productName;
    int amount;
    int quantity;

    public CartItem(int productId, String productName, int amount, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.amount = amount;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    @Override
    public String toString() {
        return "CartItem{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", amount=" + amount +
                ", quantity=" + quantity +
                '}';
    }
}
