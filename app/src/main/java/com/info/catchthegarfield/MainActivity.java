package com.info.catchthegarfield;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timeText, scoreText;
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8,
            imageView9, imageView10, imageView11, imageView12, imageView13, imageView14, imageView15;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText = findViewById(R.id.timeText);
        scoreText = findViewById(R.id.scoreText);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView5);
        imageView5 = findViewById(R.id.imageView6);
        imageView6 = findViewById(R.id.imageView7);
        imageView7 = findViewById(R.id.imageView9);
        imageView8 = findViewById(R.id.imageView10);
        imageView9 = findViewById(R.id.imageView11);
        imageView10 = findViewById(R.id.imageView13);
        imageView11 = findViewById(R.id.imageView14);
        imageView12 = findViewById(R.id.imageView15);
        imageView13 = findViewById(R.id.imageView17);
        imageView14 = findViewById(R.id.imageView18);
        imageView15 = findViewById(R.id.imageView19);

        imageArray = new ImageView[]{imageView1, imageView2, imageView3, imageView4, imageView5, imageView6,
                imageView7, imageView8, imageView9, imageView10, imageView11, imageView12, imageView13, imageView14, imageView15};

        hideImages();
        score = 0;
        firstGame();

    }

    public void setImagesInvisible() {
        for (ImageView imageView : imageArray) {
            imageView.setVisibility(View.INVISIBLE);
        }
    }
    public void firstGame() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(R.string.ready)
        .setIcon(R.drawable.start_garfield)
        .setPositiveButton(R.string.yes, (dialog, which) -> startGame())
        .show();
        setImagesInvisible();
    }

    public void startGame() {
        handler.post(runnable);

        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {

                timeText.setText(R.string.final_timer_text);
                handler.removeCallbacks(runnable);

                for (ImageView imageView : imageArray) {
                    imageView.setVisibility(View.INVISIBLE);
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                if (score < 5) {
                    alert.setTitle("Score: " + score + " Try Better !")
                            .setIcon(R.drawable.sad_garfield);
                } else {
                    alert.setIcon(R.drawable.happy_garfield)
                            .setTitle("Score: " + score + " Good Job !");
                }
                alert.setMessage(R.string.restart)
                        .setCancelable(false);
                alert.setPositiveButton(R.string.yes, (dialog, which) -> {
                    //restart
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                });
                alert.setNegativeButton(R.string.no, (dialog, which) -> timeText.setText(R.string.game_over));
                alert.show();
            }
        };
        countDownTimer.start();
    }

    public void increasScore(View view) {

        score++;
        scoreText.setText("Score: " + score);
        view.setVisibility(View.INVISIBLE);

    }

    public void hideImages() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView imageView : imageArray) {
                    imageView.setVisibility(View.INVISIBLE);
                }
                Random random = new Random();
                int i = random.nextInt(15);
                imageArray[i].setVisibility(View.VISIBLE);

                if (score >= 0 && score <= 3) {
                    handler.postDelayed(this, 500);
                } else if (score >= 4 && score <= 6) {
                    handler.postDelayed(this, 400);
                } else {
                    handler.postDelayed(this, 350);
                }
            }
        };
    }
}