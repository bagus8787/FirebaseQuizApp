package com.naman.firebasequizapp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naman.firebasequizapp.HomeActivity;
import com.naman.firebasequizapp.QuestionsModel;
import com.naman.firebasequizapp.QuizActivity;
import com.naman.firebasequizapp.R;

public class CreateKategoriActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtKategori;
    private Button btnSaveCat;

//    private QuestionsModel questionsModel;
    private CategoryModel categoryModel;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_kategori);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtKategori = findViewById(R.id.edt_kategori);

        btnSaveCat = findViewById(R.id.btn_save_kategori);

        btnSaveCat.setOnClickListener(this);

        categoryModel = new CategoryModel();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tambah Kategori");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_save_kategori) {
            saveMahasiswa();

            Intent intent = new Intent(CreateKategoriActivity.this, ListKategoriActivity.class);
            startActivity(intent);
        }

    }

    private void saveMahasiswa()
    {
        String kategori = edtKategori.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(kategori)) {
            isEmptyFields = true;
            edtKategori.setError("Field ini tidak boleh kosong");
        }

        if (! isEmptyFields) {

            Toast.makeText(CreateKategoriActivity.this, "Saving Data...", Toast.LENGTH_SHORT).show();

            DatabaseReference dbMahasiswa = mDatabase.child("Questions");

            String id = dbMahasiswa.push().getKey();
            categoryModel.setId_kategori(id);
            categoryModel.setNama(kategori);

            //insert data
            dbMahasiswa.child(id).setValue(categoryModel);

            finish();

        }
    }
}
