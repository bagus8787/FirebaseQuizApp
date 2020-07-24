package com.naman.firebasequizapp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naman.firebasequizapp.R;

public class NilaiActivity extends AppCompatActivity {
    DatabaseReference root= FirebaseDatabase.getInstance().getReference().child("Questions");

    DatabaseReference nilai_kuis = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference nilai_benar_firebase = nilai_kuis.child("nilai_benar");
    final DatabaseReference nilai_salah_firebase = nilai_kuis.child("nilai_salah");

    TextView nilai_b, nilai_s;
    Button edt_edit_nilai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai);

        nilai_b = findViewById(R.id.nilai_benar);
        nilai_s = findViewById(R.id.nilai_salah);
        edt_edit_nilai = findViewById(R.id.button_edit_nilai);

        edt_edit_nilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NilaiActivity.this, UpdateNilaiActivity.class));
            }
        });

        nilai_benar_firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Integer value = dataSnapshot.getValue(Integer.class);
//                nilai_benar = value;
                nilai_b.setText(String.valueOf(value));
                Log.d("file", "Value is: " + value);

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("File", "Failed to read value.", error.toException());

            }
        });

        nilai_salah_firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Integer value = dataSnapshot.getValue(Integer.class);
//                nilai_salah = value;
                nilai_s.setText(String.valueOf(value));
                Log.d("file", "Value is: " + value);

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("File", "Failed to read value.", error.toException());

            }
        });

        getSupportActionBar().setTitle("Nilai");
    }
}
