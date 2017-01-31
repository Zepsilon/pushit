package com.zep.pushit.util;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class Util {
	private static FileHandle	baseFileHandle;
	private static I18NBundle	bundle;

	private static Preferences	prefs;

	public static void load() {
		prefs = Gdx.app.getPreferences(Constant.PREF_GAME_NAME);
		
		baseFileHandle = Gdx.files.internal("i18n/MyBundle");
		Settings.refreshLanguage();
		bundle = I18NBundle.createBundle(baseFileHandle, new Locale(Settings.getLanguage()));
	}

	public static class Bundle {

		public static I18NBundle getBundle() {
			return bundle;
		}

		public static String getText(String key) {
			return bundle.get(key);
		}

		public static String getText(String key, Object... obj) {
			return bundle.format(key, obj);
		}

		public static Preferences getPreferences() {
			return prefs;
		}
	}

	public static class Prefs {

		public static void putValue(String key, Object value) {
			if (value == null)
				return;

			if (value instanceof String)
				prefs.putString(key, (String) value);
			else if (value instanceof Integer)
				prefs.putInteger(key, (Integer) value);
			else if (value instanceof Float)
				prefs.putFloat(key, (Float) value);
			else if (value instanceof Boolean)
				prefs.putBoolean(key, (Boolean) value);

			prefs.flush();
		}

		public static Object getValue(String key, Object value) {
			if (key == null || value == null)
				return null;

			if (value instanceof String)
				return prefs.getString(key, (String) value);
			else if (value instanceof Integer)
				return prefs.getInteger(key, (Integer) value);
			else if (value instanceof Float)
				return prefs.getFloat(key, (Float) value);
			else if (value instanceof Boolean)
				return prefs.getBoolean(key, (Boolean) value);

			return null;
		}

		public static String getValue(String key) {
			return prefs.getString(key);
		}

	}
	
	public static class MathUtil {

		public static long fibonacci(int n) {
			if (n <= 1)
				return n;
			else
				return fibonacci(n - 1) + fibonacci(n - 2);
		}
	}
	
	public static class Settings {
		private static boolean	isMusicOn;
		private static boolean	isEffectOn;
		private static float	volume;
		private static String language;
		private static String scores;


		public static void refresh() {
			refreshMusic();
			refreshScores();
			refreshLanguage();
		}

		public static void refreshMusic() {
			Settings.isMusicOn = (Boolean) Prefs.getValue(Constant.PREF_MUSIC, true);
			Settings.isEffectOn = (Boolean) Prefs.getValue(Constant.PREF_EFFECT, true);
			Settings.volume = (Float) Prefs.getValue(Constant.PREF_VOLUME, 0f);
		}
		
		public static void refreshScores() {
			Settings.scores = (String) Prefs.getValue(Constant.PREF_ALL_SCORES);
		}
		
		public static void refreshLanguage() {
			Settings.language = (String) Prefs.getValue(Constant.PREF_LANG, java.util.Locale.getDefault().toString());
		}
		
		public static void setLanguage(String language) {
			Prefs.putValue(Constant.PREF_LANG, (Constant.LOCAL_TR.equals(language) ? Constant.LOCAL_TR : Constant.LOCAL_EN));
			Settings.language = language;
		}
		
		public static String getLanguage() {
			return Settings.language;
		}

		public static void setMusic(boolean isMusicOn) {
			Prefs.putValue(Constant.PREF_MUSIC, isMusicOn);
			Settings.isMusicOn = isMusicOn;
		}

		public static boolean isMusicOn() {
			return isMusicOn;
		}

		public static void setEffect(boolean isEffectOn) {
			Prefs.putValue(Constant.PREF_EFFECT, isEffectOn);
			Settings.isEffectOn = isEffectOn;
		}

		public static boolean isEffectOn() {
			return isEffectOn;
		}

		public static float getVolume() {
			return volume;
		}

		public static void setVolume(float volume) {
			Prefs.putValue(Constant.PREF_VOLUME, volume);
			Settings.volume = volume;
		}
		
		public static String getScores() {
			return scores;
		}

		public static void setScores(String scores) {
			Util.Prefs.putValue(Constant.PREF_ALL_SCORES, scores);
			Settings.scores = scores;
		}
	}
	
}
