package com.example.kartik.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView register;
    EditText username,password;
    Button signin;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser()!= null)
        {
            profileLogin();
        }


        register = findViewById(R.id.register);
        username = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        builder = new AlertDialog.Builder(MainActivity.this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(pass))
                {
                    builder.setMessage("Enter Username and Password");
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
                    if (TextUtils.isEmpty(email))
                    {
                        builder.setMessage("Enter Username");
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
                        if(TextUtils.isEmpty(pass))
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
                        else {
                            progressDialog.setMessage("Logging in...");
                            progressDialog.show();
                            signin(email,pass);
                        }
                    }
                }


            }
        });

    }

    private  void  signin(String email,String pass)
    {
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {

                    profileLogin();
                }
                else
                {
                    builder.setMessage("Username or Password is Invalid");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
    }
    void profileLogin()
    {
        finish();
        Intent i = new Intent(MainActivity.this,Content.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
