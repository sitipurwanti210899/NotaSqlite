package com.gosigitgo.notasqlite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.gosigitgo.notasqlite.adapter.NotaAdapter;
import com.gosigitgo.notasqlite.database.NotaHelper;
import com.gosigitgo.notasqlite.model.Nota;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvNota;
    NotaHelper notaHelper;
    NotaAdapter notaAdapter;
    ArrayList<Nota> listNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvNota =findViewById(R.id.rv_nota);
        rvNota.setLayoutManager(new LinearLayoutManager(this));
        rvNota.setHasFixedSize(true);

        notaHelper = new NotaHelper(this);
        notaHelper.open();

        listNota=new ArrayList<>();

        notaAdapter = new NotaAdapter(this);
        rvNota.setAdapter(notaAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUpdateActivity.class);
                startActivityForResult(intent, AddUpdateActivity.REQUEST_ADD);
            }
        });
        new LoadNotaAsyntask().execute();
    }

    private class LoadNotaAsyntask extends AsyncTask<Void, Void, ArrayList<Nota>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (listNota.size()>0){
                listNota.clear();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Nota> notas) {
            super.onPostExecute(notas);

            listNota.addAll(notas);
            notaAdapter.setListNota(listNota);
            notaAdapter.notifyDataSetChanged();

            if (listNota.size()==0){
                Toast.makeText(MainActivity.this, "data kosong", Toast.LENGTH_SHORT).show();
            }
        }
    //tampilkan data
        @Override
        protected ArrayList<Nota> doInBackground(Void... voids) {
            return notaHelper.getAllNota();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==AddUpdateActivity.REQUEST_ADD){
            if (resultCode == AddUpdateActivity.RESULT_ADD){
                Toast.makeText(this, "Berhasil tambah Data", Toast.LENGTH_SHORT).show();
                new LoadNotaAsyntask().execute();
            }
        }
        else if (requestCode == AddUpdateActivity.REQUEST_UPDATE){
            if (resultCode == AddUpdateActivity.RESULT_UPDATE){
                Toast.makeText(this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                new LoadNotaAsyntask().execute();

            }else if (resultCode == AddUpdateActivity.RESULT_DELETE){
                Toast.makeText(this,"Berhasil Hapus Data", Toast.LENGTH_SHORT).show();
                new LoadNotaAsyntask().execute();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
