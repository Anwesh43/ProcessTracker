package com.anwesome.ui.processtrackerdemo;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anwesome.camera.processtracker.ProcessTracker;
import com.anwesome.camera.processtracker.TrackProcess;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final ProcessTracker processTracker = new ProcessTracker(this);
        TrackProcess trackProcess = new TrackProcess(BitmapFactory.decodeResource(getResources(),R.drawable.order),"Order Confirmed");
        showToastOnProcessComplete(trackProcess,"Order Confirmed");
        processTracker.addProcess(trackProcess);
        TrackProcess trackProcess1 = new TrackProcess(BitmapFactory.decodeResource(getResources(),R.drawable.onway),"On The Way");
        showToastOnProcessComplete(trackProcess1,"On the way");
        processTracker.addProcess(trackProcess1);
        TrackProcess trackProcess2 = new TrackProcess(BitmapFactory.decodeResource(getResources(),R.drawable.delivered),"Delivered");
        showToastOnProcessComplete(trackProcess2,"Delivered");
        processTracker.addProcess(trackProcess2);
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
    private void showToastOnProcessComplete(TrackProcess trackProcess,final String text) {
        trackProcess.setTrackProcessCompletionListener(new TrackProcess.TrackProcessCompletionListener() {
            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
