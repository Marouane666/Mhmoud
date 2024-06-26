package com.example.examapp;

import static com.example.examapp.R.*;
import static com.example.examapp.R.color.black;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;

public class ResultActivity extends AppCompatActivity {

    TextView home,account,result,totalq,score,correctans,incorrect,title1,title3;
    int correct,wrong,totalque;
    Button save,prescore;
    DBResult db;
    SQLiteDatabase sqLiteDatabase;
    AppBarLayout.ScrollingViewBehavior scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_result);

        correct = getIntent().getIntExtra("correct",0);
        wrong = getIntent().getIntExtra("wrong",0);

        home = findViewById(id.txthome);
        account = findViewById(id.txtaccount);
        result = findViewById(id.txtresult);
        totalq = findViewById(id.txttotalq);
        score = findViewById(id.txtscore);
        correctans = findViewById(id.txtcorrectans);
        incorrect = findViewById(id.txtincorrect);
        title1 = findViewById(id.txttitle1);
        title3 = findViewById(id.txttitle3);
        save = findViewById(id.btnsave);
        prescore = findViewById(id.btnprescore);

        db = new DBResult(this);
        sqLiteDatabase=db.getWritableDatabase();

        Intent intent = getIntent();
        String str = intent.getStringExtra("title");
        title3.setText(str);

        totalque = correct + wrong;
        totalq.setText( "" + (correct+wrong));
        if(totalque==4) {
            score.setText("" + (correct * 100) / 4  + "%");
        }
        else if(totalque==5){
            score.setText("" + (correct*100) / 5 + "%");
        }
        else{
            score.setText("" + "0%");
        }
        correctans.setText("" + correct);
        incorrect.setText("" + wrong);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                String email = title1.getText().toString();
                String subject = title3.getText().toString();
                String totalscore = score.getText().toString();
                String rightans = correctans.getText().toString();
                String wrongans = incorrect.getText().toString();

                if(subject.equals("")){
                    Toast.makeText(ResultActivity.this, "Please give an exam First...", Toast.LENGTH_SHORT).show();

                }else{
                    String q = "insert into result1 values('" + email + "','" + subject + "','" + totalscore + "','" + rightans + "','" + wrongans + "')";
                    sqLiteDatabase.execSQL(q);
                    Toast.makeText(ResultActivity.this, "Result Saved Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        prescore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = title1.getText().toString();
                Cursor c = db.get_data(email);
                if(c.getCount()==0){
                    Toast.makeText(ResultActivity.this, "There is no previous Score Found...", Toast.LENGTH_SHORT).show();
                }
                StringBuffer buffer = new StringBuffer();
                while(c.moveToNext()){
                    buffer.append("Subject: " + c.getString(1) + "\n");
                    buffer.append("Score: " + c.getString(2) + "\n");
                    buffer.append("Correct: " + c.getString(3) + "\n");
                    buffer.append("Wrong: " + c.getString(4) + "\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Your Previous Results..." + "\n");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        String val = sharedPreferences.getString("email","");
        title1.setText(val);
    }
}