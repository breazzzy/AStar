package UTIL;

import java.awt.Graphics2D;

public abstract class RectGrid {
	
	protected int width, height;
	protected int cellSize;
	
	public RectGrid(int w, int h, int cellSize) {
		this.cellSize = cellSize;
		this.width = w;
		this.height = h;
		init();
	}
	
	/*
	 * Example init for(int x = 0; x<getWidth();x++){ for(int y = 0;
	 * y<getHeight();y++){ cells[x][y] = new Cell(x,y,cellSize,cellSize);
	 * cells2[x][y] = new Cell(x,y,cellSize,cellSize); } }
	 */
	public abstract void init();
	
	public abstract void tick();
	
	public abstract void render(Graphics2D g);
	
	public abstract void clear();
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getCellSize() {
		return cellSize;
	}
	
	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}
	
}
