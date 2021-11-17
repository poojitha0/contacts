package com.example.contacts;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Comparator;


public class Contacts implements Parcelable {

    String name;
    String phone;
    boolean isChecked = false;

    public Contacts(){

    }

    public static Comparator<Contacts> ContactsAZComparator = new Comparator<Contacts>() {
        @Override
        public int compare(Contacts C1, Contacts C2) {
            return C1.getName().compareTo(C2.getName());
        }
    };


    protected Contacts(Parcel in) {
        name = in.readString();
        phone = in.readString();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<Contacts> CREATOR = new Creator<Contacts>() {
        @Override
        public Contacts createFromParcel(Parcel in) {
            return new Contacts(in);
        }

        @Override
        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeByte((byte) (isChecked ? 1 : 0));
    }
}
