package com.app.liferdeal.util;

import android.content.Context;
import android.widget.TextView;

public class DotToCommaClass {
    PrefsHelper prefsHelper;
    Context context;
    public DotToCommaClass(Context context){
        this.context=context;
    }
    public String changeDot(String string){
        //prefsHelper=new PrefsHelper(context);
        //if (prefsHelper.getPref(Constants.LNG_CODE).toString().equalsIgnoreCase("de")){
        if(string.contains(".")) {
            string = string.replace(".", ",");
        }
        else{
            string=string+",00";
        }

       // }
        return string;
    }
}
