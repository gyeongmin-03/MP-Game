package com.example.mobilegame;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

public class MainActivity extends AppCompatActivity {

    Button btnDown, btnUp, btnLeft, btnRight, btnShoot;
    ImageView gun;
    ProgressBar proBar;
    FrameLayout fLayout;
    Handler handler;

    final int MIN_Y = -15, MAX_Y = 30, MIN_X = -30, MAX_X = 30;
    int gunX = 0, gunY = 0;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //가로모드 고정
        setContentView(R.layout.activity_main);


        btnDown = findViewById(R.id.btnDown);
        btnUp = findViewById(R.id.btnUp);
        btnRight = findViewById(R.id.btnRight);
        btnLeft = findViewById(R.id.btnLeft);
        btnShoot = findViewById(R.id.btnShoot);
        gun = findViewById(R.id.Gun);
        proBar = findViewById(R.id.progressBar);
        fLayout = findViewById(R.id.fLayout);


        handler = new Handler();



        btnRight.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == ACTION_DOWN){
                    handler.postDelayed(moveRight, 10);
                }
                else if(event.getAction() == ACTION_UP){
                    handler.removeCallbacks(moveRight);
                }
                return false;
            }

            Runnable moveRight = new Runnable() {
                @Override
                public void run() {
                    int currentX = (int) gun.getX();

                    if(gunX + 1 <= MAX_X){
                        gunX++;
                        gun.setX(currentX + 10);
                    }

                    handler.postDelayed(this, 10);
                }
            };
        });

        btnLeft.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == ACTION_DOWN){
                    handler.postDelayed(moveLeft, 10);
                }
                else if(event.getAction() == ACTION_UP){
                    handler.removeCallbacks(moveLeft);
                }
                return false;
            }

            Runnable moveLeft = new Runnable() {
                @Override
                public void run() {
                    int currentX = (int) gun.getX();

                    if(gunX - 1 >= MIN_X){
                        gunX--;
                        gun.setX(currentX - 10);
                    }


                    handler.postDelayed(this, 10);
                }
            };
        });

        btnUp.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == ACTION_DOWN){
                    handler.postDelayed(moveUp, 10);
                }
                else if(event.getAction() == ACTION_UP){
                    handler.removeCallbacks(moveUp);
                }
                return false;
            }

            Runnable moveUp = new Runnable() {
                @Override
                public void run() {
                    int currentY = (int) gun.getY();


                    if(gunY + 1 <= MAX_Y){
                        gunY++;
                        gun.setY(currentY - 10);
                    }

                    handler.postDelayed(this, 10);
                }
            };
        });

        btnDown.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == ACTION_DOWN){
                    handler.postDelayed(moveDown, 10);
                }
                else if(event.getAction() == ACTION_UP){
                    handler.removeCallbacks(moveDown);
                }
                return false;
            }

            Runnable moveDown = new Runnable() {
                @Override
                public void run() {
                    int currentY = (int) gun.getY();

                    if(gunY - 1 >= MIN_Y){
                        gunY--;
                        gun.setY(currentY + 10);
                    }

                    handler.postDelayed(this, 10);
                }
            };
        });

        btnShoot.setOnTouchListener(new View.OnTouchListener() {
            boolean isUp = true;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == ACTION_DOWN){
                    proBar.setVisibility(View.VISIBLE);
                    proBar.setProgress(0);

                    handler.postDelayed(progressAct, 10);
                }
                else if(event.getAction() == ACTION_UP){
                    proBar.setVisibility(View.INVISIBLE);
                    
                    //TODO 총알발사
                    int x = (int) gun.getX() + gun.getWidth()/2;
                    Shoot(x, (int)gun.getY(), proBar.getProgress());

                    handler.removeCallbacks(progressAct);
                    //TODO return된 x,y값이 표적arr내부에 포함이 된다면 이벤트 발생
                }

                return false;
            }


            Runnable progressAct = new Runnable() {
                @Override
                public void run() {
                    int curProgress = proBar.getProgress();

                    if(curProgress == 100){
                        isUp = false;
                    }
                    else if(curProgress == 0){
                        isUp = true;
                    }

                    if(isUp){
                        proBar.incrementProgressBy(1);
                    }
                    else {
                        proBar.incrementProgressBy(-1);
                    }

                    handler.postDelayed(this, 10);
                }
            };

        });
    }

    //range는 0~100
    public void Shoot(int x, int y, int range){
        TextView t = new TextView(this); //총알
        t.setText("A");
        t.setTextSize(30);
        t.setX(x);
        t.setY(y);

        fLayout.addView(t);


        Runnable ani = new Runnable() {
            int count = range;
            @Override
            public void run() {
                if(count > 70) t.setY(t.getY()-15);
                else if(count > 55) t.setY(t.getY()-12);
                else if(count > 40) t.setY(t.getY()-9);
                else if(count > 25) t.setY(t.getY()-7);
                else if(count > 10) t.setY(t.getY()-5);
                else t.setY(t.getY()-3);

                count--;

                if(count <= 0) handler.removeCallbacks(this);
                else handler.postDelayed(this, 10);
            }
        };

        ani.run();
        //return t.getX(), t.getY()
    }
}