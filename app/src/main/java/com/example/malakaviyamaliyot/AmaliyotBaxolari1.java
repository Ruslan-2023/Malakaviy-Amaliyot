package com.example.malakaviyamaliyot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AmaliyotBaxolari1 extends AppCompatActivity {

    public List<String> list = new ArrayList<>();
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://malakaviy-amaliyot-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amaliyot_baxolari);

        ListView studentsList = findViewById(R.id.studentsListTV);
        //---------------------------------------------------------------------------------------
        dbRef.child("Users").child("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name, lastName;

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    name = String.valueOf(snapshot1.child("Name").getValue());
                    lastName = String.valueOf(snapshot1.child("LastName").getValue());
                    list.add(lastName + " " + name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        studentsList.setAdapter(arrayAdapter);
        //---------------------------------------------------------------------------------------
        studentsList.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(AmaliyotBaxolari1.this, AmaliyotBaxolari2.class);
            intent.putExtra("Position", String.valueOf(i));
            startActivity(intent);
        });
    }
}