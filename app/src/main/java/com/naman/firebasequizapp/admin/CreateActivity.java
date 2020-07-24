package com.naman.firebasequizapp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naman.firebasequizapp.HomeActivity;
import com.naman.firebasequizapp.QuestionsModel;
import com.naman.firebasequizapp.QuizActivity;
import com.naman.firebasequizapp.R;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtOpt1, edtOpt2, edtOpt3, edtOpt4, edtAns, edtQues;
    private Button btnSave;
    private TextView edtIdkat;

    public static final String EXTRA_CATEGORY = "extra_category";

    private QuestionsModel questionsModel;

    private CategoryModel categoryModel;

    public String kategori_id, kategori_nama;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtOpt1 = findViewById(R.id.edt_option1);
        edtOpt2 = findViewById(R.id.edt_option2);
        edtOpt3 = findViewById(R.id.edt_option3);
        edtOpt4 = findViewById(R.id.edt_option4);

        edtAns = findViewById(R.id.edt_answer);
        edtQues= findViewById(R.id.edt_question);


        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(this);

        kategori_id = getIntent().getStringExtra("id_kategori");
        kategori_nama = getIntent().getStringExtra("nama_kategori");

        edtIdkat = findViewById(R.id.txt_id_kateg);

        edtIdkat.setText("Tambah soal Kategori "+ kategori_nama);

        questionsModel = new QuestionsModel();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tambah Soal");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_save) {
            saveMahasiswa();

            Intent intent = new Intent(CreateActivity.this, MainSoalActivity.class);
            intent.putExtra("id_kategori", kategori_id);
            startActivity(intent);
        }

    }

    private void saveMahasiswa()
    {
//        categoryModel = getIntent().getParcelableExtra(EXTRA_CATEGORY);
//
//        if (categoryModel != null) {
//            kategori_id = categoryModel.getId_kategori();
//        } else {
//            categoryModel = new CategoryModel();
//        }

        kategori_id = getIntent().getStringExtra("id_kategori");

        String option1 = edtOpt1.getText().toString().trim();
        String option2 = edtOpt2.getText().toString().trim();
        String option3 = edtOpt3.getText().toString().trim();
        String option4 = edtOpt4.getText().toString().trim();

        String answer = edtAns.getText().toString().trim();
        String question = edtQues.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(option1)) {
            isEmptyFields = true;
            edtOpt1.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(option2)) {
            isEmptyFields = true;
            edtOpt2.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(option3)) {
            isEmptyFields = true;
            edtOpt3.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(option4)) {
            isEmptyFields = true;
            edtOpt4.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(question)) {
            isEmptyFields = true;
            edtQues.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(answer)) {
            isEmptyFields = true;
            edtAns.setError("Field ini tidak boleh kosong");
        }


        if (! isEmptyFields) {

            Toast.makeText(CreateActivity.this, "Saving Data...", Toast.LENGTH_SHORT).show();

            DatabaseReference dbMahasiswa = mDatabase.child("Questions").child(kategori_id+"/"+"soal");

            String id = dbMahasiswa.push().getKey();
            questionsModel.setId(id);
            questionsModel.setOption1(option1);
            questionsModel.setOption2(option2);
            questionsModel.setOption3(option3);
            questionsModel.setOption4(option4);

            questionsModel.setAnswer(answer);
            questionsModel.setQuestion(question);


            //insert data
            dbMahasiswa.child(id).setValue(questionsModel);

            finish();

        }
    }
}
