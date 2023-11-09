package com.example.btgtcc;

import android.content.Context;
import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public static final String TAG = "CRUD table User";


    public static int insertUser(User mUser, Context mContext) {
        int userId = -1; // Inicialize como -1 para indicar que não houve inserção
        String mSql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";

        try {
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql, Statement.RETURN_GENERATED_KEYS);

            mPreparedStatement.setString(1, mUser.getUserName());
            mPreparedStatement.setString(2, mUser.getEmail());
            mPreparedStatement.setString(3, mUser.getPassword());

            int rowsInserted = mPreparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                // Após a inserção, recupere o ID com base no email do usuário
                userId = pegaId(mUser, mContext);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return userId; // Retorna o ID do usuário inserido (ou -1 se ocorrer um erro)
    }

    public static int pegaId(User mUser, Context mContext) {
        int userId = -1;

        String mSql = "SELECT id FROM usuario WHERE email = ?";
        try {
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);

            mPreparedStatement.setString(1, mUser.getEmail());

            ResultSet mResultSet = mPreparedStatement.executeQuery();

            if (mResultSet.next()) {
                userId = mResultSet.getInt("id");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return userId;
    }

    public static int updateUser(User mUser, Context mContext) {
        int vResponse = 0; // variável de resposta com valor 0 = erro ao inserir
        String mSql;
        int userId = authenticateUser(mUser, mContext);
        try {
            mSql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);
            mPreparedStatement.setString(1, mUser.getUserName());
            mPreparedStatement.setString(2, mUser.getEmail());
            mPreparedStatement.setString(3, mUser.getPassword());
            mPreparedStatement.setInt(4, userId);
            vResponse = mPreparedStatement.executeUpdate();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return vResponse; // 0 não fez insert, 1 fez insert com sucesso
    }

        public static int deleteUser(User mUser, Context mContext) {
            int vResponse = 0; // variável de resposta com valor 0 = erro ao inserir
            String mSql;
            int userId = authenticateUser(mUser, mContext);
            try {
                mSql = "DELETE FROM usuario WHERE id = ?";

                PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);

                mPreparedStatement.setInt(1, userId);

                vResponse = mPreparedStatement.executeUpdate();

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            return vResponse; // 0 não fez insert, 1 fez insert com sucesso
        }

    public static int authenticateUser(User mUser, Context mContext) {
        int userId = -1;

        String mSql = "SELECT id FROM usuario WHERE email = ? AND senha = ?";
        try {
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);

            mPreparedStatement.setString(1, mUser.getEmail());
            mPreparedStatement.setString(2, mUser.getPassword());

            ResultSet mResultSet = mPreparedStatement.executeQuery();

            if (mResultSet.next()) {
                userId = mResultSet.getInt("id");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return userId;
    }


}