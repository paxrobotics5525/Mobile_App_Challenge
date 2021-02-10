package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.example.myapplication.Preferences.SettingsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_forums, R.id.nav_meditation, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        loadData();
    }

    // Loads the required stored data
    // Currently includes mood tracker and daily quote data
    private void loadData(){
        // Mood check data
        SharedPreferencesClass.lastMoodCheck = Calendar.getInstance().getTime();
        SharedPreferencesClass.lastMoodCheck.setYear(0);// Initialize data as a while ago
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy,kk");
        try {
            String filename = getFilesDir().getPath() + "/mood_tracker.txt";
            FileInputStream stream = new FileInputStream(filename);
            while (stream.read() != -1){

            }
            stream.skip(-16);
            byte[] data = new byte[16];
            stream.read(data, 0, 16);
            String stringData = new String(data, Charset.defaultCharset());

            Date date = dateFormat.parse(stringData.substring(2,15));
            SharedPreferencesClass.lastMoodCheck = date;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Daily quote data
        String last_update = SharedPreferencesClass.retriveData(getApplicationContext(),"last_quote_update");
        SharedPreferencesClass.lastQuote = SharedPreferencesClass.retriveData(getApplicationContext(),"last_quote");
        if(last_update.equals("no_data_found")){
            last_update = "0";
            SharedPreferencesClass.lastQuoteUpdate = Calendar.getInstance().getTime();
            SharedPreferencesClass.lastQuoteUpdate.setYear(0);
        }
        else {
            try {
                SharedPreferencesClass.lastQuoteUpdate = dateFormat.parse(last_update);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(SettingsFragment.isDarkTheme(getApplicationContext())){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();


    }
}