package com.naman.firebasequizapp.admin;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryModel implements Parcelable {
    public String id_kategori;
    public String nama;

    public CategoryModel(String id_kategori, String nama) {
        this.id_kategori = id_kategori;
        this.nama = nama;
    }

    public CategoryModel(){

    }
    //
    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.id_kategori);
        dest.writeString(this.nama);
    }

    protected CategoryModel(Parcel in) {
        this.id_kategori = in.readString();
        this.nama = in.readString();
    }

    public static final Parcelable.Creator<CategoryModel> CREATOR = new Parcelable.Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel source) {
            return new CategoryModel(source);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };
}
