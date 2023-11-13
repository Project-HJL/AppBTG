package com.example.btgtcc;

import android.content.Context;
import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GameDao {

    public static final String TAG = "CRUD table User";

        public static int insertLibrary(Game mGame,int userId, Context mContext) {
        int vResponse = 0; // variável de resposta com valor 0 = erro ao inserir
        String mSql;
        try {
            mSql = "INSERT INTO biblioteca (usuario_id, jogo_id) VALUES (?,?)";

            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);

//            ResultSet mResultSet = mPreparedStatement.executeQuery();

            mPreparedStatement.setInt(1, userId);
            mPreparedStatement.setInt(2, mGame.getId());
            vResponse = mPreparedStatement.executeUpdate();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return vResponse; // 0 não fez insert, 1 fez insert com sucesso
    }
//
//    public static int updateUser(User mUser, Context mContext) {
//        int vResponse = 0; // variável de resposta com valor 0 = erro ao inserir
//        String mSql;
//        try {
//            mSql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
//
//            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);
//
//            mPreparedStatement.setString(1, mUser.getUserName());
//            mPreparedStatement.setString(2, mUser.getEmail());
//            mPreparedStatement.setString(3, mUser.getPassword());
//            mPreparedStatement.setInt(4, mUser.getId());
//
//            vResponse = mPreparedStatement.executeUpdate();
//
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//        }
//
//        return vResponse; // 0 não fez insert, 1 fez insert com sucesso
//    }

    public static List<Game> listAllGamesGeneral(Context mContext) {
        List<Game> mGameList = null;
        String mSql;

        try {
            mSql = "SELECT jogo.nome,jogo.classificacao , empresa.nome , jogo.link , jogo.descricao, jogo.id\n" +
                    "FROM jogo\n" +
                    "INNER JOIN empresa ON jogo.empresa_id = empresa.id";
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);

            ResultSet mResultSet = mPreparedStatement.executeQuery();

            mGameList = new ArrayList<Game>();

            while (mResultSet.next()) {
                mGameList.add(new Game(
                        mResultSet.getString(1),
                        mResultSet.getString(2),
                        mResultSet.getString(3),
                        mResultSet.getString(4),
                        mResultSet.getString(5),
                        mResultSet.getInt(6)
                ));

            }


        } catch (Exception e) {
            System.out.println("deu ruim gameDao");
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return mGameList;

    }    public static List<Game> listAllGamesLibrary(int userId, Context mContext) {
        List<Game> mGameList = null;
        String mSql;

        try {
            // Ajuste da consulta para filtrar os jogos associados ao usuário na tabela de biblioteca
            mSql = "SELECT jogo.nome, jogo.classificacao, empresa.nome, jogo.link, jogo.descricao, jogo.id " +
                    "FROM jogo " +
                    "INNER JOIN empresa ON jogo.empresa_id = empresa.id " +
                    "INNER JOIN biblioteca ON jogo.id = biblioteca.jogo_id " +
                    "WHERE biblioteca.usuario_id = ?";

            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);
            mPreparedStatement.setInt(1, userId); // Define o ID do usuário como parâmetro

            ResultSet mResultSet = mPreparedStatement.executeQuery();

            mGameList = new ArrayList<>();

            while (mResultSet.next()) {
                mGameList.add(new Game(
                        mResultSet.getString(1),
                        mResultSet.getString(2),
                        mResultSet.getString(3),
                        mResultSet.getString(4),
                        mResultSet.getString(5),
                        mResultSet.getInt(6)
                ));
            }

        } catch (Exception e) {
            System.out.println("deu ruim gameDao");
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return mGameList;
    }

}


