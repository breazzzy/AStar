package UTIL;

import java.util.ArrayList;
import java.util.Comparator;


public class AStarSolver {
	
	private Comparator<AStarNode> sorter = new Comparator<AStarNode>() {
		public int compare(AStarNode o1, AStarNode o2) {
			if (o2.getfCost() < o1.getfCost()) return +1;
			if (o2.getfCost() > o1.getfCost()) return -1;
			return 0;
		}
	};
	
	ArrayList<AStarNode> open = new ArrayList<AStarNode>();
	ArrayList<AStarNode> closed = new ArrayList<AStarNode>();
	ArrayList<AStarNode> path = new ArrayList<AStarNode>();
	AStarNode current;// = new
						// AStarNode(start.getPos(),null,0,start.getPos().dist_euclidian(goal.getPos()));
	boolean completed = false;
	public Block start, goal;
	
	public void findPath(ArrayList<Block> blocks, Block Start, Block Goal) {
		current = new AStarNode(Start.getPos(), null, 0, Start.getPos().dist_euclidian(
				Goal.getPos()));
		this.start = Start;
		this.goal = Goal;
		open.add(current);
	}
	
	public void NextStep() {/*
							 * 
							 * if(open.size() > 0){
							 * Collections.sort(open,sorter); current =
							 * open.get(0);
							 * if(current.pos.equals(goal.getPos())){
							 * 
							 * System.out.println("Found goal");
							 * ArrayList<AStarNode> path = new
							 * ArrayList<AStarNode>(); while(current.parent !=
							 * null){ path.add(current); current =
							 * current.parent; } open.clear(); closed.clear();
							 * System.out.println("PathFound");
							 * this.path.addAll(path); } open.remove(current);
							 * closed.add(current); for(int i = 0; i<9; i++){
							 * if(i==4) continue; int x = current.pos.getX();
							 * int y = current.pos.getY(); int xi = (i%3)-1; int
							 * yi = (i/3)-1; Node at = this.getCell(x+xi,y+yi);
							 * if(at == null) continue; if(at.type !=
							 * BodyType.Space && at.type != BodyType.Goal &&
							 * at.type != BodyType.Path) continue; if(at.type ==
							 * BodyType.Asteroid) continue; Vector2i a = new
							 * Vector2i(x+xi,y+yi); double gCost =
							 * current.gCost+current.pos.dist_euclidian(a);
							 * double hCost = a.dist_euclidian(goal.getPos());
							 * AStarNode node = new
							 * AStarNode(a,current,gCost,hCost);
							 * if(NodeInList(closed,a)&& gCost >= node.gCost)
							 * continue; if(!NodeInList(open,a)|| gCost <
							 * node.gCost) open.add(node); }
							 */
	}
	
	public static boolean NodeInList(ArrayList<AStarNode> list, Vector2i a) {
		for (AStarNode i : list) {
			if (i.getPos().equals(a)) { return true; }
		}
		return false;
	}
	
}
