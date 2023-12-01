package com.example.btgtcc;

import android.content.Context;
import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao {
    public static final String TAG = "CRUD table User";

    public static List<Company> listAllCompanys(Context mContext) {
        List<Company> mCompanyList = null;
        String mSql;

        try {
            mSql = "SELECT jogo.nome,jogo.classificacao , empresa.nome , jogo.link , jogo.descricao, jogo.id\n" +
                    "FROM jogo\n" +
                    "INNER JOIN empresa ON jogo.empresa_id = empresa.id";
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);

            ResultSet mResultSet = mPreparedStatement.executeQuery();

            mCompanyList = new ArrayList<Company>();

            while (mResultSet.next()) {
                mCompanyList.add(new Company(
                        mResultSet.getString(1),
                        mResultSet.getInt(2)
                ));

            }


        } catch (Exception e) {
            System.out.println("deu ruim CompanyDao");
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return mCompanyList;

    }

}
