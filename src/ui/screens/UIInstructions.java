package ui.screens;

import game.GameSettings;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import ui.controls.UIRectangle;

public class UIInstructions extends KeyAdapter implements KeyListener {

	private BufferStrategy strategy;

	private boolean menuLoop;

	private int uiReturn = 1;

	private BufferedImage img;

	private Rectangle bounds;

	public UIInstructions(BufferStrategy Strategy, Rectangle Bounds) {
		strategy = Strategy;
		bounds = Bounds;

		UIRectangle rm = new UIRectangle(bounds);
		img = rm.getImage();

	}

	public void update() {

	}

	public void draw() {

		Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();

		if (GameSettings.USE_ANTIALIASING)
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);

		// Default Font
		g2d.setFont(new Font("Arial", Font.PLAIN, 14));
		// TODO: Height of the font
		// FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
		// int strH = fm.getHeight();
		// */

		int tsx = bounds.x + 25;
		int tsy = bounds.y + 120;

		g2d.setColor(Color.black);
		g2d.fill(bounds);
		g2d.drawImage(img, bounds.x, bounds.y, null);

		g2d.setColor(Color.white);
		// TODO: MUST DO IT!
		g2d.drawString("Coming Soon!!!", tsx, tsy);
		tsy += 26;
		g2d
				.drawString(
						"I need a Translator for the instructions (Spanish to English).",
						tsx, tsy);
		tsy += 16;
		g2d
				.drawString(
						"If someone wants to help me, contact me at <adaelxp@gmail.com>",
						tsx, tsy);
		tsy += 16;
		g2d
				.drawString(
						"(his/her name will appears in credits)",
						tsx, tsy);

		if (GameSettings.USE_ANTIALIASING)
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);

		g2d.dispose();
		strategy.show();

	}

	public int loop() {

		menuLoop = true;

		while (menuLoop) {
			update();
			draw();

			try {
				Thread.sleep(GameSettings.GAME_SPEED);
			} catch (InterruptedException e) {
			}

		}

		return uiReturn;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			menuLoop = false;
			break;
		case KeyEvent.VK_DOWN:
			break;
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_ENTER:
			menuLoop = false;
			break;
		}

	}

	public void keyReleased(KeyEvent e) {
	}

}
