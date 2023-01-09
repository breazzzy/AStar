package UTIL;

import java.awt.Color;
import java.awt.Graphics2D;


public abstract class Block {
	private int width,height;
	private Vector2i pos;
	/*
	 * Rectangle Params
	 */
	
	public Block(int x,int y, int h,int w){
		this.pos = new Vector2i(x,y);
		this.width = w;
		this.height = h;
	}
	public Block(int x,int y, int h,int w,Color color){
		this.pos = new Vector2i(x,y);
		this.width = w;
		this.height = h;
	}
	public Block(Vector2i pos, int h,int w){
		this.pos = new Vector2i(pos);
		this.width = w;
		this.height = h;
	}
	public Block(Vector2i pos, int h,int w,Color color){
		this.pos = new Vector2i(pos);
		this.width = w;
		this.height = h;
	}
	public abstract void render(Graphics2D g);
	public void renderOutline(Graphics2D g,Color col){
		g.setColor(col);
		g.drawRect(getPointWidth(), getPointHeight(), width, height);
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getPointWidth() {
		return pos.getX()* width;
	}
	public int getPointHeight() {
		return pos.getY() * height;
	}
	public Vector2i getPos() {
		return pos;
	}
	public void setPos(Vector2i pos) {
		this.pos = pos;
	}
}