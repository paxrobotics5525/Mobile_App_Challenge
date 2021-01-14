package com.example.myapplication.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.SharedPreferencesClass;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MoodTrackerFragment extends Fragment{

    private Button expand;
    private View root;
    private LinearLayout linear;


    private Drawable expandDrw;
    private Drawable collapseDrw;
    private Boolean moodTrackerIsExpanded = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mood_tracker, container, false);

        // Setup all of the mood tracker buttons and on click liteners
        expand = (Button) root.findViewById(R.id.mood_tracker_expand_collapse);
        Button happy = (Button) root.findViewById(R.id.mood_tracker_happy);
        Button sad = (Button) root.findViewById(R.id.mood_tracker_sad);
        Button exhausted = (Button) root.findViewById(R.id.mood_tracker_exhausted);

        Button funny = (Button) root.findViewById(R.id.mood_tracker_funny);
        Button cool = (Button) root.findViewById(R.id.mood_tracker_cool);
        Button mindblown = (Button) root.findViewById(R.id.mood_tracker_mindblown);

        Button angry = (Button) root.findViewById(R.id.mood_tracker_angry);
        Button tired = (Button) root.findViewById(R.id.mood_tracker_tired);
        Button sick = (Button) root.findViewById(R.id.mood_tracker_sick);

        expand.setOnClickListener(onClick);

        happy.setOnClickListener(onClick);
        sad.setOnClickListener(onClick);
        exhausted.setOnClickListener(onClick);

        funny.setOnClickListener(onClick);
        cool.setOnClickListener(onClick);
        mindblown.setOnClickListener(onClick);

        angry.setOnClickListener(onClick);
        tired.setOnClickListener(onClick);
        sick.setOnClickListener(onClick);

        linear = (LinearLayout) root.findViewById(R.id.linearLayout);

        // Get the images for the expand and contract button
        expandDrw = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_group_expand_00, null);
        collapseDrw = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_group_collapse_00, null);
        return root;
    }

    private final View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mood_tracker_expand_collapse:

                    // contract the fragment
                    if(moodTrackerIsExpanded) {
                        root.getLayoutParams().height = linear.getHeight() + 80;
                        root.requestLayout();
                        moodTrackerIsExpanded = false;
                        expand.setBackground(expandDrw);
                    }
                    //expand the fragment
                    else{
                        root.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        root.requestLayout();
                        expand.setBackground(collapseDrw);
                        moodTrackerIsExpanded = true;
                    }

                    break;
                case R.id.mood_tracker_happy:
                    Snackbar.make(view, "Yay! Happy!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    selfDestruct('a');
                    break;
                case R.id.mood_tracker_sad:
                    Snackbar.make(view, "To bad... Feel better", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    selfDestruct('b');
                    break;
                case R.id.mood_tracker_exhausted:
                    Snackbar.make(view, "Drink some water", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    selfDestruct('c');
                    break;
                case R.id.mood_tracker_funny:
                    Snackbar.make(view, "Hehehehe", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    selfDestruct('d');
                    break;
                case R.id.mood_tracker_cool:
                    Snackbar.make(view, "Just keep chillin'", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    selfDestruct('e');
                    break;
                case R.id.mood_tracker_mindblown:
                    Snackbar.make(view, "WoooooW", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    selfDestruct('f');
                    break;
                case R.id.mood_tracker_angry:
                    Snackbar.make(view, "Try deep breathing", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    selfDestruct('g');
                    break;
                case R.id.mood_tracker_tired:
                    Snackbar.make(view, "Get moving!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    selfDestruct('h');
                    break;
                case R.id.mood_tracker_sick:
                    Snackbar.make(view, "Get well soon!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    selfDestruct('i');
                    break;

            }
        }
    };
    private void selfDestruct(char data){
        try{
            // Form a string that contains the mood chosen and the date
            // Then write it to mood_tracker.txt

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy,kk");
            Date date = Calendar.getInstance().getTime();
            DecimalFormat format= new DecimalFormat("00");

            String output = data + "," + dateFormat.format(date) + "\n";

            String filename = getContext().getFilesDir().getPath() + "/mood_tracker.txt";
            FileOutputStream stream = new FileOutputStream(filename, true);
            stream.write(output.getBytes(Charset.defaultCharset()));
            SharedPreferencesClass.lastMoodCheck = date;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Delete the mood tracker fragment
        getParentFragmentManager().beginTransaction().remove(this).commit();
    }
}
