package com.naman.firebasequizapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naman.firebasequizapp.QuestionsModel;
import com.naman.firebasequizapp.R;

import java.util.ArrayList;

public class MainSoalActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private Button btnAdd;

    //tambahkan kode ini
    private SoalAdapter adapter;
    private ArrayList<QuestionsModel> soalList;
    DatabaseReference dbMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_soal);

        dbMahasiswa = FirebaseDatabase.getInstance().getReference("Questions");

        listView = findViewById(R.id.lv_list);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        //list mahasiswa
        soalList = new ArrayList<>();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Daftar Soal");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add) {
            Intent intent = new Intent(MainSoalActivity.this, CreateActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        dbMahasiswa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                soalList.clear();

                for (DataSnapshot mahasiswaSnapshot : dataSnapshot.getChildren()) {
                    QuestionsModel questionsModel = mahasiswaSnapshot.getValue(QuestionsModel.class);
                    soalList.add(questionsModel);
                }

                SoalAdapter adapter = new SoalAdapter(MainSoalActivity.this);
                adapter.setSoalList(soalList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainSoalActivity.this, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
            }
        });

        //kode yang ditambahkan
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainSoalActivity.this, UpdateActivity.class);
                intent.putExtra(UpdateActivity.EXTRA_MAHASISWA, soalList.get(i));

                startActivity(intent);
            }
        });
    }


}