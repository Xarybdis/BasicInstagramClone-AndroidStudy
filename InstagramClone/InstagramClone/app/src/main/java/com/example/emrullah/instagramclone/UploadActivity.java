package com.example.emrullah.instagramclone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    //DEFINITIONS
    ImageView upload_ImageView;
    TextView upload_commentText;
    Button upload_button;
    Bitmap choosenImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        upload_ImageView =findViewById(R.id.uploadActivity_imageView);
        upload_commentText=findViewById(R.id.uploadActivity_commentText);
        upload_button=findViewById(R.id.uploadActivity_postButton);


    }

    public  void upload(View view){
        String commentText = upload_commentText.getText().toString();

        /*This code makes you to compress the image into byte and put that in an array. After you put it in array,
          you easily put that array into parseFile to upload in parse.
          With creating new parseFile object you put array in it then able to post it.
         */
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        choosenImage.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
        byte[] bytes =byteArrayOutputStream.toByteArray();
        ParseFile parseFile = new ParseFile("image.png",bytes);


        ParseObject parseObject= new ParseObject("Posts");
        parseObject.put("images",parseFile);
        parseObject.put("comment",commentText);
        parseObject.put("username",ParseUser.getCurrentUser().getUsername());
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!= null){
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Post uploaded!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    /*With this method , we ask for permission to reach user's external storage to read. If you open this app first time
    then he goest to onRequestPermissionResult method. And asks for permission. Then if it gets permission , it goes to
    onActivityResult method to get media and set it on screen.
    */

    public void chooseImage(View view){
        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){//We need to be careful about request code. Because miss match may cause not to get picture.
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //We need to be careful about request code. Because miss match may cause not to get picture.
        //Also checking for resultCode is good for catching the miss match.
        if (requestCode ==2 && resultCode== RESULT_OK && data!=null){
            Uri uri = data.getData();

            try {
                choosenImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                upload_ImageView.setImageBitmap(choosenImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
