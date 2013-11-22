package ui.v2.controls;

/*
 package uicontrols;

 import java.awt.Color;
 import java.awt.Font;
 import java.awt.FontMetrics;
 import java.awt.Graphics2D;
 import java.awt.Point;
 import java.awt.Transparency;
 import java.awt.event.KeyEvent;
 import java.awt.event.KeyListener;
 import java.awt.geom.RoundRectangle2D;
 import java.awt.image.BufferedImage;


 import util.Defines;


 public class mInputBox implements Opcion, KeyListener {

 private String text;

 private BufferedImage img;

 private Font font;

 private Point bounds;

 private Color bgColor;

 private Color fgColor;

 private Color txColor;

 public mInputBox() {
 bounds = new Point(0, 0);
 font = new Font("Arial", Font.PLAIN, 10);
 text = "";
 bgColor = Color.black;
 fgColor = Defines.GrisClarito;
 txColor = Defines.GrisClarito;
 actualizar();
 }

 public void keyTyped(KeyEvent e) {

 }

 public void keyPressed(KeyEvent e) {
 text += e.getKeyChar();
 actualizar();
 }

 public void keyReleased(KeyEvent e) {

 }

 private void actualizar() {
 img = new BufferedImage(bounds.width, bounds.height,
 Transparency.BITMASK);
 Graphics2D g2d = (Graphics2D) img.getGraphics();
 RoundRectangle2D.Double r = new RoundRectangle2D.Double(0, 0,
 bounds.width - 1, bounds.height - 1, 15, 15);

 // Dibujo el Fondo
 g2d.setColor(bgColor);
 g2d.fill(r);

 // Dibujo el borde
 g2d.setColor(fgColor);
 g2d.draw(r);

 // Dibujo el texto
 g2d.setColor(txColor);
 g2d.setFont(font);
 FontMetrics fm = g2d.getFontMetrics();
 int fh = fm.getHeight();
 g2d.drawString(text, 2, fm.getMaxAscent());

 }

 public void dibujar(Graphics2D g2d) {
 g2d.drawImage(img, bounds.x, bounds.y, null);
 }

 public void setElegida(boolean b) {

 }

 public BufferedImage getImage() {
 return img;
 }

 public void refresh() {
 actualizar();
 }

 public void setFont(Font f) {
 font = f;
 }

 public String getValue() {
 return text;
 }

 public void setValue(String s) {
 text = s;
 }

 public void setBackgroundColor(Color c) {
 bgColor = c;
 }

 public void setBorderColor(Color c) {
 fgColor = c;
 }

 public void setTextColor(Color c) {
 txColor = c;
 }

 }
 */