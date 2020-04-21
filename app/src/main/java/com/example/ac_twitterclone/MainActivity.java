package com.example.ac_twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSignUpEmail,edtSignUpUserName,edtSignUpPassword;
    private Button btnSignUp,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();
        setTitle("SignUp");

        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpUserName = findViewById(R.id.edtLoginUserName);
        edtSignUpPassword = findViewById(R.id.edtLoginPassword);

        btnLogin = findViewById(R.id.btnUserLogin);
        btnSignUp = findViewById(R.id.btnUserSignUp);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){
            //ParseUser.getCurrentUser().logOut();
            transitionToTwitterActivity();
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnUserLogin:
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                break;
            case R.id.btnUserSignUp:
                if (edtSignUpUserName.getText().toString().equals("") ||
                        edtSignUpEmail.getText().toString().equals("") ||
                        edtSignUpPassword.getText().toString().equals("")){
                    FancyToast.makeText(MainActivity.this,"UserName, Email and Password are required.",
                            FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                }else{
                    ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtSignUpEmail.getText().toString());
                    appUser.setUsername(edtSignUpUserName.getText().toString());
                    appUser.setPassword(edtSignUpPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up...");
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                FancyToast.makeText(MainActivity.this,"Signed Up Successfully.",
                                        FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                transitionToTwitterActivity();
                            }else {
                                FancyToast.makeText(MainActivity.this,e.getMessage(),
                                        FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
        }
    }


    public void rootLayoutTapped(View view){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void transitionToTwitterActivity(){
        Intent intent = new Intent(MainActivity.this,TwitterActivity.class);
        startActivity(intent);
        finish();
    }

}
