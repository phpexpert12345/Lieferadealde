package com.app.liferdeal.ui.Database;

import androidx.room.RoomDatabase;
import androidx.room.Database;
import com.app.liferdeal.ui.Database.dao.CartDao;
import com.app.liferdeal.ui.Database.model.CartModel;

@Database(entities={CartModel.class},version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
}
