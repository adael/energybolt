package ui.screens;

import game.Defines;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

// TODO: Voy por aquí.

public class UIOptionValue {

	public int width = 0;

	public int height = 0;

	private String text;

	private int value;

	private boolean enabled;

	private boolean elegida;

	private Rectangle bounds;

	private Font font;
	// TODO: BACKGROUND COLOR
	// private Color bgColor;
	// TODO: FORECOLOR
	// private Color fgColor;
	// TODO: TEXT COLOR
	// private Color txtColor;

	private FontMetrics fm;

	@SuppressWarnings("deprecation")
	public UIOptionValue(String Text, int Value, Font f, boolean Enabled) {
		text = Text;
		value = Value;
		enabled = Enabled;
		font = f;
		elegida = false;
		fm = java.awt.Toolkit.getDefaultToolkit().getFontMetrics(font);
		height = fm.getHeight();
		width = fm.stringWidth(text);
	}

	public void draw(Graphics2D g2d) {

		if (elegida) {
			g2d.setColor(Defines.blanco50);
			g2d.fill(bounds);
			g2d.setColor(Color.yellow);
			g2d.draw(bounds);
			g2d.setColor(enabled ? Color.yellow : Color.gray);
		} else {
			if (enabled)
				g2d.setColor(Color.white);
			else
				g2d.setColor(Color.gray);
		}

		Font tmpfont = g2d.getFont();
		g2d.setFont(font);

		g2d.drawString(text, bounds.x + (bounds.width / 2) - (width / 2),
				bounds.y + fm.getMaxAscent());

		g2d.setFont(tmpfont);
	}

	public String getText() {
		return text;
	}

	public int getValue() {
		return value;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setText(String s) {
		text = s;
	}

	public void setValue(int v) {
		value = v;
	}

	public void setChoosed(boolean b) {
		elegida = b;
	}

	public void setEnabled(boolean b) {
		enabled = b;
	}

	public void setBounds(Rectangle b) {
		bounds = new Rectangle(b);
	}

	public void setFont(Font f) {
		font = f;
	}

}