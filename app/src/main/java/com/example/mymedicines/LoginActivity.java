package com.example.mymedicines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {
    private EditText username_editext, pass_editext;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        username_editext = findViewById(R.id.username_editext);
        pass_editext = findViewById(R.id.pass_editext);
        login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref_user = database.getReference("userinfo/"+username_editext.getText().toString());

                ref_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        if (value == null)
                        {
                            Toast.makeText(LoginActivity.this, "Böyle bir kullanıcı bulunamadı", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(pass_editext.getText().toString().equals(value.split(",")[1]))
                            {
                                String username2 = username_editext.getText().toString();
                                SharedPreferences sp = getSharedPreferences("username", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putString("username", username2);
                                edit.apply();
                                Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                intent.putExtra("username", username2);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Yanlış şifre girdiniz", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("uyarı","böyle bi kullanıcı yok");
                    }
                });

            }
        });
    }
}