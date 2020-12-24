package com.app.hassaniageoit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputUsername,inputEmail,inputPassword;
    private Button registerBtn;
    private TextView returnLogin;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();

        inputUsername = findViewById(R.id.usernameID);
        inputEmail = findViewById(R.id.emailID);
        inputPassword = findViewById(R.id.passwordID);
        registerBtn = (Button) findViewById(R.id.loginbtnID);
        returnLogin = (TextView) findViewById(R.id.registerBtnID);
        mProgressDialog = new ProgressDialog(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Register() {
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Write your username!",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Write your email adresse!",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Write your password!",Toast.LENGTH_LONG).show();
            return;
        }
        else if(password.length()<6){
            Toast.makeText(this,"Comeon! Write a stronger password!",Toast.LENGTH_LONG).show();
            return;
        }
        else if(!isValidEmail(email)){
            Toast.makeText(this,"Email is Invalid",Toast.LENGTH_LONG).show();
            return;
        }
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        mProgressDialog.setCanceledOnTouchOutside(false);
        mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this,"Succesfully Registered!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Succesfully Failed x) x)",Toast.LENGTH_LONG).show();
                }
                mProgressDialog.dismiss();
            }
        });

    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}