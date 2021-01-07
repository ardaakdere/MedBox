package com.example.mymedicines;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MedicineEdit extends AppCompatActivity {
    String name;
    String exp_date;
    int piece;
    String days;
    int id;
    EditText editText_name;
    EditText editText_date;
    EditText editText_piece;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;
    TextView textView_ledName;
    Button save_button;
    String[] daysArray;
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    String daysfromchecks = "";
    Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_edit);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        editText_name = findViewById(R.id.editText_name);
        editText_date = findViewById(R.id.editText_date);
        editText_piece = findViewById(R.id.editText_piece);

        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);

        textView_ledName = findViewById(R.id.textView_ledName);

        checkBoxes.add(checkBox1);
        checkBoxes.add(checkBox2);
        checkBoxes.add(checkBox3);
        checkBoxes.add(checkBox4);
        checkBoxes.add(checkBox5);
        checkBoxes.add(checkBox6);
        checkBoxes.add(checkBox7);

        save_button = findViewById(R.id.save_button);

        name = getIntent().getStringExtra("name");
        exp_date = getIntent().getStringExtra("exp_date");
        piece = getIntent().getIntExtra("piece", 0);
        days = getIntent().getStringExtra("days");
        id = getIntent().getIntExtra("led_id", 0);
        medicine = (Medicine) getIntent().getSerializableExtra("medicine_object");

        textView_ledName.setText(name+" - "+id);

        daysArray = days.split("(?!^)");

        for(String day : daysArray)
        {
            Log.e("gün", day);
            switch (day)
            {
                case "1":
                    checkBox1.setChecked(true);
                    break;
                case "2":
                    checkBox2.setChecked(true);
                    break;
                case "3":
                    checkBox3.setChecked(true);
                    break;
                case "4":
                    checkBox4.setChecked(true);
                    break;
                case "5":
                    checkBox5.setChecked(true);
                    break;
                case "6":
                    checkBox6.setChecked(true);
                    break;
                case "7":
                    checkBox7.setChecked(true);
                    break;
            }
        }

        editText_name.setText(name);
        editText_piece.setText(""+piece);
        editText_date.setText(exp_date);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref_days = database.getReference("Gunler/day"+(id));
                DatabaseReference ref_pieces = database.getReference("Pieces/piece"+(id));
                DatabaseReference ref_dates = database.getReference("Dates/date"+(id));
                DatabaseReference ref_names = database.getReference("Names/name"+(id));


                ref_dates.setValue(editText_date.getText().toString());

                ref_pieces.setValue(editText_piece.getText().toString());

                ref_names.setValue(editText_name.getText().toString());

                for(CheckBox x : checkBoxes)
                {
                    if(x.isChecked())
                    {
                        daysfromchecks += x.getTag().toString();
                    }
                }
                ref_days.setValue(daysfromchecks);
                daysfromchecks = "";

                Toast.makeText(MedicineEdit.this,"Saved Successfully!", Toast.LENGTH_LONG).show();

                startActivity(new Intent(MedicineEdit.this, MainActivity.class));

            }
        });





    }
}