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
import android.widget.TextView;

public class Joystick extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        Intent intent = getIntent();
        //JoystickView joystickView = new JoystickView(getApplicationContext());
        //String msg = intent.getStringExtra("message");

        //TextView textView = (TextView) findViewById(R.id.textViewMsg);
        //textView.setText(msg);

    }

    public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

        float centerX;
        float centerY;
        float baseRadius;
        float hatRadius;

        public JoystickView(Context cont) {
            super(cont);
            getHolder().addCallback(this);
            setOnTouchListener(this);
        }

        public JoystickView(Context cont, AttributeSet atr, int style) {
            super(cont, atr, style);
            getHolder().addCallback(this);
            setOnTouchListener(this);
        }

        public JoystickView(Context cont, AttributeSet atr) {
            super(cont, atr);
            getHolder().addCallback(this);
            setOnTouchListener(this);
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
                    drawJoyStickView(e.getX(), e.getY());
                }
                else {
                    drawJoyStickView(centerX, centerY);
                }
            }
            return true;
        }
    }
}
