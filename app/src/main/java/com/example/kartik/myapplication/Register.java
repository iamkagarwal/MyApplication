package com.example.kartik.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.content.DialogInterface;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Register extends Activity {

    Button register;
    EditText editTextUsername,editTextPassword,editTextConfirmPassword;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        progressDialog = new ProgressDialog(this);
        editTextUsername = findViewById(R.id.registerEmail);
        editTextConfirmPassword = findViewById(R.id.confirmPass);
        editTextPassword = findViewById(R.id.registerPassword);
        register = findViewById(R.id.registerButton);
        firebaseAuth = FirebaseAuth.getInstance();
        builder = new AlertDialog.Builder(Register.this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmpassword = editTextConfirmPassword.getText().toString().trim();

                if(TextUtils.isEmpty(confirmpassword) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
                {


                    builder.setMessage("Enter E-mail,Password & confirm it");
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
                    if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
                    {
                        builder.setMessage("Enter E-mail & Password");
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
                        if(TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmpassword))
                        {
                            builder.setMessage("Enter Password & Confirm it");
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
                            if(TextUtils.isEmpty(confirmpassword) && TextUtils.isEmpty(email))
                            {
                                builder.setMessage("Confirm Password & Enter E-mail");
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
                                if(TextUtils.isEmpty(email))
                                {
                                    builder.setMessage("Enter E-mail");
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
                                    if(TextUtils.isEmpty(password))
                                    {
                                        builder.setMessage("Enter Password");
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
                                        if(TextUtils.isEmpty(confirmpassword))
                                        {
                                            builder.setMessage("Confirm Password");
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

                                            if(password.equals(confirmpassword)== false)
                                            {
                                                builder.setMessage("Password's dont Match");
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
                                                    progressDialog.setMessage("Registering User...");
                                                    progressDialog.show();
                                                    complete(email,password);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }










            }
        });

    }

    private void complete(String email,String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            finish();
                            Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Register.this , MainActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            progressDialog.dismiss();
                            builder.setMessage(task.getException().getMessage());
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                            Toast.makeText(Register.this,"try again",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}


//
//    Query query = FirebaseDatabase
//            .getInstance().getReference().child("users").orderByChild("username")
//            .equalTo(email);
//                                                query.addListenerForSingleValueEvent(new ValueEventListener() {
//@Override
//public void onDataChange(DataSnapshot dataSnapshot) {
//        if(dataSnapshot.getChildrenCount() > 0)
//        {
//        builder.setMessage("ID already exists");
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//@Override
//public void onClick(DialogInterface dialog, int which) {
//        dialog.dismiss();
//        }
//        });
//        builder.show();
//        }
//        }
//
//@Override
//public void onCancelled(DatabaseError databaseError) {
//
//        }
//        });