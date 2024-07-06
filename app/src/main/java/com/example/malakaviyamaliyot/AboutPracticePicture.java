package com.example.malakaviyamaliyot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

import kotlin.Metadata;

public class AboutPracticePicture extends AppCompatActivity {

    ArrayList<String> imagelist;
    RecyclerView recyclerView;
    ImageAdapter adapter;
    public int k=0;
    public String foyTel, foyIsm, foyFamiliya, datePosition, timerImageName, practiceDate, day, month, year;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://malakaviy-amaliyot-default-rtdb.firebaseio.com/");
        StorageReference listRef = FirebaseStorage.getInstance().getReference().child("Images");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_practice_picture);
        //------------------------------------------------------------------------------------------------------------------
        imagelist=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ImageAdapter(imagelist,this);
        //------------------------------------------------------------------------------------------------------------------
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            foyTel = extras.getString("foyTel");
            foyIsm = extras.getString("foyIsm");
            foyFamiliya = extras.getString("foyFamiliya");
            datePosition = extras.getString("Position");
        }
//------------------------------------------------------------------------------------------------------------------
        dbRef.child("Users").child("Student").child(foyTel).child("Date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //  Amiliyot sanasini aniqlash
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    if (k == Integer.parseInt(datePosition)){
                        practiceDate = snapshot1.getKey();
                    }
                    k++;
                }
                day = practiceDate.substring(0,2);
                month = practiceDate.substring(2,4);
                year = practiceDate.substring(4,8);
                practiceDate = day + "." + month + "." + year;

                //  Amaliyot sanasi aniqlandi
                //------------------------------------------------------------------------------------------------------------------
                //  Amaliyot sanasida rasmni aniqlash
                listRef.listAll().addOnSuccessListener(listResult -> {
                    timerImageName = foyFamiliya.substring(0,1) + foyIsm.substring(0,1) + practiceDate;

                   for (StorageReference file : listResult.getItems()){
                       file.getMetadata().addOnSuccessListener(storageMetadata -> {
                           if (storageMetadata.getName().startsWith(timerImageName)){

                               file.getDownloadUrl().addOnSuccessListener(uri -> {
                                   imagelist.add(uri.toString());
                               }).addOnSuccessListener(uri -> {
                                   recyclerView.setAdapter(adapter);
                               });
                           }
                       });
                   }

                });
                //------------------------------------------------------------------------------------------------------------------
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//------------------------------------------------------------------------------------------------------------------
    }
}