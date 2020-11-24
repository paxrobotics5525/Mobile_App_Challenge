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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.SharedPreferencesClass;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;
import java.util.Calendar;

import androidx.constraintlayout.widget.ConstraintLayout;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private HomeViewModel homeViewModel;
    private TextView quoteTextView;
    private Button expand;
    private View root;
    private ConstraintLayout mood_tracker;
    private LinearLayout linear;


    private Drawable expandDrw;
    private Drawable collapseDrw;
    private Boolean moodTrackerIsExpanded = false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        quoteTextView = (TextView) root.findViewById(R.id.quoteTextView);

        //Get the current day retrieve data for the last day
        int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        String last_update = SharedPreferencesClass.retriveData(getContext(),"last_quote_update");
        String lastQuote = SharedPreferencesClass.retriveData(getContext(),"last_quote");
        if(last_update.equals("no_data_found")){
            last_update = "0";
        }
        int lastDate = Integer.parseInt(last_update);

        // Perform actions that will only occur once every day
        // Ex: daily quotes
        if(lastDate < dayOfYear){
            String[] arr = getResources().getStringArray(R.array.quotes);
            int ran = new Random().nextInt((arr.length));
            quoteTextView.setText(arr[ran]);

            SharedPreferencesClass.insertData(getContext(), "last_quote_update", String.valueOf(dayOfYear));
            SharedPreferencesClass.insertData(getContext(), "last_quote", String.valueOf(arr[ran]));
        }
        else{
            quoteTextView.setText(lastQuote);
        }

        expand = (Button) root.findViewById(R.id.mood_tracker_expand_collapse);
        Button happy = (Button) root.findViewById(R.id.mood_tracker_happy);
        Button sad = (Button) root.findViewById(R.id.mood_tracker_sad);
        Button tired = (Button) root.findViewById(R.id.mood_tracker_tired);
        expand.setOnClickListener(this);
        happy.setOnClickListener(this);
        sad.setOnClickListener(this);
        tired.setOnClickListener(this);

        mood_tracker = (ConstraintLayout) root.findViewById(R.id.mood_tracker);
        expandDrw = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_group_expand_00, null);
        collapseDrw = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_group_collapse_00, null);
        linear = (LinearLayout) root.findViewById(R.id.linearLayout);
        //mood_tracker.getLayoutParams().height = linear.getHeight() + 80;
        //mood_tracker.requestLayout();
        return root;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.mood_tracker_expand_collapse:
                if(moodTrackerIsExpanded) {
                    mood_tracker.getLayoutParams().height = linear.getHeight() + 80;
                    mood_tracker.requestLayout();
                    moodTrackerIsExpanded = false;
                    expand.setBackground(expandDrw);
                }
                else{
                    mood_tracker.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    mood_tracker.requestLayout();
                    expand.setBackground(collapseDrw);
                    moodTrackerIsExpanded = true;
                }

                break;
            case R.id.mood_tracker_happy:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.mood_tracker_sad:

                break;

        }
    }

}