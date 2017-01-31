package com.zep.pushit.ui;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.zep.pushit.fx.AxisType;
import com.zep.pushit.fx.FxRemove;
import com.zep.pushit.fx.FxRmCentripetalDot;
import com.zep.pushit.images.ImageLoader;
import com.zep.pushit.object.Direction;
import com.zep.pushit.sounds.MusicLoader;
import com.zep.pushit.states.MenuState;
import com.zep.pushit.states.PlayState;
import com.zep.pushit.util.Constant;
import com.zep.pushit.util.SkinLoader;

public class Tahta {

	private PlayState		state;
	private Kare[][]		kare;
	private Pattern[][]		pattern;
	private int				kareDW, kareDH;	// default boyutlar (update asamasinda animasyon icin kullanilacak)
	private int				x, y;			// x - y koordinatlari
//	private int				px, py;			// pattern için x - y koordinatlari
	private int				row, col;		// satir - sutun sayisi
	public static final int	CN	= 5;		// renk miktari

	private FxRemove		fx;
	private AxisType		animType;
	private Direction		direction;

	private boolean			print;

	public Tahta(PlayState state, int kareDW, int row, int col) {
		super();

		this.state = state;

		this.kareDW = kareDW;
		kareDH = kareDW;

		this.row = row;
		this.col = col;

		kare = new Kare[row][col];
		pattern = new Pattern[row][col];

		initKare(kareDW, kareDH);

		direction = Direction.NOTHING;
		fx = new FxRmCentripetalDot(); // ForeLineByAxis(); // CentripetalLineByAxis();

//		System.out.println("Score: " + state.getScore().getScore());
//		System.out.println("HighScore: " + state.getScore().getHighScore());
		print();
	}

	private void initKare(int width, int height) {
		int color;
		boolean visible;
		for (int i = 0; i < kare.length; i++) {
			for (int j = 0; j < kare[i].length; j++) {
				visible = !(i == 0 || i == kare.length - 1 || j == 0 || j == kare[i].length - 1);
				color = (i == 0 || i == kare.length - 1 || j == 0 || j == kare[i].length - 1) ? 0 : (int) new Random().nextInt(CN);
				kare[i][j] = new Kare(this, width, height, color, visible);
				pattern[i][j] = new Pattern(width + Constant.SHAPE_GAP, height + Constant.SHAPE_GAP, (i + j) % 2);
			}
		}

		kare[0][0] = new Kare(this, width, height, (int) new Random().nextInt(CN), true);
		kare[0][0].setActive(true);
//		kare[1][1].setMoveable(false);
	}

	public void render(SpriteBatch sb) {
		sb.begin();

		drawGroundPattern(sb, 0, 0);

		x = ((Gdx.graphics.getWidth() - (kareDW + Constant.SHAPE_GAP) * getKareRowLen()) / 2);
		y = (2 * Gdx.graphics.getHeight() / 3) - (kareDH * getKareColLen() / 2);

//		drawTopAndBottomCornerWall(sb);

		for (int i = 0; i < kare.length; i++) {
//			drawTopAndBottomRows(sb, i);

			for (int j = 0; j < kare[i].length; j++) {
//				drawLeftAndRightColumns(sb, j);
//				drawGroundPattern(sb, i, j);

				if (kare[i][j] != null && kare[i][j].isVisible()) {
					fx.draw(sb, kare[i][j], i, j, kareDW + Constant.SHAPE_GAP, kareDH + Constant.SHAPE_GAP, x, y);
				}
			}

		}
		
		sb.end();
	}

	private void drawGroundPattern(SpriteBatch sb, int i, int j) {
		if ("1".equals("1"))
			return;

		int x = (Gdx.graphics.getWidth() - (kareDW + Constant.SHAPE_GAP) * row) / 2;
		int y = (2 * Gdx.graphics.getHeight() / 3) - (kareDH * col / 2);
		// patern
		sb.draw(ImageLoader.txtrContainerTop[4], x, y, row * (kareDW + Constant.SHAPE_GAP), col * (kareDW + Constant.SHAPE_GAP));
		
//		TextureRegion region = SkinLoader.skin.getRegion("container.top.left.up");
//		region.flip(false, true);
//		sb.draw(region, x-kareDW, y-kareDH);
	}

	private void drawLeftAndRightColumns(SpriteBatch sb, int j) {
		if ("1".equals("1"))
			return;

		// sol ve sag sutun
		sb.draw(ImageLoader.txrgBorder[6], x - pattern[0][0].width(), y + j * pattern[0][0].height(), pattern[0][0].width(), pattern[0][0].height());
		sb.draw(ImageLoader.txrgBorder[7], x + kare.length * pattern[0][0].width(), y + j * pattern[0][0].height(), pattern[0][0].width(),
				pattern[0][0].height());
	}

	private void drawTopAndBottomRows(SpriteBatch sb, int i) {
//		if ("1".equals("1"))
//			return;

		// ust ve alt satirlar
		sb.draw(ImageLoader.txrgBorder[4], x + i * pattern[0][0].width(), y - pattern[0][0].height(), pattern[0][0].width(), pattern[0][0].height());
		sb.draw(ImageLoader.txrgBorder[5], x + i * pattern[0][0].width(), y + kare[0].length * pattern[0][0].height(), pattern[0][0].width(),
				pattern[0][0].height());
	}

	private void drawTopAndBottomCornerWall(SpriteBatch sb) {
//		if ("1".equals("1"))
//			return;
		int x = (Gdx.graphics.getWidth() - (kareDW + Constant.SHAPE_GAP) * row) / 2;
		int y = (2 * Gdx.graphics.getHeight() / 3) - (kareDH * getKareColLen() / 2);
		
		sb.draw(SkinLoader.skin.getRegion("window.center.left"), x - pattern[0][0].width(), y - pattern[0][0].height(), pattern[0][0].width(), pattern[0][0].height());
		// ust ve alt sol kenarlar
//		sb.draw(ImageLoader.txrgBorder[2], x - pattern[0][0].width(), y - pattern[0][0].height(), pattern[0][0].width(), pattern[0][0].height());
		sb.draw(ImageLoader.txrgBorder[0], x - pattern[0][0].width(), y + kare[0].length * pattern[0][0].height(), pattern[0][0].width(),
				pattern[0][0].height());

		// ust ve alt sag kenarlar
		sb.draw(ImageLoader.txrgBorder[3], x + kare.length * pattern[0][0].width(), y - pattern[0][0].height(), pattern[0][0].width(), pattern[0][0].height());
		sb.draw(ImageLoader.txrgBorder[1], x + kare.length * pattern[0][0].width(), y + kare[0].length * pattern[0][0].height(), pattern[0][0].width(),
				pattern[0][0].height());
	}

	public void update(float delta) {

		Vector2 idx = new Vector2(-1, -1);

		for (int i = 0; i < kare.length; i++) {
			idx.x = (idx.x > 0) ? idx.x : -1;
			for (int j = 0; j < kare[i].length; j++) {
				idx.y = (idx.y > 0) ? idx.y : -1;
				if (kare[i][j] != null && kare[i][j].isMarkForAnim() && kare[i][j].isVisible()) {
					fx.animate(kare[i][j], i, j, idx, kareDW, kareDH, animType);
				}
			}
		}

		deleteByIdx((int) idx.x, (int) idx.y);
	}

	private void deleteByIdx(int iD, int jD) {

		if (iD == -1 && jD == -1)
			return;

		if (getKareRowLen() > 3 || getKareColLen() > 3) {

			if (iD > 0) {
				state.getController().deleteColumn(iD);
			}

			if (jD > 0) {
				state.getController().deleteRow(jD);
			}

			state.reScheduleTask();

			animType = AxisType.RESET;
			checkSame(direction);
//			state.getController().moveByDirection(direction, false);
		} else {
			System.out.println("Bitti!");
			System.out.println("Score: " + state.getScore().getScore());
			System.out.println("HighScore: " + state.getScore().getHighScore());
			state.getScore().setHighScore(state.getScore().getScore()); // highScore yaz
			if (state.getScore().getScore() > state.getScore().getHighScore()) {
				System.out.println("New High Score: " + state.getScore().getScore());
			}

			MusicLoader.stopEffect();
			state.cancelSchedule();

			disableActive();

			if (getKareColLen() > 2)
				state.getController().deleteRow(1);

			print = true;
			print();

			getState().getSm().pushState(new MenuState(getState().getSm()));
		}
	}

	private void disableActive() {

		for (int i = 0; i < kare.length; i++) {
			for (int j = 0; j < kare[i].length; j++) {
				if (kare[i][j] != null) {
					kare[i][j].setActive(false);
				}
			}
		}
	}

	public void checkSame(Direction direction) {
		this.direction = direction;
		print();
		getSameColumn(direction);
		getSameRow(direction);

		direction = Direction.NOTHING;
	}

	private int getSameRow(Direction direction) {
		int color = -1;
		for (int j = 1; j < kare[1].length - 1; j++) {
			color = kare[1][j].color();
			for (int i = 1; i < kare.length - 1; i++) {
				if (kare[i][j] != null && kare[i][j].isVisible() && color != kare[i][j].color()) {
					color = -1;
					break;
				}
			}

			if (color > -1) {
				System.out.println("Ayni Satir: " + j);

				getFx().animType(true);

				MusicLoader.playEffect(Direction.NOTHING.equals(direction));

				state.getController().markRow(j); // animasyon icin gerekli
				animType = AxisType.COL;

				System.out.println("Score: " + state.getScore().getScore(getKareRowLen()));

				return j;
			}
		}

		return -1;
	}

	private int getSameColumn(Direction direction) {
		int color = -1;
		for (int i = 1; i < kare.length - 1; i++) {
			color = kare[i][1].color();
			for (int j = 1; j < kare[i].length - 1; j++) {
				if (kare[i][j] != null && kare[i][j].isVisible() && color != kare[i][j].color()) {
					color = -1;
					break;
				}
			}

			if (color > -1) {
				System.out.println("Ayni Sütun: " + i);

				getFx().animType(true);

				MusicLoader.playEffect(Direction.NOTHING.equals(direction));

				state.getController().markColumn(i); // animasyon icin gerekli
				animType = AxisType.ROW;

				System.out.println("Score: " + state.getScore().getScore(getKareColLen()));

				return i;
			}
		}

		return -1;
	}

	public int getKareRowLen() {
		return kare.length;
	}

	public int getKareColLen() {
		return kare[1].length;
	}

	public int x() {
		return this.x;
	}

	public int y() {
		return this.y;
	}

	public Pattern[][] getPattern() {
		return pattern;
	}

	public void setPattern(Pattern[][] pattern) {
		this.pattern = pattern;
	}

	public Kare[][] getKare() {
		return kare;
	}

	public void setKare(Kare[][] kare) {
		this.kare = kare;
	}

	public int getKareDW() {
		return kareDW;
	}

	public void setKareDW(int kareDW) {
		this.kareDW = kareDW;
	}

	public int getKareDH() {
		return kareDH;
	}

	public void setKareDH(int kareDH) {
		this.kareDH = kareDH;
	}

	public FxRemove getFx() {
		return fx;
	}

	public PlayState getState() {
		return state;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void print() {

		if (print == false)
			return;

		for (int i = 0; i < kare.length; i++) {
			for (int j = 0; j < kare[i].length; j++) {
				if (kare[i][j] != null && kare[i][j].isVisible())
					System.out.print(kare[i][j].color() + " ");
				else
					System.out.print("x ");
			}
			System.out.println();
		}
		System.out.println();

	}

	@Override
	public String toString() {
		return "Tahta [x=" + x + ", y=" + y + ", row=" + row + ", col=" + col + "]";
	}

}
