package com.zep.pushit.fx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.zep.pushit.images.ImageLoader;
import com.zep.pushit.ui.Kare;

public class FxRmCentripetalLineByAxis implements FxRemove {

	private Vector2 speed;
	private boolean	animType; // remove: true, add: false

	public FxRmCentripetalLineByAxis() {
		animType = true;
		speed = new Vector2(-1, -1);
	}

	@Override
	public void draw(SpriteBatch sb, Kare kare, int i, int j, int dw, int dh, int x, int y) {
		sb.draw(ImageLoader.txtrRegBtn[kare.color()], x + i * dw + (dw - kare.width()) / 2, y + j * dh + (dh - kare.height()) / 2, kare.width(), kare.height());
	}

	@Override
	public void animate(Kare kare, int i, int j, Vector2 idx, int dw, int dh, AxisType axisType) {

		if (animType) {
			speed = new Vector2(-1, -1);
			remove(kare, i, j, idx, axisType);
		} else {
			speed = new Vector2(1, 1);
		}
	}

	public void animType(boolean remove) {
		this.animType = remove;
	}

	private void remove(Kare kare, int i, int j, Vector2 idx, AxisType animType) {
		if (animType.equals(AxisType.ROW))
			if (speed.x > 0 || (speed.x < 1 && kare.width() > 1))
				kare.setWidth(kare.width() + speed.x);

		if (animType.equals(AxisType.COL))
			if (speed.y > 0 || (speed.y < 1 && kare.height() > 1))
				kare.setHeight(kare.height() + speed.y);

		if (kare.width() < 2 || kare.height() < 2) {
			if (animType.equals(AxisType.ROW)) {
				idx.x = i;
			} else if (animType.equals(AxisType.COL)) {
				idx.y = j;
			}
		}
	}

}
