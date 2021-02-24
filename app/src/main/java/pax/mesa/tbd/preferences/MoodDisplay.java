package pax.mesa.tbd.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

import android.preference.Preference;

import com.example.myapplication.R;

public class MoodDisplay extends Preference {

    public MoodDisplay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWidgetLayoutResource(R.layout.mood_display);
    }


}
