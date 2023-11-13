package com.example.btgtcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.btgtcc.GameDao.listAllGamesGeneral;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    MyAdapter adapter;
    SearchView searchView;
    List<Game> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);

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


        //BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_search:
                    startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_settings:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_library:
                    startActivity(new Intent(getApplicationContext(), LibraryActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
        //BottomNavigation

//        ...

        GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

// Obtenha a lista de todos os jogos do banco de dados
        dataList = listAllGamesGeneral(HomeActivity.this);

        // Verifique se a lista não está vazia antes de prosseguir
        if (dataList != null && !dataList.isEmpty()) {
            adapter = new MyAdapter(HomeActivity.this, dataList);
            recyclerView.setAdapter(adapter);
        } else {
            System.out.println("Erro HomeActivity");
            // Lidar com o caso em que a lista de jogos está vazia (por exemplo, exibir uma mensagem de aviso)
        }

//        ...
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
