package gameobject;

import game.GameSettings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import util.ImageCache;

public class Bolt extends Ellipse2D.Double {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1898984824847807256L;

	public double inertiaX;

	public double inertiaY;

	public boolean activated;

	private BufferedImage img;

	public Bolt(double diameter, Color c) {
		width = diameter;
		height = diameter;
		inertiaX = 0;
		inertiaY = 0;
		activated = false;

		if (GameSettings.QUALITY_BOLTS) {
			img = new BufferedImage((int) width + 1, (int) height + 1,
					Transparency.BITMASK);
		} else {
			// Compatible: Muy baja calidad, pero muy rápido
			img = ImageCache.createCompatible((int) width + 1,
					(int) height + 1, Transparency.BITMASK);
		}

		Graphics2D g2d = (Graphics2D) img.getGraphics();

		Ellipse2D.Double b = new Ellipse2D.Double(0, 0, width, height);
		g2d.setColor(c);
		g2d.fill(b);
		if (width > 3) {
			g2d.setColor(Color.white);

			if (GameSettings.USE_ANTIALIASING)
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

			g2d.draw(b);

			if (GameSettings.USE_ANTIALIASING)
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_OFF);
		}

	}

	public void activate() {
		activated = true;
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

	public void setTransform(AffineTransform at) {
		Shape s = at.createTransformedShape(this);
		x = s.getBounds().x;
		y = s.getBounds().y;
	}

}