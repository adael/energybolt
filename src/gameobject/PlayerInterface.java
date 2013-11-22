package gameobject;

import game.Defines;
import game.GameSettings;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import util.ImageCache;
import util.Keys;

public class PlayerInterface {
	// name of the Player
	public String name;
	// score of the player
	public int score;
	// bounds of the HUD
	Rectangle bounds;
	// Player's Ship
	public SpaceShip ship;
	// Player's keys
	Keys keys;

	boolean down = false;

	boolean up = false;

	boolean right = false;

	boolean left = false;

	boolean shot = false;

	boolean status = false;

	Rectangle ap1;

	Rectangle ap2;

	Rectangle ap3;

	EnergyBar bSpeed;

	EnergyBar bPower;

	BufferedImage interfaceImage = null;

	BufferedImage statusImage = null;

	boolean interfaceChanged = false;

	boolean statusChanged = false;

	public PlayerInterface(String name, BufferedImage img, Rectangle bounds,
			Keys Tec) {
		this.name = name;
		this.bounds = bounds;
		ship = new SpaceShip(this, img);
		keys = Tec;
		score = 0;

		int a1 = Math.round(32 * bounds.width / 100);
		int a2 = Math.round(36 * bounds.width / 100);
		int a3 = Math.round(32 * bounds.width / 100);

		ap1 = new Rectangle(0, 0, a1, bounds.height);
		ap2 = new Rectangle(a1, 0, a2, bounds.height);
		ap3 = new Rectangle(a1 + a2, 0, a3, bounds.height);

		bSpeed = new EnergyBar(new Rectangle(ap2.x + 45, ap2.y + 22, 50, 8));
		bPower = new EnergyBar(new Rectangle(ap2.x + 45, ap2.y + 5, 50, 8));
		bPower.setGradient(Color.red, Color.orange);
	}

	public void update() {
		if (down)
			ship.decelerate();
		if (up)
			ship.acelerate();
		if (right)
			ship.rotateRight();
		if (left)
			ship.rotateLeft();
		if (shot)
			ship.concentrate();
		ship.update();
	}

	private double messageEndTime = 0;

	private String message = null;

	public void showMessage(String Message, int Seconds) {
		message = Message;
		messageEndTime = System.currentTimeMillis() + Seconds * 1000;
	}

	public void clearMessage() {
		message = null;
		messageEndTime = 0;
	}

	static final int jumpY = 17;

	static final int barWidth = 50;

	public void draw(Graphics2D g2d) {

		// Dibujar Interfaz
		if (interfaceImage == null || interfaceChanged)
			drawInterface();
		g2d.drawImage(interfaceImage, bounds.x, bounds.y, null);

		// showMessage
		if (message != null) {
			g2d.setColor(Color.white);

			if (GameSettings.USE_ANTIALIASING)
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

			g2d.drawString(message, this.bounds.x + 2, this.bounds.y
					+ this.bounds.height + 15);

			if (GameSettings.USE_ANTIALIASING)
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_OFF);

			if (System.currentTimeMillis() >= messageEndTime)
				clearMessage();
		}

		// Dibujar Nave
		ship.draw(g2d);

		// showStatus

		if (status) {
			if (statusImage == null || statusChanged)
				drawStatus();
			g2d.drawImage(statusImage, bounds.x + 2, bounds.y + bounds.height
					+ 4, null);
		}

	}

	private void drawInterface() {
		int tsx;
		int tsy;
		Rectangle cBounds = new Rectangle(0, 0, bounds.width - 1,
				bounds.height - 1);
		interfaceImage = ImageCache.createCompatible(bounds.width,
				bounds.height, Transparency.OPAQUE);

		Graphics2D g2d = (Graphics2D) interfaceImage.getGraphics();

		// if(GameSettings.USE_ANTIALIASING_IN_INTERFAZ)
		// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);

		// Espacio del interfaz
		// g2d.setColor(Color.darkGray);
		// g2d.fill(cBounds);
		g2d.setColor(Color.white);
		g2d.draw(cBounds);

		// 1 APARTADO: Nombre del jugador y Score
		tsx = ap1.x + 5;
		tsy = ap1.y + 14;

		// CADENAS
		g2d.drawString(name, tsx, tsy);
		tsy += 16;
		g2d.drawString("Energy: " + ship.energy + " | Shield: " + ship.shield,
				tsx, tsy);

		// Separador 2º Apartado
		g2d.setColor(Color.gray);
		g2d.fill(new Rectangle(ap1.x + ap1.width, cBounds.y + 1, 2,
				cBounds.height - 1));

		// 2 APARTADO: Barras de Power y Speed
		tsx = ap2.x + 5;
		tsy = ap2.y + 14;
		// Power
		g2d.setColor(Color.white);
		g2d.drawString("Power", tsx, tsy);
		bPower.setValue(ship.powerValue, ship.maxPowerValue);
		bPower.draw(g2d);
		tsy += 17;
		g2d.drawString("Speed", tsx, tsy);
		g2d.drawString(String.valueOf(Math.round(ship.speed * 100) / 100.0),
				tsx + 95, tsy);

		bSpeed.setValue(ship.speed, ship.maxSpeed);
		if (ship.speed < 0) {
			bSpeed.setGradient(Defines.darkRed, Color.red);
			bSpeed.draw(g2d);
		} else {
			bSpeed.setGradient(Defines.darkBlue, Color.blue);
			bSpeed.draw(g2d);
		}

		// Separador 3º apartado
		g2d.setColor(Color.gray);
		g2d.fill(new Rectangle(ap2.x + ap2.width, cBounds.y + 1, 2,
				cBounds.height - 1));

		// 3 APARTADO: Armas

		tsx = ap3.x + 5;
		tsy = ap3.y + 14;

		// Weapon & Stats
		g2d.setColor(Color.white);
		g2d.drawString("Weapon: " + ship.weapon, tsx, tsy);
		tsy += 16;
		g2d.drawString("Shots", tsx, tsy);
		double bw = GameSettings.BOLT_RADIUS;
		tsx += 35;

		for (int i = 0; i < (ship.maxShots - ship.currentShots); i++) {
			if (i < 5) {
				Bolt b = new Bolt(bw, ship.color);
				b.x = tsx;
				b.y = tsy - (bw);
				b.draw(g2d);
				tsx += b.width + 2;
			} else {
				g2d.drawString("+" + (ship.maxShots - ship.currentShots - 5),
						tsx + 2, tsy);
			}
		}

		// if(GameSettings.USE_ANTIALIASING_IN_INTERFAZ)
		// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_OFF);

		interfaceChanged = false;
	}

	private void drawStatus() {
		int saltoY = 12;
		Rectangle r = new Rectangle(0, 0, bounds.width - 4, saltoY * 13);
		statusImage = ImageCache.createCompatible(r.width + 1, r.height + 1,
				Transparency.BITMASK);

		Graphics2D g2d = (Graphics2D) statusImage.getGraphics();

		// Recuadro Y fondo para el STATUS
		// g2d.setColor(Color.WHITE);
		// g2d.draw(r);
		// g2d.setColor(Defines.blanco50);
		// g2d.fill(r);

		g2d.setColor(Color.yellow);
		g2d.setFont(new Font("Arial", Font.PLAIN, 9));

		int tsx = r.x + 2;
		int tsy = r.y + saltoY;
		g2d.drawString("Accel Speed: " + Math.round(ship.accelSpeed * 100.0)
				/ 100.0, tsx, tsy);
		tsy += saltoY;
		g2d.drawString("Max Speed: " + Math.round(ship.maxSpeed * 100.0)
				/ 100.0, tsx, tsy);
		tsy += saltoY;
		g2d.drawString("Min Speed: " + Math.round(ship.maxReverseSpeed * 100.0)
				/ 100.0, tsx, tsy);
		tsy += saltoY;
		g2d.drawString("BrakeSpeed: " + Math.round(ship.brakeSpeed * 100.0)
				/ 100.0, tsx, tsy);
		tsy += saltoY;
		g2d.drawString("Max Shots: " + ship.maxShots, tsx, tsy);
		tsy += saltoY;
		g2d.drawString("Shot Speed: " + Math.round(ship.shotSpeed * 100.0)
				/ 100.0, tsx, tsy);
		tsy += saltoY;
		g2d.drawString("Energy: " + ship.energy, tsx, tsy);
		tsy += saltoY;

		// tsx+=150;
		// tsy = r.y + saltoY;

		g2d.drawString("Power Speed: "
				+ Math.round(ship.concentrateSpeed * 100.0) / 100.0, tsx, tsy);
		tsy += saltoY;
		g2d.drawString("Max Power Value: "
				+ Math.round(ship.maxPowerValue * 100.0) / 100.0, tsx, tsy);
		tsy += saltoY;
		g2d.drawString("Rotate Speed: " + Math.round(ship.rotateSpeed * 100.0)
				/ 100.0, tsx, tsy);
		tsy += saltoY;
		g2d.drawString("Max Rotate Speed: "
				+ Math.round(ship.maxRotateSpeed * 100.0) / 100.0, tsx, tsy);
		tsy += saltoY;
		g2d.drawString("Min Rotate Speed: "
				+ Math.round(ship.minRotateSpeed * 100.0) / 100.0, tsx, tsy);
		tsy += saltoY;
		g2d.drawString("Shield: " + ship.shield, tsx, tsy);
		tsy += saltoY;

		statusChanged = false;

	}

	public void changeStatus() {
		statusChanged = true;
	}

	public void changeInterface() {
		interfaceChanged = true;
	}

	/*
	 * VALORES INICIALES accelSpeed = 0.09; maxSpeed = 4; minSpeed = -3;
	 * brakeSpeed = 1.5; maxShots = 1; currentShots = 0; shotSpeed = 1.5;
	 * powerSpeed = 0.15; powerValue = 0; maxPowerValue = 5; rotateSpeed = 1;
	 * maxRotateSpeed = 8; minRotateSpeed = 3;
	 */

	public void keyPressed(int k) {

		if (k == keys.DOWN)
			down = true;
		if (k == keys.UP)
			up = true;
		if (k == keys.LEFT)
			left = true;
		if (k == keys.RIGHT)
			right = true;
		if (k == keys.SHOT)
			shot = true;
		if (k == keys.STATUS)
			status = true;

	}

	public void keyReleased(int k) {

		if (k == keys.DOWN)
			down = false;
		if (k == keys.UP)
			up = false;
		if (k == keys.LEFT)
			left = false;
		if (k == keys.RIGHT)
			right = false;
		if (k == keys.SHOT) {
			shot = false;
			ship.shot();
		}
		if (k == keys.STATUS)
			status = false;

	}

}