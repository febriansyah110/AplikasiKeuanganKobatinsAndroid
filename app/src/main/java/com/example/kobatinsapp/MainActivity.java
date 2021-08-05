package com.example.kobatinsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Sesion sesion;
    BaseApiService baseApiService;
    Context context;

    LinearLayout pemasukan, riwayat, info, keluar;
    TextView nama, hp;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        sesion = new Sesion(this);
        baseApiService = UtilsApi.getAPIService();
        context = this;

        pemasukan = findViewById(R.id.pemasukan);
        info = findViewById(R.id.info);
        riwayat = findViewById(R.id.riwayat);
        keluar = findViewById(R.id.keluar);
        nama = findViewById(R.id.nama);
        nama.setText("Hi, "+sesion.getNmuser());
        hp = findViewById(R.id.hp);
        hp.setText(sesion.getHpuser());

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
            }
        });

        pemasukan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, PemasukanActivity.class));
        }
    });

        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RiwayatActivity.class));
            }
        });

    info.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, InfoActivity.class));
        }
    });
}

    private void keluar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Apakah anda ingin keluar dari aplikasi ?");
        builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                sesion.saveSPBoolean(sesion.SUDAH_LOGIN, false);
                startActivity(new Intent(MainActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            sesion.saveSPBoolean(sesion.SUDAH_LOGIN, true);
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();

    }
}