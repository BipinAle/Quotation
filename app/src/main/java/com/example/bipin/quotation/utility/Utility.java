package com.example.bipin.quotation.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by bipin on 8/9/16.
 */
public class Utility {

    public static  SharedPreferences getPreferences(Context context){

        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
