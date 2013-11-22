// Updated: Pickup
/*
 package gameobject;

 import game.Defines;
 import game.GameSettings;

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

 public class Item extends Ellipse2D.Double {

 public double inertiaX = 0;

 public double inertiaY = 0;

 private int stat;

 @SuppressWarnings("unused")
 private double value;

 private String sn;

 private Color sc;

 private BufferedImage img;

 public Item(int Stat, Rectangle gameBounds) {
 Random rnd = new Random();

 x = gameBounds.x + rnd.nextInt(gameBounds.width);
 y = gameBounds.y + rnd.nextInt(gameBounds.height);

 inertiaX = (rnd.nextDouble() * 9) + 1;
 inertiaY = (rnd.nextDouble() * 9) + 1;

 width = 8.0;
 height = 8.0;
 stat = Stat;
 sn = getName();
 sc = getColor();
 value = getValue();

 if (GameSettings.QUALITY_ITEMS) {
 img = new BufferedImage((int) width + 1, (int) height + 1,
 Transparency.BITMASK);
 } else {
 // Muy baja calidad, pero mejor rendimiento
 img = ImageCache.createCompatible((int) width + 1,
 (int) height + 1, Transparency.BITMASK);
 }

 Graphics2D g2d = (Graphics2D) img.getGraphics();

 double centro = width / 2;

 Ellipse2D.Double c2 = new Ellipse2D.Double(centro - (width / 4), centro
 - (height / 4), width / 2, height / 2);
 Ellipse2D.Double c = new Ellipse2D.Double(0, 0, width, height);
 g2d.setColor(Color.black);
 // g2d.fill(c);
 // g2d.setFont(new Font("Arial", Font.PLAIN, 7));
 g2d.setColor(sc);

 if (GameSettings.USE_ANTIALIASING)
 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
 RenderingHints.VALUE_ANTIALIAS_ON);

 g2d.draw(c);
 g2d.fill(c2);

 // g2d.drawString(sn, 3.0f, radio+2.5f);

 if (GameSettings.USE_ANTIALIASING)
 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
 RenderingHints.VALUE_ANTIALIAS_OFF);

 }

 public void update() {
 x += inertiaX;
 y += inertiaY;
 }

 public void draw(Graphics2D g2d) {
 AffineTransform at = new AffineTransform();
 at.translate(x, y);
 // at.rotate(Math.toRadians(1));
 g2d.drawImage(img, at, null);
 }

 private String getName() {
 String s = null;
 switch (stat) {
 case Defines.accelSpeed:
 s = "AS";
 break;
 case Defines.maxSpeed:
 s = "MS";
 break;
 case Defines.minSpeed:
 s = "mS";
 break;
 case Defines.brakeSpeed:
 s = "BS";
 break;
 case Defines.maxShots:
 s = "MB";
 break;
 case Defines.shotSpeed:
 s = "SS";
 break;
 case Defines.powerSpeed:
 s = "PS";
 break;
 case Defines.maxPowerValue:
 s = "MP";
 break;
 case Defines.rotateSpeed:
 s = "RS";
 break;
 case Defines.maxRotateSpeed:
 s = "MR";
 break;
 case Defines.minRotateSpeed:
 s = "mR";
 break;
 }
 return s;
 }

 private Color getColor() {
 Color c = Color.black;
 switch (stat) {
 case Defines.accelSpeed:
 c = Color.blue;
 break;
 case Defines.maxSpeed:
 c = Color.cyan;
 break;
 case Defines.minSpeed:
 c = Color.red;
 break;
 case Defines.brakeSpeed:
 c = Color.orange;
 break;
 case Defines.maxShots:
 c = Color.white;
 break;
 case Defines.shotSpeed:
 c = Color.gray;
 break;
 case Defines.powerSpeed:
 c = Color.yellow;
 break;
 case Defines.maxPowerValue:
 c = Color.pink;
 break;
 case Defines.rotateSpeed:
 c = Color.lightGray;
 break;
 case Defines.maxRotateSpeed:
 c = Color.green;
 break;
 case Defines.minRotateSpeed:
 c = Defines.darkGreen;
 break;
 }
 return c;
 }

 public double getValue() {
 double d = 0;
 switch (stat) {
 case Defines.accelSpeed:
 d = 0.02;
 break;
 case Defines.maxSpeed:
 d = 0.1;
 break;
 case Defines.minSpeed:
 d = -0.1;
 break;
 case Defines.brakeSpeed:
 d = 0.1;
 break;
 case Defines.maxShots:
 d = 1.0;
 break;
 case Defines.shotSpeed:
 d = 0.05;
 break;
 case Defines.powerSpeed:
 d = 0.05;
 break;
 case Defines.maxPowerValue:
 d = 0.2;
 break;
 case Defines.rotateSpeed:
 d = 0.05;
 break;
 case Defines.maxRotateSpeed:
 d = 0.2;
 break;
 case Defines.minRotateSpeed:
 d = 0.1;
 break;
 }
 return d;
 }

 public int getItemType() {
 return stat;
 }

 /*
 * // INITIAL VALUES / VALORES INICIALES accelSpeed = 0.09; speed = 0;
 * maxSpeed = 4; minSpeed = -3; brakeSpeed = 1.5; maxShots = 1; currentShots =
 * 0; shotSpeed = 1.5; powerSpeed = 0.15; powerValue = 0; maxPowerValue = 5;
 * rotateSpeed = 1; maxRotateSpeed = 8; minRotateSpeed = 3;
 */
/*
 }
 */