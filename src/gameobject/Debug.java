package gameobject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

/**
 * This class display some debug information while pressing the assigned key.
 * 
 * @author Adael
 * 
 */
public class Debug {

	Vector<String> t;

	Vector<String> v;

	Rectangle bounds;

	int height = 14;

	int width;

	Color color;

	public Debug(Rectangle Bounds) {
		bounds = new Rectangle(Bounds);
		t = new Vector<String>();
		v = new Vector<String>();
		width = Math.round(bounds.width / 2);
		color = Color.gray;
	}

	public void setColor(Color c) {
		color = c;
	}

	public void setColsByPercent(int c1) {
		width = Math.round(bounds.width * c1 / 100);
	}

	public void setColsBySize(int c1) {
		width = c1;
	}

	public void add(String Text, int Value) {
		t.add(Text);
		v.add(String.valueOf(Value));
	}

	public void add(String Text, long Value) {
		t.add(Text);
		v.add(String.valueOf(Value));
	}

	public void add(String Text, float Value) {
		t.add(Text);
		v.add(String.valueOf(Value));
	}

	public void add(String Text, float Value, int Decimales) {
		double d = 1;
		if (Decimales > 0)
			for (int i = 0; i < Decimales; i++)
				d *= 10;

		t.add(Text);
		v.add(String.valueOf((Math.round(Value * d) / d)));
	}

	public void add(String Text, double Value) {
		t.add(Text);
		v.add(String.valueOf(Value));
	}

	public void add(String Text, double Value, int Decimales) {
		double d = 1;
		if (Decimales > 0)
			for (int i = 0; i < Decimales; i++)
				d *= 10;

		t.add(Text);
		v.add(String.valueOf((Math.round(Value * d) / d)));
	}

	public void add(String Text, boolean Value) {
		t.add(Text);
		v.add(String.valueOf(Value));
	}

	public void add(String Text, String Value) {
		t.add(Text);
		v.add(Value);
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.draw(bounds);
		Rectangle r = g2d.getClipBounds();

		int tsy = bounds.y + 13;
		// int tsx = bounds.x + 5;

		int i;

		for (i = 0; i < t.size(); i++) {
			g2d.setClip(bounds);
			g2d.drawLine(bounds.x, tsy + (i * height), bounds.x + bounds.width,
					tsy + (i * height));
			g2d.drawLine(bounds.x + width, tsy + (i * height) - height + 1,
					bounds.x + width, tsy + (i * height));
			g2d.setClip(bounds.x, bounds.y, width, bounds.height);
			g2d.drawString(String.valueOf(t.get(i)), bounds.x + 2, tsy
					+ (i * height) - 2);
			g2d.setClip(bounds.x + width, bounds.y, bounds.width - width,
					bounds.height);
			g2d.drawString(String.valueOf(v.get(i)), bounds.x + width + 2, tsy
					+ (i * height) - 2);
		}
		g2d.setClip(r);
	}

	public void reset() {
		t.clear();
		v.clear();
	}
}
