package com.example.malakaviyamaliyot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AmaliyotBaxolari2 extends AppCompatActivity {

    public int k=0, wordsSum;
    String position, sTel, sName, sLastName, day, month, year, timeClass;
    List<String> practiceDates, practiceAllClass, allDataList, practiceScore, allClass, sectionsList;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://malakaviy-amaliyot-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amaliyot_baxolari2);

        ListView amaliyotBaxolariList = findViewById(R.id.amailyot_baxolari_listview);

        practiceDates = new ArrayList<>();
        practiceAllClass = new ArrayList<>();
        allDataList = new ArrayList<>();
        practiceScore = new ArrayList<>();
        allClass = new ArrayList<>();
        sectionsList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            position = extras.getString("Position");
        }

        dbRef.child("Users").child("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //-----Talaba telefon raqamini qabul qilish-----
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    if(k == Integer.parseInt(position)){
                        sTel = snapshot1.getKey();
                    }
                    k++;
                }
                //-----/Talaba telefon raqamini qabul qilish/------
                //-------------------------------------------------------------------------------------------------------------------
                dbRef.child("Users").child("Student").child(sTel).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot PhoneSnapshot) {
                        //-----Talaba ism va familiyasini qabul qilish-----
                        sName = String.valueOf(PhoneSnapshot.child("Name").getValue());
                        sLastName = String.valueOf(PhoneSnapshot.child("LastName").getValue());
                        //-----/Talaba ism va familiyasini qabul qilish/------

                        if (PhoneSnapshot.hasChild("Date")){
                            //-----Amaliyot sanalari mavjud-----

                            //-----Amaliyotni baxolash-----
                            for (DataSnapshot snapshot1 : PhoneSnapshot.child("Date").getChildren()) {
                                //-----Sanalarni qabul qilish sikli
                                practiceDates.add(String.valueOf(snapshot1.getKey()));
                            }
                            for (String practiceDate : practiceDates) {
                                System.out.println("------------------------------------------------------------");
                                System.out.println(practiceDate + " sana uchun ish boshlandi");
                                for (DataSnapshot snapshot1 : PhoneSnapshot.child("Date").child(practiceDate).child("Class").getChildren()) {
                                    //-----Sinflarni qabul qilish sikli
                                    timeClass = snapshot1.getKey();
                                    System.out.println(timeClass + " sinf uzatildi!");

                                    String words = String.valueOf(PhoneSnapshot.child("Date").child(practiceDate).child("Class").child(timeClass).child("Details").getValue());
                                    StringTokenizer st = new StringTokenizer(words);
                                    wordsSum = st.countTokens();

                                    day = practiceDate.substring(0,2);
                                    month = practiceDate.substring(2,4);
                                    year = practiceDate.substring(4,8);

                                    if (wordsSum >= 13) {
                                        allDataList.add("Ism: " + sLastName + " " + sName + "\nSana: " + day + "." + month + "." + year + "\nSinf: " + timeClass + "\nAmaliyot baxosi: " + "5");
                                    } else if (wordsSum >= 10){
                                        allDataList.add("Ism: " + sLastName + " " + sName + "\nSana: " + day + "." + month + "." + year + "\nSinf: " + timeClass + "\nAmaliyot baxosi: " + "4");
                                    } else if (wordsSum >=7){
                                        allDataList.add("Ism: " + sLastName + " " + sName + "\nSana: " + day + "." + month + "." + year + "\nSinf: " + timeClass + "\nAmaliyot baxosi: " + "3");
                                    } else {
                                        allDataList.add("Ism: " + sLastName + " " + sName + "\nSana: " + day + "." + month + "." + year + "\nSinf: " + timeClass + "\nAmaliyot baxosi: " + "2");
                                    }
                                }
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, allDataList);
                            amaliyotBaxolariList.setAdapter(arrayAdapter);

                            //-----/Amaliyot sanalari mavjud/----
                        } else {
                            Toast.makeText(AmaliyotBaxolari2.this, "Ushbu talaba amaliyot o'tamagan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                //-------------------------------------------------
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}