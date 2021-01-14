package com.example.myapplication.ui.home;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.SharedPreferencesClass;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Calendar;
import java.util.Date;

import androidx.constraintlayout.widget.ConstraintLayout;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;
    private TextView quoteTextView;
    private View root;

    public LinearLayout linear;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        // Create the needed objects from the resource file
        quoteTextView = (TextView) root.findViewById(R.id.quoteTextView);
        linear = (LinearLayout) root.findViewById(R.id.home_linear);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy,kk");

        // Check whether or not to update the daily quote
        if(getTimeDifference(Calendar.getInstance().getTime(), SharedPreferencesClass.lastQuoteUpdate) > 24){
            String[] arr = getResources().getStringArray(R.array.quotes);
            int ran = new Random().nextInt((arr.length));
            quoteTextView.setText(arr[ran]);

            SharedPreferencesClass.insertData(getContext(), "last_quote_update", dateFormat.format(date));
            SharedPreferencesClass.insertData(getContext(), "last_quote", String.valueOf(arr[ran]));
        }
        else{
            quoteTextView.setText(SharedPreferencesClass.lastQuote);
        }

        // Check whether or not to take a mood check
        if(getTimeDifference(Calendar.getInstance().getTime(), SharedPreferencesClass.lastMoodCheck) > 24){
            addFragment(new MoodTrackerFragment());
        }
        return root;
    }

    // Return the time difference in hours
    public long getTimeDifference(Date firstDate, Date secondDate){
        long diff = firstDate.getTime() - secondDate.getTime();
        long millisInHour = 60*60*1000;
        return diff/millisInHour;
    }

    // Add a fragment to the bottom of the vertical linear layout
    public void addFragment(Fragment frag){
        getActivity().getSupportFragmentManager().beginTransaction().add(linear.getId(), frag).commit();
    }
}