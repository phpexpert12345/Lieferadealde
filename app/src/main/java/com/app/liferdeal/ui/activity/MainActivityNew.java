/*
package com.app.liferdeal.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.app.liferdeal.R;
import com.app.liferdeal.ui.Database.Database;
import com.app.liferdeal.ui.Database.MyDatabase;
import com.app.liferdeal.ui.activity.login.SignInActivity;
import com.app.liferdeal.ui.activity.login.SignUpActivity;
import com.app.liferdeal.ui.activity.splash.SplashActivity;
import com.app.liferdeal.ui.fragment.LocationMapFragment;
import com.app.liferdeal.ui.fragment.restaurant.RestaurantMain;
import com.app.liferdeal.ui.navigationdrawer.NavMenuAdapter;
import com.app.liferdeal.ui.navigationdrawer.NavMenuModel;
import com.app.liferdeal.ui.navigationdrawer.SubTitle;
import com.app.liferdeal.ui.navigationdrawer.TitleMenu;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.bumptech.glide.Glide;
import com.google.android.gms.vision.text.Line;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivityNew extends AppCompatActivity implements NavMenuAdapter.MenuItemClickListener, View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    public static int navItemIndex = 0;
    private Toolbar toolbar;
    private LinearLayout btn_signIn, btn_signUp;
    ArrayList<NavMenuModel> menu;
    public static LinearLayout rl_main_left_drawer;
    private ImageView img_logo;
    private PrefsHelper prefsHelper;
    private Database database;
    public static MyDatabase myDatabase;

    private ArrayList<String> selected_cusines;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init()
    {
        System.out.println("==== called fom activy");
        //  toolbar = findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
        prefsHelper = new PrefsHelper(this);
        database = new Database(this);
        myDatabase= Room.databaseBuilder(getApplicationContext(),MyDatabase.class,"My_Cart").allowMainThreadQueries().build();

        selected_cusines = new ArrayList<>();

        String logo =  prefsHelper.getPref(Constants.APP_LOGO);
        System.out.println("==== ff : " + logo);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img_logo = toolbar.findViewById(R.id.logo_img);
        // setToolbar();

        if(logo!=null) {
            if (!logo.equalsIgnoreCase("")) {
                Glide.with(MainActivityNew.this).load(Uri.parse(logo)).into(img_logo);

            }
        }
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryone));
        toggle.syncState();
        // navigationView = findViewById(R.id.nav_view);
        // Navigation view header
        // navHeader = navigationView.getHeaderView(0);
        rl_main_left_drawer = findViewById(R.id.rl_main_left_drawer);
        btn_signIn = findViewById(R.id.btn_sign);
        btn_signUp = findViewById(R.id.btn_signup);
        setNavigationDrawerMenu();


        btn_signIn.setOnClickListener(this);
        btn_signUp.setOnClickListener(this);
        //  database.delete();

    }


    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setNavigationDrawerMenu() {
        NavMenuAdapter adapter = new NavMenuAdapter(this, getMenuList(), this);
        RecyclerView navMenuDrawer = (RecyclerView) findViewById(R.id.main_nav_menu_recyclerview);
        navMenuDrawer.setAdapter(adapter);
        navMenuDrawer.setLayoutManager(new LinearLayoutManager(this));
        navMenuDrawer.setAdapter(adapter);

//        INITIATE SELECT MENU
        adapter.selectedItemParent = menu.get(0).menuTitle;
        onMenuItemClick(adapter.selectedItemParent);
        adapter.notifyDataSetChanged();
    }
    private List<TitleMenu> getMenuList() {
        List<TitleMenu> list = new ArrayList<>();

        menu = Constants.getMenuNavigasi();
        for (int i = 0; i < menu.size(); i++) {
            ArrayList<SubTitle> subMenu = new ArrayList<>();
            if (menu.get(i).subMenu.size() > 0){
                for (int j = 0; j < menu.get(i).subMenu.size(); j++) {
                    subMenu.add(new SubTitle(menu.get(i).subMenu.get(j).subMenuTitle));
                }
            }

            list.add(new TitleMenu(menu.get(i).menuTitle, subMenu, menu.get(i).menuIconDrawable));
        }

        return list;
    }

    @Override
    public void onMenuItemClick(String itemString) {
        for (int i = 0; i < menu.size(); i++) {
            if (itemString.equals(menu.get(i).menuTitle)){
                if (itemString.equalsIgnoreCase("Language"))
                {
                    //  toolbar.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_content, menu.get(i).fragment)
                            .commit();
                }
                else
                {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_content, menu.get(i).fragment)
                            .commit();
                }

                if (itemString.equalsIgnoreCase("Logout"))
                {

                    logoutDialog();
                }
                break;
            }
            else
            {
                for (int j = 0; j < menu.get(i).subMenu.size(); j++)
                {
                    if (itemString.equals(menu.get(i).subMenu.get(j).subMenuTitle)){
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_content, menu.get(i).subMenu.get(j).fragment)
                                .commit();
                        break;
                    }
                }
            }
        }

        if (drawer != null){
            //  drawer.closeDrawer(GravityCompat.START);
            drawer.closeDrawer(rl_main_left_drawer);
        }
    }

    private void logoutDialog() {
        new AlertDialog.Builder(MainActivityNew.this)
                .setTitle("Liferdeal")
                .setMessage("Are you sure you want to logout now ?")
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
                                try {
                                    File dir = getCacheDir();
                                    //deleteDir(dir);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                prefsHelper.clearAllPref();
                                startActivity(new Intent(MainActivityNew.this, SplashActivity.class));
                                finish();
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
                                dialog.dismiss();

                            }
                        }).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_sign:
                Intent intent = new Intent(MainActivityNew.this, SignInActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_signup:
                Intent intentsi = new Intent(MainActivityNew.this, SignUpActivity.class);
                startActivity(intentsi);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        System.out.println("==== onnew " + intent);
        if (intent!=null) {
            String checkFromWhere = intent.getStringExtra("FROMWHERE");
            if (checkFromWhere!=null) {
                if (checkFromWhere.equalsIgnoreCase("pagecusine")) {
                    System.out.println("==== onnew 1");
                    selected_cusines = intent.getStringArrayListExtra("SELECTEDCUSINE");
                    System.out.println("==== selected cusine in main activityu" + selected_cusines);
                    initiateRestFragment();
                } else if (checkFromWhere.equalsIgnoreCase("fromhome")) {
                    LocationMapFragment locationMapFragment = new LocationMapFragment();
                    FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_content, locationMapFragment);
                    // transaction.addToBackStack(restaurantMain.getTag());
                    transaction.commit();
                } else if (checkFromWhere.equalsIgnoreCase("fromlocation")) {
                    LocationMapFragment locationMapFragment = new LocationMapFragment();
                    FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_content, locationMapFragment);
                    // transaction.addToBackStack(restaurantMain.getTag());
                    transaction.commit();
                }
            }
        }
    }

    private void initiateRestFragment() {

        RestaurantMain restaurantMain = new RestaurantMain();
        Bundle args = new Bundle();
        args.putStringArrayList("SELECTEDCUSINES", selected_cusines);
        restaurantMain.setArguments(args);
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, restaurantMain);
        // transaction.addToBackStack(restaurantMain.getTag());
        transaction.commit();
    }
}*/
