package game;

/**
 * A class to join game settings
 *
 * @author Carlos
 */
public class GameSettings {

	/**
	 * Game title
	 */
	public static final String GAME_TITLE = "EnergyBolt 1.0 (J2SE Version)";
	/**
	 * Sets the game speed
	 * default: 10
	 */
	public static final int GAME_SPEED = 10;
	/**
	 * Max items on screen
	 * default: 10
	 */
	public static final int MAX_ITEMS_ON_SCREEN = 10; // 10
	/**
	 * default: 5
	 */
	public static final int FIRST_NEXT_ITEM_TIME = 5; // 5
	/**
	 * default: 60
	 */
	public static final int IN_GAME_NEXT_ITEM_TIME = 60; // 60
	/**
	 * default: 8
	 */
	public static final double BOLT_RADIUS = 8;
	/**
	 * 1 = BOUNCE
	 * 2 = TRANSPOSE
	 * default: 1
	 */
	public static final int TYPE_OF_WALL = 1;
	/**
	 * Message timeout
	 * default: 3
	 */
	public static final int MESSAGE_TIME = 3;
	// Graphics Options
	/**
	 * default: true
	 */
	public static boolean FULLSCREEN = false; // true
	/**
	 * default: true
	 */
	public static boolean USE_ANTIALIASING = true; // true
	/**
	 * default true
	 */
	public static boolean SHOW_BACKGROUND = true; // true
	/**
	 * default true
	 */
	public static boolean QUALITY_BOLTS = true; // true
	/**
	 * default true
	 */
	public static boolean QUALITY_ITEMS = true; // true
	/**
	 * default true
	 */
	public static boolean SHOW_FPS = false; // false
	public static int SHOW_FPS_DELAY = 500; // 2000
	// Screen Resolution / Window Size
	/**
	 * default: 800
	 */
	public static final int WINDOW_WIDTH = 1024;
	/**
	 * default: 600
	 */
	public static final int WINDOW_HEIGHT = 768;
	// Players data
	/**
	 * default: "Player 1"
	 */
	public static String DEFAULT_PLAYER1_NAME = "Player 1";
	/**
	 * default: "Player 2"
	 */
	public static String DEFAULT_PLAYER2_NAME = "Player 2";
}