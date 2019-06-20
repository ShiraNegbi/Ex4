package com.example.rocknrollfairies.ex4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

public class Joystick extends AppCompatActivity implements JoystickView.JoystickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);
        JoystickView joystickView = new JoystickView(this);
        setContentView(joystickView);

        Intent intent = getIntent();
        //JoystickView joystickView = new JoystickView(getApplicationContext());
        //String msg = intent.getStringExtra("message");

        //TextView textView = (TextView) findViewById(R.id.textViewMsg);
        //textView.setText(msg);
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        Log.d("Main method", "X percent: " + xPercent + " Y percent: " + yPercent);
    }
}
