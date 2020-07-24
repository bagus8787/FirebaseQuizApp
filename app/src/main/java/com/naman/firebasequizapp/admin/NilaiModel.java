package com.naman.firebasequizapp.admin;

import android.os.Parcel;
import android.os.Parcelable;

public class NilaiModel implements Parcelable {
    public String nilai_benar;
    public String nilai_salah;

    public NilaiModel(String nilai_benar, String nilai_salah) {
        this.nilai_benar = nilai_benar;
        this.nilai_salah = nilai_salah;
    }

    public NilaiModel(){

    }
    //
    public String getNilai_benar() {
        return nilai_benar;
    }

    public void setNilai_benar(String nilai_benar) {
        this.nilai_benar = nilai_benar;
    }


    public String getNilai_salah() {
        return nilai_salah;
    }

    public void setNilai_salah(String nilai_salah) {
        this.nilai_salah = nilai_salah;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.nilai_benar);
        dest.writeString(this.nilai_salah);
    }

    protected NilaiModel(Parcel in) {
        this.nilai_benar = in.readString();
        this.nilai_salah = in.readString();
    }

    public static final Parcelable.Creator<NilaiModel> CREATOR = new Parcelable.Creator<NilaiModel>() {
        @Override
        public NilaiModel createFromParcel(Parcel source) {
            return new NilaiModel(source);
        }

        @Override
        public NilaiModel[] newArray(int size) {
            return new NilaiModel[size];
        }
    };
}
