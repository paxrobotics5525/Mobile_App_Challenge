package pax.mesa.tbd.ui.login;

import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.concurrent.Executor;

import pax.mesa.tbd.ui.createAccount.CreateAccountFragment;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private FirebaseAuth mAuth;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mAuth = FirebaseAuth.getInstance();

        //hide action bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        Button buttonLogin = root.findViewById(R.id.b_Login);
        TextView buttonCreate = root.findViewById(R.id.b_createLink);
        TextView buttonForgot = root.findViewById(R.id.b_forgotPass);
        EditText inputEmail = root.findViewById(R.id.i_Email);
        EditText inputPassword = root.findViewById(R.id.i_Password);

        //check if "login" button is pressed
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String targetEmail = inputEmail.getText().toString();
                String targetPassword = inputPassword.getText().toString();

                validate(targetEmail, targetPassword);
            }
        });

        //check if "create" button is pressed
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show action bar
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();

                //Go to create account screen
                NavDirections action = LoginFragmentDirections.actionLoginToCreate();
                Navigation.findNavController(getView()).navigate(action);
            }
        });

        //check if "forgot pass" is pressed
        buttonForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to forgot pass screen
                NavDirections action = LoginFragmentDirections.actionLoginToForgot();
                Navigation.findNavController(getView()).navigate(action);
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            //show app bar and go to home screen
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
            NavDirections action = LoginFragmentDirections.actionLoginToHome();
            Navigation.findNavController(getView()).navigate(action);

            Toast.makeText(getActivity(), "Logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    //function to check if login is valid
    private void validate(String email, String password) {
        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "The email or password field was left blank.", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        // Sign in failed
                        Toast.makeText(getActivity(), "Login failed.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Sign in gucci
                        Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                        //show app bar again lol
                        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                        //Go to home screen
                        NavDirections action = LoginFragmentDirections.actionLoginToHome();
                        Navigation.findNavController(getView()).navigate(action);
                    }
                }
            });
        }
    }
}