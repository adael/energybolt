package gameobject.pickups;

import game.GameSettings;
import gameobject.SpaceShip;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import util.ImageCache;

public abstract class Pickup extends Ellipse2D.Double {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2564614062200010799L;
	public double inertiaX;
	public double inertiaY;
	private BufferedImage img;

	protected void initialize(Rectangle gameBounds, Color color) {
		setBounds();
		initializePosition(gameBounds);
		createBufferedImage(color);
	}

	private void setBounds() {
		width = 8.0d;
		height = 8.0d;
	}

	private void initializePosition(Rectangle gameBounds) {
		Random rnd = new Random();
		x = gameBounds.x + rnd.nextInt(gameBounds.width);
		y = gameBounds.y + rnd.nextInt(gameBounds.height);
		inertiaX = rnd.nextDouble();
		inertiaY = rnd.nextDouble();
	}

	private void createBufferedImage(Color color) {
		if (GameSettings.QUALITY_ITEMS) {
			img = new BufferedImage((int) width + 1, (int) height + 1,
					Transparency.BITMASK);
		} else {
			// Muy baja calidad, pero mejor rendimiento
			img = ImageCache.createCompatible((int) width + 1,
					(int) height + 1, Transparency.BITMASK);
		}

		Graphics2D g2d = (Graphics2D) img.getGraphics();

		double centro = this.width / 2;
		// Draw the in-Ball detail
		Ellipse2D.Double c2 = new Ellipse2D.Double(centro - (width / 4), centro
				- (height / 4), width / 2, height / 2);
		// 
		Ellipse2D.Double c = new Ellipse2D.Double(0, 0, width, height);

		g2d.setColor(color);

		if (GameSettings.USE_ANTIALIASING)
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.draw(c);
		g2d.fill(c2);

		if (GameSettings.USE_ANTIALIASING)
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);

	}

	// Actualiza la posición de la nave en la pantalla.
	public void update() {
		x += inertiaX;
		y += inertiaY;
	}

	public void draw(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		g2d.drawImage(img, at, null);
	}

	public abstract void pickup_catched(SpaceShip ship);

}