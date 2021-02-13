package com.example.refereeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.fragment.app.Fragment;

public class Login extends AppCompatActivity {

    public EditText email;
    public EditText password;
    public Button login;
    public Button register;
    public TextView result;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPass);
        login = (Button) findViewById(R.id.btnLogin);
        register = (Button) findViewById(R.id.btnRegister);
        result = (TextView) findViewById(R.id.txtResult);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent nav = new Intent(getBaseContext(),nav.class);
                            nav.putExtra("key",email.getText().toString());
                            startActivity(nav);
                        } else {
                            result.setText("Email or password incorrect");
                        }
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getBaseContext(),Register.class);
                startActivity(register);
            }
        });
    }
}