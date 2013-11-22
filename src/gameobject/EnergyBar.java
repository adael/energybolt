package gameobject;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * It's a progress bar.
 * 
 * @author Adael
 * 
 */
public class EnergyBar {

	private Rectangle bounds;

	private Rectangle lBounds;

	private int val;

	private Color color;

	private GradientPaint gr;

	public EnergyBar(Rectangle Bounds) {
		bounds = new Rectangle(Bounds);
		lBounds = new Rectangle(bounds.x + 1, bounds.y + 1, bounds.width - 1,
				bounds.height - 1);
		color = Color.gray;
	}

	public void setGradient(Color c1, Color c2) {
		Point2D p1 = new Point2D.Double(lBounds.x, lBounds.y);
		Point2D p2 = new Point2D.Double(lBounds.x + lBounds.width, lBounds.y);
		gr = new GradientPaint(p1, c1, p2, c2);
	}

	public void setColor(Color c) {
		color = c;
	}

	public void setValue(double val, double max) {
		if (val < 0)
			val *= -1;
		this.val = (int) Math.round(val * lBounds.width / max);
	}

	public void draw(Graphics2D g2d) {

		Color col = g2d.getColor();
		Paint p = g2d.getPaint();

		if (gr != null)
			g2d.setPaint(gr);
		else
			g2d.setColor(color);

		g2d.fillRect(lBounds.x, lBounds.y, val, lBounds.height);
		g2d.setColor(Color.white);
		g2d.draw(bounds);

		g2d.setColor(col);
		if (gr != null)
			g2d.setPaint(p);

	}

}