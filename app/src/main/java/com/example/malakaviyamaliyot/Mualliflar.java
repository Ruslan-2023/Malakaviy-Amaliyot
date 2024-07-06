package com.example.malakaviyamaliyot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Mualliflar extends AppCompatActivity {

    Task<Uri> uri;
    ImageView muallifRasmi;
    TextView muallifIsmFam, muallifHaqida;
    String muallifIsm, muallifFamiliya;

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://malakaviy-amaliyot-default-rtdb.firebaseio.com/");
    StorageReference listRef = FirebaseStorage.getInstance().getReference().child("MualliflarRasmi");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mualliflar);

        muallifHaqida = findViewById(R.id.muallifHaqida);
        muallifIsmFam = findViewById(R.id.muallifIsmFam);
        muallifRasmi = findViewById(R.id.muallifRasmi);

        dbRef.child("Mualliflar").child("muallif1").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot muallif1Snapshot : snapshot.getChildren()){
                    if (String.valueOf(muallif1Snapshot.getKey()).equals("familiya")){
                        muallifFamiliya = String.valueOf(muallif1Snapshot.getValue());
                    } else if (String.valueOf(muallif1Snapshot.getKey()).equals("ism")){
                        muallifIsm = String.valueOf(muallif1Snapshot.getValue());
                    } else if (String.valueOf(muallif1Snapshot.getKey()).equals("haqida")){
                        muallifHaqida.setText(String.valueOf(muallif1Snapshot.getValue()));
                    }
                }
                muallifIsmFam.setText(muallifFamiliya + " " + muallifIsm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //-----------------------------------------------------------------------------------------
        //      Muallif rasmini yuklash
        listRef.listAll().addOnSuccessListener(listResult -> {

            for (StorageReference file : listResult.getItems()){
                file.getMetadata().addOnSuccessListener(storageMetadata -> {
                    if (storageMetadata.getName().startsWith("Avezov")){

                        uri = file.getDownloadUrl();
                    }
                });
            }

            Glide.with(this).load(uri).into(muallifRasmi);

        });
        //-----------------------------------------------------------------------------------------
    }
}