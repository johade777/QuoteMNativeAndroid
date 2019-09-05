package com.johade.quotem.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;

public class Question implements Parcelable {
    @SerializedName("Id")
    public int id;

    @SerializedName("Question")
    public String question;

    @SerializedName("Answer")
    public String answer;

    @SerializedName("Wrong1")
    public String wrong1;

    @SerializedName("Wrong2")
    public String wrong2;

    @SerializedName("Wrong3")
    public String wrong3;

    public ArrayList<String> shuffle(){
        ArrayList<String> answers = new ArrayList<>();
        answers.add(answer);
        answers.add(wrong1);
        answers.add(wrong2);
        answers.add(wrong3);
        Collections.shuffle(answers);
        return answers;
    }

    public Question(Parcel in) {
        this.id = in.readInt();
        this.question = in.readString();
        this.answer = in.readString();
        this.wrong1 = in.readString();
        this.wrong2 = in.readString();
        this.wrong3 = in.readString();
    }

    public boolean isCorrect(String clickedText){
        return clickedText.equals(answer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(question);
        parcel.writeString(answer);
        parcel.writeString(wrong1);
        parcel.writeString(wrong2);
        parcel.writeString(wrong3);
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {

        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
