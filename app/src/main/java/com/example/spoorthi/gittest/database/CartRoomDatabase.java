package com.example.spoorthi.gittest.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {CartItem.class}, version = 1)
public abstract class CartRoomDatabase extends RoomDatabase
{
    public abstract CartDao cartDao();

    private static CartRoomDatabase INSTANCE;


    static CartRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CartRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CartRoomDatabase.class, "cart_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
