package com.example.rocknrollfairies.ex4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private JoystickListener joystickCallback;
    private final int ratio = 5;

    public JoystickView(Context cont) {
        super(cont);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(cont instanceof JoystickListener) {
            joystickCallback = (JoystickListener) cont;
        }
    }

    public JoystickView(Context cont, AttributeSet atr, int style) {
        super(cont, atr, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(cont instanceof JoystickListener) {
            joystickCallback = (JoystickListener) cont;
        }
    }

    public JoystickView(Context cont, AttributeSet atr) {
        super(cont, atr);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(cont instanceof JoystickListener) {
            joystickCallback = (JoystickListener) cont;
        }
    }

    private void setDimensions() {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        baseRadius = Math.min(getWidth(), getHeight()) / 3;
        hatRadius = Math.min(getWidth(), getHeight()) / 5;
    }

    private void drawJoyStickView(float x, float y) {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = this.getHolder().lockCanvas();
            Paint clr = new Paint();
            clr.setARGB(255, 20, 255, 50);
            canvas.drawCircle(centerX, centerY, baseRadius, clr);
            clr.setARGB(255, 255, 180, 255);
            canvas.drawCircle(x, y, hatRadius, clr);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setDimensions();
        drawJoyStickView(centerX, centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public boolean onTouch(View v, MotionEvent e) {
        if(v.equals(this)) {
            if(e.getAction() != e.ACTION_UP) {
                float displacement = (float) Math.sqrt(Math.pow(e.getX() - centerX, 2) + Math.pow(e.getY() - centerY, 2));
                if (displacement < baseRadius) {
                    drawJoyStickView(e.getX(), e.getY());
                    joystickCallback.onJoystickMoved((e.getX() - centerX) / baseRadius, (e.getY() - centerY) / baseRadius, getId());
                }
                else {
                    float ratio = baseRadius / displacement;
                    float constrainedX = centerX + (e.getX() - centerX) * ratio;
                    float constrainedY = centerY + (e.getY() - centerY) * ratio;
                    drawJoyStickView(constrainedX, constrainedY);
                    joystickCallback.onJoystickMoved((constrainedX - centerX) / baseRadius, (constrainedY - centerY) / baseRadius, getId());
                }
            }
            else {
                drawJoyStickView(centerX, centerY);
                joystickCallback.onJoystickMoved(0, 0, getId());
            }
        }
        return true;
    }

    public interface JoystickListener {
        void onJoystickMoved(float xPercent, float yPercent, int id);
    }
}
