package com.app.liferdeal.ui.navigationdrawer;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.app.liferdeal.ui.fragment.LanguageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miki on 7/8/17.
 */

public class NavMenuModel {
    public String menuTitle;
    public int menuIconDrawable;
    public List<SubMenuModel> subMenu;
    public Fragment fragment;
 //   public Activity activity;

    public NavMenuModel(String menuTitle, int menuIconDrawable, Fragment fragment) {
        this.menuTitle = menuTitle;
        this.menuIconDrawable = menuIconDrawable;
        this.fragment = fragment;
        this.subMenu = new ArrayList<>();
    }
/*    public NavMenuModel(String menuTitle, int menuIconDrawable, Activity fragment) {
        this.menuTitle = menuTitle;
        this.menuIconDrawable = menuIconDrawable;
        this.activity = fragment;
        this.subMenu = new ArrayList<>();
    }*/


    public NavMenuModel(String menuTitle, int menuIconDrawable, ArrayList<SubMenuModel> subMenu) {
        this.menuTitle = menuTitle;
        this.menuIconDrawable = menuIconDrawable;
        this.subMenu = new ArrayList<>();
        this.subMenu.addAll(subMenu);
    }


    public static class SubMenuModel {
        public String subMenuTitle;
        public Fragment fragment;
       // public Activity activity;

        public SubMenuModel(String subMenuTitle, Fragment fragment) {
            this.subMenuTitle = subMenuTitle;
            this.fragment = fragment;
        }


      /*  public SubMenuModel(String subMenuTitle, Activity fragment) {
            this.subMenuTitle = subMenuTitle;
            this.activity = fragment;
        }*/

    }
}
