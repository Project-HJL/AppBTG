package com.example.btgtcc;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btgtcc.R;

public class DetailActivity extends AppCompatActivity {
    private TextView detailGameName, detailGameClassification, detailGameDeveloper, detailGameDescription;
    private Button detailDownloadButton;
    private String downloadUrl;

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
        }

        detailDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload();
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
