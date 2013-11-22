
import game.Game;
import game.GameSettings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import ui.controls.UIOptions;
import ui.screens.UIInstructions;
import ui.screens.UIMain;

/**
 * Clase principal, simplemente incicia la ventana Main class of the Game, just
 * start a Frame
 *
 * @author Adael
 *
 */
public class EnergyBolt implements WindowListener, WindowFocusListener,
		WindowStateListener {

	/**
	 * Un bufferStrategy es una clase de java que se usa para realizar el double
	 * buffer de forma automatizada y controlada.
	 */
	private BufferStrategy strategy;
	private Game game;
	/**
	 * Los uso para establecer el tamaño que tendrá la ventana por dentro para
	 * establecer el area de juego
	 */
	private Rectangle insetBounds;
	/**
	 * Ventana donde se cargará el juego
	 */
	public JFrame frame;

	/**
	 * Inicia la aplicacion
	 *
	 * @param args
	 *            No los uso para nada
	 */
	public static void main(String args[]) {
		EnergyBolt game = new EnergyBolt();
		game.Init();
	}

	/**
	 * Constructor, Creo la ventana, y le pongo su titulo El panel que tiene
	 * dentro la ventana es donde dibujaré el juego
	 */
	public void Init() {

		frame = new JFrame();
		frame.setTitle(GameSettings.GAME_TITLE);

		frame.setBackground(Color.black);
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);

		// PANTALLA COMPLETA
		if (GameSettings.FULLSCREEN == true) {
			frame.setUndecorated(true);
			frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
					new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
					new Point(0, 0), ""));
			frame.setBounds(0, 0, GameSettings.WINDOW_WIDTH,
					GameSettings.WINDOW_HEIGHT);
			insetBounds = new Rectangle(0, 0, GameSettings.WINDOW_WIDTH,
					GameSettings.WINDOW_HEIGHT);

			GraphicsDevice gd = frame.getGraphicsConfiguration().getDevice();
			int bitDepth = gd.getDisplayMode().getBitDepth();
			try {
				// Activamos el modo a pantalla completa
				if (gd.isFullScreenSupported()) {
					gd.setFullScreenWindow(frame);

					if (gd.isDisplayChangeSupported()) {
						gd.setDisplayMode(new DisplayMode(
								GameSettings.WINDOW_WIDTH,
								GameSettings.WINDOW_HEIGHT, bitDepth,
								DisplayMode.REFRESH_RATE_UNKNOWN));
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
				System.out.println("ERROR CRITICO: " + e.getMessage());
			}
			// show the window / muestro la ventana
			frame.setVisible(true);

		} else {

			// In windowned mode / en modo ventana
			// Getting the screen size / Obtengo el tamaño de la pantalla
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int wx = Math.round((screenSize.width / 2)
					- (GameSettings.WINDOW_WIDTH / 2));
			int wy = Math.round((screenSize.height / 2)
					- (GameSettings.WINDOW_HEIGHT / 2));

			// set the window's position / Posiciono la Ventana
			frame.setBounds(wx, wy, GameSettings.WINDOW_WIDTH,
					GameSettings.WINDOW_HEIGHT);
			// show the window / muestro la ventana
			frame.setVisible(true);

			// System.out.println("INSETS: " + frame.getInsets().toString());
			// Stablish the inner window's size according the configuration
			// That's a REAL 640x480 Size Game (or other resolution)
			insetBounds = new Rectangle(frame.getInsets().left, frame.getInsets().top, GameSettings.WINDOW_WIDTH
					- frame.getInsets().right - frame.getInsets().left,
					GameSettings.WINDOW_HEIGHT - frame.getInsets().bottom
					- frame.getInsets().top);

			System.out.println(insetBounds.toString());
		}

		frame.requestFocus();

		frame.addWindowListener(this);
		frame.addWindowFocusListener(this);
		frame.addWindowStateListener(this);

		frame.createBufferStrategy(2);
		strategy = frame.getBufferStrategy();

		start();
	}

	/**
	 * Este metodo aunque es un poco confuso es el que me permite utilizar menus
	 */
	public void start() {
		boolean exit = false;
		int o = iniciarMenuPrincipal();
		while (!exit) {
			switch (o) {
				case 0: // Salir
					exit = true;
					break;
				case 1: // Menu Principal
					o = iniciarMenuPrincipal();
					break;
				case 2: // Instrucciones
					o = initUIInstructions();
					break;
				case 3: // Opciones
					break;
				case 100: // Nueva Partida
					newGame();
					o = resumeGame();
					break;
				case 101: // Continuar Partida
					if (game == null) {
						newGame();
					}
					o = resumeGame();
					break;
				case 102: // Terminar partida
					if (game != null) {
						game = null;
					}
					o = 1;
					break;
				case 201:
					o = initUIOptions();
					break;
				case 301: // Multiplayer Game
					o = initUIMultiplayerGame();
					break;
			}
		}
		System.exit(0);
	}

	/**
	 * Inicia el menu principal
	 *
	 * @return Devuelve el valor de la opcion elegida en este menu
	 */
	public int iniciarMenuPrincipal() {
		int exit;
		UIMain menu = new UIMain(strategy, insetBounds);

		menu.createOption("New Game", 100, true);
		menu.createOption("New Multiplayer Game", 301, true);

		if (game != null) {
			menu.createOption("Continue Game", 101, true);
			menu.createOption("Finish Game", 102, true);
			menu.chooseOption(1);
		}

		menu.createOption("Options", 201, false);
		menu.createOption("Help", 2, true);
		menu.createOption("Exit", 0, true);

		frame.addKeyListener(menu);
		exit = menu.loop();
		frame.removeKeyListener(menu);
		return exit;
	}

	public int initUIInstructions() {
		UIInstructions menu = new UIInstructions(strategy, insetBounds);
		frame.addKeyListener(menu);
		int salida = menu.loop();
		frame.removeKeyListener(menu);
		return salida;
	}

	public int initUIOptions() {
		UIOptions mo = new UIOptions(strategy, insetBounds);
		frame.addKeyListener(mo);
		int salida = mo.loop();
		frame.removeKeyListener(mo);
		return salida;
	}

	public int initUIMultiplayerGame() {
		return 0;
	}

	public void newGame() {
		game = new Game(strategy, insetBounds);
	}

	public int resumeGame() {
		int salida;
		frame.addKeyListener(game);
		salida = game.resume();
		frame.removeKeyListener(game);
		return salida;
	}

	public void exitGame() {
		game = null;
	}

	public void windowClosing(WindowEvent e) {
		System.out.println("Exiting");
		System.exit(0);
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
		// System.out.println("Ventana Activada");
	}

	public void windowDeactivated(WindowEvent e) {
		// System.out.println("Ventana Desactivada");
	}

	public void windowGainedFocus(WindowEvent e) {
		// System.out.println("Foco Ganado");
		frame.transferFocus();
	}

	public void windowLostFocus(WindowEvent e) {
		// System.out.println("Foco Perdido");
		if (game != null) {
			game.pause();
			// juego.setBounds(insetBounds);
		}
	}

	public void windowStateChanged(WindowEvent e) {
	}
}

/*
 * // By this way you can start the game in a canvas // DE ESTA FORMA SE INICIA
 * EL JUEGO EN UN CANVAS DENTRO DE UN PANEL JPanel jp = (JPanel)
 * this.getContentPane(); jp.setLayout(new BorderLayout()); // Creo el canvas de
 * juego y lo oculto gameCanvas = new Canvas(); jp.add(gameCanvas);
 * gameCanvas.setBounds(jp.getBounds()); gameCanvas.setBackground(Color.black);
 * insetBounds = gameCanvas.getBounds(); //
 * System.out.println(insetBounds.toString()); gameCanvas.setVisible(true);
 * gameCanvas.setIgnoreRepaint(true); // Doble buffer automatico
 * gameCanvas.createBufferStrategy(2); strategy =
 * gameCanvas.getBufferStrategy();
 */
