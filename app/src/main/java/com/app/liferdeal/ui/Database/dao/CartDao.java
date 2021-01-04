package com.app.liferdeal.ui.Database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.app.liferdeal.ui.Database.model.CartModel;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    public void addToCart(CartModel cart);

    @Query("SELECT * FROM MyCart")
    public List<CartModel> getData();

    @Query("SELECT EXISTS (SELECT 1 FROM mycart WHERE id=:id)")
    public int isAddToCart(String id);

    @Query("select COUNT (*) from MyCart")
    int countCart();

    @Query("DELETE FROM MyCart WHERE id=:id ")
    int deleteItem(int id);

}
