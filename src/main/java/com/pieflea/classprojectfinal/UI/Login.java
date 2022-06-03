package com.pieflea.classprojectfinal.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pieflea.classprojectfinal.R;

public class Login extends AppCompatActivity {
    EditText userNameInput;
    EditText passWordInput;
    String userName;
    String passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameInput = findViewById(R.id.usernameInput);
        passWordInput = findViewById(R.id.passwordInput);
    }

    public void attemptLogin(View view) {
        userName = userNameInput.getText().toString();
        passWord = passWordInput.getText().toString();
        String loginCred = "test";
        if (userName.equals(loginCred) && passWord.equals(loginCred)){
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        }
        else{
            Toast.makeText(this, "Invalid Username or password, username and password are both 'test'", Toast.LENGTH_LONG).show();
        }
    }
}