package com.app.liferdeal.model.splash;

public class DataModel {
    public int icon;
    public String name;

    public DataModel( String name,int icon) {
        this.name = name;
        this.icon = icon;
    }




    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
