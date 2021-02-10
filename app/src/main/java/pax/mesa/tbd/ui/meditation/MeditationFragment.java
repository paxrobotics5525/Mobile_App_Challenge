package pax.mesa.tbd.ui.meditation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;

public class MeditationFragment extends Fragment {

    private MeditationViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(MeditationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_meditation, container, false);
        return root;
    }
}