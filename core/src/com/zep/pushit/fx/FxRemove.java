package com.zep.pushit.fx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.zep.pushit.ui.Kare;

public interface FxRemove {

	public void draw(SpriteBatch sb, Kare kare, int i, int j, int dw, int dh, int x, int y);
	
	public void animate(Kare kare, int i, int j, Vector2 idx, int dw, int dh, AxisType animType);
	
	public void animType(boolean remove);
}
