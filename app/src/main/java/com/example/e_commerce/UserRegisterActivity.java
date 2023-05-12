package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userName, email, pass, confirmPass;
    private Button submit;
    private FirebaseAuth Auth;
    private ProgressBar progress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        Auth = FirebaseAuth.getInstance();

        userName = (EditText) findViewById(R.id.txtUserName);
        email = (EditText) findViewById(R.id.txtEmail);
        pass = (EditText) findViewById(R.id.txtPass);
        confirmPass = (EditText) findViewById(R.id.txtConfirmPass);

        submit = (Button) findViewById(R.id.btnSubmitRegister);
        submit.setOnClickListener(this);

        progress = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSubmitRegister:
                register();
                break;
        }
    }

    private void register() {
        String tUserName = userName.getText().toString().trim();
        String tEmail = email.getText().toString().trim();
        String tPass = pass.getText().toString().trim();
        String tConfirmPass = confirmPass.getText().toString().trim();

        if (tUserName.isEmpty()){
            userName.setError("User Name is required!");
            userName.requestFocus();
            return;
        }
        if (tEmail.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(tEmail).matches()){
            email.setError("Invalid email!");
            email.requestFocus();
            return;
        }
        if (tPass.isEmpty()){
            pass.setError("Password is required!");
            pass.requestFocus();
            return;
        }
        if (tPass.length() <= 5){
            pass.setError("Min password length should be more then 5 characters!");
            pass.requestFocus();
            return;
        }
        else if (tConfirmPass.equals(tPass)){
            confirmPass.requestFocus();
        } else {
            confirmPass.setError("Please check your password again!");
            return;
        }

        progress.setVisibility(View.VISIBLE);
        Auth.createUserWithEmailAndPassword(tEmail, tPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(tUserName, tEmail, tPass);

                    FirebaseDatabase.getInstance("https://final-project-44dce-default-rtdb.asia-southeast1.firebasedatabase.app/")
                            .getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progress.setVisibility(View.GONE);
                                        finish();
                                        Toast.makeText(UserRegisterActivity.this, "User has been registered successfully!",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        progress.setVisibility(View.GONE);
                                        Toast.makeText(UserRegisterActivity.this, "Failed to register, try again!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(UserRegisterActivity.this, "Email already exists!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}