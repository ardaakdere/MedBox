package com.example.mymedicines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.Calendar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView_today;
    private RecyclerView rv;
    private ArrayList<Medicine> medicineList;
    private MedicineAdapter adapter;
    Button led1, led2, led3, led4;
    boolean led11,led22,led33,led44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //MAINACTIVITY EN BASA GUN YAZDIRMA
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        textView_today = findViewById(R.id.textView_today);
        Intent intent  = getIntent();
        String username = intent.getStringExtra("username");

        switch (day) {
            case Calendar.SUNDAY:
                textView_today.setText("SUNDAY");
                break;
            case Calendar.MONDAY:
                textView_today.setText("MONDAY");
                break;
            case Calendar.TUESDAY:
                textView_today.setText("TUESDAY");
                break;
            case Calendar.WEDNESDAY:
                textView_today.setText("WEDNESDAY");
                break;
            case Calendar.THURSDAY:
                textView_today.setText("THURSDAY");
                break;
            case Calendar.FRIDAY:
                textView_today.setText("FRIDAY");
                break;
            case Calendar.SATURDAY:
                textView_today.setText("SATURDAY"+"-"+username);
                break;
        }


        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        Medicine m1 = new Medicine(1,"Dolven","12/10/2024",0,false,"123");
        Medicine m2 = new Medicine(2,"Aspirin","14/09/2021",0,false,"234");
        Medicine m3 = new Medicine(3,"Kereviz","20/05/2022",0, false,"12");
        Medicine m4 = new Medicine(4,"Kereviz2","20/05/2023",0, false,"13");

        medicineList = new ArrayList<>();

        medicineList.add(m1);
        medicineList.add(m2);
        medicineList.add(m3);
        medicineList.add(m4);

        adapter = new MedicineAdapter(this,medicineList);

        rv.setAdapter(adapter);



        /*led11=false;
        led22=false;
        led33=false;
        led44=false;


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference led1_ref = database.getReference("led1");
        final DatabaseReference led2_ref = database.getReference("led2");
        final DatabaseReference led3_ref = database.getReference("led3");
        final DatabaseReference led4_ref = database.getReference("led4");

        //led3_ref.setValue("0");



        // Read from the database
        led1_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("DEGER", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEGER", "Failed to read value.", error.toException());
            }
        });
         */


    }
}