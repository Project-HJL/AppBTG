package com.example.btgtcc;

import static com.example.btgtcc.GameDao.listAllGamesLibrary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter adapter;
    SearchView searchView;
    List<Game> dataList = new ArrayList<>();
    private SharedPreferences mSharedPreferencesLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        recyclerView = findViewById(R.id.recyclerViewLibrary);
        searchView = findViewById(R.id.searchLibrary);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

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
                    return true;

                case R.id.bottom_settings:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });

        mSharedPreferencesLogin = getSharedPreferences("MyAppName", MODE_PRIVATE);
        int userId = mSharedPreferencesLogin.getInt("userId", -1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(LibraryActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Obtenha a lista de jogos do banco de dados
        dataList = listAllGamesLibrary(userId, LibraryActivity.this);

        // Verifique se a lista não está vazia antes de prosseguir
        if (dataList != null && !dataList.isEmpty()) {
            adapter = new MyAdapter(LibraryActivity.this, dataList);
            recyclerView.setAdapter(adapter);
        } else {
            System.out.println("Erro LibraryActivity");
            // Lidar com o caso em que a lista de jogos está vazia (por exemplo, exibir uma mensagem de aviso)
        }

    }

    //Pesquisa
    private void searchList(String text) {
        List<Game> dataSearchList = new ArrayList<>();
        for (Game data : dataList) {
            if (data.getGameName().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(dataSearchList);
        }
    }
}
