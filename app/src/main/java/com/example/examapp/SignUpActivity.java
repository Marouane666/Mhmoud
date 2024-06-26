package com.example.examapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText uname,email,pass,repass;
    private Button signup;
    private TextView login;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        uname = findViewById(R.id.txtuname);
        email = findViewById(R.id.txtemail);
        pass = findViewById(R.id.txtpassword);
        repass = findViewById(R.id.txtrepassword);
        signup = findViewById(R.id.btnsignup);
        login = findViewById(R.id.btnlogin);
        db = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = uname.getText().toString();
                String emailID = email.getText().toString();
                String password = pass.getText().toString();
                String repassword = repass.getText().toString();

                if(username.equals("") || emailID.equals("") || password.equals("") || repassword.equals(""))
                    Toast.makeText(SignUpActivity.this, "All Fields are required", Toast.LENGTH_SHORT).show();
                else{
                    if(password.equals(repassword)) {
                        Boolean checkuser = db.checkuseremail(emailID);
                        if (checkuser == false) {
                            Boolean insert = db.insertData(username, emailID , password);
                            if (insert == true) {
                                Toast.makeText(SignUpActivity.this, "Registerd Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignUpActivity.this, "User already exists! Please Sign In", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignUpActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}