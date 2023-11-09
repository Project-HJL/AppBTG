package com.example.btgtcc;

public class User {
    public static final String TAG = "User Entity";

    private int mId;
    private String mUserName;
    private String mPassword;
    private String mEmail;

    // Construtor para login
    public User(String password, String email) {
        mPassword = password;
        mEmail = email;
    }

    // Construtor para cadastro
    public User(String userName, String password, String email) {
        mUserName = userName;
        mPassword = password;
        mEmail = email;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "mId=" + mId +
                ", mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mEmail='" + mEmail + '\'' +
                '}';
    }
}
