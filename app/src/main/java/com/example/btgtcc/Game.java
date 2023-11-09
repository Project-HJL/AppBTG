package com.example.btgtcc;

public class Game {

    public static final String TAG = "User Entity";

    private String mGameName;
    private String mClassification;
    private String mCompanyName;
    private String mLink;
    private String mDescription;


    @Override
    public String toString() {
        return "Game{" +
                ", mGameName='" + mGameName + '\'' +
                ", mClassification='" + mClassification + '\'' +
                ", mCompanyName='" + mCompanyName +
                '}';
    }

    public Game(String gameName, String classification,
                String companyName, String link, String description) {
        mGameName = gameName;
        mClassification = classification;
        mCompanyName = companyName;
        mLink = link;
        mDescription = description;
    }

    public String getGameName() {
        return  mGameName;
    }

    public void setGameName(String gameName) {
        mGameName = gameName;
    }


    public String getClassification() {
        return mClassification;
    }

    public void setClassification(String classification) {
        mClassification = classification;
    }


    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }




}