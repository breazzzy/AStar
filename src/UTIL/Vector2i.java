package UTIL;


public class Vector2i {
	
	public static final Vector2i UP = new Vector2i(0, 1);
	public static final Vector2i DOWN = new Vector2i(0, -1);
	public static final Vector2i LEFT = new Vector2i(-1, 0);
	public static final Vector2i RIGHT = new Vector2i(-1, 0);
	public static final Vector2i TOP_RIGHT = new Vector2i(1, 1);
	public static final Vector2i BOTTOM_RIGHT = new Vector2i(1, -1);
	public static final Vector2i TOP_LEFT = new Vector2i(-1, 1);
	public static final Vector2i BOTTOM_LEFT = new Vector2i(-1, -1);
	
	private int x, y;
	
	public Vector2i(int x, int y) {
		set(x, y);
	}
	
	public Vector2i(Vector2i vec) {
		set(vec);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2i vec) {
		this.setX(vec.getX());
		this.setY(vec.getY());
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Vector2i add(Vector2i vec) {
		return new Vector2i(this.getX() + vec.getX(), this.getY() + vec.getY());
	}
	
	public Vector2i subtract(Vector2i vec) {
		return new Vector2i(this.getX() - vec.getX(), this.getY() - vec.getY());
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof Vector2i)) return false;
		Vector2i vec = (Vector2i) obj;
		if (vec.x == this.x && this.y == vec.y)
			return true;
		else return false;
	}
	
	@Override
	public String toString() {
		return "Vector2i [x=" + x + ", y=" + y + "]";
	}
	
	/*
	 * Distance algorithms
	 * https://github.com/adrian17/AsteroidPathFinder/blob/master
	 * /PathFind/path.cpp
	 */
	public double dist_euclidian(Vector2i vec) {
		int x1 = vec.getX(), y1 = vec.getY();
		return Math.sqrt((getX() - x1) * (getX() - x1) + (getY() - y1 ) * (getY() - y1));
	}
	
	public double dist_manhatten(Vector2i vec) {
		int x1 = vec.getX(), y1 = vec.getY();
		return Math.abs(getX()-x1) + Math.abs( getY()-y1);
	}
	public double dist_octile(Vector2i vec) {
		int x1 = vec.getX(), y1 = vec.getY();
		int dx = Math.abs(x1 - getX()), dy = Math.abs(y1 - getY());
		return MinMax.Max(dx, dy) + (Math.sqrt(2) - 1) * MinMax.Min(dx, dy);
	}
	//Static Functions
	public static double dist_euclidian(Vector2i vec1,Vector2i vec2) {
		int x1 = vec1.getX(), y1 = vec1.getY(), x2 =  vec2.getX(), y2 = vec2.getY();
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public static double dist_manhatten(Vector2i vec1,Vector2i vec2) {
		int x1 = vec1.getX(), y1 = vec1.getY(), x2 =  vec2.getX(), y2 = vec2.getY();
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
	
	public static double dist_octile(Vector2i vec1,Vector2i vec2) {
		int x1 = vec1.getX(), y1 = vec1.getY(), x2 =  vec2.getX(), y2 = vec2.getY();
		int dx = Math.abs(x1 - x2), dy = Math.abs(y1 - y2);
		return MinMax.Max(dx, dy) + (Math.sqrt(2) - 1) * MinMax.Min(dx, dy);
	}
}
