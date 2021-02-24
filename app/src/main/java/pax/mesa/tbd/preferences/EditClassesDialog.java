package pax.mesa.tbd.preferences;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;

import java.util.List;

import pax.mesa.tbd.preferences.SettingsFragment;

public class EditClassesDialog extends Dialog {
    private EditText searchEdit;
    private ListView classesList;
    ArrayAdapter<String> adapter = null;

    public EditClassesDialog(Context context, String[] list, Boolean adding, SettingsFragment settings) {
        super(context);
        /** Design the dialog in main.xml file */
        setContentView(R.layout.edit_classes);
        setTitle("Choose A Class");
        searchEdit = (EditText) findViewById(R.id.searchEdit);
        searchEdit.addTextChangedListener(filterTextWatcher);
        classesList = (ListView) findViewById(R.id.classesList);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
        classesList.setAdapter(adapter);
        classesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //Log.d("TAG", "Selected Item is = "+classesList.getItemAtPosition(position));
                if(adding){
                    settings.addClass(classesList.getItemAtPosition(position).toString());
                }
                else{
                    settings.removeClass(classesList.getItemAtPosition(position).toString());
                }
                dismiss();
            }
        });
    }
    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            adapter.getFilter().filter(s);
        }
    };
    @Override
    public void onStop(){
        searchEdit.removeTextChangedListener(filterTextWatcher);
    }
}
