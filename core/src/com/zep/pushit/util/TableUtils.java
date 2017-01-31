package com.zep.pushit.util;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.zep.pushit.images.ImageLoader;

public class TableUtils {

	public Table createMainTable(Table tableButton, String title) {
		ImageTextButton btnTitle = new ImageTextButton(title, SkinLoader.skin, "headDark");

		Image imgTopLeftUp = new Image(ImageLoader.txtrContainerTop[0]);
		Image imgTopLeftDown = new Image(ImageLoader.txtrContainerTop[1]);
		Image imgTopRightUp = new Image(ImageLoader.txtrContainerTop[2]);
		Image imgTopRightDown = new Image(ImageLoader.txtrContainerTop[3]);
		Image imgBackgrd = new Image(ImageLoader.txtrContainerTop[4]);

		Image imgBottomCenter = new Image(ImageLoader.txtrContainerBottom[0]);
		Image imgBottomLeft = new Image(ImageLoader.txtrContainerBottom[1]);
		Image imgBottomRight = new Image(ImageLoader.txtrContainerBottom[2]);
		Image imgCenterLeft = new Image(ImageLoader.txtrContainerBottom[3]);
		Image imgCenterRight = new Image(ImageLoader.txtrContainerBottom[4]);

		Table table = new Table(SkinLoader.skin);
		table.setBackground(new TextureRegionDrawable(ImageLoader.txtrContainerTop[4]));
		table.setFillParent(true);
		table.row().bottom();
		table.add(imgTopLeftUp).right();
		table.add(btnTitle).center().fillX();
		table.add(imgTopRightUp).left();
		table.row();
		table.add(imgTopLeftDown).right();
		table.add(imgBackgrd).fill();
		table.add(imgTopRightDown).left();
		table.row();
		table.add(imgCenterLeft).right().fillY();
		table.add(tableButton).fillX();
		table.add(imgCenterRight).left().fillY();
		table.row();
		table.add(imgBottomLeft).right();
		table.add(imgBottomCenter).fill();
		table.add(imgBottomRight).left();

		table.debug();

		return table;
	}

	public Table createMainTableByButton(Table tableButton, String title, ImageButton... button) {
		ImageTextButton btnTitle = new ImageTextButton(title, SkinLoader.skin, "headDark");

		Image imgTopLeftUp = new Image(ImageLoader.txtrContainerTop[0]);
		Image imgTopLeftDown = new Image(ImageLoader.txtrContainerTop[1]);
		Image imgTopRightUp = new Image(ImageLoader.txtrContainerTop[2]);
		Image imgTopRightDown = new Image(ImageLoader.txtrContainerTop[3]);
		Image imgBackgrd = new Image(ImageLoader.txtrContainerTop[4]);

//		Image imgBottomCenter = new Image(SkinLoader.skin.getRegion("window.bottom"));
//		Image imgCenterLeft = new Image(SkinLoader.skin.getRegion("window.center.left"));
//		Image imgCenterRight = new Image(SkinLoader.skin.getRegion("window.center.right"));

		Table table = new Table(SkinLoader.skin);
		table.setBackground(new TextureRegionDrawable(SkinLoader.skin.getRegion("container.center")));
		table.setFillParent(true);
		table.row().bottom();
		table.add(imgTopLeftUp).right();
		table.add(btnTitle).center().colspan(3).fillX();
		table.add(imgTopRightUp).left();
		table.row();
		table.add(imgTopLeftDown).right();
		table.add(imgBackgrd).colspan(3).fill();
		table.add(imgTopRightDown).left();
		table.row();
		table.add(new Image(SkinLoader.skin.getRegion("window.center.left"))).right().fillY();
		table.add(tableButton).colspan(3).fillX();
		table.add(new Image(SkinLoader.skin.getRegion("window.center.right"))).left().fillY();
		table.row();
		if (button.length == 1) {
			table.add(new Image(SkinLoader.skin.getRegion("window.left.bottom"))).right();
			table.add(new Image(SkinLoader.skin.getRegion("window.bottom"))).right().fillX().expandX();
			table.add(button[0]);
			table.add(new Image(SkinLoader.skin.getRegion("window.bottom"))).left().fillX().expandX();
			table.add(new Image(SkinLoader.skin.getRegion("window.right.bottom"))).left();
		} else if (button.length == 2) {
			table.add(button[0]).right();
			table.add(new Image(SkinLoader.skin.getRegion("window.bottom"))).colspan(3).fillX();
			table.add(button[1]).left();
		} else if (button.length == 3){
			table.add(button[0]).right();
			table.add(new Image(SkinLoader.skin.getRegion("window.bottom"))).right().fillX().expandX();
			table.add(button[1]);
			table.add(new Image(SkinLoader.skin.getRegion("window.bottom"))).right().fillX().expandX();
			table.add(button[2]).left();
		}

//		table.debug();

		return table;
	}

}
