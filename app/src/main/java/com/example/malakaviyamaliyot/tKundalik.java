package com.example.malakaviyamaliyot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tKundalik extends AppCompatActivity {

    public List<String> list = new ArrayList<>();
    public String foyIsm, foyFamiliya, daraja;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://malakaviy-amaliyot-default-rtdb.firebaseio.com/");

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tkundalik);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            foyIsm = extras.getString("foyIsm");
            foyFamiliya = extras.getString("foyFamiliya");
            daraja = extras.getString("daraja");
        }

        ListView studentsList = findViewById(R.id.list_items);

        //-----Talabalar ID------
        dbRef.child("Users").child("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    String name = snapshot1.child("Name").getValue().toString();
                    String lastName = snapshot1.child("LastName").getValue().toString();
                    list.add(lastName + " " + name);
                    name = "";
                    lastName = "";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        studentsList.setAdapter(arrayAdapter);
        //-----/Talabalar ID/------

        // -----Tekshirish Kundalik klassga o'tish va malumotlarni olib o'tish
        studentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(tKundalik.this, Tekshirish_Kundalik.class);
                intent.putExtra("Position", String.valueOf(position));
                startActivity(intent);
            }
        });
        // -----/Tekshirish Kundalik klassga o'tish va malumotlarni olib o'tish/-------------------
    }
}