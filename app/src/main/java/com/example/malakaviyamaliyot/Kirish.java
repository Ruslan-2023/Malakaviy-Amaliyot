package com.example.malakaviyamaliyot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Kirish extends AppCompatActivity {

    Button kirishBtn;
    EditText telRaqamET, parolET;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://malakaviy-amaliyot-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kirish);

        kirishBtn = findViewById(R.id.kirishButton);
        telRaqamET = findViewById(R.id.telRaqam);
        parolET = findViewById(R.id.parol);


        kirishBtn.setOnClickListener(v -> {

            String phoneNumText = telRaqamET.getText().toString();
            String passwordText = parolET.getText().toString();

            if (!phoneNumText.isEmpty() && !passwordText.isEmpty()){
                dbRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//-----------------------------------------------------------------------------------------------------
                        if (snapshot.child("Student").hasChild(phoneNumText)){
                            String getPassword = snapshot.child("Student").child(phoneNumText).child("Password").getValue(String.class);
                            if (getPassword.equals(passwordText)){
                                String foyIsm = snapshot.child("Student").child(phoneNumText).child("Name").getValue(String.class);
                                String foyFamiliya = snapshot.child("Student").child(phoneNumText).child("LastName").getValue(String.class);
                                String daraja = "Student";

                                Intent intent = new Intent(Kirish.this, MainActivity.class);
                                intent.putExtra("foyIsm", foyIsm);
                                intent.putExtra("foyFamiliya", foyFamiliya);
                                intent.putExtra("daraja", daraja);
                                intent.putExtra("foyTel", phoneNumText);
                                startActivity(intent);
                            }
                        } else if (snapshot.child("Teacher").hasChild(phoneNumText)){
                            String getPassword = snapshot.child("Teacher").child(phoneNumText).child("Password").getValue(String.class);
                            if (getPassword.equals(passwordText)){
                                String foyIsm = snapshot.child("Teacher").child(phoneNumText).child("Name").getValue(String.class);
                                String foyFamiliya = snapshot.child("Teacher").child(phoneNumText).child("LastName").getValue(String.class);
                                String daraja = "Teacher";

                                Intent intent = new Intent(Kirish.this, MainActivity.class);
                                intent.putExtra("foyIsm", foyIsm);
                                intent.putExtra("foyFamiliya", foyFamiliya);
                                intent.putExtra("daraja", daraja);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(Kirish.this, "Siz bazada mavjud emassiz!", Toast.LENGTH_LONG).show();
                        }
//-----------------------------------------------------------------------------------------------------
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
//-----------------------------------------------------------------------------------------------------
                        Toast.makeText(Kirish.this, "Internet aloqangizni tekshirib qaytadan urinib ko'ring", Toast.LENGTH_SHORT).show();
//-----------------------------------------------------------------------------------------------------
                    }
                });
            }
        });
    }
}