package thegenuinegourav.timer;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private CounterClass timer;  //Inner User-defined Class
    long remainMilli = 0;
    boolean isRunning=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView)findViewById(R.id.timerTextView);
        timer = new CounterClass(30000,1000);

    }

    //When start button clicks
    public void Start(View view)
    {
        timer.cancel();  //First of all, cancel the running timer
        timer = new CounterClass(30000, 1000);  //Create a new timer
        timer.start();   //Start the timer
        isRunning = true;
    }

    //When Stop button clicks
    public void Stop(View view)
    {
        timer.cancel();  //Cancel the running timer
        timerTextView.setText("Timer");
        isRunning=false;
    }

    //When Resume button clicks
    public void Resume(View view) {
        if (!isRunning) {  //This method will execute only when timer is not running
            timer = new CounterClass(remainMilli, 1000); //resume timer from where it is paused
            timer.start();  //Start the timer
            isRunning = true;
        }
    }

    //When Pause button clicks
    public void Pause(View view) {
        if(isRunning){  //This method will execute only when timer is running
            timer.cancel();  //cancel (pause) timer when it is running
            timer=null;
            isRunning=false;
        }
    }

    //Inner class which extends from CountDownTimer
    public class CounterClass extends CountDownTimer {

        //All three methods (constructor) need to be overridden to use this class

        //Default Constructor
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        //When timer is ticking, what should happen at that duration; will go in this method
        @Override
        public void onTick(long millisUntilFinished) {
            remainMilli = millisUntilFinished;

            //Format to display the timer
            String hms = String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(remainMilli)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(remainMilli)),
                    TimeUnit.MILLISECONDS.toSeconds(remainMilli)- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainMilli)));

            timerTextView.setText(hms);

        }

        //When time is finished, what should happen: will go in this method
        @Override
        public void onFinish() {
            // reset all variables
            timerTextView.setText("Time Up!");
            isRunning=false;
            remainMilli=0;
        }
    }
}
