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

public class UpdateKategoriActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNamaKat;
    private Button btnUpdate;

    public static final String EXTRA_CATEGORY = "extra_category";
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;

//    private QuestionsModel questionsModel;

    private CategoryModel categoryModel;
    private String soalId, kategori_id;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_kategori);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtNamaKat = findViewById(R.id.edt_edit_nama_kategori);

        btnUpdate = findViewById(R.id.btn_update_kategori);
        btnUpdate.setOnClickListener(this);

        categoryModel = getIntent().getParcelableExtra(EXTRA_CATEGORY);

        if (categoryModel != null) {
            soalId = categoryModel.getId_kategori();
        } else {
            categoryModel = new CategoryModel();
        }

        if (categoryModel != null) {
            edtNamaKat.setText(categoryModel.getNama());
        }

        kategori_id = getIntent().getStringExtra("id_kategori");

        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Edit K");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update_kategori) {
            updateMahasiswa();

        }

    }

    private void updateMahasiswa() {
        String namaKategori = edtNamaKat.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(namaKategori)) {
            isEmptyFields = true;
            edtNamaKat.setError("Field ini tidak boleh kosong");
        }

        if (! isEmptyFields) {

            Toast.makeText(UpdateKategoriActivity.this, "Updating Data...", Toast.LENGTH_SHORT).show();

            categoryModel.setNama(namaKategori);

            DatabaseReference dbMahasiswa = mDatabase.child("Questions");

            //update data
            dbMahasiswa.child(kategori_id).setValue(categoryModel);

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
            dialogMessage = "Apakah anda ingin membatalkan perubahan kategori";
        } else {
            dialogTitle = "Hapus Kategori";
            dialogMessage = "Apakah anda yakin ingin menghapus kategori ini";
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

                            Toast.makeText(UpdateKategoriActivity.this, "Deleting data...",
                                    Toast.LENGTH_SHORT).show();
                            finish();

                            Intent intent = new Intent(UpdateKategoriActivity.this, HomeActivity.class);
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
