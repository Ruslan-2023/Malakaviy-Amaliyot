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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tekshirish_Kundalik extends AppCompatActivity {

    public int IntPosition, i;
    public List<String> list = new ArrayList<>();
    public String phoneNumber, Name, lastName;
    public TextView fullName;
    public String Position;
    public ListView datesList;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://malakaviy-amaliyot-default-rtdb.firebaseio.com/");


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tekshirish_kundalik);

        fullName = findViewById(R.id.fullNameTV);
        datesList = findViewById(R.id.dateTV);

        //---------------------------------------------------------------------------
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            Position = extras.getString("Position");
        }
        IntPosition = Integer.parseInt(Position);
        //---------------------------------------------------------------------------
        dbRef.child("Users").child("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int k = 0;
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (k == IntPosition) {
                        phoneNumber = snapshot1.child("PhoneNum").getValue().toString();
                        Name = snapshot1.child("Name").getValue().toString();
                        lastName = snapshot1.child("LastName").getValue().toString();
                    }
                    k++;
                }
                fullName.setText(lastName + " " + Name);

                dbRef.child("Users").child("Student").child(phoneNumber).child("Date").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            list.add(snapshot1.getKey());
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                        datesList.setAdapter(arrayAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        datesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Tekshirish_Kundalik.this, Amaliyot_Tafsilotlari_Kundalik.class);
                intent.putExtra("AboutDate", String.valueOf(datesList.getItemAtPosition(position)));
                intent.putExtra("PhoneNum", phoneNumber);
                startActivity(intent);
            }
        });
    }
}