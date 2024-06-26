package com.example.examapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText email,pass;
    private Button login;
    private TextView signup;
    DBHelper db;
    private int Mode_Private;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.txtemail);
        pass = findViewById(R.id.txtpassword);
        login = findViewById(R.id.btnlogin);
        signup = findViewById(R.id.btnsignup);
        db = new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("mypref",Mode_Private);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email",email.getText().toString());
                editor.apply();

                String emailID = email.getText().toString();
                String password = pass.getText().toString();

                if( emailID.equals("") || password.equals(""))
                    Toast.makeText(MainActivity.this, "All Fields are required", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = db.checkuserpass(emailID,password);
                    if(checkuserpass==true){
                        Toast.makeText(MainActivity.this, "Sign In Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}