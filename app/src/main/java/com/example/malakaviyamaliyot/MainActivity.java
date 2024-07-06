package com.example.malakaviyamaliyot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public String foyIsm, foyFamiliya, daraja, foyTel;
    TextView kundalik,
            maNizomi,
            maBuyichaMetodikTavsiya,
            maHujjatlari,
            maMaydonchasi,
            topshiriqlar,
            galeriya,
            namunaviyHisobot,
            qushimchaTavsiyalar,
            kutubxona,
            amaliyotBaxolari,
            mualliflar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kundalik = findViewById(R.id.kundalikTV);
        maNizomi = findViewById(R.id.maNizomiTV);
        maBuyichaMetodikTavsiya = findViewById(R.id.maBuyichaMetodikTavsiyaTV);
        maHujjatlari = findViewById(R.id.maHujjatlariTV);
        maMaydonchasi = findViewById(R.id.maMaydonchasiTV);
        topshiriqlar = findViewById(R.id.topshiriqlarTV);
        galeriya = findViewById(R.id.galeriyaTV);
        namunaviyHisobot = findViewById(R.id.namunaviyHisobotTV);
        qushimchaTavsiyalar = findViewById(R.id.qushimchaTavsiyalarTV);
        kutubxona = findViewById(R.id.KutubxonaTV);
        amaliyotBaxolari = findViewById(R.id.amaliyot_baxolariTV);
        mualliflar = findViewById(R.id.MualliflarTV);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            foyIsm = extras.getString("foyIsm");
            foyFamiliya = extras.getString("foyFamiliya");
            foyTel = extras.getString("foyTel");
            daraja = extras.getString("daraja");
        }

        kundalik.setOnClickListener(v -> {
            if (Objects.equals(daraja, "Teacher")){
                Intent intent = new Intent(MainActivity.this, tKundalik.class);
                intent.putExtra("foyIsm", foyIsm);
                intent.putExtra("foyFamiliya", foyFamiliya);
                startActivity(intent);
            } else if (Objects.equals(daraja, "Student")){
                Intent intent = new Intent(MainActivity.this, Kundalik.class);
                intent.putExtra("foyIsm", foyIsm);
                intent.putExtra("foyFamiliya", foyFamiliya);
                intent.putExtra("foyTel", foyTel);
                startActivity(intent);
            }

        });
        maNizomi.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, maNizomi.class);
            intent.putExtra("foyIsm", foyIsm);
            intent.putExtra("foyFamiliya", foyFamiliya);
            startActivity(intent);
        });
        maBuyichaMetodikTavsiya.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, maBuyichaMetodikTavsiya.class);
            intent.putExtra("foyIsm", foyIsm);
            intent.putExtra("foyFamiliya", foyFamiliya);
            startActivity(intent);
        });
        maHujjatlari.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, maHujjatlari.class);
            intent.putExtra("foyIsm", foyIsm);
            intent.putExtra("foyFamiliya", foyFamiliya);
            startActivity(intent);
        });
        maMaydonchasi.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, maMaydonchasi.class);
            intent.putExtra("foyIsm", foyIsm);
            intent.putExtra("foyFamiliya", foyFamiliya);
            startActivity(intent);
        });
        topshiriqlar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Topshiriqlar.class);
            intent.putExtra("foyIsm", foyIsm);
            intent.putExtra("foyFamiliya", foyFamiliya);
            startActivity(intent);
        });
        galeriya.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Galeriya.class);
            intent.putExtra("foyIsm", foyIsm);
            intent.putExtra("foyFamiliya", foyFamiliya);
            intent.putExtra("foyTel", foyTel);
            intent.putExtra("daraja", daraja);
            startActivity(intent);
        });
        namunaviyHisobot.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NamunaviyHisobot.class);
            intent.putExtra("foyIsm", foyIsm);
            intent.putExtra("foyFamiliya", foyFamiliya);
            startActivity(intent);
        });
        qushimchaTavsiyalar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QushimchaTavsiyalar.class);
            intent.putExtra("foyIsm", foyIsm);
            intent.putExtra("foyFamiliya", foyFamiliya);
            startActivity(intent);
        });
        kutubxona.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Kutubxona.class);
            intent.putExtra("foyIsm", foyIsm);
            intent.putExtra("foyFamiliya", foyFamiliya);
            startActivity(intent);
        });
        mualliflar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Mualliflar.class);
            intent.putExtra("foyIsm", foyIsm);
            intent.putExtra("foyFamiliya", foyFamiliya);
            startActivity(intent);
        });

        amaliyotBaxolari.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, AmaliyotBaxolari1.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, Kirish.class);
        intent.putExtra("foyIsm", foyIsm);
        intent.putExtra("foyFamiliya", foyFamiliya);
        startActivity(intent);
    }
}