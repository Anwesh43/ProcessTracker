package com.anwesome.camera.processtracker;

import android.graphics.Bitmap;
import android.graphics.*;

/**
 * Created by anweshmishra on 26/01/17.
 */
public class TrackProcess {
    private boolean complete = false;
    private Bitmap bitmap;
    private String phase;
    
    public TrackProcess(Bitmap bitmap,String phase) {
        this.bitmap = bitmap;
        this.phase = phase;
    }
    public void draw(Canvas canvas, Paint paint,int x,int y,int r) {
        if(complete) {
            paint.setStyle(Paint.Style.FILL);
        }
        else {
            paint.setStyle(Paint.Style.STROKE);
        }
        canvas.drawCircle(x+r,y,r,paint);
        if(bitmap!=null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, r, r, true);
            canvas.drawBitmap(bitmap, x + r / 2, y - r / 2, paint);
        }
        if(phase!=null) {
            paint.setColor(Color.WHITE);
            paint.setTextSize(canvas.getHeight()/12);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawText(phase,x+r-paint.measureText(phase)/2,y+r+r/3,paint);
        }
    }
    public void setComplete(boolean complete) {
        this.complete = complete;
    }
    public boolean matchPhase(String phase) {
        return phase.equals(this.phase);
    }
    public boolean isComplete() {
        return complete;
    }
    public int hashCode() {
        return bitmap.hashCode()+phase.hashCode()+(complete?1:0);
    }
}
