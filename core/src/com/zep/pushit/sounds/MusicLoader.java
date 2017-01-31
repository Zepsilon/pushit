package com.zep.pushit.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.zep.pushit.util.Util.Settings;

public class MusicLoader {

	private static Music	music;
	private static Sound	effect;
//	private static Sound fxAdd;

	public static void load() {
		System.out.println("Music loading..");
		music = Gdx.audio.newMusic(Gdx.files.internal("music/SpinCycle.mp3"));
		effect = Gdx.audio.newSound(Gdx.files.internal("music/remove.mp3"));
		Settings.refreshMusic();
	}

	public static Music getMusic() {
		return music;
	}

	public static void playMusic(boolean loop) {
		playMusic(Settings.getVolume(), loop);
	}

	public static void playMusic(float volume, boolean loop) {
		if (music != null && Settings.isMusicOn()) {
			music.setVolume(volume); // sets the volume to half the maximum volume
			music.setLooping(loop); // will repeat playback until music.stop() is called
			music.play(); // resumes the playback
		}
	}

	/**
	 * isExtra parametresi true ise aynı anda birden cok satir ya da sutun silinmistir, ekstra effect gerekebilir
	 * 
	 * @param isExtra
	 * @return
	 */
	public static long playEffect(boolean isExtra) {
		return playEffect(Settings.getVolume(), isExtra);
	}
	
	public static long playEffect(float volume, boolean isExtra) {
		// TODO add exstra effect 
		if (effect != null && Settings.isEffectOn()) {
			return effect.play(volume);
		}
		return 0;
	}

	public static void stopMusic() {
		System.out.println("Music stopping..");
		if (music != null)
			music.stop(); // stops the playback
	}

	/**
	 * Effect'leri durdurur
	 * 
	 */
	public static void stopEffect() {
		System.out.println("Effect stopping..");
		if (effect != null)
			effect.stop();
		// fx icin sound calistir
	}

	public static void pauseMusic() {
		music.pause(); // pauses the playback
	}

	public static void setMusic(boolean isMusicOn) {
		Settings.setMusic(isMusicOn);
	}

	public static boolean isMusicOn() {
		return Settings.isMusicOn();
	}

	public static void setEffect(boolean isEffectOn) {
		Settings.setEffect(isEffectOn);
	}

	public static boolean isEffectOn() {
		return Settings.isEffectOn();
	}

	public static float getVolume() {
		return Settings.getVolume();
	}

	public static void setVolume(float volume) {
		Settings.setVolume(volume);
	}

	public static void refresh() {
		Settings.refreshMusic();
	}

	public static void dispose() {
		if (music != null)
			music.dispose();
		music = null;
		if (effect != null)
			effect.dispose();
		effect = null;
//		fxAdd.dispose();
	}
}
