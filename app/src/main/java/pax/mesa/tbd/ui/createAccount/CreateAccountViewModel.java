package pax.mesa.tbd.ui.createAccount;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateAccountViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CreateAccountViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is create fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
