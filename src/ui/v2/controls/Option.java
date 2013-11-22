package ui.v2.controls;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.image.BufferedImage;

public interface Option {

	public void update();

	public BufferedImage getImage();

	public void setBounds(Point Location, int Align);

	public void setFont(Font f);

	public void setElegida(boolean b);

	public void setEnabled(boolean b);

	public void setBackgroundColor(Color c);

	public void setBorderColor(Color c);

	public void setTextColor(Color c);

	public void setBorderWidth(int s);

	public void setPadding(int p);

	public void setPaddingTop(int p);

	public void setPaddingLeft(int p);

	public void setPaddingRight(int p);

	public void setPaddingBottom(int p);

	public int getHeight();

	public int getWidth();
}