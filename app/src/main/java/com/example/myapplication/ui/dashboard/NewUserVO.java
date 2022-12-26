package com.example.myapplication.ui.dashboard;

public class NewUserVO {
        public final String account;
        public final String userName;
        public final String password;
        public final String gender;
        public final String age;
        public final String phone;
        public final String address;
        public final String familyID;
        public final String familyName;
        public final String familyPhone;
        public final String token;
        public final String wristbandName;

    public NewUserVO(String account, String userName, String password, String gender, String age, String phone, String address, String familyID, String familyName, String familyPhone, String token, String wristbandName) {
        this.account = account;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.familyID = familyID;
        this.familyName = familyName;
        this.familyPhone = familyPhone;
        this.token = token;
        this.wristbandName = wristbandName;
    }
}
