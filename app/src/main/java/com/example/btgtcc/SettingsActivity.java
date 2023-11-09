package com.example.btgtcc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btgtcc.HomeActivity;
import com.example.btgtcc.LibraryActivity;
import com.example.btgtcc.R;
import com.example.btgtcc.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonSave, buttonDeleteAccount, buttonLogout;
    private TextView textViewUsername, textViewPassword;

    private void showNavigation() {
        Intent mIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(mIntent);
        finish();
    }

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
    }
}
