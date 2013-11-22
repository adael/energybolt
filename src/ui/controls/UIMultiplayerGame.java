package ui.controls;

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
import java.util.Vector;

import ui.screens.UIOptionValue;

public class UIMultiplayerGame extends KeyAdapter implements KeyListener {

	private BufferStrategy strategy;

	private boolean menuLoop;

	private Vector<UIOptionValue> opciones;

	private int opcionElegida;

	private int retorno = 101;

	private BufferedImage img;

	private Rectangle bounds;

	private Font font = new Font("Arial", Font.BOLD, 14);

	public UIMultiplayerGame(BufferStrategy Strategy, Rectangle Bounds) {
		strategy = Strategy;
		bounds = Bounds;
		opciones = new Vector<UIOptionValue>();
		opcionElegida = 0;

		UIRectangle rm = new UIRectangle(bounds);
		img = rm.getImage();
	}

	public void update() {

	}

	public void createOption(String text, int value, boolean enabled) {
		opciones.add(new UIOptionValue(text, value, font, enabled));
	}

	public void chooseOption(int opcionelegida) {
		if (opcionelegida < opciones.size())
			opcionElegida = opcionelegida;
	}

	public void draw() {

		Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();

		if (GameSettings.USE_ANTIALIASING)
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

		int tsy = bounds.y + 120;

		g2d.setColor(Color.black);
		g2d.fill(bounds);
		g2d.drawImage(img, bounds.x, bounds.y, null);

		for (int i = 0; i < opciones.size(); i++) {
			UIOptionValue o = (UIOptionValue) opciones.get(i);

			int tsx = bounds.x + (bounds.width / 2) - ((o.width + 10) / 2);

			o.setBounds(new Rectangle(tsx, tsy, o.width + 10, o.height));

			o.setChoosed(opcionElegida == i);

			o.draw(g2d);

			tsy += o.height + 5;
		}

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

		return retorno;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			menuLoop = false;
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_NUMPAD2:
			if (opcionElegida < opciones.size() - 1)
				opcionElegida++;
			else
				opcionElegida = 0;
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_NUMPAD8:
			if (opcionElegida > 0)
				opcionElegida--;
			else
				opcionElegida = opciones.size() - 1;
			break;
		case KeyEvent.VK_ENTER:
			if (opciones.get(opcionElegida) != null) {
				UIOptionValue o = (UIOptionValue) opciones.get(opcionElegida);
				if (o.isEnabled()) {
					retorno = o.getValue();
					menuLoop = false;
				}
			}
			break;
		}

	}

	public void keyReleased(KeyEvent e) {
	}

}
