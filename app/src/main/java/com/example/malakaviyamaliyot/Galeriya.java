package com.example.malakaviyamaliyot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Galeriya extends AppCompatActivity {

    FloatingActionButton plus;
    public ListView studentsList;
    public List<String> list = new ArrayList<>();
    public String foyIsm, foyFamiliya, daraja, foyTel;
    //public static Double latitude, longtitude, LatTop, LatBottom, LongLeft, LongRight;
    private static final int REQUEST_CODE = 101;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://malakaviy-amaliyot-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galeriya);

        //getLocation();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            foyIsm = extras.getString("foyIsm");
            foyFamiliya = extras.getString("foyFamiliya");
            foyTel = extras.getString("foyTel");
            daraja = extras.getString("daraja");
        }

        studentsList = findViewById(R.id.studentsListTV);
        plus = findViewById(R.id.plusBtn);

        studentsList.setVisibility(View.INVISIBLE);
        plus.setVisibility(View.INVISIBLE);
        //---------------------------------------------------------------------------------------------

        if (daraja.equals("Teacher")){
            studentsList.setVisibility(View.VISIBLE);

            dbRef.child("Users").child("Student").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        String name = String.valueOf(snapshot1.child("Name").getValue());
                        String lastName = String.valueOf(snapshot1.child("LastName").getValue());
                        list.add(lastName + " " + name);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
            studentsList.setAdapter(arrayAdapter);
            // -----AboutPracticePicture klassiga o'tish---------------
            studentsList.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(Galeriya.this, PracticeDates.class);
                intent.putExtra("Position", String.valueOf(position));

                startActivity(intent);
            });
            // -----/AboutPracticePicture klassiga o'tish/-------------------
        } else if (daraja.equals("Student")){
            plus.setVisibility(View.VISIBLE);
//            dbRef.child("MAMK").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    LatTop = snapshot.child("LatTop").getValue(Double.class);
//                    LatBottom = snapshot.child("LatBottom").getValue(Double.class);
//                    LongLeft = snapshot.child("LongLeft").getValue(Double.class);
//                    LongRight = snapshot.child("LongRight").getValue(Double.class);
//
//                    if (((LongLeft < longtitude) && (longtitude < LongRight)) && ((LatTop > latitude) && (latitude > LatBottom))){
//                        Toast.makeText(Galeriya.this, "Siz malakaviy amaliyot maydonchasidasiz :)", Toast.LENGTH_LONG).show();
//                        Toast.makeText(Galeriya.this, "Rasm yuklash uchun '+' belgisini bosing!", Toast.LENGTH_SHORT).show();
//                        plus.setVisibility(View.VISIBLE);
//
//                    } else {
//                        Toast.makeText(Galeriya.this, "Siz malakaviy amaliyot maydonchasida emassiz!", Toast.LENGTH_LONG).show();
//                        Toast.makeText(Galeriya.this, "Rasm yuklash uchun malakaviy amaliyot maydonchasiga borib boshqatdan urinib ko'ring!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(Galeriya.this, "Baza bilan bog'lanishda xatolik mavjud!!!", Toast.LENGTH_SHORT).show();
//                }
//            });

        } else {
            Toast.makeText(this, "Dastur ishdan chiqdi!!!", Toast.LENGTH_SHORT).show();
        }
        //---------------------------------------------------------------------------------------------
        plus.setOnClickListener(v -> {
            Intent intent = new Intent(Galeriya.this, RasmYuklash.class);
            intent.putExtra("foyTel", foyTel);
            intent.putExtra("foyIsm", foyIsm);
            intent.putExtra("foyFamiliya", foyFamiliya);
            startActivity(intent);
        });
    }
    //--------------------------------------------------------------------------------------------
    //--Lokatsiyaga murojat--
//    private void getLocation() {
//        FusedLocationProviderClient fusedLocationProviderClient;
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
//                if (location != null){
//                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//                    try {
//                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                        latitude = Double.valueOf(addresses.get(0).getLatitude());
//                        longtitude = Double.valueOf(addresses.get(0).getLongitude());
//                    } catch (IOException e){
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } else {
//            askPermission();
//        }
    }
    //--/Lokatsiyaga murojat/--
    //--------------------------------------------------------------------------------------------
    //--Lokatsiyadan foydalanishga ruxsat so'rash--
//    private void askPermission() {
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
//    }
    //--/Lokatsiyadan foydalanishga ruxsat so'rash/--
    //--------------------------------------------------------------------------------------------
    //--Lokatsiyadan foydalanishga ruxsatni tekshirish
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CODE){
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                getLocation();
//            } else {
//                Toast.makeText(this, "Dasturdan foydalanish uchun joylashuvni olishga ruxsat bering!", Toast.LENGTH_SHORT).show();
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//    }
    //--/Lokatsiyadan foydalanishga ruxsatni tekshirish/--
    //--------------------------------------------------------------------------------------------
//}