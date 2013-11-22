package util;

public class Keys {

	public int UP;

	public int DOWN;

	public int LEFT;

	public int RIGHT;

	public int SHOT;

	public int STATUS;

	public Keys() {
	}

	public Keys(int up, int down, int left, int right, int shot, int status) {
		setKeys(up, down, left, right, shot, status);
	}

	public void setKeys(int up, int down, int left, int right, int shot,
			int status) {
		UP = up;
		DOWN = down;
		LEFT = left;
		RIGHT = right;
		SHOT = shot;
		STATUS = status;
	}
}