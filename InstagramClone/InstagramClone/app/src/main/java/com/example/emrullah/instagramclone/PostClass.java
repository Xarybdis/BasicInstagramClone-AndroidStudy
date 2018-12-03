package com.example.emrullah.instagramclone;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

//This class make the connection between custom view and feedActivity class.
// With this code we save the data sent from feedAcvitiy to these arraylists in below. Then we send that data to custom view to show.

public class PostClass extends ArrayAdapter<String> {
    //With this code we save the data from feedActivity to these arraylists
    private final ArrayList<String> getUsername;
    private final ArrayList<String> getUserComment;
    private final ArrayList<Bitmap> getUserImage;
    private final Activity context;

    //With constructor we get the data when we invoke an object from this class
    public PostClass(ArrayList<String> getUsername, ArrayList<String> getUserComment, ArrayList<Bitmap> getUserImage, Activity context){
        super(context,R.layout.custom_view,getUsername);

        this.getUsername=getUsername;
        this.getUserComment=getUserComment;
        this.getUserImage=getUserImage;
        this.context=context;
    }



    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        //With this code we inflate the data we get from feedActivity to custom view.
        //
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_view,null,true);
        TextView userNameText = customView.findViewById(R.id.customView_userNameText);
        TextView userCommentText= customView.findViewById(R.id.customView_commentText);
        ImageView userImageView = customView.findViewById(R.id.customView_imageView);

        //We set the data to custom view :D
        userNameText.setText(getUsername.get(position));
        userCommentText.setText(getUserComment.get(position));
        userImageView.setImageBitmap(getUserImage.get(position));


        return customView;
    }
}
