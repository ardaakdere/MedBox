package com.example.mymedicines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {
    private EditText username_edittext, email_edittext, pass_edittext;
    private Button button_signup, register_to_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        username_edittext = findViewById(R.id.username_editext);
        email_edittext = findViewById(R.id.email_editext);
        pass_edittext = findViewById(R.id.pass_editext);

        button_signup = findViewById(R.id.button_signup);
        register_to_login = findViewById(R.id.register_to_login);

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference ref_user = database.getReference("userinfo/"+username_edittext.getText().toString());
                final String username = username_edittext.getText().toString();
                final String email = email_edittext.getText().toString();
                final String password = pass_edittext.getText().toString();
                ref_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        Log.e("Hata",""+value);
                        if(value != null)
                            Toast.makeText(RegisterActivity.this, "There is already an account with same username", Toast.LENGTH_SHORT).show();
                        else {
                            ref_user.setValue(username + "," + password + "," + email);
                            Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        register_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
}