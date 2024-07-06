package com.example.malakaviyamaliyot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class RasmYuklash extends AppCompatActivity {

    public EditText sana, sinf;
    public ImageView uploadPic;
    public String foyTel, foyIsm, foyFamiliya, uploadImageName;
    final int IMAGE_REQUEST=71;
    Uri imageLocationPath;
    StorageReference objStorageRef;
    FirebaseFirestore objFirebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rasm_yuklash);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            foyIsm = extras.getString("foyIsm");
            foyFamiliya = extras.getString("foyFamiliya");
            foyTel = extras.getString("foyTel");
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("foyIsm: " + foyIsm + "\nfoyFamiliya: " + foyFamiliya + "\nfoyTel: " + foyTel);
        System.out.println("------------------------------------------------------------------------");

        sinf = findViewById(R.id.sinfET);
        sana = findViewById(R.id.sanaET);
        uploadPic = findViewById(R.id.imageID);
        objStorageRef = FirebaseStorage.getInstance().getReference("Images");
        objFirebaseFirestore =FirebaseFirestore.getInstance();

    }
    //-----Rasm tanlash-----------
    public void selectImage(View view){
        try {
            Intent objIntent = new Intent();
            objIntent.setType("image/*");
            objIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objIntent, IMAGE_REQUEST);

        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //--------------------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                imageLocationPath = data.getData();
                Bitmap objBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageLocationPath);
                uploadPic.setImageBitmap(objBitmap);
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //-----/Rasm tanlash funksiyasi/--------------------
    //-----Rasmni yuklash funksiyasi--------------------
    public void uploadImage(View view){
        try {
            if (!sana.getText().toString().isEmpty() && !sinf.getText().toString().isEmpty() && imageLocationPath != null){
                uploadImageName = foyFamiliya.substring(0,1) + foyIsm.substring(0,1) + sana.getText().toString() + sinf.getText().toString();

                String nameOfImage = uploadImageName + "." +getExtension(imageLocationPath);

                StorageReference imageRef = objStorageRef.child(nameOfImage);
                UploadTask objUploadTask =imageRef.putFile(imageLocationPath);
                objUploadTask.continueWithTask(task -> {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return imageRef.getDownloadUrl();
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Map<String, String> objMap = new HashMap<>();
                        objMap.put("url", task.getResult().toString());

                        objFirebaseFirestore.collection("Links").document(uploadImageName)
                                .set(objMap)
                                .addOnSuccessListener(unused -> Toast.makeText(RasmYuklash.this, "Rasm bazaga yuklandi", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(RasmYuklash.this, "Rasm bazaga yuklanmadi!", Toast.LENGTH_SHORT).show());
                    } else if(!task.isSuccessful()) {
                        Toast.makeText(RasmYuklash.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else{
                Toast.makeText(this, "Iltimos qatorlarni to'ldiring!", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //-----/Rasmni yuklash funksiyasi/-------------------
    //--
    private String getExtension(Uri uri){
        try {
            ContentResolver objContentResolver = getContentResolver();
            MimeTypeMap objMimetypeMap = MimeTypeMap.getSingleton();
            return objMimetypeMap.getExtensionFromMimeType(objContentResolver.getType(uri));
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    //--/
}