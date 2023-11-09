package com.example.btgtcc;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MSSQLConnectionHelper {


    public static final String TAG = "Database Connection";
    private static String mStringConnectionUrl;

    // 1 - para o SOMEE.COM
    private static String mStringServerIpName = "bdbtgtcc.mssql.somee.com";
    private static String mStringUserName = "joaovsbade_SQLLogin_1";
    private static String mStringPassword = "9lf1ohyuim";
    private static String mStringDatabase = "bdbtgtcc";
    public static Connection getConnection(Context mContext){
        Connection mConnection = null;
        try{
            // adicionar uma politica (policy) para criacao de uma tarefa (thread)S
            StrictMode.ThreadPolicy mPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(mPolicy);

            // verificar se o driver(bibilioteca) de conexao foi copiada/implementada para o projeto
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            // criando a string de conexao para o SOMEE.COM
            mStringConnectionUrl = "jdbc:jtds:sqlserver://" + mStringServerIpName +
                    ";databaseName=" + mStringDatabase +
                    ";user=" + mStringUserName +
                    ";password=" + mStringPassword + ";" ;

            // para o LOCALHOST ou MySql a mStringConnectionUrl é programada de outra forma

            mConnection = DriverManager.getConnection(mStringConnectionUrl);

            // passar para o programador uma mensagem no Android Studio - registrar (Log) o que acontece

            Log.i(TAG , "Connection successful"); // deu certo a conexao com o banco

        } catch (ClassNotFoundException e){
            String mMessage = "Class not found" + e.getMessage();
            Log.e(TAG, mMessage);

        }  catch (SQLException e){
            String mMessage = "Database fail" + e.getMessage();
            Log.e(TAG, mMessage);

        } catch (Exception e){
            String mMessage = "Error unknown" + e.getMessage();
            Log.e(TAG, mMessage);
        }
        return mConnection;
    }

}