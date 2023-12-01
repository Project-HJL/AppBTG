package com.example.btgtcc;

public class Company {

        public static final String TAG = "User Entity";

        private String mCompanyName;
        private int mId;



        @Override
        public String toString() {
            return "Company{" +
                    ", mCompanyName='" + mCompanyName + '\'' +
                    "mId=" + mId +
                    '}';
        }

        public Company(int id) {
            mId = id;
        }

        public Company(String companyName, int id) {
            mCompanyName = companyName;
            mId = id;
        }

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            mId = id;
        }

        public String getCompanyName() {
            return  mCompanyName;
        }

        public void setCompanyName(String companyName) {
            mCompanyName = companyName;
        }
    }

