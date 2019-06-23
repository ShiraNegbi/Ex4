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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static com.example.rocknrollfairies.ex4.Client.*;

public class Joystick extends AppCompatActivity implements JoystickView.JoystickListener {
    String msgIp;
    String msgPort;
    float x;
    float y;
    float coordinate;
    String path;
    Thread messageThread;
    private final Lock _mutex = new ReentrantLock(true);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);
        JoystickView joystickView = new JoystickView(this);
        setContentView(joystickView);

        Intent intent = getIntent();
        //JoystickView joystickView = new JoystickView(getApplicationContext());
        msgIp = intent.getStringExtra("messageIp");
        msgPort = intent.getStringExtra("messagePort");
        ConnectTask connect = new ConnectTask();
        Thread connectThread = new Thread(connect);
        connectThread.start();
        SendMessage send = new SendMessage();
        messageThread = new Thread(send);
        //connect.run();
//        TextView textViewIp = (TextView) findViewById(R.id.textIp);
//        TextView textViewPort = (TextView) findViewById(R.id.textPort);
//        String msgServer = "";
//        try {
//            Log.d("Client", "Trying to connect...");
//            //Client client = Client.instance();
//            Log.d("Client", "ip = " + msgIp + " port = " + Integer.parseInt(msgPort));
//            Client.instance().connect(msgIp, Integer.parseInt(msgPort));
//            //msgServer = client.sendMessage("aileron", 0.8);
//            Log.d("Client", Client.instance().sendMessage("aileron", 0.8));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        TextView textViewServer = (TextView) findViewById(R.id.serverMessage);
        //textViewServer.setText(msgServer);
//        textViewIp.setText(msgIp);
//        textViewPort.setText(msgPort);
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        //Log.d("Main method", "X percent: " + xPercent + " Y percent: " + yPercent);
        coordinate = xPercent;
        path = "aileron";
        SendMessage send = new SendMessage();
        //_mutex.lock();
        messageThread = new Thread(send);
        messageThread.start();
        //_mutex.unlock();
//        try {
//            Client.instance().sendMessage(path, coordinate);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        coordinate = yPercent;
        path = "elevator";
        //_mutex.lock();
        messageThread = new Thread(send);
        messageThread.start();
        //_mutex.unlock();
//        SendMessage send = new SendMessage();
//        Thread thread = new Thread(send);
//        thread.start();
//        try {
//            Client.instance().sendMessage(path, coordinate);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private class ConnectTask implements Runnable {
        public void run() {
//            try {
//                val sAddress :InetAddress = Inet4Address.getByName(ip)
//                socket = Socket(sAddress, Integer.parseInt(port))
//                mBufferOut = PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream())), true)
//                mBufferIn = BufferedReader(InputStreamReader(socket.getInputStream()))
//            } catch (e : Exception) {
//                Log.e("Exception", e.message)
//            }
            try {
                Log.d("Client", "Trying to connect...");
                //Client client = Client.instance();
                Log.d("Client", "ip = " + msgIp + " port = " + Integer.parseInt(msgPort));
                Client.instance().connect(msgIp, Integer.parseInt(msgPort));
                //msgServer = client.sendMessage("aileron", 0.8);
                //Log.d("Client", Client.instance().sendMessage("aileron", 0.8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class SendMessage implements Runnable {
        public void run() {
            try {
                Client.instance().sendMessage(path, coordinate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class Disconnect implements Runnable {
        public void run() {
            try {
                Client.instance().disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Thread thread = new Thread(new Disconnect());
        thread.start();
    }
}
