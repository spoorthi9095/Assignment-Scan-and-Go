package com.example.spoorthi.gittest.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CartRepository
{
    private CartDao mCartDao;
    private LiveData<List<CartItem>> mAllCartItems;

    CartRoomDatabase db;

    CartRepository(Application application) {
        db = CartRoomDatabase.getDatabase(application);
        mCartDao = db.cartDao();
        mAllCartItems = mCartDao.getAllCartItems();
    }

    LiveData<List<CartItem>> getmAllCartItems() {
        return mAllCartItems;
    }

    public void insert (CartItem cartItem) {
        new insertAsyncTask(mCartDao).execute(cartItem);
    }

    private static class insertAsyncTask extends AsyncTask<CartItem, Void, Void> {

        private CartDao mAsyncTaskDao;

        insertAsyncTask(CartDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CartItem... params) {
            mAsyncTaskDao.insertCartItem(params[0]);
            return null;
        }
    }

    public void deleteAll(CartItem cartItem)
    {
        new deleteAsyncTask(db).execute(cartItem);
    }

    private static class deleteAsyncTask extends AsyncTask<CartItem, Void, Void> {

        private CartRoomDatabase cartRoomDatabase;

        deleteAsyncTask(CartRoomDatabase cartRoomDatabase) {
            this.cartRoomDatabase = cartRoomDatabase;
        }

        @Override
        protected Void doInBackground(final CartItem... params) {
            cartRoomDatabase.cartDao().deleteAll();
            return null;
        }
    }
}
