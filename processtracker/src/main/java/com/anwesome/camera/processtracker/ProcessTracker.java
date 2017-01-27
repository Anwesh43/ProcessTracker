package com.anwesome.camera.processtracker;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 26/01/17.
 */
public class ProcessTracker {
    private Activity activity;
    private List<TrackProcess> trackProcesses = new ArrayList<>();
    private int color = Color.parseColor("#FF6F00");
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ProcessTrackerView processTrackerView;
    public ProcessTracker(Activity activity) {
        this.activity = activity;
        processTrackerView = new ProcessTrackerView(activity.getApplicationContext());
    }
    public void addProcess(TrackProcess process) {
        trackProcesses.add(process);
    }
    public void setColor(int color) {
        this.color = color;
    }
    public void completeProcess(String phase) {
        for(TrackProcess trackProcess:trackProcesses) {
            if(trackProcess.matchPhase(phase)) {
                trackProcess.setComplete(true);
                processTrackerView.invalidate();
                break;
            }
        }
    }
    public void completeProcess(int index) {
        if(index<trackProcesses.size()) {
            trackProcesses.get(index).setComplete(true);
            TrackProcess.TrackProcessCompletionListener trackProcessCompletionListener = trackProcesses.get(index).getTrackProcessCompletionListener();
            if(trackProcessCompletionListener!=null) {
                trackProcesses.get(index).getTrackProcessCompletionListener().onComplete();
            }
            processTrackerView.invalidate();
        }

    }
    public void show() {
        int w = 100,h = 100;
        DisplayManager displayManager = (DisplayManager)activity.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        if(display!=null) {
            Point size = new Point();
            display.getRealSize(size);
            w = size.x;
            h = size.y/5;
        }
        activity.addContentView(processTrackerView,new ViewGroup.LayoutParams(w,h));
    }
    private class ProcessTrackerView extends View {
        public ProcessTrackerView(Context context) {
            super(context);
        }
        public void onDraw(Canvas canvas) {
            paint.setStrokeWidth(paint.getStrokeWidth()+5);
            canvas.drawColor(Color.parseColor("#BDBDBD"));
            int prev_x=canvas.getWidth()/20,x = canvas.getWidth()/20, y= canvas.getHeight()/2;
            int r = canvas.getHeight()/4,line_gap = 0;
            if(trackProcesses.size()>1) {
                line_gap = ((canvas.getWidth()*9)/10-(2*r*trackProcesses.size()))/(trackProcesses.size()-1);
            }
            int lineColor = Color.WHITE;

            for(TrackProcess trackProcess:trackProcesses) {
                paint.setColor(lineColor);
                canvas.drawLine(prev_x,y,x,y,paint);
                paint.setColor(color);
                trackProcess.draw(canvas,paint,x,y,r);
                prev_x = x+2*r;
                x += 2*r+line_gap;
                if(trackProcess.isComplete()) {
                    lineColor = color;
                }
                else {
                    lineColor = Color.WHITE;
                }
            }
        }
    }
}
