package com.naman.firebasequizapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naman.firebasequizapp.HomeActivity;
import com.naman.firebasequizapp.QuestionsModel;
import com.naman.firebasequizapp.R;

import java.util.ArrayList;

public class MainSoalActivity extends AppCompatActivity implements View.OnClickListener {

    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;

    private ListView listView;
    private Button btnAdd, btnEdit;
    private TextView txt_id_kat;

    public String id_kategori, nama_kategori;

    public static final String EXTRA_CATEGORY = "extra_category";

    //tambahkan kode ini
    private SoalAdapter adapter;
    CategoryAdapter categoryAdapter;
    private ArrayList<QuestionsModel> soalList;

    private ArrayList<CategoryModel> kategoryList;

    DatabaseReference dbMahasiswa;

    CategoryModel categoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_soal);

        dbMahasiswa = FirebaseDatabase.getInstance().getReference("Questions");

        listView = findViewById(R.id.lv_list);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        btnEdit = findViewById(R.id.btn_edit_kategori);


        txt_id_kat = findViewById(R.id.txt_id_kategori);

//        tambahan
        categoryModel = getIntent().getParcelableExtra(EXTRA_CATEGORY);

        if (categoryModel != null) {
            id_kategori = categoryModel.getId_kategori();
            nama_kategori = categoryModel.getNama();
        } else {
            categoryModel = new CategoryModel();
        }

        txt_id_kat.setText(nama_kategori);

        //list mahasiswa
        soalList = new ArrayList<>();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Daftar Soal");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainSoalActivity.this, UpdateKategoriActivity.class);
                intent.putExtra("id_kategori", categoryModel.getId_kategori());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add) {
            Intent intent = new Intent(MainSoalActivity.this, CreateActivity.class);
//            intent.putExtra(CreateActivity.EXTRA_CATEGORY, id_kategori);
            intent.putExtra("id_kategori", categoryModel.getId_kategori());
            intent.putExtra("nama_kategori", categoryModel.getNama());

            startActivity(intent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        dbMahasiswa.child(id_kategori+"/"+"soal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                soalList.clear();

                for (DataSnapshot mahasiswaSnapshot : dataSnapshot.getChildren()) {
                    QuestionsModel questionsModel = mahasiswaSnapshot.getValue(QuestionsModel.class);
                    soalList.add(questionsModel);
                }

                SoalAdapter adapter = new SoalAdapter(MainSoalActivity.this);
                adapter.setSoalList(soalList);

//               for (DataSnapshot mahasiswaSnapshot : dataSnapshot.getChildren()) {
//                    CategoryModel categoryModel = mahasiswaSnapshot.getValue(CategoryModel.class);
//                    soalList.add(categoryModel);
//                }
//
//                SoalAdapter adapter = new SoalAdapter(MainSoalActivity.this);
//                adapter.setSoalList(soalList);

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
                intent.putExtra("id_kategori", categoryModel.getId_kategori());
                intent.putExtra("nama_kategori", categoryModel.getNama());

                startActivity(intent);
            }
        });
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
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog(int type) {
//        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        dialogTitle = "Hapus Kategori";
        dialogMessage = "Apakah anda yakin ingin menghapus kategori ini";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dbMahasiswa.child("Questions").child(id_kategori).removeValue();
                        categoryModel = getIntent().getParcelableExtra(EXTRA_CATEGORY);

                        if (categoryModel != null) {
                            id_kategori = categoryModel.getId_kategori();
                            nama_kategori = categoryModel.getNama();
                        } else {
                            categoryModel = new CategoryModel();
                        }

                        DatabaseReference dbMahasiswal =
                                dbMahasiswa.child(id_kategori);

                        dbMahasiswal.removeValue();

//                        dbMahasiswaa.removeValue();

                        Toast.makeText(MainSoalActivity.this, "Deleting data...",
                                Toast.LENGTH_SHORT).show();
                        finish();

                        Intent intent = new Intent(MainSoalActivity.this, MainKategoriActivity.class);
                        startActivity(intent);

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