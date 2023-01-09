package UTIL;

import java.awt.Color;
import java.util.Random;

public class UTIL {
	public static Color randomColor() {
		Random rand = new Random();
		return new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
	}
	
	public static Color randomColor(long seed) {
		Random rand = new Random(seed);
		return new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
	}
	
	public static Color randomColor(Random rand) {
		return new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
	}
}
