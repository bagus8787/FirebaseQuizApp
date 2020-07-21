package com.naman.firebasequizapp.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.naman.firebasequizapp.R;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CategoryModel> soalList = new ArrayList<>();

    public void setSoalList(ArrayList<CategoryModel> soalList) {
        this.soalList = soalList;
    }

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return soalList.size();
    }

    @Override
    public Object getItem(int i) {
        return soalList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;

        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_kategori, viewGroup, false);
        }

        CategoryAdapter.ViewHolder viewHolder = new CategoryAdapter.ViewHolder(itemView);

        CategoryModel soal = (CategoryModel) getItem(i);
        viewHolder.bind(soal);
        return itemView;
    }

    private class ViewHolder {
        private TextView txtNama;
        private TextView txtIdkategori;


        ViewHolder(View view) {
            txtNama = view.findViewById(R.id.txt_nama);
            txtIdkategori = view.findViewById(R.id.txt_id_kategori);

        }

        void bind(CategoryModel soal) {
            txtNama.setText(soal.getNama());
            txtIdkategori.setText(soal.getId_kategori());

        }

    }
}
