package com.example.examapp;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AccountActivity extends AppCompatActivity {

    TextView home,search,account,titlename,testtxt;
    Button logout,edit,delete;
    EditAndDelete db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        home = findViewById(R.id.txthome);
        search = findViewById(R.id.txtsearch);
        account = findViewById(R.id.txtaccount);
        logout = findViewById(R.id.btnlogout);
        edit = findViewById(R.id.btnedit);
        delete = findViewById(R.id.btndelete);
        titlename = findViewById(R.id.txtuname);
        testtxt = findViewById(R.id.test);

        db = new EditAndDelete(this);



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this,ResultActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this,EditActivity.class);
                startActivity(intent);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(AccountActivity.this);
                dialog.setContentView(R.layout.activity_delete);
                dialog.show();
                dialog.findViewById(R.id.btnno).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AccountActivity.this,AccountActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.findViewById(R.id.btnyes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mail = testtxt.getText().toString();
                        boolean i = db.delete_data(mail);
                        if(i==true){
                            Toast.makeText(AccountActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AccountActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        String val = sharedPreferences.getString("email","");
        testtxt.setText(val);
    }
}