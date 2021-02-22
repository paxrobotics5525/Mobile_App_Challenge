package pax.mesa.tbd.ui.forums;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;

public class ForumsFragment extends Fragment {

    private ForumsViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(ForumsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_forums, container, false);
        


        return root;
    }
}