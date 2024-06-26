package com.example.examapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class CActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    int timervalue;
    ProgressBar progressBar;
    ArrayList<QuizModel> qlist;
    QuizModel quizModel;
    int index = 0;
    TextView coption1, coption2, coption3, coption4, cquestion, ctitle;
    CardView cc1, cc2, cc3, cc4;
    int correctcount = 0, wrongcount = 0;
    LinearLayout cnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cactivity);

        progressBar = findViewById(R.id.cquiz_timer);
        cquestion = findViewById(R.id.ctxtquestion);
        coption1 = findViewById(R.id.txtcopt1);
        coption2 = findViewById(R.id.txtcopt2);
        coption3 = findViewById(R.id.txtcopt3);
        coption4 = findViewById(R.id.txtcopt4);
        cc1 = findViewById(R.id.cvcopt1);
        cc2 = findViewById(R.id.cvcopt2);
        cc3 = findViewById(R.id.cvcopt3);
        cc4 = findViewById(R.id.cvcopt4);
        ctitle = findViewById(R.id.txtctitle);

        cnext = findViewById(R.id.cnextbtn);
        timervalue = 20;

        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                timervalue = timervalue - 1;
                progressBar.setProgress(timervalue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(CActivity.this);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.show();
                dialog.findViewById(R.id.btntimeout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }.start();


        qlist = new ArrayList<>();

        qlist.add(new QuizModel("Who is the father of C language?", "Guido van Rossum", "James Gosling", "Dennis Ritchie", "Bjarne Stroustrup", "Dennis Ritchie"));
        qlist.add(new QuizModel("What is the size of the int data type (in bytes) in C?", "4", "8", "2", "1", "4"));
        qlist.add(new QuizModel("All keywords in C are in ____________", "Lower", "Upper", "A and B both", "None of the Above", "Lower"));
        qlist.add(new QuizModel("What is an example of iteration in C?", "for", "while", "do-while", "All of the above", "All of the above"));
        qlist.add(new QuizModel("Functions in C Language are always _________", "Internal", "External", "A and B both", "None of the above", "External"));

        Collections.shuffle(qlist);
        quizModel = qlist.get(index);

        cnext.setClickable(false);
        csetAllData();
    }


    private void csetAllData() {
        cquestion.setText(quizModel.getQuestion());
        coption1.setText(quizModel.getOptionA());
        coption2.setText(quizModel.getOptionB());
        coption3.setText(quizModel.getOptionC());
        coption4.setText(quizModel.getOptionD());
        cnext.setClickable(false);
    }


    public void correct(CardView cardView) {

        cardView.setCardBackgroundColor(getResources().getColor(R.color.green));

        cnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctcount++;
                if (index < qlist.size() - 1) {
                    index++;
                    quizModel = qlist.get(index);
                    csetAllData();
                    resetColor();
                    enablebtn();
                } else {
                    gamefinish();
                }
            }
        });
    }

    public void wrong(CardView cardView) {

        cardView.setCardBackgroundColor(getResources().getColor(R.color.red));

        cnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongcount++;
                if (index < qlist.size() - 1) {
                    index++;
                    quizModel = qlist.get(index);
                    csetAllData();
                    resetColor();
                    enablebtn();
                } else {
                    gamefinish();
                }
            }
        });
    }

    private void gamefinish() {
        String cstr = ctitle.getText().toString();
        Intent intent = new Intent(CActivity.this, ResultActivity.class);
        intent.putExtra("correct", correctcount);
        intent.putExtra("wrong", wrongcount);
        intent.putExtra("title",cstr);
        startActivity(intent);
    }

    public void enablebtn() {
        cc1.setClickable(true);
        cc2.setClickable(true);
        cc3.setClickable(true);
        cc4.setClickable(true);
    }

    public void disablebtn() {
        coption1.setClickable(false);
        coption2.setClickable(false);
        coption3.setClickable(false);
        coption4.setClickable(false);
    }

    public void resetColor() {
        cc1.setCardBackgroundColor(getResources().getColor(R.color.white));
        cc2.setCardBackgroundColor(getResources().getColor(R.color.white));
        cc3.setCardBackgroundColor(getResources().getColor(R.color.white));
        cc4.setCardBackgroundColor(getResources().getColor(R.color.white));
    }

    public void coption1click(View view) {
        cc2.setClickable(false);
        cc3.setClickable(false);
        cc4.setClickable(false);
        cnext.setClickable(true);
        if (quizModel.getOptionA().equals(quizModel.getAnswer())) {
            //c1.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < qlist.size()) {
                correct(cc1);
            } else {
                gamefinish();
            }
        } else {
            wrong(cc1);
        }
    }

    public void coption2click(View view) {
        cc1.setClickable(false);
        cc3.setClickable(false);
        cc4.setClickable(false);
        cnext.setClickable(true);
        if (quizModel.getOptionB().equals(quizModel.getAnswer())) {
            //c2.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < qlist.size()) {
                correct(cc2);
            } else {
                gamefinish();
            }
        } else {
            wrong(cc2);
        }
    }

    public void coption3click(View view) {
        cc1.setClickable(false);
        cc2.setClickable(false);
        cc4.setClickable(false);
        cnext.setClickable(true);
        if (quizModel.getOptionC().equals(quizModel.getAnswer())) {
            //c3.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < qlist.size()) {
                correct(cc3);
            } else {
                gamefinish();
            }
        } else {
            wrong(cc3);
        }
    }

    public void coption4click(View view) {
        cc1.setClickable(false);
        cc3.setClickable(false);
        cc2.setClickable(false);
        cnext.setClickable(true);
        if (quizModel.getOptionD().equals(quizModel.getAnswer())) {
            //c4.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < qlist.size()) {
                correct(cc4);
            } else {
                gamefinish();
            }
        } else {
            wrong(cc4);
        }
    }
}