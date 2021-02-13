package com.example.refereeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText email,password,first,last,league;
    private Button register;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        register = (Button) findViewById(R.id.btnRegister);
        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPass);
        first = (EditText) findViewById(R.id.txtFirst);
        last = (EditText) findViewById(R.id.txtLast);
        league = (EditText) findViewById(R.id.txtLeague);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = email.getText().toString();
                String inputPass = password.getText().toString();
                if (TextUtils.isEmpty(inputEmail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(inputPass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(inputEmail,inputPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(Register.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                        } else {
                            Map<String, Object> data = new HashMap<>();
                            data.put("firstName", first.getText().toString());
                            data.put("lastName",last.getText().toString());
                            data.put("league",league.getText().toString());
                            db.collection("users").document(inputEmail).set(data);
                            Intent nav = new Intent(getBaseContext(),nav.class);
                            nav.putExtra("key",email.getText().toString());
                            startActivity(nav);
                        }
                    }
                });
            }
        });
    }
}