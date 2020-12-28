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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private ImageView googleSignIn;
    private EditText inputEmail,inputPassword;
    private Button loginbtn;
    private TextView goRegister;
    private ProgressDialog mProgressDialog;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onStart() {

       /* FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
        }*/

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();


        inputEmail = findViewById(R.id.inputEmailID);
        inputPassword = findViewById(R.id.inputPasswordID);
        loginbtn = (Button) findViewById(R.id.loginbtnID);
        goRegister = (TextView) findViewById(R.id.goRegisterID);
        googleSignIn = findViewById(R.id.loginGglID);
        mProgressDialog = new ProgressDialog(this);

        createRequest();

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

    }

    private void createRequest() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this,"Failed",Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });
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
