package pax.mesa.tbd.ui.forums;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavAction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.myapplication.R;

import pax.mesa.tbd.Prefs;
import pax.mesa.tbd.ui.forums.ClassHomeScreen;

public class ForumsFragment extends Fragment {

    private ForumsViewModel forumsViewModel;
    private View root;
    private ListView listView;
    private String[] classes;
    ArrayAdapter<String> adapter = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        forumsViewModel = new ViewModelProvider(this).get(ForumsViewModel.class);
        root = inflater.inflate(R.layout.fragment_forums, container, false);

        String list = Prefs.getPrefs(getContext()).getString("classes", "");
        classes = list.split("\t");
        listView = (ListView) root.findViewById(R.id.forums_list);
        if (list == "") {
            //TODO: Check for no data
        }
        else {
            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, classes);
            listView.setAdapter(adapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ForumsFragmentDirections.ActionForumsToClass action = ForumsFragmentDirections.actionForumsToClass();
                action.setClassName(classes[position]);
                Navigation.findNavController(root).navigate(action);
            }
        });
        return root;
    }
}