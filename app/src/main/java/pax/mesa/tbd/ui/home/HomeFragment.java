package pax.mesa.tbd.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;

import pax.mesa.tbd.Prefs;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Calendar;
import java.util.Date;

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
        if(savedInstanceState != null) return root;
        // Create the needed objects from the resource file
        quoteTextView = (TextView) root.findViewById(R.id.quoteTextView);
        linear = (LinearLayout) root.findViewById(R.id.home_linear);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy,kk");

        // Check whether or not to update the daily quote
        if(getTimeDifference(Calendar.getInstance().getTime(), Prefs.lastQuoteUpdate) > 24){
            String[] arr = getResources().getStringArray(R.array.quotes);
            int ran = new Random().nextInt((arr.length));
            quoteTextView.setText(arr[ran]);

            Prefs.insertString(getContext(), "last_quote_update", dateFormat.format(date));
            Prefs.insertString(getContext(), "last_quote", String.valueOf(arr[ran]));
        }
        else{
            quoteTextView.setText(Prefs.lastQuote);
        }

        // Check whether or not to take a mood check
        if(getTimeDifference(Calendar.getInstance().getTime(), Prefs.lastMoodCheck) > 24){
            addFragment(new com.example.myapplication.ui.home.MoodTrackerFragment());
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