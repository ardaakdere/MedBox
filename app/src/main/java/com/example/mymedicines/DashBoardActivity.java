package com.example.mymedicines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class DashBoardActivity extends AppCompatActivity {
    TextView customer_name;
    CardView go_to_medicines, go_to_notes, go_to_profile;
    private PeriodicWorkRequest periodicWorkRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        periodicWorkRequest
                = new PeriodicWorkRequest.Builder(NotifyWorker.class, 30, TimeUnit.MINUTES)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .build();

        try {
            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                    "Remember",
                    ExistingPeriodicWorkPolicy.KEEP,
                    periodicWorkRequest);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        customer_name = findViewById(R.id.customer_name);
        go_to_medicines = findViewById(R.id.go_to_medicines);
        go_to_notes = findViewById(R.id.go_to_notes);
        go_to_profile = findViewById(R.id.go_to_profile);

        Intent gelenIntent = getIntent();
        String username = gelenIntent.getStringExtra("username");
        customer_name.setText(username);

        go_to_medicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, MainActivity.class));
            }
        });

        go_to_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, NoteActivity.class));
            }
        });

        go_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, ProfileActivity.class));
            }
        });


    }
}