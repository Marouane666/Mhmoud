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

public class PHPActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    int timervalue = 20;
    ProgressBar phpprogressBar;
    ArrayList<QuizModel> qlist;
    QuizModel quizModel;
    int index = 0;
    TextView phpoption1, phpoption2, phpoption3, phpoption4, phpquestion, phptitle;
    CardView phpc1, phpc2, phpc3, phpc4;
    int correctcount = 0, wrongcount = 0;
    LinearLayout phpnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phpactivity);

        phpprogressBar = findViewById(R.id.phpquiz_timer);
        phpquestion = findViewById(R.id.txtphpquestion);
        phpoption1 = findViewById(R.id.txtphpopt1);
        phpoption2 = findViewById(R.id.txtphpopt2);
        phpoption2 = findViewById(R.id.txtphpopt2);
        phpoption3 = findViewById(R.id.txtphpopt3);
        phpoption4 = findViewById(R.id.txtphpopt4);
        phpc1 = findViewById(R.id.cvphpopt1);
        phpc2 = findViewById(R.id.cvphpopt2);
        phpc3 = findViewById(R.id.cvphpopt3);
        phpc4 = findViewById(R.id.cvphpopt4);
        phptitle = findViewById(R.id.txtphptitle);

        phpnext = findViewById(R.id.phpnextbtn);

        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                timervalue = timervalue - 1;
                phpprogressBar.setProgress(timervalue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(PHPActivity.this);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.show();
                dialog.findViewById(R.id.btntimeout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PHPActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }.start();

        qlist = new ArrayList<>();

        qlist.add(new QuizModel("PHP stands for -", "Hypertext Preprocessor", "A and C Both", "Personal Home Processor", "None of the above", "Hypertext Preprocessor"));
        qlist.add(new QuizModel("Who is known as the father of PHP?", "Drek Kolkevi", "List Barely", "Rasmus Lerdrof", "None of the above", "Rasmus Lerdrof"));
        qlist.add(new QuizModel("Variable name in PHP starts with -", "!", "$", "&", "#", "$"));
        qlist.add(new QuizModel("Which of the following is the default file extension of PHP?", ".php", ".hphp", ".xml", ".html", ".php"));

        Collections.shuffle(qlist);
        quizModel = qlist.get(index);

        phpnext.setClickable(false);
        phpsetAllData();
    }

    private void phpsetAllData() {
        phpquestion.setText(quizModel.getQuestion());
        phpoption1.setText(quizModel.getOptionA());
        phpoption2.setText(quizModel.getOptionB());
        phpoption3.setText(quizModel.getOptionC());
        phpoption4.setText(quizModel.getOptionD());
        phpnext.setClickable(false);
    }


    public void correct(CardView cardView) {

        cardView.setCardBackgroundColor(getResources().getColor(R.color.green));

        phpnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctcount++;
                if (index < qlist.size() - 1) {
                    index++;
                    quizModel = qlist.get(index);
                    phpsetAllData();
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

        phpnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongcount++;
                if (index < qlist.size() - 1) {
                    index++;
                    quizModel = qlist.get(index);
                    phpsetAllData();
                    resetColor();
                    enablebtn();
                } else {
                    gamefinish();
                }
            }
        });
    }

    private void gamefinish() {
        String phpstr = phptitle.getText().toString();
        Intent intent = new Intent(PHPActivity.this, ResultActivity.class);
        intent.putExtra("correct", correctcount);
        intent.putExtra("wrong", wrongcount);
        intent.putExtra("title",phpstr);
        startActivity(intent);
    }

    public void enablebtn() {
        phpc1.setClickable(true);
        phpc2.setClickable(true);
        phpc3.setClickable(true);
        phpc4.setClickable(true);
    }

    public void disablebtn() {
        phpoption1.setClickable(false);
        phpoption2.setClickable(false);
        phpoption3.setClickable(false);
        phpoption4.setClickable(false);
    }

    public void resetColor() {
        phpc1.setCardBackgroundColor(getResources().getColor(R.color.white));
        phpc2.setCardBackgroundColor(getResources().getColor(R.color.white));
        phpc3.setCardBackgroundColor(getResources().getColor(R.color.white));
        phpc4.setCardBackgroundColor(getResources().getColor(R.color.white));
    }

    public void phpoption1click(View view) {
        phpc2.setClickable(false);
        phpc3.setClickable(false);
        phpc4.setClickable(false);
        phpnext.setClickable(true);
        if (quizModel.getOptionA().equals(quizModel.getAnswer())) {
            if (index < qlist.size()) {
                correct( phpc1);
            } else {
                gamefinish();
            }
        } else {
            wrong( phpc1);
        }
    }

    public void phpoption2click(View view) {
        phpc1.setClickable(false);
        phpc3.setClickable(false);
        phpc4.setClickable(false);
        phpnext.setClickable(true);
        if (quizModel.getOptionB().equals(quizModel.getAnswer())) {
            if (index < qlist.size()) {
                correct( phpc2);
            } else {
                gamefinish();
            }
        } else {
            wrong( phpc2);
        }
    }

    public void phpoption3click(View view) {
        phpc1.setClickable(false);
        phpc2.setClickable(false);
        phpc4.setClickable(false);
        phpnext.setClickable(true);
        if (quizModel.getOptionC().equals(quizModel.getAnswer())) {
            if (index < qlist.size()) {
                correct(phpc3);
            } else {
                gamefinish();
            }
        } else {
            wrong(phpc3);
        }
    }

    public void phpoption4click(View view) {
        phpc1.setClickable(false);
        phpc3.setClickable(false);
        phpc2.setClickable(false);
        phpnext.setClickable(true);
        if (quizModel.getOptionD().equals(quizModel.getAnswer())) {
            if (index < qlist.size()) {
                correct(phpc4);
            } else {
                gamefinish();
            }
        } else {
            wrong(phpc4);
        }
    }

}