package com.example.kartik.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends Activity {
    Button submit;
    RadioGroup ratingGroup;
    EditText name,suggetions;
    RadioButton ratingButton;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        submit = findViewById(R.id.submitFeedback);
        ratingGroup = findViewById(R.id.rating);
        name = findViewById(R.id.name);
        builder = new AlertDialog.Builder(Feedback.this);

        suggetions = findViewById(R.id.suggetions);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString().trim();
                    int selectedID = ratingGroup.getCheckedRadioButtonId();
                    String rating;
                    if (selectedID == -1)
                    {
                        rating = null;
                    }
                    else
                    {
                        ratingButton = findViewById(selectedID);
                        rating = ratingButton.getText().toString();
                    }



                String suggest = suggetions.getText().toString().trim();



                if(TextUtils.isEmpty(username))
                {
                    builder.setMessage("Enter name");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
                else
                {
                    if (TextUtils.isEmpty(rating))
                    {
                        builder.setMessage("Rate our app");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                    else
                    {
                        UserInfo userInfo=saveInfo(username,rating,suggest);
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        databaseReference.child(user.getUid()).setValue(userInfo);
                        finish();
                    }
                }






            }
        });
    }
    UserInfo saveInfo(String name,String rating, String suggetions)
    {
        UserInfo userInfo = new UserInfo(name,rating,suggetions);
        return  userInfo;
    }
}
