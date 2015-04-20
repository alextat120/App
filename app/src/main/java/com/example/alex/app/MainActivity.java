package com.example.alex.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.location.GpsStatus;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.Random;

import static android.media.MediaPlayer.*;

public class MainActivity extends ActionBarActivity {
    Random random = new Random();
       ProgressBar bar;
    //EditText edit;
    TextView t;
    Button big;
    Button small;
    TextView percent;
    int percentNum;
    static int green=Color.parseColor("#209903");
    static int red = Color.parseColor("#ff99242b");
    public final static String TIME = "com.example.alex.app";
   Chronometer chronometer;
    boolean started;

    View.OnClickListener greenListener;
    View.OnClickListener redListener;


    CharSequence redText;
    CharSequence greenText;

    MediaPlayer goodSound;
    MediaPlayer badSound;
    MediaPlayer start;
    ViewGroup layout;
    String s;
    int difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout=(ViewGroup)findViewById(R.id.mainLayout);

        Intent intent=getIntent();

        s=intent.getStringExtra(SettingsActivity.DIFFICULTY_INTENT);
        if(s.equals("Easy")){
            this.difficulty= 20;
        }
        else if(s.equals("Normal")){
            this.difficulty=25;
        }
        else if(s.equals("Hard")){
            this.difficulty=30;
        }
        else if(s.equals("Extreme")){
            this.difficulty=35;
        }
        //goodSound= create(this, R.raw.beep9);

                percent=(TextView)findViewById(R.id.percentNum);

       big  = (Button)findViewById(R.id.button);
        small = (Button)findViewById(R.id.smallButton);
        redText  = small.getText();
        greenText=big.getText();

        greenListener=new MyBigListener();
        redListener=new MySmallListener();
        chronometer=(Chronometer)findViewById(R.id.chronometer);
        chronometer.start();
        goodSound= create(MainActivity.this, R.raw.beep9);


        t = (TextView)findViewById(R.id.textView);
        small.setOnClickListener(redListener);
        big.setOnClickListener(greenListener);
         bar= (ProgressBar)findViewById(R.id.progressBar);
        bar.setProgress(0);

    }
    private class MyBigListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            if(goodSound.isPlaying()){
                goodSound.stop();
            }

                goodSound.start();

            if(started==false){
                started=true;
            }
          checkWon();
          /*  if(bar.getProgress()==50 ){
                reverse();
            }
            if(bar.getProgress()==75){
                reverse();
            }*/

            if(random.nextInt(100)<difficulty){
                animate();

                reverse();
            }
           // t.setTextSize(t.getTextSize()+10);

          // t.setText(edit.getText());
            bar.incrementProgressBy(5);
            percentNum=bar.getProgress();
            percent.setText(""+percentNum);
            System.out.println("Clicked big");

        }
    }
    public void checkWon(){

        if(bar.getProgress()==100)
        {
            chronometer.stop();
            t.setText("COMPLETE");
            Intent intent=new Intent(this,Complete.class);
            long timeElapsed = SystemClock.elapsedRealtime() - chronometer.getBase();
            intent.putExtra(TIME,""+(timeElapsed*.001));
            finish();
            startActivity(intent);

        }


    }
    private class MySmallListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

           // t.setTextSize(t.getTextSize()-30);
           // t.setText(edit.getText());
            //System.out.println("Text Size is: " + t.getTextSize());
            bar.incrementProgressBy(-10);
            percentNum=bar.getProgress();
            percent.setText(""+percentNum);
            System.out.println("Clicked small");
        }
    }
    public void animate(){
       /* View redButton = findViewById(R.id.smallButton);
        View greenButton = findViewById(R.id.button);
        RelativeLayout.LayoutParams positionRules = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        */
        Animation animation = new AlphaAnimation(0,100);
        Animation animation2 = new AlphaAnimation(0,100);

        animation.setDuration(1000);
        animation2.setDuration(1000);
        big.startAnimation(animation);
        small.startAnimation(animation2);

    }
    public void reverse(){
        big.setText(redText);
        small.setText(greenText);
        big.setBackgroundColor(red);
        small.setBackgroundColor(green);
        big.setOnClickListener(redListener);
        small.setOnClickListener(greenListener);

        CharSequence temp = redText;
        redText=greenText;
        greenText=temp;
        int tempInt = red;
        red=green;
        green=tempInt;
        View.OnClickListener tempListener = redListener;
        redListener=greenListener;
        greenListener=tempListener;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add("Restart");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(item.getTitle().equals("Restart")){
            restart();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void restart(){
        //Intent i = new Intent(this,MainActivity.class);
        Intent i = new Intent(this,SettingsActivity.class);
        finish();
        startActivity(i);
    }
}
