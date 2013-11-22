package gameobject;

import game.Defines;
import game.GameSettings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class FpsCounter {

	private long next_update;
	private BufferedImage img;

	private int x, y, w, h;

	public FpsCounter(Rectangle bounds) {
		this.x = bounds.x + 4;
		this.y = bounds.y + bounds.height - 20;
		this.w = bounds.width - 8;
		this.h = 16;
	}

	public void draw(Graphics2D g2d, double usedTime, String otherText) {
		if (usedTime <= 0)
			return;

		if (System.currentTimeMillis() >= next_update) {
			// Calculate the next iteration
			next_update = System.currentTimeMillis() + GameSettings.SHOW_FPS_DELAY;
			img = new BufferedImage(this.w, this.h, Transparency.BITMASK);
			Graphics2D tmpg = img.createGraphics();
			tmpg.setFont(Defines.defaultFont);
			//tmpg.setColor(Color.black);
			//tmpg.fillRect(0, 0, this.w - 1, this.h - 1);
			tmpg.setColor(Color.LIGHT_GRAY);
			//tmpg.drawRect(0, 0, this.w - 1, this.h - 1);
			tmpg.drawString(String.valueOf(Math.round(1000 / usedTime))
					+ " FPS, " + otherText, 5, 11);
		}
		g2d.drawImage(img, null, x, y);

	}
}
