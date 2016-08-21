package com.example.bipin.quotation.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;

import com.example.bipin.quotation.R;

/**
 * Created by bipin on 8/9/16.
 */
public class Utility {
    public  static String KEY="com.example.bipin.quotation.adapters";

    public static  SharedPreferences getPreferences(Context context){

        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void menuItemColorGarney(NavigationView navigationView) {
        navigationView.getMenu().findItem(R.id.home).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        navigationView.getMenu().findItem(R.id.setting).getIcon().setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_IN);
        navigationView.getMenu().findItem(R.id.exit).getIcon().setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_IN);
        navigationView.getMenu().findItem(R.id.favourites).getIcon().setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_IN);

    }
}
