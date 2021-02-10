package pax.mesa.tbd.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import pax.mesa.tbd.SharedPreferencesClass;

import java.util.Random;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView quoteTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        quoteTextView = (TextView) root.findViewById(R.id.quoteTextView);

        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String last_update = SharedPreferencesClass.retriveData(getContext(),"last_quote_update");
        String lastQuote = SharedPreferencesClass.retriveData(getContext(),"last_quote");
        if(last_update == "no_data_found"){
            last_update = "0";
        }
        int lastDate = Integer.parseInt(last_update);

        if(lastDate < dayOfMonth){
            String[] arr = getResources().getStringArray(R.array.quotes);
            int ran = new Random().nextInt((arr.length));
            quoteTextView.setText(arr[ran]);

            SharedPreferencesClass.insertData(getContext(), "last_quote_update", String.valueOf(dayOfMonth));
            SharedPreferencesClass.insertData(getContext(), "last_quote", String.valueOf(arr[ran]));
        }
        else{
            quoteTextView.setText(lastQuote);
        }

        return root;
    }
}