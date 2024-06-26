package com.example.examapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    int timervalue = 20;
    ProgressBar progressBar;
    ArrayList<QuizModel> qlist;
    QuizModel quizModel;
    int index = 0;
    TextView option1, option2, option3, option4, question,javatitle;
    CardView c1, c2, c3, c4;
    int correctcount = 0, wrongcount = 0;
    LinearLayout next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        progressBar = findViewById(R.id.quiz_timer);
        question = findViewById(R.id.txtquestion);
        option1 = findViewById(R.id.txtopt1);
        option2 = findViewById(R.id.txtopt2);
        option3 = findViewById(R.id.txtopt3);
        option4 = findViewById(R.id.txtopt4);
        c1 = findViewById(R.id.cvopt1);
        c2 = findViewById(R.id.cvopt2);
        c3 = findViewById(R.id.cvopt3);
        c4 = findViewById(R.id.cvopt4);
        javatitle = findViewById(R.id.txtjavatitle);

        next = findViewById(R.id.nextbtn);

        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                timervalue = timervalue - 1;
                progressBar.setProgress(timervalue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(JavaActivity.this);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.show();
                dialog.findViewById(R.id.btntimeout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(JavaActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }.start();


        qlist = new ArrayList<>();

        qlist.add(new QuizModel("Who invented Java Programming?", "Guido van Rossum", "James Gosling", "Dennis Ritchie", "Bjarne Stroustrup", "James Gosling"));
        qlist.add(new QuizModel("Which component is used to compile, debug and execute the java programs?", "JRE", "JIT", "JDK", "JVM", "JDK"));
        qlist.add(new QuizModel("Which of the following is not a Java features?","Dynamic","Architecture Neutral","Use of pointers","Object-oriented","Use of pointers"));
        qlist.add(new QuizModel("Which package contains the Random class?","java.util package","java.lang package","java.awt package","java.io package","java.util package"));
        qlist.add(new QuizModel("Which keyword is used for accessing the features of a package?","package","import","extends","export","import"));

        Collections.shuffle(qlist);
        quizModel = qlist.get(index);

        next.setClickable(false);
        setAllData();
    }


    private void setAllData() {
        question.setText(quizModel.getQuestion());
        option1.setText(quizModel.getOptionA());
        option2.setText(quizModel.getOptionB());
        option3.setText(quizModel.getOptionC());
        option4.setText(quizModel.getOptionD());
        next.setClickable(false);
    }


    public void correct(CardView cardView) {

        cardView.setCardBackgroundColor(getResources().getColor(R.color.green));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctcount++;
                if (index < qlist.size() - 1) {
                    index++;
                    quizModel = qlist.get(index);
                    setAllData();
                    resetColor();

//                    disablebtn();
                    enablebtn();
                } else {
                    gamefinish();
                }
            }
        });
    }

    public void wrong(CardView cardView) {

        cardView.setCardBackgroundColor(getResources().getColor(R.color.red));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongcount++;
                if (index < qlist.size() - 1) {
                    index++;
                    quizModel = qlist.get(index);
                    setAllData();
                    resetColor();
                    enablebtn();
//                    disablebtn();
                } else {
                    gamefinish();
                }
            }
        });
    }

    private void gamefinish() {
        String javastr = javatitle.getText().toString();
        Intent intent = new Intent(JavaActivity.this, ResultActivity.class);
        intent.putExtra("correct", correctcount);
        intent.putExtra("wrong", wrongcount);
        intent.putExtra("title",javastr);
        startActivity(intent);
    }

    public void enablebtn() {
        c1.setClickable(true);
        c2.setClickable(true);
        c3.setClickable(true);
        c4.setClickable(true);
    }

    public void disablebtn() {
        option1.setClickable(false);
        option2.setClickable(false);
        option3.setClickable(false);
        option4.setClickable(false);
    }

    public void resetColor() {
        c1.setCardBackgroundColor(getResources().getColor(R.color.white));
        c2.setCardBackgroundColor(getResources().getColor(R.color.white));
        c3.setCardBackgroundColor(getResources().getColor(R.color.white));
        c4.setCardBackgroundColor(getResources().getColor(R.color.white));
    }

    public void option1click(View view) {
        c2.setClickable(false);
        c3.setClickable(false);
        c4.setClickable(false);
        next.setClickable(false);
        if (quizModel.getOptionA().equals(quizModel.getAnswer())) {
            //c1.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < qlist.size()) {
                correct(c1);
            } else {
                gamefinish();
            }
        } else {
            wrong(c1);
        }
    }

    public void option2click(View view) {
        c1.setClickable(false);
        c3.setClickable(false);
        c4.setClickable(false);
        next.setClickable(false);
        if (quizModel.getOptionB().equals(quizModel.getAnswer())) {
            //c2.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < qlist.size()) {
                correct(c2);
            } else {
                gamefinish();
            }
        } else {
            wrong(c2);
        }
    }

    public void option3click(View view) {
        c1.setClickable(false);
        c2.setClickable(false);
        c4.setClickable(false);
        next.setClickable(false);
        if (quizModel.getOptionC().equals(quizModel.getAnswer())) {
            //c3.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < qlist.size()) {
                correct(c3);
            } else {
                gamefinish();
            }
        } else {
            wrong(c3);
        }
    }

    public void option4click(View view) {
        c1.setClickable(false);
        c3.setClickable(false);
        c2.setClickable(false);
        next.setClickable(false);
        if (quizModel.getOptionD().equals(quizModel.getAnswer())) {
            //c4.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < qlist.size()) {
                correct(c4);
            } else {
                gamefinish();
            }
        } else {
            wrong(c4);
        }
    }
}




