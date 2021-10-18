import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Principal extends PApplet {

	public static void main(String[] args) {
		PApplet.main(Principal.class.getName());
	}

	@Override
	public void settings() {
		size(1200, 700);
	}

	private ArrayList<Fresa> listaFresas = new ArrayList<Fresa>();

	int estado;
	int segundos;
	int minutos;
	int timer;
	int puntajeFinal1;
	int puntajeFinal2;
	int fresasCount;
	int[] positions = { 160, 400, 640, 880, 1120 };
	boolean gametime;

	private Zorro playerZ;
	private Nino playerN;
	private Session sessionZ;
	private Session sessionN;
	private String ganador;

	PImage pantallaInicio;
	PImage pantallaHistoria;
	PImage pantallaInstrucciones;
	PImage pantallaEscenario;

	PImage botonJugar;
	PImage botonInstrucciones;
	PImage botonSigHistoria;
	PImage botonSigInstrucciones;

	PImage nino;
	PImage zorro;
	PImage fresa;
	PImage ninofresa;
	PImage lobofresa;

	@Override
	public void setup() {
		sessionN = new Session(this, Session.PLAYER_2);

		sessionN.start();

		sessionZ = new Session(this, Session.PLAYER_1);

		sessionZ.start();

		playerN = new Nino(734, 440, 0, false);
		playerZ = new Zorro(350, 420, 0, false);

		listaFresas = new ArrayList<>();
		agregarFresas();

		estado = 0;
		segundos = 0;
		minutos = 0;

		pantallaInicio = loadImage("PantallaInicio.jpg");
		pantallaHistoria = loadImage("PantallaHistoria.jpg");
		pantallaInstrucciones = loadImage("PantallaInstrucciones.jpg");
		pantallaEscenario = loadImage("PantallaEscenario.jpg");

		botonJugar = loadImage("BotonJugar.png");
		botonInstrucciones = loadImage("BotonInstrucciones.png");
		botonSigHistoria = loadImage("BotonSigHistoria.png");
		botonSigInstrucciones = loadImage("BotonSigInstrucciones.png");

		nino = loadImage("Nino.png");
		zorro = loadImage("Zorro.png");
		fresa = loadImage("Fresa.png");
		ninofresa = loadImage("Ninofresa.png");
		lobofresa = loadImage("Lobofresa.png");

		gametime = true;
	}

	@Override
	public void draw() {
		background(255);

		image(pantallaInicio, 0, 0);

		switch (estado) {
		case 0:
			// PANTALLA INICIO
			// Imagen Pantalla Inicio
			image(pantallaInicio, 0, 0);

			// Si esta el mouse encima del Boton Jugar mostrar imagen Boton Oprimido
			if (mouseX > 341 && mouseX < 341 + 520 && mouseY > 458 && mouseY < 458 + 60) {
				image(botonJugar, 341, 458);
			}

			// Si esta el mouse encima del Boton Instrucciones mostrar imagen Boton Oprimido
			if (mouseX > 340 && mouseX < 340 + 520 && mouseY > 544 && mouseY < 544 + 60) {
				image(botonInstrucciones, 340, 544);
			}
			break;

		case 1:
			// PANTALLA HISTORIA
			// Imagen Pantalla Historia
			image(pantallaHistoria, 0, 0);

			// Si esta el mouse encima del Boton Siguiente mostrar imagen Boton Oprimido
			if (mouseX > 352 && mouseX < 352 + 520 && mouseY > 534 && mouseY < 534 + 60) {
				image(botonSigHistoria, 352, 534);
			}
			break;

		case 2:
			// PANTALLA INSTRUCCIONES
			// Imagen Pantalla Instrucciones
			image(pantallaInstrucciones, 0, 0);

			// Si esta el mouse encima del Boton Siguiente mostrar imagen Boton Oprimido
			if (mouseX > 984 && mouseX < 984 + 165 && mouseY > 600 && mouseY < 600 + 60) {
				image(botonSigInstrucciones, 984, 600);
			}
			break;

		case 3:
			// PANTALLA ESCENARIO
			// Imagen Pantalla Escenario
			image(pantallaEscenario, 0, 0);
			if (gametime) {

				// Imagen Nino
				if (playerN.getOnHands()) {
					image(ninofresa, playerN.getX(), playerN.getY());
				} else if (!playerN.getOnHands()) {
					image(nino, playerN.getX(), playerN.getY());
				}

				// Imagen Zorro
				if (playerZ.getOnHands()) {
					image(lobofresa, playerZ.getX(), playerZ.getY());
				} else if (!playerZ.getOnHands()) {
					image(zorro, playerZ.getX(), playerZ.getY());
				}

				AtraparFresaZorro();
				AtraparFresaNino();

				agregarFresas();
				for (int i = 0; i < listaFresas.size(); i++) {
					Fresa f = listaFresas.get(i);
					f.move();
					image(fresa, f.getX(), f.getY());
					if (f.getY() > 650) {
						listaFresas.remove(i);
						break;
					}
				}

				temporizador();
			}

			// Puntaje 1 Zorro
			fill(255);
			textSize(20);
			text(mouseX + " ," + mouseY, mouseX, mouseY);
			text(playerZ.getScore(), 356, 55);

			// Puntaje 2
			fill(255);
			textSize(20);
			text(playerN.getScore(), 477, 55);

			// Hacer metodo para finalizar el juego
			if (minutos > 2) {
				puntajeFinal1 = playerZ.getScore();
				puntajeFinal2 = playerN.getScore();
				endgame();
			}

			break;
		}
	}

	@Override
	public void mousePressed() {
		switch (estado) {
		case 0:
			// PANTALLA INICIO
			// Cuando se le de clic en Jugar pasar a la Historia
			if (mouseX > 341 && mouseX < 341 + 520 && mouseY > 458 && mouseY < 458 + 60) {
				estado = 1;
			}

			// Cuando se le de clic en Instrucciones pasar a las Instrucciones
			if (mouseX > 340 && mouseX < 340 + 520 && mouseY > 544 && mouseY < 544 + 60) {
				estado = 2;
			}
			break;

		case 1:
			// PANTALLA HISTORIA
			// Cuando se le de clic en Siguiente pasar al Escenario
			if (mouseX > 352 && mouseX < 352 + 520 && mouseY > 534 && mouseY < 534 + 60) {
				estado = 3;
			}
			break;

		case 2:
			// PANTALLA INSTRUCCIONES
			// Cuando se le de clic en Siguiente pasar al Escenario
			if (mouseX > 984 && mouseX < 984 + 165 && mouseY > 600 && mouseY < 600 + 60) {
				estado = 3;
			}
			break;
		}
	}

	public void AtraparFresaZorro() {

		for (int i = 0; i < listaFresas.size(); i++) {

			int posX1 = listaFresas.get(i).getX();
			int posY1 = listaFresas.get(i).getY();
			int posX2 = playerZ.getX();
			int posY2 = playerZ.getY();

			if (PApplet.dist(posX1, posY1, posX2, posY2) < 80 && !playerZ.getOnHands()) {

				playerZ.setOnHands(true);
				System.out.println("Zorro con fresa en mano");
				listaFresas.remove(i);
			}
		}
	}

	public void AtraparFresaNino() {

		for (int i = 0; i < listaFresas.size(); i++) {

			int posX1 = listaFresas.get(i).getX();
			int posY1 = listaFresas.get(i).getY();
			int posX2 = playerN.getX();
			int posY2 = playerN.getY();

			if (PApplet.dist(posX1, posY1, posX2, posY2) < 80 && !playerN.getOnHands()) {

				playerN.setOnHands(true);
				listaFresas.remove(i);
			}
		}
	}

	public void temporizador() {
		if (frameCount % 60 == 0 && minutos >= 0) {
			segundos++;
			timer++;
		}
		if (segundos == 60) {
			minutos++;
			segundos = 0;
		}
		if (minutos < 0) {
			fill(255);
			textSize(20);
			text("0:00", 134, 55);
		} else if (segundos <= 9) {
			fill(255);
			textSize(20);
			text(minutos + ":0" + segundos, 134, 55);
		} else if (segundos > 9) {
			fill(255);
			textSize(20);
			text(minutos + ":" + segundos, 134, 55);
		}
	}

	public void endgame() {

		String ganador = null;

		gametime = false;

		if (puntajeFinal1 > puntajeFinal2) {

			ganador = "El Jugador 1";
		} else if (puntajeFinal2 > puntajeFinal1) {
			ganador = "El Jugador 2";
		} else {
			ganador = "Empate";
		}

		fill(255);
		rect(300, 250, 300, 80);

		fill(0);
		textAlign(RIGHT, BOTTOM);
		if (ganador.equals("El Jugador 1") || ganador.equals("El Jugador 2")) {
			text(ganador + " es el ganador", 590, 300);
		} else {
			text("Es empate!", 590, 300);
		}

	}

	public void agregarFresas() {

		if (timer > 0) {
			if (fresasCount < 205) {
				int p = (int) Math.floor(Math.random() * 5);
				Fresa e = new Fresa(positions[p], -100 - (50 * fresasCount));
				for (int i = 0; i < listaFresas.size(); i++) {
					if (e.getY() == listaFresas.get(i).getY() && e.getX() == listaFresas.get(i).getX()) {
						e.setY(e.getY() + 500);
					}
				}
				listaFresas.add(e);
				fresasCount++;
			}

		}
//		if (timer <= 30 && timer > 15) {
//			int p = (int) Math.floor(Math.random() * 5);
//			if (fresasCount < 16) {
//
//				Fresa e = new Fresa(positions[p], -100 - (50 * fresasCount));
//				for (int i = 0; i < listaFresas.size(); i++) {
//					if (e.getY() == listaFresas.get(i).getY() && e.getX() == listaFresas.get(i).getX()) {
//						e.setY(e.getY() + 500);
//					}
//				}
//				listaFresas.add(e);
//				fresasCount++;
//			}
//
//		}
//		if (timer <= 45 && timer > 30) {
//			int p = (int) Math.floor(Math.random() * 5);
//			if (fresasCount < 24) {
//				Fresa e = new Fresa(positions[p], -100 - (50 * fresasCount));
//				for (int i = 0; i < listaFresas.size(); i++) {
//					if (e.getY() == listaFresas.get(i).getY() && e.getX() == listaFresas.get(i).getX()) {
//						e.setY(e.getY() + 500);
//					}
//				}
//				listaFresas.add(e);
//				fresasCount++;
//			}
//		}
//		
//		if (timer >= 45) {
//			int p = (int) Math.floor(Math.random() * 5);
//			if (fresasCount < 24) {
//				Fresa e = new Fresa(positions[p], -100 - (50 * fresasCount));
//				for (int i = 0; i < listaFresas.size(); i++) {
//					if (e.getY() == listaFresas.get(i).getY() && e.getX() == listaFresas.get(i).getX()) {
//						e.setY(e.getY() + 500);
//					}
//				}
//				listaFresas.add(e);
//				fresasCount++;
//			}
//		}
	}

	public void notificar(Motion m, Session session) {

		if (session.getPlayer().equals(Session.PLAYER_1)) {
			switch (m.getAccion()) {

			case "DOWNSTART":
				if (playerZ.getOnHands()) {
					System.out.println("Tiene fresa en la mano");
					playerZ.activateDropStrawberry();
					playerZ.acciones();
					playerZ.desactivateDropStrawberry();
				}

				break;
			case "DOWNSTOP":
				// playerZ.desactivateDropStrawberry();
				break;

			case "UPSTART":
				int posX1 = playerZ.getX();
				int posY1 = playerZ.getY();
				int posX2 = playerN.getX();
				int posY2 = playerN.getY();

				if (PApplet.dist(posX1, posY1, posX2, posY2) < 80) {
					playerZ.activateTakeStrawberry();
					playerZ.acciones();
					playerN.setOnHands(false);
					playerZ.desactivateTakeStrawberry();
				}

				break;
			case "UPSTOP":
				// playerZ.desactivateTakeStrawberry();
				break;

			case "RIGHTSTART":
				playerZ.activateRightMovement();
				playerZ.acciones();
				break;
			case "RIGHTSTOP":
				playerZ.desactivateRightMovement();
				playerZ.acciones();
				break;

			case "LEFTSTART":
				playerZ.activateLeftMovement();
				playerZ.acciones();
				break;
			case "LEFTSTOP":
				playerZ.desactivateLeftMovement();
				playerZ.acciones();
				break;

			}
		}

		else if (session.getPlayer().equals(Session.PLAYER_2)) {
			switch (m.getAccion()) {

			case "DOWNSTART":
				playerN.activateDropStrawberry();
				playerN.acciones();
				playerN.desactivateDropStrawberry();
				break;
			case "DOWNSTOP":
				// playerZ.desactivateDropStrawberry();
				break;

			case "UPSTART":
				int posX1 = playerZ.getX();
				int posY1 = playerZ.getY();
				int posX2 = playerN.getX();
				int posY2 = playerN.getY();

				if (PApplet.dist(posX1, posY1, posX2, posY2) < 80) {
					playerN.activateTakeStrawberry();
					playerN.acciones();
					playerZ.setOnHands(false);
					playerN.desactivateTakeStrawberry();
				}

				break;
			case "UPSTOP":
				// playerZ.desactivateTakeStrawberry();
				break;

			case "RIGHTSTART":
				playerN.activateRightMovement();
				playerN.acciones();
				break;
			case "RIGHTSTOP":
				playerN.desactivateRightMovement();
				playerN.acciones();
				break;

			case "LEFTSTART":
				playerN.activateLeftMovement();
				playerN.acciones();
				break;
			case "LEFTSTOP":
				playerN.desactivateLeftMovement();
				playerN.acciones();
				break;

			}
		}
	}

}
