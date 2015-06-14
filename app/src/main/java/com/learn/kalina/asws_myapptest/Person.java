package com.learn.kalina.asws_myapptest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KalinaRain on 2015/4/10.
 */
public class Person implements Parcelable{
    public String userName;
    public String userPass;
    public int age;
    public char gender;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    /*public Creator<> CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };*/

    @Override
    public String toString() {
        return super.toString();
    }
}
