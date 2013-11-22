package gameobject;

import game.GameSettings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * Represents a SpaceShip
 *
 * @author Adael
 *
 */
public class SpaceShip extends Rectangle2D.Double {

	/**
	 *
	 */
	private static final long serialVersionUID = 4206598548256903386L;

	private static final double ACCEL_SPEED = 0.09d; // 0.09

	private static final double MAX_SPEED = 4.0d; // 4

	private static final double MAX_REVERSE_SPEED = -3.0d; // -3

	private static final double BRAKE_SPEED = 1.5d; // 1.5

	private static final int MAX_SHOTS = 1; // 1

	private static final double SHOT_SPEED = 1.0d; // 1.5

	private static final double CONCENTRATE_SPEED = 0.15d; // 0.15

	private static final double MAX_POWER_VALUE = 5.0d; // 5

	private static final double ROTATE_SPEED = 1.0d; // 1

	private static final double MAX_ROTATE_SPEED = 8.0d; // 8

	private static final double MIN_ROTATE_SPEED = 3.0d; // 3

	private static final int SHIELD = 1; // 0

	private static final int ENERGY = 5; // 5

	// Velocidad
	public double v;

	// Rotación
	public double r;

	// Velocidad de aceleracion de la nave
	public double accelSpeed;

	// Velocidad de la nave
	public double speed;

	// Máxima velocidad de la nave
	public double maxSpeed;

	// Minima velocidad de la nave
	public double maxReverseSpeed;

	// Velocidad a la que frena la nave. 0 = no
	public double brakeSpeed;

	// frena, 1 = frena tan rapido como acelera, 2 =
	// frena el doble de rápido. (Se admiten
	// decimales).
	// Máximo número de disparos a la vez
	public int maxShots;

	// Número de disparos concurrentes
	public int currentShots;

	// Velocidad del bolt una vez disparado
	public double shotSpeed;

	// Configuración de Concentrado
	public double concentrateSpeed;

	// Valor del concentrado (en tiempo de juego)
	public double powerValue;

	// Velocidad a la que se crea el concentrado
	public double maxPowerValue;

	// Velocidad de Rotacion
	public double rotateSpeed;

	// Velocidad máxima de rotación
	public double maxRotateSpeed;

	// Velocidad minima de rotación
	public double minRotateSpeed;

	// Balas que pertenecen a la nave
	public Vector<Bolt> bolts;

	public int shield;

	public int energy;

	public String weapon;

	public Color color;

	public PlayerInterface pi;

	public BufferedImage img;

	private double cx;

	private double cy;

	private double margenX;

	private double margenY;

	public SpaceShip(PlayerInterface p, BufferedImage Img) {
		// Starting atributes // Default Values
		setAttributesToDefault();
		// That's the weapon name. Support for Weapons (laters versions)
		weapon = "Energybolt"; // "Energybolt"
		// Ingame variables
		pi = p;
		width = 27;
		height = 25;
		cx = width / 2;
		cy = height / 2;
		speed = 0.0d;
		currentShots = 0;
		powerValue = 0.0d;

		// Each ship have his bolts in a vector.
		bolts = new Vector<Bolt>();
		img = Img;

		margenX = width + (width / 3);
		margenY = height / 2;

		// debug = new Debug(new Rectangle(EnergyBolt.GAME_BOUNDS.x,
		// EnergyBolt.GAME_BOUNDS.y, 150, EnergyBolt.GAME_BOUNDS.height));
		// debug.setColsByPercent(70);
	}

	public void acelerate() {
		if (speed <= maxSpeed) {
			speed += accelSpeed;
			pi.changeInterface();
		}
	}// Acelera la velocidad de la nave

	public void decelerate() {
		if (speed > 0) {
			speed -= accelSpeed * brakeSpeed;
			pi.changeInterface();
		} else if (speed >= maxReverseSpeed) {
			speed -= accelSpeed;
			pi.changeInterface();
		}
	}// Frena la nave

	/**
	 * rotate the ship to the left.
	 *
	 */
	public void rotateLeft() {
		r -= Math.min(Math.max((Math.abs(speed) / 1.5 + rotateSpeed),
				minRotateSpeed), maxRotateSpeed);
	} // Gira a la izquierda

	/**
	 * rotate the ship to the right.
	 *
	 */
	public void rotateRight() {
		r += Math.min(Math.max((Math.abs(speed) / 1.5 + rotateSpeed),
				minRotateSpeed), maxRotateSpeed);
	} // Gira a la derecha

	/**
	 * Conentra el disparo mientras el jugador tenga el boton pulsado.
	 * Concentrate the shot while the player hold down the shot button.
	 */
	public void concentrate() {
		if (currentShots < maxShots) {
			powerValue += concentrateSpeed;
			pi.changeInterface();
		} else
			powerValue = 0;
	} // Realiza el concentrado

	/**
	 * Después de que el jugador suelte el boton de disparo, se produce el
	 * disparo (tras la concentración) After the player
	 */
	public void shot() {

		if (powerValue > 0) {

			if (powerValue < 1)
				powerValue = 1;

			// Calculamos los valores necesarios para el disparo
			double vel = powerValue * shotSpeed;
			double angle = Math.toRadians(r);
			double bw = powerValue * GameSettings.BOLT_RADIUS / maxPowerValue; // Correcto
			double mbw = -bw / 2;

			AffineTransform ax = new AffineTransform();
			ax.translate(x, y);
			ax.rotate(angle, cx, cy);
			ax.translate(margenX + mbw, margenY + mbw);

			Bolt b = new Bolt(bw, color);
			b.setTransform(ax);
			b.inertiaX = vel * Math.cos(angle);
			b.inertiaY = vel * Math.sin(angle);
			// Lo enlazamos
			bolts.add(b);
			powerValue = 0;
			currentShots++;

			pi.changeInterface();
		}
	}

	private double messageEndTime = 0;

	private String message = null;

	/**
	 * Display a message near the ship.
	 *
	 * @param Message
	 * @param Seconds
	 */
	public void showMessage(String Message, int Seconds) {
		message = Message;
		messageEndTime = System.currentTimeMillis() + Seconds * 1000;
	}

	public void clearMessage() {
		message = null;
		messageEndTime = 0;
	}

	public void update() {

		// Limites de Velocidades y de poder
		if (speed > maxSpeed)
			speed = maxSpeed;
		if (speed < maxReverseSpeed)
			speed = maxReverseSpeed;
		if (powerValue > maxPowerValue)
			powerValue = maxPowerValue;
		if (r > 180)
			r = -180;
		if (r < -180)
			r = 180;

		// Rumbo
		double angle = Math.toRadians(r);
		x += speed * Math.cos(angle);
		y += speed * Math.sin(angle);

	}

	public void draw(Graphics2D g2d) {

		double angle = Math.toRadians(r);
		// draw the Bolt
		if (powerValue > 0) {

			double bw = powerValue * GameSettings.BOLT_RADIUS / maxPowerValue; // Correcto
			double mbw = -bw / 2;

			AffineTransform ax = new AffineTransform();
			ax.translate(x, y);
			ax.rotate(angle, cx, cy);
			ax.translate(margenX + mbw, margenY + mbw);

			Bolt b = new Bolt(bw, color);
			b.setTransform(ax);
			b.draw(g2d);

		}
		// Draw the message
		if (message != null) {
			g2d.setColor(color);
			g2d.drawString(message, Math.round(x), Math.round(y + height + 10));
			if (System.currentTimeMillis() >= messageEndTime)
				clearMessage();
		}

		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.rotate(angle, cx, cy);

		g2d.setColor(Color.white);
		// Draw the Ship
		g2d.drawImage((Image) img, at, null);
		// Draw the shield
		if (shield > 0) {
			Ellipse2D.Double sh = new Ellipse2D.Double();
			sh.width = width + 4;
			sh.height = height + 8;
			sh.x = x - 2;
			sh.y = y - 4;
			g2d
					.setColor(new Color(0, 0, 255, Math.min(shield * 20 + 100,
							255)));
			g2d.draw(sh);
		}
	}

	public void changeSpeed(double Speed) {
		speed = Speed;
		pi.changeInterface();
	}

	/**
	 * When a weapon hit this shop.
	 *
	 * @param damage
	 *            ammount of damate
	 * @param direct
	 *            true = damage directly to energy, false = damage to shiled if
	 *            have
	 */
	public void hitted(int damage, boolean direct) {
		if (!direct && shield > 0) {
			if (shield >= damage) {
				shield -= damage;
				return;
			} else {
				int ashield = shield;
				shield -= damage;
				damage -= ashield;
			}
		}
		if (energy >= damage) {
			energy -= damage;
		} else {
			energy = 0;
		}
	}

	public void setAttributesToDefault() {
		accelSpeed = ACCEL_SPEED;
		maxSpeed = MAX_SPEED;
		maxReverseSpeed = MAX_REVERSE_SPEED;
		brakeSpeed = BRAKE_SPEED;
		maxShots = MAX_SHOTS;
		shotSpeed = SHOT_SPEED;
		concentrateSpeed = CONCENTRATE_SPEED;
		maxPowerValue = MAX_POWER_VALUE;
		rotateSpeed = ROTATE_SPEED;
		maxRotateSpeed = MAX_ROTATE_SPEED;
		minRotateSpeed = MIN_ROTATE_SPEED;
		shield = SHIELD;
		energy = ENERGY;
	}

}