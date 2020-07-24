package com.naman.firebasequizapp.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naman.firebasequizapp.HomeActivity;
import com.naman.firebasequizapp.QuestionsModel;
import com.naman.firebasequizapp.R;

public class UpdateNilaiActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNilaibenar, edtNilaisalah;
    private Button btnUpdate;

    public static final String EXTRA_CATEGORY = "extra_category";
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;

    DatabaseReference nilai_kuis = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference nilai_benar_firebase = nilai_kuis.child("nilai_benar");
    final DatabaseReference nilai_salah_firebase = nilai_kuis.child("nilai_salah");

    private NilaiModel nilaiModel;
    private String soalId, kategori_id;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nilai);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtNilaibenar = findViewById(R.id.edt_edit_nilai_benar);
        edtNilaisalah = findViewById(R.id.edt_edit_nilai_salah);

        btnUpdate = findViewById(R.id.btn_update_nilai);
        btnUpdate.setOnClickListener(this);

     nilai_benar_firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Integer value = dataSnapshot.getValue(Integer.class);
//                nilai_benar = value;
                edtNilaibenar.setText(String.valueOf(value));
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
                edtNilaisalah.setText(String.valueOf(value));
                Log.d("file", "Value is: " + value);

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("File", "Failed to read value.", error.toException());

            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Nilai");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update_nilai) {
            updateMahasiswa();

        }

    }

    private void updateMahasiswa() {
        String nilai_benar_d = edtNilaibenar.getText().toString().trim();
        String nilai_salah_d = edtNilaisalah.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(nilai_benar_d)) {
            isEmptyFields = true;
            edtNilaibenar.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(nilai_salah_d)) {
            isEmptyFields = true;
            edtNilaisalah.setError("Field ini tidak boleh kosong");
        }

        if (! isEmptyFields) {

            Toast.makeText(UpdateNilaiActivity.this, "Updating Data...", Toast.LENGTH_SHORT).show();

//            nilaiModel.setNilai_benar(nilai_benar_d);
//            nilaiModel.setNilai_salah(nilai_salah_d);

//            DatabaseReference dbMahasiswa = mDatabase.child("n");

            //update data
//            mDatabase.setValue(nilaiModel);
//            dbMahasiswa.setValue(nilai_salah);

            nilai_kuis.child("nilai_benar").setValue(Integer.parseInt(nilai_benar_d));
            nilai_kuis.child("nilai_salah").setValue(Integer.parseInt(nilai_salah_d));

            finish();

        }
    }
}
