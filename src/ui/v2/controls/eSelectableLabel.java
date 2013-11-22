package ui.v2.controls;

/*
 package uicontrols;

 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Point;
 import java.awt.image.BufferedImage;


 import util.Defines;


 public class eSelectableLabel implements Opcion {

 private String text = "";

 private int value = 0;

 private BufferedImage img;

 private Font font = new Font("Arial", Font.PLAIN, 10);

 private Point location = new Point(0, 0);

 private int align = ALIGN_TOP_LEFT;

 private boolean elegida = false;

 private boolean enabled = true;

 private Color bgColor = Color.black;

 private Color fgColor = Color.white;

 private Color txColor = Color.white;

 private int borderWidth = 1;

 private int paddingLeft = 0;

 private int paddingRight = 0;

 private int paddingTop = 0;

 private int paddingBottom = 0;

 public eSelectableLabel(String Text, int Value) {
 text = Text;
 value = Value;
 }

 public void update() {

 int sh = fm.getHeight();
 int sw = fm.stringWidth(text);

 if (elegida) {
 g2d.setColor(Defines.blanco50);
 g2d.fill(bounds);
 g2d.setColor(Color.yellow);
 g2d.draw(bounds);
 if (enabled)
 g2d.setColor(Color.yellow);
 else
 g2d.setColor(Color.gray);

 } else {
 if (enabled)
 g2d.setColor(Color.white);
 else
 g2d.setColor(Color.gray);
 }

 g2d.drawString(text, bounds.x + 2, bounds.y + fm.getMaxAscent());

 }

 public BufferedImage getImage() {
 return img;
 }

 public void setBounds(Point Location, int Align) {
 location = new Point(Location);
 align = Align;
 }

 public void setFont(Font f) {
 font = new Font(f.getFontName(), f.getStyle(), f.getSize());
 }

 public void setElegida(boolean b) {
 elegida = b;
 }

 public void setEnabled(boolean b) {
 enabled = b;
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

 public int getHeight() {

 }

 public void setBorderWidth(int s) {
 borderWidth = s;
 }

 public void setPadding(int p) {
 paddingLeft = p;
 paddingRight = p;
 paddingTop = p;
 paddingBottom = p;
 }

 public void setPaddingTop(int p) {
 paddingTop = p;
 }

 public void setPaddingLeft(int p) {
 paddingLeft = p;
 }

 public void setPaddingRight(int p) {
 paddingRight = p;
 }

 public void setPaddingBottom(int p) {
 paddingBottom = p;
 }
 }
 */