package com.example.malakaviyamaliyot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Kundalik extends AppCompatActivity {

    public EditText sana, soat, qatnashuvchilar, sinf, mavzu, bayonnoma;
    public String sanaText, soatText, qatnashuvchilarText, sinfText, mavzuText, bayonnomaText;
    public Button yuborish;
    public String foyIsm, foyFamiliya, foyTel, sanaText2 = "";
    public char[] sanaText1;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://malakaviy-amaliyot-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kundalik);

        sana = findViewById(R.id.sanaET);
        soat = findViewById(R.id.soatET);
        qatnashuvchilar = findViewById(R.id.qatnashuvchilarET);
        sinf = findViewById(R.id.sinfET);
        mavzu = findViewById(R.id.mavzuET);
        bayonnoma = findViewById(R.id.bayonnomaET);
        yuborish = findViewById(R.id.yuborishBtn);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            foyIsm = extras.getString("foyIsm");
            foyFamiliya = extras.getString("foyFamiliya");
            foyTel = extras.getString("foyTel");
        }

        yuborish.setOnClickListener(v -> {
           if ((!sana.getText().toString().equals("")) && (!soat.getText().toString().equals("")) && (!qatnashuvchilar.getText().toString().equals("")) && (!sinf.getText().toString().equals("")) && (!mavzu.getText().toString().equals("")) && (!bayonnoma.getText().toString().equals(""))){

               sanaText = sana.getText().toString();
               soatText = soat.getText().toString();
               qatnashuvchilarText = qatnashuvchilar.getText().toString();
               sinfText = sinf.getText().toString();
               mavzuText = mavzu.getText().toString();
               bayonnomaText = bayonnoma.getText().toString();

               sanaText1 = sana.getText().toString().toCharArray();
               for (char x : sanaText1){
                   if (x == '.'){
                       continue;
                   } else {
                       sanaText2 = sanaText2 + x;
                   }
               }

               //   Bayonnoma
               dbRef.child("Users")
                       .child("Student")
                       .child(foyTel)
                       .child("Date")
                       .child(sanaText2)
                       .child("Class")
                       .child(sinfText)
                       .child("Details").setValue(bayonnomaText);
               //   /Bayonnoma/

               //   Qatnashuvchilar
               dbRef.child("Users")
                       .child("Student")
                       .child(foyTel)
                       .child("Date")
                       .child(sanaText2)
                       .child("Class")
                       .child(sinfText)
                       .child("Partner").setValue(qatnashuvchilarText);
               //   /Qatnashuvchilar/

               //   Amaliyot sanasi
               dbRef.child("Users")
                       .child("Student")
                       .child(foyTel)
                       .child("Date")
                       .child(sanaText2)
                       .child("Class")
                       .child(sinfText)
                       .child("PracticeDate").setValue(sanaText);
               //   /Amaliyot sanasi/

               //   Vaqt
               dbRef.child("Users")
                       .child("Student")
                       .child(foyTel)
                       .child("Date")
                       .child(sanaText2)
                       .child("Class")
                       .child(sinfText)
                       .child("Time").setValue(soatText);
               //   /Vaqt/

               //   Mavzu
               dbRef.child("Users")
                       .child("Student")
                       .child(foyTel)
                       .child("Date")
                       .child(sanaText2)
                       .child("Class")
                       .child(sinfText)
                       .child("Topic").setValue(mavzuText);
               //   /Mavzu/
               sanaText2 = "";

               sanaText = "";
               soatText = "";
               qatnashuvchilarText = "";
               sinfText = "";
               mavzuText = "";
               bayonnomaText = "";

               sana.setText(sanaText);
               soat.setText(soatText);
               qatnashuvchilar.setText(qatnashuvchilarText);
               sinf.setText(sinfText);
               mavzu.setText(mavzuText);
               bayonnoma.setText(bayonnomaText);

               Toast.makeText(this, "Malumotlar bazaga yuborildi", Toast.LENGTH_SHORT).show();
           } else {
               Toast.makeText(this, "Barcha qatorlar to'liqligiga ishonch hosil qiling!", Toast.LENGTH_SHORT).show();
           }
        });


    }
}