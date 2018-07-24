package com.example.spoorthi.gittest.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.view.Menu;

public class CartCountViewModel extends ViewModel
{
    int cartCount;

    Menu cartMenu;

    public Menu getCartMenu() {
        return cartMenu;
    }

    public void setCartMenu(Menu cartMenu) {
        this.cartMenu = cartMenu;
    }

    public int getCartCount() {
        return cartCount;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }
}
