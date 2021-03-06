package com.apps.shreya.chatapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton; */
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import org.w3c.dom.Text;


public class LoginPageOne extends AppCompatActivity {

    private TextView forgot_password, page_signup;
    // LoginButton login_with_fb;
    private Button login_button;
    private TextInputLayout emailAddress;
    private TextInputLayout password;
    //progress dialog
    private ProgressDialog mLoginProgress;
    // firebase auth
    private FirebaseAuth mAuth;
    //  CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Toast.makeText(this, "on Create", Toast.LENGTH_SHORT).show();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_page_one);

        initializeControls();
        //loginWithFb();
        setUser();   //checks whether the username and password is empty


        //AccessToken accessToken = AccessToken.getCurrentAccessToken();
        //boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


    }
/*
    //to send the notification for no internet connection

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) {
                return true;
            } else{ return false;}
        } else{ return false;}
    }

    //to hide all data

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
*/
    private void setUser() {
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user  = emailAddress.getEditText().getText().toString().trim();
                String pass_text = password.getEditText().getText().toString().trim();
/*
// checking the internet connnection
                if(!isConnected(LoginPageOne.this)){ buildDialog(LoginPageOne.this).show();}
                else {
                    Toast.makeText(LoginPageOne.this,"Welcome", Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.activity_main);
                }
*/
                if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass_text)) {


                    mLoginProgress.setTitle("Logging In");
                    mLoginProgress.setMessage("Please wait while we check your credentials.");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                    loginUser(user, pass_text);

                }

            }
        });


             // if user dont have account
        page_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signing_page = new Intent(LoginPageOne.this, SignUpPage.class);
                startActivity(signing_page);
            }
        });
        // if user forgot the password
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgot_pass = new Intent(LoginPageOne.this, ForgotPasswordActivity.class);
                startActivity(forgot_pass);
            }
        });
    }


    private void loginUser(String user, String pass_text) {

        mAuth.signInWithEmailAndPassword(user, pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    mLoginProgress.dismiss();
                    Intent mainIntent = new Intent(LoginPageOne.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                } else {
                    /*
                    String error = "";

                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException e) {
                        error = "Invalid Email!";
                    }
                    catch (FirebaseAuthInvalidCredentialsException e) {
                        error = "Invalid Password!";
                    }
                    catch (Exception e) {
                        error = "Default error!";
                        e.printStackTrace();
                    }
*/
                    mLoginProgress.hide();
                    Toast.makeText(LoginPageOne.this, "Cannot Login. Please check the form and try again.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void initializeControls() {
        // callbackManager = CallbackManager.Factory.create();
        //  login_with_fb = (LoginButton)findViewById(R.id.login_button);
        mLoginProgress = new ProgressDialog(this);
        login_button = (Button) findViewById(R.id.btn_login);
        emailAddress = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        forgot_password = (TextView) findViewById(R.id.tv_forgotpass);
        page_signup = (TextView) findViewById(R.id.tv_signup);
        mAuth = FirebaseAuth.getInstance();

    }
}
/*

    private void loginWithFb(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    */

