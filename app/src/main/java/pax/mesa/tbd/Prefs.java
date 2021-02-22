package pax.mesa.tbd;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.Set;

public class Prefs {
    SharedPreferences prefs;
    Context context;
    public static Date lastMoodCheck;
    public static Date lastQuoteUpdate;
    public static String lastQuote;

    public static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
    }

    public static void insertString(Context context,String key,String value){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putString(key,value);
        editor.commit();
    }
    public static void insertBool(Context context,String key,Boolean value){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static void insertStringSet(Context context,String key,Set<String> value){
        getPrefs(context).edit().putStringSet(key,value).commit();
    }

    public static String retrieveData(Context context,String key){
        return getPrefs(context).getString(key,"no_data_found");
    }

    public static Set<String> getStringSet(Context context, String key){
        return getPrefs(context).getStringSet(key,null);
    }

    public static void deleteData(Context context,String key){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.remove(key);
        editor.commit();
    }
}