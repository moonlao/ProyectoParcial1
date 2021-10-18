package co.eco.juego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Control extends AppCompatActivity implements View.OnTouchListener{

    private Button upBtn, downBtn, rightBtn, leftBtn;

    private BufferedWriter bw;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);


        upBtn = findViewById(R.id.upBtn);
        downBtn = findViewById(R.id.downBtn);
        rightBtn = findViewById(R.id.rightBtn);
        leftBtn = findViewById(R.id.leftBtn);




        upBtn.setOnTouchListener(this);
        downBtn.setOnTouchListener(this);
        rightBtn.setOnTouchListener(this);
        leftBtn.setOnTouchListener(this);
        i= getIntent();



        new Thread(

                () -> {

                    try {
                        String msg = i.getStringExtra("KEY");
                        String[] a = msg.split(",");
                        Socket socket = new Socket(a[0], Integer.parseInt(a[1]));

                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        bw = new BufferedWriter(osw);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

        ).start();
    }



    public void send(String msg) {
        new Thread(() -> {
            try {
                bw.write(msg + "\n");
                bw.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Gson gson = new Gson();

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:

                switch (view.getId()){
                    case R.id.upBtn:
                        Motion upstart = new Motion("UPSTART");
                        String jsonupstart = gson.toJson(upstart);
                        send(jsonupstart);
                        break;

                    case R.id.downBtn:
                        Motion downstart = new Motion("DOWNSTART");
                        String jsondownstart = gson.toJson(downstart);
                        send(jsondownstart);
                        break;

                    case R.id.leftBtn:
                        Motion leftstart = new Motion("LEFTSTART");
                        String jsonleftstart = gson.toJson(leftstart);
                        send(jsonleftstart);
                        break;
                    case R.id.rightBtn:
                        Motion rightstart = new Motion("RIGHTSTART");
                        String jsonrightstart = gson.toJson(rightstart);
                        send(jsonrightstart);
                        break;

                }
                break;


            case MotionEvent.ACTION_UP:

                switch (view.getId()){
                    case R.id.upBtn:

                        break;

                    case R.id.downBtn:

                        break;

                    case R.id.leftBtn:
                        Motion leftstop = new Motion("LEFTSTOP");
                        String jsonleftstop = gson.toJson(leftstop);
                        send(jsonleftstop);
                        break;
                    case R.id.rightBtn:
                        Motion rightstop = new Motion("RIGHTSTOP");
                        String jsonrightstop = gson.toJson(rightstop);
                        send(jsonrightstop);
                        break;

                }
                break;
        }
        return false;
    }
}