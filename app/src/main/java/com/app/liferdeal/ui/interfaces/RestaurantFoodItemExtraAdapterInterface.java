package com.app.liferdeal.ui.interfaces;

import com.app.liferdeal.model.restaurant.FoodExtraModel;

import java.util.ArrayList;
import java.util.List;

public interface RestaurantFoodItemExtraAdapterInterface {
    void getrvcheckeddata(ArrayList<String> tempextraId, ArrayList<Integer> extraId, ArrayList<String> extraName, ArrayList<String> extraPrice);
    void getCheckedItem(String item, String price, int id, List<FoodExtraModel.MenuItemExtraGroup.SubExtraItemsRecord> list);
}
