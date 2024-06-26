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

import java.util.ArrayList;
import java.util.Collections;

public class CppActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    int timervalue = 20;
    ProgressBar cppprogressBar;
    ArrayList<QuizModel> qlist;
    QuizModel quizModel;
    int index = 0;
    TextView cppoption1, cppoption2, cppoption3, cppoption4, cppquestion, cpptitle;
    CardView cppc1, cppc2, cppc3, cppc4;
    int correctcount = 0, wrongcount = 0;
    LinearLayout cppnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpp);

        cppprogressBar = findViewById(R.id.cppquiz_timer);
        cppquestion = findViewById(R.id.cpptxtquestion);
        cppoption1 = findViewById(R.id.txtcppopt1);
        cppoption2 = findViewById(R.id.txtcppopt2);
        cppoption3 = findViewById(R.id.txtcppopt3);
        cppoption4 = findViewById(R.id.txtcppopt4);
        cppc1 = findViewById(R.id.cvcppopt1);
        cppc2 = findViewById(R.id.cvcppopt2);
        cppc3 = findViewById(R.id.cvcppopt3);
        cppc4 = findViewById(R.id.cvcppopt4);
        cpptitle = findViewById(R.id.txtcpptitle);

        cppnext = findViewById(R.id.cppnextbtn);

        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                timervalue = timervalue - 1;
                cppprogressBar.setProgress(timervalue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(CppActivity.this);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.show();
                dialog.findViewById(R.id.btntimeout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CppActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }.start();


        qlist = new ArrayList<>();

        qlist.add(new QuizModel("Who invented C++?", "Guido van Rossum", "James Gosling", "Dennis Ritchie", "Bjarne Stroustrup", "Bjarne Stroustrup"));
        qlist.add(new QuizModel("What is C++?", "object oriented programming language", "procedural programming language", "Both", "None", "Both"));
        qlist.add(new QuizModel("Which of the following is not a type of Constructor in C++?", "Default Constructor", "Parameterized constructor", "Copy constructor", "Friend constructor", "Friend constructor"));
        qlist.add(new QuizModel("Which of the following approach is used by C++?", "Left-right", "Right-left", "Bottom-up", "Top-down", "Bottom-up"));
//        qlist.add(new QuizModel("Functions in C Language are always _________", "Internal", "External", "A and B both", "None of the above", "External"));

        Collections.shuffle(qlist);
        quizModel = qlist.get(index);

        cppnext.setClickable(false);
        cppsetAllData();
    }


    private void cppsetAllData() {
        cppquestion.setText(quizModel.getQuestion());
        cppoption1.setText(quizModel.getOptionA());
        cppoption2.setText(quizModel.getOptionB());
        cppoption3.setText(quizModel.getOptionC());
        cppoption4.setText(quizModel.getOptionD());
        cppnext.setClickable(false);
    }


    public void correct(CardView cardView) {

        cardView.setCardBackgroundColor(getResources().getColor(R.color.green));

        cppnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctcount++;
                if (index < qlist.size() - 1) {
                    index++;
                    quizModel = qlist.get(index);
                    cppsetAllData();
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

        cppnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongcount++;
                if (index < qlist.size() - 1) {
                    index++;
                    quizModel = qlist.get(index);
                    cppsetAllData();
                    resetColor();
                    enablebtn();
                } else {
                    gamefinish();
                }
            }
        });
    }

    private void gamefinish() {
        String cppstr = cpptitle.getText().toString();
        Intent intent = new Intent(CppActivity.this, ResultActivity.class);
        intent.putExtra("correct", correctcount);
        intent.putExtra("wrong", wrongcount);
        intent.putExtra("title",cppstr);
        startActivity(intent);
    }

    public void enablebtn() {
        cppc1.setClickable(true);
        cppc2.setClickable(true);
        cppc3.setClickable(true);
        cppc4.setClickable(true);
    }

    public void disablebtn() {
        cppoption1.setClickable(false);
        cppoption2.setClickable(false);
        cppoption3.setClickable(false);
        cppoption4.setClickable(false);
    }

    public void resetColor() {
        cppc1.setCardBackgroundColor(getResources().getColor(R.color.white));
        cppc2.setCardBackgroundColor(getResources().getColor(R.color.white));
        cppc3.setCardBackgroundColor(getResources().getColor(R.color.white));
        cppc4.setCardBackgroundColor(getResources().getColor(R.color.white));
    }

    public void cppoption1click(View view) {
        cppc2.setClickable(false);
        cppc3.setClickable(false);
        cppc4.setClickable(false);
        cppnext.setClickable(true);
        if (quizModel.getOptionA().equals(quizModel.getAnswer())) {
            //c1.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < qlist.size()) {
                correct(cppc1);
            } else {
                gamefinish();
            }
        } else {
            wrong(cppc1);
        }
    }

    public void cppoption2click(View view) {
        cppc1.setClickable(false);
        cppc3.setClickable(false);
        cppc4.setClickable(false);
        cppnext.setClickable(true);
        if (quizModel.getOptionB().equals(quizModel.getAnswer())) {
            //c2.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < qlist.size()) {
                correct(cppc2);
            } else {
                gamefinish();
            }
        } else {
            wrong(cppc2);
        }
    }

    public void cppoption3click(View view) {
        cppc1.setClickable(false);
        cppc2.setClickable(false);
        cppc4.setClickable(false);
        cppnext.setClickable(true);
        if (quizModel.getOptionC().equals(quizModel.getAnswer())) {
            //c3.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < qlist.size()) {
                correct(cppc3);
            } else {
                gamefinish();
            }
        } else {
            wrong(cppc3);
        }
    }

    public void cppoption4click(View view) {
        cppc1.setClickable(false);
        cppc3.setClickable(false);
        cppc2.setClickable(false);
        cppnext.setClickable(true);
        if (quizModel.getOptionD().equals(quizModel.getAnswer())) {
            //c4.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < qlist.size()) {
                correct(cppc4);
            } else {
                gamefinish();
            }
        } else {
            wrong(cppc4);
        }
    }
}