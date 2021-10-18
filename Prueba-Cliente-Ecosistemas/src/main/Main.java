package main;

import java.io.*;
import java.net.Socket;

import com.google.gson.Gson;

import processing.core.PApplet;

public class Main extends PApplet {

	private Gson gson;
	private BufferedWriter bwriter;

	public static void main(String[] args) {
		PApplet.main(Main.class.getName());
	}

	@Override
	public void settings() {
		size(700, 700);
		gson = new Gson();
		new Thread(

				() -> {

					try {
						Socket socket = new Socket("127.0.0.1", 5000);

						OutputStream os = socket.getOutputStream();
						OutputStreamWriter osw = new OutputStreamWriter(os);
						bwriter = new BufferedWriter(osw);

					} catch (IOException e) {
						e.printStackTrace();
					}

				}

		).start();

	}

	public void send(String msg) {
		new Thread(() -> {
			try {
				bwriter.write(msg + "\n");
				bwriter.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}

	@Override
	public void keyPressed() {
		if (key == 'a') {
			Motion leftstart = new Motion("LEFTSTART");
			String jsonleftstart = gson.toJson(leftstart);
            send(jsonleftstart);
		}
		if (key == 'd') {
			Motion rightstart = new Motion("RIGHTSTART");
			String jsonrightstart = gson.toJson(rightstart);
            send(jsonrightstart);
		}
		if (key == 's') {
			Motion downstart = new Motion("DOWNSTART");
			String jsondownstart = gson.toJson(downstart);
            send(jsondownstart);
		}
		if (key == 'w') {
			Motion upstart = new Motion("UPSTART");
			String jsonupstart = gson.toJson(upstart);
            send(jsonupstart);
		}
	}
	
	@Override
	public void keyReleased() {
		if (key == 'a') {
			Motion leftstop = new Motion("LEFTSTOP");
			String jsonleftstop = gson.toJson(leftstop);
            send(jsonleftstop);
		}
		if (key == 'd') {
			Motion rightstop = new Motion("RIGHTSTOP");
			String jsonrightstop = gson.toJson(rightstop);
            send(jsonrightstop);
		}
	}
	
   

	@Override
	public void draw() {
		background(0);
		fill(255);
		text("Presiona A o D para moverse y presione w para quitar la fresa a su oponente, use s para dejar la fresa en la canasta",
				0, 250);
	}

}
