package com.zep.pushit.ui;

public class Kare {

	private Tahta	tahta;
	private float	width, height;	// boyutlar
	private int		color;			// renk no
	private boolean	active;			// kontrol edilebilir
	private boolean	visible;		// gorulebilen
	private boolean	moveable;		// hareket edebilir
	private boolean	markForAnim;	// animasyon icin isaretlenmis

	public Kare(Tahta tahta, int width, int height, int color, boolean visible) {
		this.tahta = tahta;
		this.width = width;
		this.height = height;
		this.color = color;
		this.visible = visible;
		active = false;
		moveable = true;
		markForAnim = false;

	}

	public Kare(Tahta tahta, int width, int height, int color, boolean visible, boolean markForAnim) {
		this.tahta = tahta;
		this.width = width;
		this.height = height;
		this.color = color;
		this.visible = visible;
		this.markForAnim = markForAnim;
		active = false;
		moveable = true;

	}

//	public Kare(Tahta tahta, int width, int height, int color, boolean visible, boolean moveable) {
//		super();
//		this.tahta = tahta;
//		this.width = width;
//		this.height = height;
//		this.color = color;
//		this.visible = visible;
//		this.moveable = moveable;
//		markForAnim = false;
//	}

	public float width() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float height() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public boolean isNotMoveable() {
		return !moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public int color() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isMarkForAnim() {
		return markForAnim;
	}

	public void setMarkForAnim(boolean markForAnim) {
		if (active && !markForAnim)
			return;

		this.markForAnim = markForAnim;
	}

	public boolean moveRight(int i, int j, int color, boolean visibility) {

		if (isNotMoveable()) {
			return false;
		}

		boolean active = this.active;
		this.active = false;
		if (visible) {
			if (i + 1 < tahta.getKareRowLen() && tahta.getKare()[i + 1][j] != null) { // bir sonraki kare visible ise onun bilgilerini al ve bu kareyi gorunmez yap
				if (tahta.getKare()[i + 1][j].moveRight(i + 1, j, this.color, true) == false) {
					this.active = active;
					return false;
				}
				this.color = color;
				this.visible = visibility;
			} else {
				this.active = true;
			}
		} else {
			this.color = color;
			this.active = true;
			this.visible = true;
		}
		
		return true;
	}

	public boolean moveLeft(int i, int j, int color, boolean visibility) {

		if (isNotMoveable()) {
			return false;
		}

		boolean active = this.active;
		this.active = false;
		if (visible) {
			if (i > 0 && tahta.getKare()[i - 1][j] != null) { // bir onceki kare visible ise onun bilgilerini al ve bu kareyi gorunmez yap
				if (tahta.getKare()[i - 1][j].moveLeft(i - 1, j, this.color, true) == false) {
					this.active = active;
					return false;
				}
				this.color = color;
				this.visible = visibility;
			} else {
				this.active = true;
			}
		} else {
			this.color = color;
			this.active = true;
			this.visible = true;
		}
		
		return true;
	}

	public boolean moveUp(int i, int j, int color, boolean visibility) {

		if (isNotMoveable()) {
			return false;
		}

		boolean active = this.active;
		this.active = false;
		if (visible) {
			if (j > 0 && tahta.getKare()[i][j - 1] != null) { // bir onceki kare visible ise onun bilgilerini al ve bu ekrani gorunmez yap
				if (tahta.getKare()[i][j - 1].moveUp(i, j - 1, this.color, true) == false) {
					this.active = active;
					return false;
				}
				this.color = color;
				this.visible = visibility;
			} else {
				this.active = true;
			}
		} else {
			this.color = color;
			this.active = true;
			this.visible = true;
		}
		
		return true;
	}

	public boolean moveDown(int i, int j, int color, boolean visibility) {

		if (isNotMoveable()) {
			return false;
		}
		
		boolean active = this.active;
		this.active = false;
		if (visible) {
			if (j + 1 < tahta.getKareColLen() && tahta.getKare()[i][j + 1] != null) { // bir onceki kare visible ise onun bilgilerini al ve bu ekrani gorunmez yap
				if (tahta.getKare()[i][j + 1].moveDown(i, j + 1, this.color, true) == false) {
					this.active = active;
					return false;
				}
				this.color = color;
				this.visible = visibility;
			} else {
				this.active = true;
			}
		} else {
			this.color = color;
			this.active = true;
			this.visible = true;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "Kare [color=" + color + ", visible=" + visible + ", active=" + active + ", width=" + width + ", height=" + height + ", moveable=" + moveable
				+ "]";
	}

}
