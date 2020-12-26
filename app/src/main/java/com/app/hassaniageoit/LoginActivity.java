package com.app.hassaniageoit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail,inputPassword;
    private Button loginbtn;
    private TextView goRegister;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();


        inputEmail = findViewById(R.id.inputEmailID);
        inputPassword = findViewById(R.id.inputPasswordID);
        loginbtn = (Button) findViewById(R.id.loginbtnID);
        goRegister = (TextView) findViewById(R.id.goRegisterID);
        mProgressDialog = new ProgressDialog(this);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

    }

    @Override
    protected void onStart() {

        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, registerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        super.onStart();
    }

    private void Login() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Write your email adresse!",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Write your password!",Toast.LENGTH_LONG).show();
            return;
        }

        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        mProgressDialog.setCanceledOnTouchOutside(false);
        mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"Succesfully Registered!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this,"Succesfully Failed x) x)",Toast.LENGTH_LONG).show();
                }
                mProgressDialog.dismiss();
            }
        });

    }


    }
