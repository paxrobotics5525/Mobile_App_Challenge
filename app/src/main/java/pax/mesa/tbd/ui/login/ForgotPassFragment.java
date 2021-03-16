package pax.mesa.tbd.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassFragment extends Fragment {
    private FirebaseAuth mAuth;

    public ForgotPassFragment() {
        super(R.layout.fragment_forgot_pass);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_forgot_pass, container, false);

        //show action bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        mAuth = FirebaseAuth.getInstance();

        Button buttonReset = (Button) root.findViewById(R.id.b_reset);
        EditText targetEmail = (EditText) root.findViewById(R.id.i_forgotEmail);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = targetEmail.getText().toString();

                //If email is invalid, ask for new
                //If email is valid, send email
                if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getActivity(), "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                //Go back to login
                                NavDirections action = ForgotPassFragmentDirections.actionForgotToLogin();
                                Navigation.findNavController(getView()).navigate(action);

                                Toast.makeText(getActivity(), "Email sent.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "An error occurred and the email was not sent.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
        return root;
    }
}