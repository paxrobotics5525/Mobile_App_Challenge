package com.example.myapplication.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.myapplication.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    //SharedPreferences preferences;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        //preferences = getPreferenceManager().getSharedPreferences();
    }

    // Returns what classes the user wants to be included in, ordered:
    // Geometry, Algebra 1, Algebra 2, Pre-Calc, Calc 1, Calc 2
    static public Boolean[] getClasses(Context context){
        SharedPreferences preferences = context.getSharedPreferences("com.example.mobile_app_challenge_preferences",Context.MODE_PRIVATE);
        return new Boolean[]{preferences.getBoolean("geo", false), preferences.getBoolean("alg1", false),
                preferences.getBoolean("alg2", false),preferences.getBoolean("precalc", false),
                preferences.getBoolean("calc1", false),preferences.getBoolean("calc2", false)};
    }
    static public String getFirstName(Context context){
        SharedPreferences preferences = context.getSharedPreferences("com.example.mobile_app_challenge_preferences",Context.MODE_PRIVATE);
        return preferences.getString("first_name", "default name");
    }
    static public String getLastName(Context context){
        SharedPreferences preferences = context.getSharedPreferences("com.example.mobile_app_challenge_preferences",Context.MODE_PRIVATE);
        return preferences.getString("last_name", "default name");
    }
    static public Boolean isDarkTheme(Context context){
        SharedPreferences preferences = context.getSharedPreferences("com.example.mobile_app_challenge_preferences",Context.MODE_PRIVATE);
        return preferences.getBoolean("darkmode", true);
    }

    static void setName(Context context, String firstName, String lastName){
        SharedPreferences preferences = context.getSharedPreferences("com.example.mobile_app_challenge_preferences",Context.MODE_PRIVATE);
        preferences.edit().putString("first_name", firstName).apply();
        preferences.edit().putString("last_name", lastName).apply();
    }
    static void setClasses(Context context, Boolean geom, Boolean alg1, Boolean alg2, Boolean precalc, Boolean calc1, Boolean calc2){
        SharedPreferences preferences = context.getSharedPreferences("com.example.mobile_app_challenge_preferences",Context.MODE_PRIVATE);
        preferences.edit().putBoolean("geom", geom).apply();
        preferences.edit().putBoolean("alg1", alg1).apply();
        preferences.edit().putBoolean("alg2", alg2).apply();
        preferences.edit().putBoolean("precalc", precalc).apply();
        preferences.edit().putBoolean("calc1", calc2).apply();
    }
    static void setDarkTheme(Context context, Boolean dark){
        SharedPreferences preferences = context.getSharedPreferences("com.example.mobile_app_challenge_preferences",Context.MODE_PRIVATE);
        preferences.edit().putBoolean("darkmode", dark).apply();
    }
}