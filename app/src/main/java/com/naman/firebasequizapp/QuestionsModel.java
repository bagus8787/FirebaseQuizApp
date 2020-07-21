package com.naman.firebasequizapp;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionsModel implements Parcelable {
    public String id;
    public String question;
    public String option1;
    public String option2;
    public String option3;
    public String option4;
    public String answer;

    public QuestionsModel(String question, String option1, String option2, String option3, String option4, String answer, String id) {
        this.id = id;

        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.question = question;
        this.answer = answer;
    }

    public QuestionsModel(){

    }
//
    public String getid() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.id);
        dest.writeString(this.option1);
        dest.writeString(this.option2);
        dest.writeString(this.option3);
        dest.writeString(this.option4);

        dest.writeString(this.question);
        dest.writeString(this.answer);
    }

    protected QuestionsModel(Parcel in) {
        this.id = in.readString();
        this.option1 = in.readString();
        this.option2 = in.readString();
        this.option3 = in.readString();
        this.option4 = in.readString();

        this.question = in.readString();
        this.answer = in.readString();
    }

    public static final Parcelable.Creator<QuestionsModel> CREATOR = new Parcelable.Creator<QuestionsModel>() {
        @Override
        public QuestionsModel createFromParcel(Parcel source) {
            return new QuestionsModel(source);
        }

        @Override
        public QuestionsModel[] newArray(int size) {
            return new QuestionsModel[size];
        }
    };
}
