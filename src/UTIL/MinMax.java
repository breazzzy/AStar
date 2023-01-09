package UTIL;

public class MinMax {
	public static double Max(double first, double second) {
		if (first > second) {
			return first;
		} else {
			return second;
		}
	}
	
	public static double Min(double first, double second) {
		if (first < second) {
			return first;
		} else {
			return second;
		}
	}
}
