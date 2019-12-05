package com.gosigitgo.notasqlite.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gosigitgo.notasqlite.AddUpdateActivity;
import com.gosigitgo.notasqlite.model.Nota;
import com.gosigitgo.notasqlite.R;

import java.util.ArrayList;

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.ViewHolder> {

    ArrayList<Nota> listNota=null;
    Activity activity;

    public NotaAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Nota> getListNota() {
        return listNota;
    }

    public void setListNota(ArrayList<Nota> listNota) {
        this.listNota = listNota;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_nota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txtJudul.setText(getListNota().get(position).getJudul());
        holder.txtTanggal.setText(getListNota().get(position).getTanggal());
        holder.txtDeskripsi.setText(getListNota().get(position).getDeskripsi());

        // todo intent ke AddUpdateActivity, untuk di update / delete
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, AddUpdateActivity.class);
                intent.putExtra(AddUpdateActivity.EXTRA_POSITION, position);
                intent.putExtra(AddUpdateActivity.EXTRA_NOTA, getListNota().get(position));
                activity.startActivityForResult(intent, AddUpdateActivity.REQUEST_UPDATE);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listNota.size() > 0){
            return listNota.size();
        }
        return listNota.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtJudul, txtTanggal, txtDeskripsi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            txtJudul = itemView.findViewById(R.id.tv_judul);
            txtTanggal = itemView.findViewById(R.id.tv_tanggal);
        }
    }
}
