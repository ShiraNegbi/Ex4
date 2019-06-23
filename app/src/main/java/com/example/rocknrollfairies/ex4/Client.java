//package com.example.rocknrollfairies.ex4;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.InputStreamReader;
//import java.net.Socket;
//import java.util.HashMap;
//
//class Client
//{
//
//    private Socket clientSocket;
//    private static Client singleton;
//    private HashMap<String, String> variablesMap;
//
//    private Client()
//    {
//        variablesMap = new HashMap<>();
//        variablesMap.put("aileron", "/controls/flight/aileron");
//        variablesMap.put("elevator", "/controls/flight/elevator");
//
//    }
//
//    public static Client instance()
//    {
//        if (singleton == null)
//        {
//            singleton = new Client();
//        }
//
//        return singleton;
//    }
//
//    public void connect(String ip, int port) throws Exception
//    {
//        this.clientSocket = new Socket(ip, port);
//    }
//
//    public String sendMessage(String variable, double value) throws Exception
//    {
//        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        String sentence;
//        String modifiedSentence;
//        String mappedVariable = variablesMap.get(variable);
//        if (mappedVariable != null)
//        {
//            variable = mappedVariable;
//        }
//        sentence = "set" + " " + variable + " " + value + "\r\n";
//        outToServer.writeBytes(sentence);
//
//        // Get server's response
//        modifiedSentence = inFromServer.readLine();
//        //System.out.println("FROM SERVER: " + modifiedSentence);
//        //Log.d("Client", "FROM SERVER: " + modifiedSentence);
//        return "FROM SERVER: " + modifiedSentence;
//    }
//
//    public void disconnect() throws Exception
//    {
//        this.clientSocket.close();
//    }
//}

package com.example.rocknrollfairies.ex4;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

class Client
{

    private Socket clientSocket;
    private static Client singleton;
    private HashMap<String, String> variablesMap;

    private Client()
    {
        variablesMap = new HashMap<>();
        variablesMap.put("aileron", "/controls/flight/aileron");
        variablesMap.put("elevator", "/controls/flight/elevator");

    }

    public static Client instance()
    {
        if (singleton == null)
        {
            singleton = new Client();
        }

        return singleton;
    }

    public void connect(String ip, int port) throws Exception
    {
        this.clientSocket = new Socket(ip, port);
    }

    public void sendMessage(String variable, double value) throws Exception
    {
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String sentence;
        String modifiedSentence;
        String mappedVariable = variablesMap.get(variable);
        if (mappedVariable != null)
        {
            variable = mappedVariable;
        }
        sentence = "set" + " " + variable + " " + value + "\r\n";
        synchronized (this) {
            outToServer.writeBytes(sentence);
        }
        // Get server's response
        modifiedSentence = inFromServer.readLine();
        //System.out.println("FROM SERVER: " + modifiedSentence);
        Log.d("Client", "FROM SERVER: " + modifiedSentence);
        //return "FROM SERVER: " + modifiedSentence;
    }

    public void disconnect() throws Exception
    {
        this.clientSocket.close();
    }
}

