    package com.example.btgtcc;

    import android.content.Context;
    import android.util.Log;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.util.ArrayList;
    import java.util.List;

    public class UserDao {

        public static final String TAG = "CRUD table User";


        public static int insertUser(User mUser, Context mContext) {
            int vResponse = 0; // variável de resposta com valor 0 = erro ao inserir
            String mSql;
            try {
                mSql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";

                PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);

                mPreparedStatement.setString(1, mUser.getUserName());
                mPreparedStatement.setString(2, mUser.getEmail());
                mPreparedStatement.setString(3, mUser.getPassword());

                vResponse = mPreparedStatement.executeUpdate();

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            return vResponse; // 0 não fez insert, 1 fez insert com sucesso
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

                // Adicione logs para verificar o fluxo e o valor de userId
                Log.d(TAG, "pegaId: Método chamado com sucesso. userId: " + userId);
            } catch (Exception e) {
                Log.e(TAG, "Erro em pegaId: " + e.getMessage());
                e.printStackTrace();
            }

            return userId;
        }


        public static int updateUser(User mUser,int userId, Context mContext) {
            int vResponse = 0; // variável de resposta com valor 0 = erro ao inserir
            String mSql;
            try {
                mSql = "UPDATE usuario SET email = ?, senha = ? WHERE id = ?";
                PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);
                mPreparedStatement.setString(1, mUser.getEmail());
                mPreparedStatement.setString(2, mUser.getPassword());
                mPreparedStatement.setInt(3, userId);
                vResponse = mPreparedStatement.executeUpdate();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return vResponse; // 0 não fez insert, 1 fez insert com sucesso
        }

        public static int deleteUser(int userId, Context mContext) {
            int vResponse = 0; // variável de resposta com valor 0 = erro ao inserir
            String mSql;
            try {
                mSql = "DELETE FROM usuario WHERE id = ?";

                PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);

                mPreparedStatement.setInt(1, userId);

                vResponse = mPreparedStatement.executeUpdate();

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            Log.d(TAG, "O id excluído é: " + userId);

            return vResponse; // 0 não fez insert, 1 fez insert com sucesso
        }

        public static String authenticateUser(User mUser, Context mContext) {
            String mResponse = "";
    //    String mSql = "SELECT id, nome , email, senha FROM usuario WHERE senha LIKE ? AND email LIKE ?";
            String mSql = "SELECT id, nome , email, senha FROM usuario WHERE senha=? AND email=?";
            try {
                PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);

                mPreparedStatement.setString(1, mUser.getPassword());
                mPreparedStatement.setString(2, mUser.getEmail());

                ResultSet mResultSet = mPreparedStatement.executeQuery();

                while (mResultSet.next()) {
                    mResponse = mResultSet.getString(2);
                }

            } catch (Exception e) {
                mResponse = "Exception";
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
            Log.d(TAG, "Email: " + mUser.getEmail() + ", Password: " + mUser.getPassword());

            return mResponse;
        }
    }