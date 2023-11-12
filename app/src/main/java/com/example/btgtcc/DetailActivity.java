package com.example.btgtcc;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btgtcc.R;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = "Detail Activity";
    private TextView detailGameName, detailGameClassification, detailGameDeveloper, detailGameDescription,detailId;
    private Button detailDownloadButton;
    private String downloadUrl;
    private Game mGame;
    private SharedPreferences mSharedPreferencesLogin;
//    private int detailId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailGameName = findViewById(R.id.detailGameName);
        detailGameClassification = findViewById(R.id.detailClassification);
        detailGameDeveloper = findViewById(R.id.detailCompanyName);
        detailDownloadButton = findViewById(R.id.detailLink);
        detailGameDescription = findViewById(R.id.detailDescription);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailGameName.setText(bundle.getString("GameName"));
            detailGameClassification.setText(bundle.getString("Classification"));
            detailGameDeveloper.setText(bundle.getString("CompanyName"));
            downloadUrl = bundle.getString("Link"); // Salve o URL do download
            detailGameDescription.setText(bundle.getString("Description"));
            detailId.setText(bundle.getString("Id"));
        }

        detailDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload();

                mSharedPreferencesLogin = getSharedPreferences("MyAppName", MODE_PRIVATE);

                int userId = mSharedPreferencesLogin.getInt("userId", -1);
                Game mGame = new Game(detailId);


                if (userId != -1) {
                    int insertResult = GameDao.insertLibrary(mGame ,userId, getApplicationContext());


                    Log.d(TAG, "Deu Certo");

                } else {
                    Log.d(TAG, "Deu ruim na porra do update no settings");
                }
            }



        });
    }

    private void startDownload() {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(downloadUrl);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Nome do arquivo");
        request.setDescription("Baixando APK");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "app.apk");

        downloadManager.enqueue(request);
    }
}
