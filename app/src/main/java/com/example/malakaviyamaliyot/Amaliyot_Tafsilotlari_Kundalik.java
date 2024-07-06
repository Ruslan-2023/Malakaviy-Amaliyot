package com.example.malakaviyamaliyot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Amaliyot_Tafsilotlari_Kundalik extends AppCompatActivity {
    public String AboutDate,PhoneNum;
    public TextView date;
    public ListView practiceClass;
    public List<String> AllClass = new ArrayList<>();
    public List<String> AboutPracticeList = new ArrayList<>();
    public List<String> list = new ArrayList<>();

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://malakaviy-amaliyot-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amaliyot_tafsilotlari_kundalik);

        date = findViewById(R.id.dateTV);
        practiceClass = findViewById(R.id.PracticeClassTV);

        //-----Tekshiriluvchi amaliyot sanasi-----
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            AboutDate = extras.getString("AboutDate");
            PhoneNum = extras.getString("PhoneNum");
        }
        //-----/Tekshiriluvchi amaliyot sanasi/-----

        dbRef.child("Users")
                .child("Student")
                .child(PhoneNum)
                .child("Date")
                .child(AboutDate)
                .child("Class")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    //  Sinflarni "AllClass"ga kiritish
                    AllClass.add(snapshot1.getKey());
                    //  /Sinflarni "AllClass"ga kiritish/
                    }
                for (String x : AllClass){
                    dbRef.child("Users")
                            .child("Student")
                            .child(PhoneNum)
                            .child("Date")
                            .child(AboutDate)
                            .child("Class")
                            .child(x).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                        AboutPracticeList.add(snapshot1.getValue().toString());
                                    }
                                    list.add("Sana: " + AboutPracticeList.get(2) + "\nSoat: " + AboutPracticeList.get(3) + "\nQatnashuvchilar: " + AboutPracticeList.get(1) + "\nSinf: " + x + "\nMavzu: " + AboutPracticeList.get(4) + "\nBayonnoma: " + AboutPracticeList.get(0));
                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                                    practiceClass.setAdapter(arrayAdapter);
                                    AboutPracticeList.clear();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}});
                }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});
    }
}