package com.app.liferdeal.ui.interfaces;

import com.app.liferdeal.model.CuisineList;
import com.app.liferdeal.model.restaurant.CusineFilterModel;

import java.util.List;

public interface FilterInterface {
    public void getSelectedFilter(List<CuisineList> listCategory);
}
