package com.example.ac_twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginUserName,edtLoginPassword;
    private Button btnUserLogin,btnUserSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        edtLoginUserName = findViewById(R.id.edtLoginUserName);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        btnUserLogin = findViewById(R.id.btnUserLogin);
        btnUserSignUp = findViewById(R.id.btnUserSignUp);

        btnUserLogin.setOnClickListener(this);
        btnUserSignUp.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){
            //ParseUser.getCurrentUser().logOut();
            transitionToTwitterActivity();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnUserLogin:
                if (edtLoginUserName.getText().toString().equals("") || edtLoginPassword.getText().toString().equals("")){
                    FancyToast.makeText(Login.this,"UserName and Password is required.",
                            FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                }else{
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Logging In...");
                    progressDialog.show();
                    ParseUser.logInInBackground(edtLoginUserName.getText().toString(), edtLoginPassword.getText().toString(),
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null){
                                        FancyToast.makeText(Login.this,"Login successful.",
                                                FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                        transitionToTwitterActivity();
                                    }else {
                                        FancyToast.makeText(Login.this,e.getMessage(),
                                                FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
                }
                break;
            case R.id.btnUserSignUp:
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void transitionToTwitterActivity(){
        Intent intent = new Intent(Login.this,TwitterActivity.class);
        startActivity(intent);
        finish();
    }
}
