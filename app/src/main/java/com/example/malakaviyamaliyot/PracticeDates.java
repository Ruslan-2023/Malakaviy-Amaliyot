package com.example.malakaviyamaliyot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class PracticeDates extends AppCompatActivity {

    public int k=0;
    public ListView practiceDatesList;
    public String studentPosition, foyTel,foyIsm, foyFamiliya, timeText = null, practiceDateFocus = null;
    public List<String> practiceDatesText = new ArrayList<>();
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://malakaviy-amaliyot-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_dates);

        practiceDatesList = findViewById(R.id.practiceDatesLV); //  <-Amaliyot sanalarini chiqaruvchi list
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            studentPosition = extras.getString("Position");
        }
        //---------------------------------------------------------------------------------------------------------

        dbRef.child("Users").child("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    if (k == Integer.parseInt(studentPosition)){
                        foyTel = snapshot1.getKey();
                        foyIsm = String.valueOf(snapshot1.child("Name").getValue());
                        foyFamiliya = String.valueOf(snapshot1.child("LastName").getValue());
                    }
                    k++;
                }
//-------------------------------------------------------------------------------------------------------------------
                dbRef.child("Users").child("Student").child(foyTel).child("Date").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            timeText = snapshot1.getKey();
                            timeText = timeText.substring(0, 2) + "." + timeText.substring(2, 4) + "." + timeText.substring(4, 8);
                            practiceDatesText.add(timeText);
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, practiceDatesText);
                        practiceDatesList.setAdapter(arrayAdapter);
//-------------------------------------------------------------------------------------------------------------------
                        practiceDatesList.setOnItemClickListener((parent, view, position, id) -> {

                            Intent intent = new Intent(PracticeDates.this, AboutPracticePicture.class);
                            intent.putExtra("foyTel", foyTel);
                            intent.putExtra("foyIsm", foyIsm);
                            intent.putExtra("foyFamiliya", foyFamiliya);
                            intent.putExtra("Position", String.valueOf(position));
                            startActivity(intent);
                        });
//-------------------------------------------------------------------------------------------------------------------
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
//-------------------------------------------------------------------------------------------------------------------

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}