package com.gosigitgo.notasqlite;

import androidx.annotation.NonNull;
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

import com.gosigitgo.notasqlite.database.NotaHelper;
import com.gosigitgo.notasqlite.model.Nota;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddUpdateActivity extends AppCompatActivity {
    private EditText edtJudul, edtDeskripsi;
    private Button btnSubmit;

    //atur kode
    public static int REQUEST_ADD =100;
    public static int RESULT_ADD=101;

    public static int REQUEST_UPDATE =200;
    public static int RESULT_UPDATE =201;

    public static int RESULT_DELETE=301;

    public boolean isUpdate =false;

    public static String EXTRA_POSITION="extra_position";
    public static String EXTRA_NOTA="extra_nota";

    Nota nota;
    NotaHelper notaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);

        edtDeskripsi=findViewById(R.id.edt_deskripsi);
        edtJudul=findViewById(R.id.edt_judul);
        btnSubmit=findViewById(R.id.btn_submit);

        notaHelper =new NotaHelper(this);
        notaHelper.open();

        nota=getIntent().getParcelableExtra(EXTRA_NOTA);
        if (nota !=null){
            isUpdate=true;
        }
        String actionBarTitle, btnTitle;
        if (isUpdate){
            actionBarTitle ="Update Data";
            btnTitle="Update";

            edtJudul.setText(nota.getJudul());
            edtDeskripsi.setText(nota.getDeskripsi());
        }else {
            actionBarTitle="Tambah Data";
            btnTitle="Tambah";
        }

        getSupportActionBar().setTitle(actionBarTitle);
        btnSubmit.setText(btnTitle);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judul = edtJudul.getText().toString();
                String deskripsi = edtDeskripsi.getText().toString();

                if (TextUtils.isEmpty(judul)||TextUtils.isEmpty(deskripsi)){
                    Toast.makeText(AddUpdateActivity.this,"Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else {
                    Nota newNota= new Nota();
                    newNota.setJudul(judul);
                    newNota.setDeskripsi(deskripsi);

//                    Intent intent=new Intent();
                    if (isUpdate){
                        newNota.setTanggal(nota.getTanggal());
                        newNota.setId(nota.getId());

                        notaHelper.updateNota(newNota);
                        setResult(RESULT_UPDATE);
                        finish();
                    }else {
                        newNota.setTanggal(currentDate());
                        notaHelper.insertNota(newNota);
                        setResult(RESULT_ADD);
                        finish();
                    }
                }
            }
        });
    }

    private String currentDate(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date= new Date();
        return simpleDateFormat.format(date);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isUpdate){
            getMenuInflater().inflate(R.menu.menu_delete, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_delete){

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Are You Delete ?");
            alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    notaHelper.deleteNota(nota.getId());
                    setResult(RESULT_DELETE);
                    finish();
                }
            });
            alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                }
            });
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
