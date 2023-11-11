package com.example.btgtcc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btgtcc.HomeActivity;
import com.example.btgtcc.LibraryActivity;
import com.example.btgtcc.R;
import com.example.btgtcc.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    public static final String TAG = "Settings Activity";

    private EditText editTextUsername, editTextPassword;
    private Button buttonSave, buttonDeleteAccount, buttonLogout;
    private TextView textViewUsername, textViewPassword;
    String mStringPassword, mStringEmail;
    private User mUser;
    private SharedPreferences mSharedPreferencesLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDeleteAccount = findViewById(R.id.buttonDeleteAccount);
        buttonLogout = findViewById(R.id.buttonLogout);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewPassword = findViewById(R.id.textViewPassword);

        mStringEmail = String.valueOf(editTextUsername.getText()).toLowerCase(Locale.ROOT);
        mStringPassword = String.valueOf(editTextPassword.getText());


//Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_search:
                    startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;

                case R.id.bottom_library:
                    startActivity(new Intent(getApplicationContext(), LibraryActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;

                case R.id.bottom_settings:
                    return true;
            }
            return false;
        });
        //Bottom Navigation

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v); // Chame o método logout quando o botão for clicado
            }
        });
        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Certifique-se de inicializar mSharedPreferencesLogin corretamente
                mSharedPreferencesLogin = getSharedPreferences("MyAppName", MODE_PRIVATE);

                int userId = mSharedPreferencesLogin.getInt("userId", -1);

                if (userId != -1) {
                    int deleteResult = UserDao.deleteUser(userId, getApplicationContext());

                    // Verifica se a exclusão foi bem-sucedida
                    if (deleteResult > 0) {
                        // Limpa as SharedPreferences ou faz qualquer outra ação necessária após a exclusão
                        SharedPreferences.Editor editor = mSharedPreferencesLogin.edit();
                        editor.clear();
                        editor.apply();

                        // Encaminha para a tela de login ou realiza qualquer outra ação necessária
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Exibe uma mensagem ou trata de acordo se a exclusão falhar
                        Toast.makeText(getApplicationContext(), "Falha ao excluir a conta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "Deu ruim na porra do Id");
                }
            }
        });

        
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });














    }

    public void logout(View view) {
        // Limpar as informações de login no SharedPreferences
        SharedPreferences mSharedPreferencesLogin = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferencesLogin.edit();
        mEditor.remove("logged");  // Remove a chave "logged" para indicar que o usuário não está mais logado
        mEditor.remove("userId");  // Remove o ID do usuário
        mEditor.apply();

        // Redirecionar para a tela de login
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();  // Encerra a atividade atual
    }

}