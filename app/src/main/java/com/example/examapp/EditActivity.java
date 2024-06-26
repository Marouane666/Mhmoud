package com.example.examapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText name,email,newpass;
    Button update,cancel;
    EditAndDelete db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        name = findViewById(R.id.txtusername);
        email = findViewById(R.id.txtmail);
        newpass = findViewById(R.id.txtnewpassword);
        update = findViewById(R.id.btnupdate);
        cancel = findViewById(R.id.btncancel);
        db = new EditAndDelete(this);

        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String nm = name.getText().toString();
                String mail = email.getText().toString();
                String npass = newpass.getText().toString();


                if( nm.equals("") || mail.equals("") || npass.equals(""))
                    Toast.makeText(EditActivity.this, "All Fields are required", Toast.LENGTH_SHORT).show();
                else{
                    boolean i = db.update_data(nm, mail, npass);
                    if(i==true){
                        Toast.makeText(EditActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditActivity.this,AccountActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(EditActivity.this, "Profile Not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
    }
}