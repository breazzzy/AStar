package AStar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ActionListeners {
	protected static class actionListner implements ActionListener {
		int x, y;
		Board board;
		
		public actionListner(int x, int y, Board board) {
			this.x = x;
			this.y = y;
			this.board = board;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.getActionCommand() + " " + e.getSource());
			board.createStart(x, y);
		}
		
	}
	
	protected static class actionListnerG implements ActionListener {
		int x, y;
		Board board;
		
		public actionListnerG(int x, int y, Board board) {
			this.x = x;
			this.y = y;
			this.board = board;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.getActionCommand() + " " + e.getSource());
			board.createEnd(x, y);
		}
		
	}
	
	protected static class actionListnerRedraw implements ActionListener {
		Board board;
		
		public actionListnerRedraw(Board board) {
			this.board = board;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.getActionCommand() + " " + e.getSource());
			board.redrawPath();
		}
		
	}
	
	protected static class actionListnerKillStart implements ActionListener {
		Board board;
		
		public actionListnerKillStart(Board board) {
			this.board = board;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.getActionCommand() + " " + e.getSource());
			board.destroyStart();
		}
	}
	
	protected static class actionListnerKillGoal implements ActionListener {
		Board board;
		
		public actionListnerKillGoal(Board board) {
			this.board = board;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.getActionCommand() + " " + e.getSource());
			board.destroyGoal();
		}
		
	}
	
	protected static class actionListnerClear implements ActionListener {
		Board board;
		
		public actionListnerClear(Board board) {
			this.board = board;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.getActionCommand() + " " + e.getSource());
			board.clear();
		}
		
	}
	
	protected static class actionListnerNext implements ActionListener {
		Board board;
		
		public actionListnerNext(Board board) {
			this.board = board;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.getActionCommand() + " " + e.getSource());
			board.nextHuerstic((JMenuItem) e.getSource());
		}
		
	}
	
	protected static class actionListnerNextSpace implements ActionListener {
		Board board;
		
		public actionListnerNextSpace(Board board) {
			this.board = board;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.getActionCommand() + " " + e.getSource());
			board.nextSpaceCheck((JMenuItem) e.getSource());
		}
		
	}
	
	protected static class actionListnerBrush implements ActionListener {
		AStarPanel toggle;
		
		public actionListnerBrush(AStarPanel toggle) {
			this.toggle = toggle;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.getActionCommand() + " " + e.getSource());
			if (this.toggle.brushDelete) {
				JMenuItem temp = (JMenuItem) e.getSource();
				temp.setText("Set Brush to Delete");
				this.toggle.brushDelete = false;
			} else {
				JMenuItem temp = (JMenuItem) e.getSource();
				temp.setText("Set Brush to Create");
				this.toggle.brushDelete = true;
			}
		}
		
	}
	
	protected static class actionListnerRegen implements ActionListener {
		Board board;
		
		public actionListnerRegen(Board board) {
			this.board = board;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			board.genMap(AStarPanel.calcSeed(JOptionPane.showInputDialog("Enter new seed")));
		}
		
	}
}
