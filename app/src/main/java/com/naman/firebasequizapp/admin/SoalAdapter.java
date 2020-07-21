package com.naman.firebasequizapp.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.naman.firebasequizapp.QuestionsModel;
import com.naman.firebasequizapp.R;

import java.util.ArrayList;

public class SoalAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<QuestionsModel> soalList = new ArrayList<>();

    public void setSoalList(ArrayList<QuestionsModel> soalList) {
        this.soalList = soalList;
    }

    public SoalAdapter(Context context) {
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
                    .inflate(R.layout.item_soal, viewGroup, false);
        }

        ViewHolder viewHolder = new ViewHolder(itemView);

        QuestionsModel soal = (QuestionsModel) getItem(i);
        viewHolder.bind(soal);
        return itemView;
    }

    private class ViewHolder {
        private TextView txtQuestion, txtAnswer;

        ViewHolder(View view) {
            txtQuestion = view.findViewById(R.id.txt_question);
            txtAnswer = view.findViewById(R.id.txt_answer);
        }

        void bind(QuestionsModel soal) {
            txtQuestion.setText(soal.getQuestion());
            txtAnswer.setText(soal.getAnswer());
        }
    }
}

