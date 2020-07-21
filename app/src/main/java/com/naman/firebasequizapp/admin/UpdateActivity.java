package com.naman.firebasequizapp.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naman.firebasequizapp.HomeActivity;
import com.naman.firebasequizapp.QuestionsModel;
import com.naman.firebasequizapp.R;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtOption1, edtOption2, edtOption3, edtOption4, edtQuestion, edtAnswer;
    private Button btnUpdate;

    public static final String EXTRA_MAHASISWA = "extra_mahasiswa";
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;

    private QuestionsModel questionsModel;
    private String soalId;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtAnswer = findViewById(R.id.edt_edit_answer);
        edtQuestion = findViewById(R.id.edt_edit_question);

        edtOption1 = findViewById(R.id.edt_edit_option1);
        edtOption2 = findViewById(R.id.edt_edit_option2);
        edtOption3 = findViewById(R.id.edt_edit_option3);
        edtOption4 = findViewById(R.id.edt_edit_option4);


        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        questionsModel = getIntent().getParcelableExtra(EXTRA_MAHASISWA);

        if (questionsModel != null) {
            soalId = questionsModel.getid();
        } else {
            questionsModel = new QuestionsModel();
        }

        if (questionsModel != null) {
            edtOption1.setText(questionsModel.getOption1());
            edtOption2.setText(questionsModel.getOption2());
            edtOption3.setText(questionsModel.getOption3());
            edtOption4.setText(questionsModel.getOption4());


            edtQuestion.setText(questionsModel.getQuestion());
            edtAnswer.setText(questionsModel.getAnswer());

        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Soal");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update) {
            updateMahasiswa();

        }

    }

    private void updateMahasiswa() {
        String option1 = edtOption1.getText().toString().trim();
        String option2 = edtOption2.getText().toString().trim();
        String option3 = edtOption3.getText().toString().trim();
        String option4 = edtOption4.getText().toString().trim();

        String question = edtQuestion.getText().toString().trim();
        String answer = edtAnswer.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(option1)) {
            isEmptyFields = true;
            edtOption1.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(option2)) {
            isEmptyFields = true;
            edtOption2.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(option3)) {
            isEmptyFields = true;
            edtOption3.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(option4)) {
            isEmptyFields = true;
            edtOption4.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(question)) {
            isEmptyFields = true;
            edtQuestion.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(answer)) {
            isEmptyFields = true;
            edtAnswer.setError("Field ini tidak boleh kosong");
        }

        if (! isEmptyFields) {

            Toast.makeText(UpdateActivity.this, "Updating Data...", Toast.LENGTH_SHORT).show();

            questionsModel.setOption1(option1);
            questionsModel.setOption2(option2);
            questionsModel.setOption3(option3);
            questionsModel.setOption4(option4);

            questionsModel.setQuestion(question);
            questionsModel.setAnswer(answer);

            DatabaseReference dbMahasiswa = mDatabase.child("Questions");

            //update data
            dbMahasiswa.child(soalId).setValue(questionsModel);

            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //pilih menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                //menampilkan dialog
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form";
        } else {
            dialogTitle = "Hapus Data";
            dialogMessage = "Apakah anda yakin ingin menghapus item ini";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (isDialogClose) {
                            finish();
                        } else {
                            //hapus data
                            DatabaseReference dbMahasiswa =
                                    mDatabase.child("Questions").child(soalId);

                            dbMahasiswa.removeValue();

                            Toast.makeText(UpdateActivity.this, "Deleting data...",
                                    Toast.LENGTH_SHORT).show();
                            finish();

                            Intent intent = new Intent(UpdateActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
