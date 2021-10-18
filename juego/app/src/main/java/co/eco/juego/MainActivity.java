package co.eco.juego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText inputIP;
    private Button p1Btn, p2Btn;

    private String IP;
    private Boolean chosenP;
    private int socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputIP = findViewById(R.id.inputIP);
        p1Btn = findViewById(R.id.p1Btn);
        p2Btn = findViewById(R.id.p2Btn);


        chosenP = false;

        p1Sel();
        p2Sel();
    }


    private void p1Sel() {
        p1Btn.setOnClickListener(
                v -> {

                    chosenP = true;
                    socket = 5000;
                    Toast.makeText(this, "Eres el Jugador 1", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Control.class);
                    String e = IP+","+socket;
                    intent.putExtra("KEY", e);
                    startActivity(intent);
                }
        );
    }


    private void p2Sel() {
        p2Btn.setOnClickListener(
                v -> {

                    chosenP = true;
                    socket = 6000;
                    Toast.makeText(this, "Eres el Jugador 2", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Control.class);
                    String e = IP+","+socket;
                    intent.putExtra("KEY", e);
                    startActivity(intent);
                }
        );
    }



}