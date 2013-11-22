package game;

import gameobject.Bolt;
import gameobject.FpsCounter;
import gameobject.PlayerInterface;
import gameobject.SpaceShip;
import gameobject.pickups.AttributeChanger;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.Transparency;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;
import util.ImageCache;
import util.Keys;

/**
 * This is the Game Main Class, its run the gameloop and initialize all the game components
 *
 * @author Adael
 *
 */
public class Game extends KeyAdapter implements KeyListener {

	private boolean PAUSED = false;
	private BufferStrategy strategy;
	private Vector<PlayerInterface> pi;
	private boolean gameLoop = false;
	private ImageCache imageCache;
	private BufferedImage background, backgroundTile;
	private Vector<AttributeChanger> items;
	private double nextItemTime; // sets WHEN will appears the next item
	private FpsCounter fpsc;
	private Rectangle bounds;
	private Rectangle gameBounds;
	private Rectangle scoreBounds;
	private Rectangle borderTop;
	private Rectangle borderBottom;
	private Rectangle borderRight;
	private Rectangle borderLeft;
	private int gameWinBy = -1;
	private double startTime;
	private double usedTime;

	@SuppressWarnings("unchecked")
	public Game(BufferStrategy Strategy, Rectangle Bounds) {

		Random rnd = new Random();

		items = new Vector();
		nextItemTime = System.currentTimeMillis()
				+ (rnd.nextInt(GameSettings.FIRST_NEXT_ITEM_TIME) * 1000);

		strategy = Strategy;
		imageCache = new ImageCache();

		bounds = Bounds;
		scoreBounds = new Rectangle(bounds.x, bounds.y, bounds.width, 40);
		gameBounds = new Rectangle(bounds.x, bounds.y + scoreBounds.height,
				bounds.width, bounds.height - scoreBounds.height);

		// Obtain the border's position / Obtengo la posicion de los bordes
		borderTop = new Rectangle(gameBounds.x, gameBounds.y, gameBounds.width,
				1);
		borderBottom = new Rectangle(gameBounds.x, gameBounds.y
				+ gameBounds.height - 1, gameBounds.width, 1);
		borderRight = new Rectangle(gameBounds.x + gameBounds.width - 1,
				gameBounds.y, 1, gameBounds.height);
		borderLeft = new Rectangle(gameBounds.x, gameBounds.y, 1,
				gameBounds.height);

		// Creo el contador de FPS
		if (GameSettings.SHOW_FPS) {
			fpsc = new FpsCounter(gameBounds);
		}

		// Calculate the horizontal middle of the screen
		// Calculamos la mitad horizontal de la pantalla
		int middle = Math.round(scoreBounds.width / 2);

		// Set Player 1 UI Zone
		// Delimito la zona para el interfaz del jugador 1
		Rectangle r1 = new Rectangle(scoreBounds.x, scoreBounds.y + 2,
				middle - 2, scoreBounds.height - 4);
		// Same for Player 2 / Lo mismo para el jugador 2
		Rectangle r2 = new Rectangle(middle + 1, scoreBounds.y + 2, middle - 2,
				scoreBounds.height - 4);

		// Asigno las teclas a cada jugador
		// Asign the keys for each player
		Keys t1 = new Keys(104, 98, 100, 102, 155, 103);
		Keys t2 = new Keys(82, 70, 68, 71, 90, 65);

		// Creo un playerinterface para cada jugador
		// Create a PlayerInterface for each player
		PlayerInterface p1 = new PlayerInterface("Player 1", imageCache
				.getImage("naves/nav_red_27x25.png"), r1, t1);
		p1.ship.x = bounds.x + 5;
		p1.ship.y = bounds.y + 100;
		p1.ship.color = Color.red;

		PlayerInterface p2 = new PlayerInterface("Player 2", imageCache
				.getImage("naves/nav_blue_27x25.png"), r2, t2);
		p2.ship.x = bounds.x + bounds.width - 30;
		p2.ship.y = bounds.y + bounds.height - 100;
		p2.ship.r = 180;
		p2.ship.color = Color.blue;

		pi = new Vector(2);
		pi.add(p1);
		pi.add(p2);

		background = ImageCache.createCompatible(gameBounds.width,
				gameBounds.height, Transparency.OPAQUE);

		Graphics2D g2d = (Graphics2D) background.getGraphics();

		if (GameSettings.USE_ANTIALIASING) {
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		if (GameSettings.SHOW_BACKGROUND) {

			// Dibujo el Fondo con Estrellas
			backgroundTile = imageCache.getImage("texturas/space2.jpg");
			g2d.setPaint(new TexturePaint(backgroundTile, new Rectangle(0, 0,
					backgroundTile.getWidth(), backgroundTile.getHeight())));
			g2d.fillRect(0, 0, background.getWidth(), background.getHeight());

			// Creo Posiciones Aleatorias y cargo Graficos aleatorios

			Vector<String> imgPaths = new Vector<String>(8);
			imgPaths.add("background_detail/tn_galaxy2.png");
			imgPaths.add("background_detail/galaxy4.png");
			imgPaths.add("background_detail/2galaxy.png");
			imgPaths.add("background_detail/tn_galaxy3.png");
			imgPaths.add("background_detail/galaxy5.png");
			imgPaths.add("background_detail/adael_1.png");

			int imgSize = rnd.nextInt(64) + 64;
			int imgX = rnd.nextInt(gameBounds.width - gameBounds.x - imgSize)
					+ gameBounds.x;
			int imgY = rnd.nextInt(gameBounds.height - gameBounds.y - imgSize)
					+ gameBounds.y;
			int imgS = rnd.nextInt(imgPaths.size());
			// Dibujo la Imagen generada aleatoriamente
			g2d.drawImage(imageCache.getImage((String) imgPaths.get(imgS)),
					imgX, imgY, imgSize, imgSize, null);
		}

		g2d.setColor(Color.white);
		g2d.drawRect(0, 0, gameBounds.width - 2, gameBounds.height - 2);

		if (GameSettings.USE_ANTIALIASING) {
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);
		}

	}

	public int loop() {

		gameLoop = true;

		while (gameLoop) {
			startTime = System.currentTimeMillis();

			updateWorld();
			drawWorld();

			try {
				Thread.sleep(GameSettings.GAME_SPEED);
			} catch (InterruptedException e) {
			}

			usedTime = System.currentTimeMillis() - startTime;
		}

		return 1;

	}

	public void updateWorld() {

		// check the collisions / COMPRUEBO LAS COLISIONES
		checkWallCollisions();
		checkInterShipsCollisions();
		checkBoltsCollisions();

		// update the ships and his bolts / ACTUALIZO LAS NAVES Y SUS BOLAS
		for (int i = 0; i < 2; i++) {
			PlayerInterface p = (PlayerInterface) pi.get(i);
			if (gameWinBy == -1) {
				p.update();
				for (int i2 = 0; i2 < p.ship.bolts.size(); i2++) {
					Bolt b = (Bolt) p.ship.bolts.get(i2);
					b.update();
				}
			}
			// check if the match was finish
			if (p.ship.energy == 0) {
				gameWinBy = (i == 0 ? 1 : 0);
			}

		}

		// update the items / ACTUALIZO LOS ITEMS DE LA PANTALLA
		for (int i = 0; i < items.size(); i++) {
			AttributeChanger ac = (AttributeChanger) items.get(i);
			ac.update();
		}
		/*
		 * // put the pickup items on the screen when time has... ¿reached?
		 * sorry my english if (System.currentTimeMillis() >= nextItemTime) {
		 * Random rnd = new Random(); nextItemTime = System.currentTimeMillis()
		 * + (rnd.nextInt(IN_GAME_NEXT_ITEM_TIME) 1000); if (items.size() <
		 * GameSettings.MAX_ITEMS_ON_SCREEN) { Item it = new
		 * Item(rnd.nextInt(Defines.nItems), gameBounds); items.add(it); } } //
		 */
		// Pickups Items V2
		if (System.currentTimeMillis() >= nextItemTime) {
			Random rnd = new Random();
			nextItemTime = System.currentTimeMillis()
					+ (rnd.nextInt(GameSettings.IN_GAME_NEXT_ITEM_TIME) * 100);
			if (items.size() < GameSettings.MAX_ITEMS_ON_SCREEN) {
				AttributeChanger ac = new AttributeChanger(gameBounds);
				items.add(ac);
			}
		}
	}

	public void drawWorld() {
		Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();

		// Default font / Fuente por defecto
		g2d.setFont(Defines.defaultFont);

		// Initializing effects / INICIALIZAMOS LOS EFECTOS
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_SPEED);

		// clear the screen / Borro la pantalla
		g2d.setColor(Color.black);
		g2d.fill(bounds);

		// background with texture / FONDO CON TEXTURA
		g2d.drawImage(background, gameBounds.x, gameBounds.y, gameBounds.width,
				gameBounds.height, null);

		// Draw the delimiter rectangle / Dibujo el recuadro delimitador
		g2d.setColor(Color.white);

		/*
		 * // CUADRICULA g2d.setColor(Color.darkGray); // Filas for(int i =
		 * gameBounds.x; i < gameBounds.x + gameBounds.width ; i += 10)
		 * g2d.drawLine(i, gameBounds.y, i, gameBounds.y + gameBounds.height);
		 * // Columnas for(int i = gameBounds.y; i < gameBounds.y +
		 * gameBounds.height ; i += 10) g2d.drawLine(gameBounds.x, i,
		 * gameBounds.x+gameBounds.width, i); //
		 */

		// Draw the pickup items / Dibujo los items
		for (int i = 0; i < items.size(); i++) {
			AttributeChanger ac = (AttributeChanger) items.get(i);
			ac.draw(g2d);
		}

		// Draw the scores, ships and bolts / DIBUJO LOS SCORES, LAS NAVES Y LAS
		// BOLAS
		for (int i = 0; i < 2; i++) {
			PlayerInterface p = (PlayerInterface) pi.get(i);
			for (int i2 = 0; i2 < p.ship.bolts.size(); i2++) {
				Bolt b = (Bolt) p.ship.bolts.get(i2);
				b.draw(g2d);
			}
			p.draw(g2d);
		}

		// Draw FPS / DIBUJO LOS FPS
		if (GameSettings.SHOW_FPS) {
			fpsc.draw(g2d, usedTime, "Items: " + items.size());
		}

		// Draw The Win Label
		if (this.gameWinBy > -1) {

			PlayerInterface p = (PlayerInterface) pi.get(gameWinBy);
			String winstr = p.name + " WINS!!!";
			Rectangle wr = g2d.getFontMetrics(g2d.getFont()).getStringBounds(
					winstr, g2d).getBounds();
			wr.width += 10;
			wr.height += 10;
			wr.x = (bounds.width / 2) - (wr.width / 2);
			wr.y = (bounds.height / 2) - (wr.height / 2);

			g2d.setColor(Color.BLACK);
			g2d.fill(wr);
			g2d.setColor(Color.WHITE);
			g2d.draw(wr);
			g2d.drawString(winstr, wr.x + 7, wr.y + 15);

		}

		g2d.dispose();
		strategy.show();

	}

	private void checkWallCollisions() {

		// COLISION DE LA NAVE EN LA PARED
		for (int i = 0; i < 2; i++) {

			PlayerInterface p = (PlayerInterface) pi.get(i);
			SpaceShip ship = p.ship;

			Rectangle rec = ship.getBounds();

			if (!checkCollision(bounds, rec)) {
				ship.x = bounds.x;
				ship.y = bounds.y;
			}

			if (checkCollision(borderTop, rec)) {
				if (ship.speed >= 1) {
					ship.r *= -1;
				} else {
					ship.changeSpeed(Math.abs(ship.speed));
				}
				ship.y += 1;
			}

			if (checkCollision(borderBottom, rec)) {
				if (ship.speed >= 1) {
					ship.r *= -1;
				} else {
					ship.changeSpeed(Math.abs(ship.speed));
				}
				ship.y -= 1;
			}

			if (checkCollision(borderLeft, rec)) {
				if (ship.speed >= 1) {
					if (ship.r >= 0) {
						ship.r = -(ship.r - 180);
					} else {
						ship.r = -(ship.r + 180);
					}
				} else {
					ship.changeSpeed(Math.abs(ship.speed));
				}
				ship.x += 1;
			}

			if (checkCollision(borderRight, rec)) {
				if (ship.speed >= 1) {
					if (ship.r >= 0) {
						ship.r = -(ship.r - 180);
					} else {
						ship.r = -(ship.r + 180);
					}
				} else {
					ship.changeSpeed(Math.abs(ship.speed));
				}
				ship.x -= 1;
			}

			// COLISION DE LAS BOLAS EN LA PARED Y RECOGER LA BOLA
			for (int i2 = 0; i2 < ship.bolts.size(); i2++) {
				Bolt b = (Bolt) ship.bolts.get(i2);

				if (b.x < borderLeft.x) {
					if (b.inertiaX < 0) {
						b.inertiaX *= -1;
					}
					if (!b.activated) {
						b.activate();
					}
				}

				if (b.x + b.width > borderRight.x) {
					if (b.inertiaX > 0) {
						b.inertiaX *= -1;
					}
					if (!b.activated) {
						b.activate();
					}
				}

				if (b.y < borderTop.y) {
					if (b.inertiaY < 0) {
						b.inertiaY *= -1;
					}
					if (!b.activated) {
						b.activate();
					}
				}

				if (b.y + b.height > borderBottom.y) {
					if (b.inertiaY > 0) {
						b.inertiaY *= -1;
					}
					if (!b.activated) {
						b.activate();
					}
				}

				if (b.activated
						&& checkCollision(ship.getBounds(), b.getBounds())) {
					ship.bolts.remove(b);
					ship.currentShots--;
					ship.pi.changeInterface();
				}

			}

			// COLISION DE LOS ITEMS EN LA PARED Y RECOGER LOS ITEMS
			for (int i2 = 0; i2 < items.size(); i2++) {
				AttributeChanger ac = (AttributeChanger) items.get(i2);

				if (ac.x < borderLeft.x) {
					if (ac.inertiaX < 0) {
						ac.inertiaX *= -1;
					}
				}

				if (ac.x + ac.width > borderRight.x) {
					if (ac.inertiaX > 0) {
						ac.inertiaX *= -1;
					}
				}

				if (ac.y < borderTop.y) {
					if (ac.inertiaY < 0) {
						ac.inertiaY *= -1;
					}
				}

				if (ac.y + ac.height > borderBottom.y) {
					if (ac.inertiaY > 0) {
						ac.inertiaY *= -1;
					}
				}

				if (checkCollision(ac.getBounds(), ship.getBounds())) {
					ac.pickup_catched(ship);
					items.remove(ac);
				}

			}

		}

	}

	private void checkInterShipsCollisions() {
		PlayerInterface p1 = (PlayerInterface) pi.get(0);
		PlayerInterface p2 = (PlayerInterface) pi.get(1);
		SpaceShip n1 = p1.ship;
		SpaceShip n2 = p2.ship;

		if (checkCollision(n1.getBounds(), n2.getBounds())) {
			double tmpr = n1.r;
			n1.r = n2.r;
			n2.r = tmpr;
			tmpr = n1.speed;
			n1.changeSpeed(n2.speed);
			n2.changeSpeed(tmpr);
		}
	}

	private void checkBoltsCollisions() {

		PlayerInterface p1 = (PlayerInterface) pi.get(0);
		PlayerInterface p2 = (PlayerInterface) pi.get(1);
		SpaceShip n1 = p1.ship;
		SpaceShip n2 = p2.ship;

		for (int i = 0; i < n1.bolts.size(); i++) {
			Bolt b = (Bolt) n1.bolts.get(i);
			if (checkCollision(b.getBounds(), n2.getBounds())) {
				n2.hitted(1, false);
				p1.score++;
				n1.bolts.remove(b);
				n1.currentShots--;
				p2.changeInterface();
			}
		}

		for (int i = 0; i < n2.bolts.size(); i++) {
			Bolt b = (Bolt) n2.bolts.get(i);
			if (checkCollision(b.getBounds(), n1.getBounds())) {
				n1.hitted(1, false);
				p2.score++;
				n2.bolts.remove(b);
				n2.currentShots--;
				p1.changeInterface();
			}
		}

	}

	private boolean checkCollision(Rectangle s1, Rectangle s2) {
		return (s1.x < s2.x + s2.width && s2.x < s1.x + s1.width
				&& s1.y < s2.y + s2.height && s2.y < s1.y + s1.height);
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {

		for (int i = 0; i < 2; i++) {
			PlayerInterface p = (PlayerInterface) pi.get(i);
			p.keyPressed(e.getKeyCode());
		}

		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				pause();
				break;
		}

	}

	public void keyReleased(KeyEvent e) {

		// System.out.println("Se ha pulsado la tecla: " + e.getKeyCode());
		for (int i = 0; i < 2; i++) {
			PlayerInterface p = (PlayerInterface) pi.get(i);
			p.keyReleased(e.getKeyCode());
		}

	}
	private double pauseTime = 0;

	public void pause() {
		if (gameLoop) {
			gameLoop = false;
			pauseTime = System.currentTimeMillis();
			// System.out.println("El juego se ha pausado.\n\t=>ItemsTime =
			// "+itemsTime);
		}
	}

	public int resume() {
		if (!gameLoop) {
			if (pauseTime > 0) {
				nextItemTime += (System.currentTimeMillis() - pauseTime);
				// System.out.println("El juego se ha reanudado.\n\t=>ItemsTime
				// = "+itemsTime);
			}
			return loop();
		} else {
			return 1;
		}
	}

	public void setBounds(Rectangle Bounds) {
		bounds = Bounds;
	}
}
