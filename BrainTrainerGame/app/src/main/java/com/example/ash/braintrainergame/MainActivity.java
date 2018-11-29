package com.example.ash.braintrainergame;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private TextView sumTextView;
    private ArrayList<Integer> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    int unicodeHappy = 0x1F603;
    int unicodeSad = 0x1F614;
    private TextView resultTextView;
    private int score = 0;
    private int numberOfQuestion = 0;
    private TextView scoreTextView;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private TextView timerTextView;
    private Button restartTheGameButton;
    private ConstraintLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        sumTextView = findViewById(R.id.sumTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextVIew);
        timerTextView = findViewById(R.id.timerTextView);
        restartTheGameButton = findViewById(R.id.restartTheGameButton);
        gameLayout = findViewById(R.id.gameLayout);

        startButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);



    }

    public void startTheGame(View view) {

        startButton.setVisibility(View.INVISIBLE);
        restart(findViewById(R.id.restartTheGameButton));
        gameLayout.setVisibility(View.VISIBLE);
    }


    public void chooseAnswer(View view) {

       if(Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){

           resultTextView.setText("Correct! " + getEmojiHappyByUnicode(unicodeHappy));
           score++;
       }else{

           resultTextView.setText("Wrong! " + getEmojiSadByUnicode(unicodeSad));
       }

       numberOfQuestion++;
       scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestion));
       newQuestion();
    }

    public String getEmojiHappyByUnicode(int unicode){
        return new String(Character.toChars(unicodeHappy));
    }

    public String getEmojiSadByUnicode(int unicode){
        return new String(Character.toChars(unicodeSad));
    }

    public void newQuestion(){

        SecureRandom random = new SecureRandom();
        int firstNumber = random.nextInt(21);
        int secondNumber = random.nextInt(21);
        int result = firstNumber + secondNumber;
        sumTextView.setText(Integer.toString(firstNumber) + " + " + Integer.toString(secondNumber));

        locationOfCorrectAnswer = random.nextInt(4);

        answers.clear();

        for(int i = 0; i < 4; i++){

            String oldWrongAnswer = "";

            if(i == locationOfCorrectAnswer) {

                answers.add(result);

            } else{

                int wrongAnswer = random.nextInt(42);

                oldWrongAnswer += Integer.toString(wrongAnswer);


                if(wrongAnswer == result || oldWrongAnswer.contains(Integer.toString(wrongAnswer))){

                    wrongAnswer = random.nextInt(42);
                }

                answers.add(wrongAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    public void restart(View view){

        score = 0;
        numberOfQuestion = 0;
        timerTextView.setText("30s");
        resultTextView.setText("");
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestion));
        restartTheGameButton.setVisibility(View.INVISIBLE);
        newQuestion();

        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");

            }

            @Override
            public void onFinish() {

                resultTextView.setText("Done! Score is " + Integer.toString(score));
                restartTheGameButton.setVisibility(View.VISIBLE);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                MediaPlayer gameOverSound = MediaPlayer.create(getApplicationContext(),R.raw.gameover);
                gameOverSound.start();


            }
        }.start();


    }
}
