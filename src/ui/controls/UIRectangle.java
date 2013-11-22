package ui.controls;

import game.Defines;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import util.ImageCache;

public class UIRectangle {

	/*
	 * private boolean menuLoop;
	 * 
	 * private Vector opciones;
	 * 
	 * private int opcionElegida;
	 * 
	 * 
	 * private int marginY;
	 * 
	 * private int retorno = 101;
	 */

	private BufferedImage img;

	private Rectangle bounds;

	public UIRectangle(Rectangle Bounds) {
		this.bounds = Bounds;
		InitializeGraphics();
	}

	private void InitializeGraphics() {
		ImageCache s = new ImageCache();

		img = new BufferedImage(bounds.width, bounds.height,
				Transparency.BITMASK);
		Graphics2D g2d = (Graphics2D) img.getGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Color.black);
		g2d.fill(bounds);

		// Rectangle menuBounds = new Rectangle(bounds.width / 2 -
		// img.getWidth()/2, 50, 390, 500);

		// Dibujo el fondo del rectangulo que delimita el MenuPrincipal
		Rectangle rMenu = new Rectangle(20, 100, bounds.width - 40,
				bounds.height - 140);

		g2d.setColor(Defines.darkBlue);
		Point2D p1 = new Point2D.Double(rMenu.x, rMenu.y);
		Point2D p2 = new Point2D.Double(rMenu.x + rMenu.width, rMenu.y);
		Paint dp = g2d.getPaint();
		g2d.setPaint(new GradientPaint(p1, Defines.darkBlue, p2, Color.black));
		g2d.fillRoundRect(rMenu.x, rMenu.y, rMenu.width, rMenu.height, 15, 15);

		g2d.setPaint(dp);

		g2d.setColor(Color.white);
		g2d.drawRoundRect(rMenu.x, rMenu.y, rMenu.width, rMenu.height, 15, 15);

		// Dibujo el Logotipo
		BufferedImage logo = s.getImage("logotipo.png");
		g2d.drawImage(logo, bounds.width / 2 - logo.getWidth() / 2, 0, null);

		// Dibujo El rectangulo que engloba el logotipo
		// g2d.setColor(Color.white);
		// g2d.drawRoundRect(0, 0, menuBounds.width, menuBounds.height, 35, 35);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);

		g2d.setColor(Color.green);
		g2d.setFont(new Font("Arial", Font.BOLD, 12));
		int tsy = bounds.height - 25;
		g2d
				.drawString(
						"Game Concept, Graphics, Music, Sound, Design and Programing by :",
						20, tsy);
		g2d.drawString(
				"Carlos Gant Bernal < adaelxp@gmail.es > < www.adael.es >",
				20, tsy + 16);
	}

	public BufferedImage getImage() {
		return img;
	}

}
