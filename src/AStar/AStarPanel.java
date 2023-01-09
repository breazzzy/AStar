package AStar;

import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import AStar.Node.BodyType;
import UTIL.FPSLogger;
import UTIL.GamePanel;

public class AStarPanel extends GamePanel {
	
	/**
	 * Possible optimizations:
	 * Clean up:
	 * Add actions listeners to their own class
	 * Features:
	 * Add other pathfinding algorithms that aren't A*
	 * Generation algorithms mazes, braidmazes etc.
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPopupMenu Menu = new JPopupMenu();
	protected static JMenuItem Start, Goal, Redraw, KillStart, KillGoal, Clear, Next, Space, Brush, Regen;
	public boolean brushDelete = false;
	Board board;
	FPSLogger fps;
	protected long seed = 1;
	
	public AStarPanel(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
		Menu.add(Start = new JMenuItem("Set Start"));
		Menu.add(Goal = new JMenuItem("Set Goal"));
		Menu.add(Redraw = new JMenuItem("Redraw Path"));
		Menu.add(KillStart = new JMenuItem("Kill Start"));
		Menu.add(KillGoal = new JMenuItem("Kill Goal"));
		Menu.add(Clear = new JMenuItem("Clear"));
		Menu.add(Next = new JMenuItem("Next Dist Meth | Current = " + board.algo));
		Menu.add(Space = new JMenuItem(board.only4 ? "Set to 8 spaces" : "Set to 4 spaces"));
		Menu.add(Brush = new JMenuItem("Set brush to Delete"));
		Menu.add(Regen = new JMenuItem("Enter a new seed | Current = " + board.getSeed()));
	}
	
	@Override
	public void paint(Graphics2D g) {
		// TODO Auto-generated method stub
		board.render(g);
	}
	
	@Override
	protected void reset() {
		// TODO Auto-generated method stub
		// Didn't use for clearing board method because not technically a reset
		// Could be used to make a new random board with a given seed
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		board = new Board(80, 60, 10);
		seed = AStarPanel.calcSeed(JOptionPane.showInputDialog("Enter Level Seed"));
		System.out.println("Seed = " + seed);
		board.genMap(seed);
		// this.setPreferredSize(new
		// Dimension(board.getWidth()*board.getCellSize(),board.getHeight()*board.getCellSize()));
		this.setPaused(false);
		this.setSleepTime(20);
		fps = new FPSLogger();
		this.setComponentPopupMenu(Menu);
		
	}
	
	@Override
	protected void tick() {
		// TODO Auto-generated method stub
		board.tick();
	}
	
	/*
	 * Input
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated metod stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	ActionListener slist;
	ActionListener glist;
	ActionListener redraw;
	ActionListener killStartList;
	ActionListener killGoalList;
	ActionListener clearList;
	ActionListener nextList;
	ActionListener nextSpaceList;
	ActionListener brushToggle;
	ActionListener regenMap;
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (SwingUtilities.isRightMouseButton(e)) {
			Start.removeActionListener(slist);
			Goal.removeActionListener(glist);
			Redraw.removeActionListener(redraw);
			KillStart.removeActionListener(killStartList);
			KillGoal.removeActionListener(killGoalList);
			Clear.removeActionListener(clearList);
			Next.removeActionListener(nextList);
			Space.removeActionListener(nextSpaceList);
			Brush.removeActionListener(brushToggle);
			Regen.removeActionListener(regenMap);
			slist = new ActionListeners.actionListner(e.getX() / board.getCellSize(), e.getY() / board.getCellSize(), board);
			glist = new ActionListeners.actionListnerG(e.getX() / board.getCellSize(), e.getY() / board.getCellSize(), board);
			redraw = new ActionListeners.actionListnerRedraw(board);
			killStartList = new ActionListeners.actionListnerKillStart(board);
			killGoalList = new ActionListeners.actionListnerKillGoal(board);
			clearList = new ActionListeners.actionListnerClear(board);
			nextList = new ActionListeners.actionListnerNext(board);
			nextSpaceList = new ActionListeners.actionListnerNextSpace(board);
			brushToggle = new ActionListeners.actionListnerBrush(this);
			regenMap = new ActionListeners.actionListnerRegen(board);
			System.out.println("POPUP");
			Start.addActionListener(slist);
			Goal.addActionListener(glist);
			Redraw.addActionListener(redraw);
			KillStart.addActionListener(killStartList);
			KillGoal.addActionListener(killGoalList);
			Clear.addActionListener(clearList);
			Next.addActionListener(nextList);
			Space.addActionListener(nextSpaceList);
			Brush.addActionListener(brushToggle);
			Regen.addActionListener(regenMap);
			Menu.show(this, e.getX(), e.getY());
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!SwingUtilities.isRightMouseButton(e)) {
			if (!brushDelete) {
				if (board.getCell((int) (e.getX()) / board.getCellSize(), (int) (e.getY()) / board.getCellSize()).type != BodyType.GravityWell && board.getCell((int) (e.getX()) / board.getCellSize(), (int) (e.getY()) / board.getCellSize()).type != BodyType.Gravity) board.getCell((int) (e.getX())
						/ board.getCellSize(), (int) (e.getY()) / board.getCellSize()).type = BodyType.Asteroid;
			}
			if (brushDelete) {
				if (board.getCell((int) (e.getX()) / board.getCellSize(), (int) (e.getY()) / board.getCellSize()).type == BodyType.Asteroid) board.getCell((int) (e.getX()) / board.getCellSize(), (int) (e.getY()) / board.getCellSize()).type = BodyType.Space;
			}
			// board.checkPath();
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static long calcSeed(String seed) {
		String seedS = "";
		if (seed.toCharArray().length == 0) {
			seedS = "1";
		}
		for (char c : seed.toCharArray()) {
			if (Character.isWhitespace(c)) {
				continue;
			}
			if (Character.isDigit(c)) {
				seedS += c;
			} else {
				seedS += (byte) c;
			}
		}
		if (seedS.equals("")) {
			seedS = "1";
		}
		try{
			if(Long.parseLong(seedS)>Long.MAX_VALUE){
				return Long.MAX_VALUE;
			}
			return Long.parseLong(seedS);
		}catch(Exception e){}
		return Long.MAX_VALUE;
	}
}
