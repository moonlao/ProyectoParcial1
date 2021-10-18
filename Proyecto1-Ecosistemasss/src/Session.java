import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

public class Session extends Thread {

	public final static String PLAYER_1 = "Player1";
	public final static String PLAYER_2 = "Player2";

	private Principal principal;
	private String player;
	private boolean estado;

	public Session(Principal principal, String player) {
		this.principal = principal;
		this.player = player;
		estado=false;
	}

	public void run() {
		try {
			ServerSocket server = null;
			if (player.contentEquals(PLAYER_1)) {
				server = new ServerSocket(5000);
				System.out.println("Esperando cliente en el 5000...");
			} else if (player.contentEquals(PLAYER_2)) {
				server = new ServerSocket(6000);
				System.out.println("Esperando cliente en el 6000...");
			}
			Socket socket = server.accept();

			if (player.contentEquals(PLAYER_1)) {
				System.out.println("Player 1 Conectado");
			} else if (player.contentEquals(PLAYER_2)) {
				System.out.println("Player 2 Conectado");
			}
            estado=true;
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			// Hacer que el objeto is tenga la capacidad de leer Strings completos
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader breader = new BufferedReader(isr);

			while (true) {
				// Esperando mensaje
				String mensajeRecibido = breader.readLine();
				Gson gson = new Gson();
				Motion m = gson.fromJson(mensajeRecibido, Motion.class);
				// Notificar o avisar a MAIN
				principal.notificar(m, this);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

}
