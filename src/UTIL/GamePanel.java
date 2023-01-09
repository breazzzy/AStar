package UTIL;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public abstract class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener,
		MouseMotionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean IsRunning = false;
	private Thread thread;
	private boolean paused = false;
	private long sleepTime = 20;
	private boolean debug = true;
	public  static int width,height;
	
	public GamePanel(int width, int height) {
		GamePanel.width = width;
		GamePanel.height = height;
		this.setFocusable(true);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(width, height);
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		start();
	}
	
	public void start() {
		IsRunning = true;
		initialize();
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		IsRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		paint((Graphics2D) g);
	}
	
	public abstract void paint(Graphics2D g);
	
	protected abstract void reset();
	
	protected abstract void initialize();
	
	protected abstract void tick();
	
	@Override
	public void run() {
		while (IsRunning) {
			if (!paused) {
				tick();
				repaint();
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	public void togglePaused() {
		setPaused(paused ? true : false);
	}
	
	public long getFps() {
		return sleepTime;
	}
	
	public void setFps(int fps) {
		this.sleepTime = 1000 / fps;
	}
	
	public void setSleepTime(long sleepTime) {
		if (debug)
			System.out
					.println("Debug: setSleepTime() overides projected fps with a set sleep time in milliseconds");
		this.sleepTime = sleepTime;
	}
	
	public boolean isDebug() {
		return debug;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
