package gameobject.pickups;

import game.Defines;
import game.GameSettings;
import gameobject.SpaceShip;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

public class AttributeChanger extends Pickup {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8950966954479217242L;
	private ShipAttributes itemType;

	public AttributeChanger(Rectangle gameBounds) {

		Random r = new Random();
		itemType = ShipAttributes.values()[r
				.nextInt(ShipAttributes.values().length)];

		this.initialize(gameBounds, getColor());
	}

	/*
	 * // INITIAL VALUES / VALORES INICIALES accelSpeed = 0.09; speed = 0;
	 * maxSpeed = 4; minSpeed = -3; brakeSpeed = 1.5; maxShots = 1; currentShots
	 * = 0; shotSpeed = 1.5; powerSpeed = 0.15; powerValue = 0; maxPowerValue =
	 * 5; rotateSpeed = 1; maxRotateSpeed = 8; minRotateSpeed = 3;
	 */

	@Override
	public void pickup_catched(SpaceShip s) {

		switch (itemType) {
		case ACCEL_SPEED:
			s.accelSpeed += (s.accelSpeed * 20 / 100);
			s.pi.showMessage("Accel Speed +20%", GameSettings.MESSAGE_TIME);
			break;
		case MAX_SPEED:
			s.maxSpeed += (s.maxSpeed * 5 / 100);
			s.pi.showMessage("Max Speed +5%", GameSettings.MESSAGE_TIME);
			break;
		case MIN_SPEED:
			s.maxReverseSpeed += (s.maxReverseSpeed * 5 / 100);
			s.pi.showMessage("Max Back Speed +5%", GameSettings.MESSAGE_TIME);
			break;
		case BRAKE_SPEED:
			s.brakeSpeed += (s.brakeSpeed * 10 / 100);
			s.pi.showMessage("Brake Speed +10%", GameSettings.MESSAGE_TIME);
			break;
		case MAX_SHOTS:
			s.maxShots += 1;
			s.pi.showMessage("Max Shots +1", GameSettings.MESSAGE_TIME);
			break;
		case SHOT_SPEED:
			s.shotSpeed += (s.shotSpeed * 5 / 100);
			s.pi.showMessage("Shot Speed +5%", GameSettings.MESSAGE_TIME);
			break;
		case POWER_SPEED:
			s.concentrateSpeed += (s.concentrateSpeed * 10 / 100);
			s.pi.showMessage("Concentrate Speed +10%",
					GameSettings.MESSAGE_TIME);
			break;
		case MAX_POWER_VALUE:
			s.maxPowerValue += (s.maxPowerValue * 10 / 100);
			s.pi.showMessage("Max Power +10%", GameSettings.MESSAGE_TIME);
			break;
		case ROTATE_SPEED:
			s.rotateSpeed += (s.rotateSpeed * 5 / 100);
			s.pi.showMessage("Rotate Speed +5%", GameSettings.MESSAGE_TIME);
			break;
		case MAX_ROTATE_SPEED:
			s.maxRotateSpeed += (s.maxRotateSpeed * 10 / 100);
			s.pi
					.showMessage("Max Rotate Speed +10%",
							GameSettings.MESSAGE_TIME);
			break;
		case SHIELD:
			s.shield += 1;
			s.pi.showMessage("Shield +1", GameSettings.MESSAGE_TIME);
			break;
		case ENERGY:
			s.energy += 1;
			s.pi.showMessage("Energy +1", GameSettings.MESSAGE_TIME);
			break;
		}
		s.pi.changeInterface();
		s.pi.changeStatus();
	}

	public enum ShipAttributes {
		ACCEL_SPEED, MAX_SPEED, MIN_SPEED, BRAKE_SPEED, MAX_SHOTS, SHOT_SPEED, POWER_SPEED, MAX_POWER_VALUE, ROTATE_SPEED, MAX_ROTATE_SPEED, MIN_ROTATE_SPEED, SHIELD, ENERGY
	}

	/*
	 * private String getName() { String s = null; switch (itemType) { case
	 * Defines.accelSpeed: s = "AS"; break; case Defines.maxSpeed: s = "MS";
	 * break; case Defines.minSpeed: s = "mS"; break; case Defines.brakeSpeed: s
	 * = "BS"; break; case Defines.maxShots: s = "MB"; break; case
	 * Defines.shotSpeed: s = "SS"; break; case Defines.powerSpeed: s = "PS";
	 * break; case Defines.maxPowerValue: s = "MP"; break; case
	 * Defines.rotateSpeed: s = "RS"; break; case Defines.maxRotateSpeed: s =
	 * "MR"; break; case Defines.minRotateSpeed: s = "mR"; break; } return s; }
	 */

	private Color getColor() {
		Color c = Color.black;
		switch (itemType) {
		case ACCEL_SPEED:
			c = Color.blue;
			break;
		case MAX_SPEED:
			c = Color.cyan;
			break;
		case MIN_SPEED:
			c = Color.red;
			break;
		case BRAKE_SPEED:
			c = Color.orange;
			break;
		case MAX_SHOTS:
			c = Color.white;
			break;
		case SHOT_SPEED:
			c = Color.gray;
			break;
		case POWER_SPEED:
			c = Color.yellow;
			break;
		case MAX_POWER_VALUE:
			c = Color.pink;
			break;
		case ROTATE_SPEED:
			c = Color.lightGray;
			break;
		case MAX_ROTATE_SPEED:
			c = Color.green;
			break;
		case MIN_ROTATE_SPEED:
			c = Color.magenta;
			break;
		case SHIELD:
			c = Defines.lightBlue;
			break;
		case ENERGY:
			c = Defines.lightGreen;
			break;
		default:
			System.out.println("Unknow itemType:" + itemType.toString());
			break;
		}
		return c;
	}

	public ShipAttributes getItemType() {
		return itemType;
	}

}