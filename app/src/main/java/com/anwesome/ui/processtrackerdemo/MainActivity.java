package com.anwesome.ui.processtrackerdemo;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.camera.processtracker.ProcessTracker;
import com.anwesome.camera.processtracker.TrackProcess;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final ProcessTracker processTracker = new ProcessTracker(this);
        processTracker.addProcess(new TrackProcess(BitmapFactory.decodeResource(getResources(),R.drawable.order),"Order Confirmed"));
        processTracker.addProcess(new TrackProcess(BitmapFactory.decodeResource(getResources(),R.drawable.onway),"On The Way"));
        processTracker.addProcess(new TrackProcess(BitmapFactory.decodeResource(getResources(),R.drawable.delivered),"Delivered"));
        processTracker.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(i<3) {
                    try {
                        Thread.sleep(5000);
                    }
                    catch (Exception ex) {

                    }
                    final int index = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            processTracker.completeProcess(index);
                        }
                    });
                    i++;
                }
            }
        }).start();
    }
}
