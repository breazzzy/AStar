package AStar;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JMenuItem;

import AStar.Node.BodyType;
import UTIL.AStarNode;
import UTIL.AStarSolver;
import UTIL.RectGrid;
import UTIL.Vector2i;

public class Board extends RectGrid {
	
	private enum Huerstic {
		Euclidian, Manhatten, Octile;
		
		Huerstic next;
		static {
			Euclidian.next = Huerstic.Manhatten;
			Manhatten.next = Huerstic.Octile;
			Octile.next = Huerstic.Euclidian;
		}
		
		public double calculate(Vector2i vec1, Vector2i vec2) {
			if (this == Euclidian) { return Vector2i.dist_euclidian(vec1, vec2); }
			if (this == Manhatten) { return Vector2i.dist_manhatten(vec1, vec2); }
			if (this == Octile) { return Vector2i.dist_octile(vec1, vec2); }
			return Vector2i.dist_euclidian(vec1, vec2);// Returns Euclidian if the method somehow reaches this line
		}
		
		public Huerstic nextType() {
			return this.next;
		}
	}
	
	protected Node[][] blocks;
	private long seed = 1;
	ArrayList<AStarNode> path = new ArrayList<AStarNode>();
	ArrayList<AStarNode> open = new ArrayList<AStarNode>();
	ArrayList<AStarNode> closed = new ArrayList<AStarNode>();
	long startTime;
	boolean solved = false;
	int currentPos = 0;
	AStarNode start;
	Vector2i startVec;// <---- Most retarted idea i ever had
	private Vector2i goal;
	private AStarNode current;
	// Algo
	protected Huerstic algo = Huerstic.Euclidian;
	// 4SpacesOr8
	protected boolean only4 = true;
	private int forAmount;
	private int forIter;
	private int forStart;
	
	private Comparator<AStarNode> sorter = new Comparator<AStarNode>() {
		public int compare(AStarNode o1, AStarNode o2) {
//			System.out.println("Sorter List Size:" + open.size());
			if (o2.getfCost() < o1.getfCost()) return +1;
			if (o2.getfCost() > o1.getfCost()) return -1;
			return 0;
		}
	};
	
	public void nextHuerstic(JMenuItem caller) {
		this.algo = this.algo.nextType();
		caller.setText("Next Dist Meth | Current = " + this.algo);
		this.redrawPath();
	}
	
	public void nextSpaceCheck(JMenuItem caller) {
		if (!only4) {
			this.only4 = true;
			this.forAmount = 8;
			this.forIter = 2;
			this.forStart = 1;
			caller.setText("Set to 8 spaces");
			this.redrawPath();
			return;
		} else {
			this.only4 = !only4;
			this.forAmount = 9;
			this.forIter = 1;
			this.forStart = 0;
			caller.setText("Set to 4 spaces");
			this.redrawPath();
			return;
		}
	}
	
	public void nextSpaceCheck() {
		if (!only4) {
			this.only4 = true;
			this.forAmount = 8;
			this.forIter = 2;
			this.forStart = 1;
			return;
		} else {
			this.only4 = !only4;
			this.forAmount = 9;
			this.forIter = 1;
			this.forStart = 0;
			return;
		}
	}
	
	public Board(int w, int h, int cellSize) {
		super(w, h, cellSize);
		nextSpaceCheck();
	}
	
	@Override
	public void init() {
		blocks = new Node[getWidth()][getHeight()];
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				blocks[x][y] = new Node(x, y, cellSize, cellSize);
			}
		}
	}
	
	public void genMap(long seed) {
		System.out.println(seed);
		this.clear();
		this.generateTypes(seed);
		this.seed = seed;
		this.startTime = System.currentTimeMillis();
	}
	
	// Bugge allows ability to change path after it is created would go under
	// aestroid mouse input method in panel class
	public void checkPath() {
		int i = 0;
		for (AStarNode node : path) {
			if (this.getCell(node.getPos()).type == BodyType.Asteroid) {
				this.getCell(path.get(i + 1).getPos()).type = BodyType.GravityWell;
				current = path.get(i + 1);
				this.solved = false;
				for (AStarNode t : path) {
					this.getCell(t.getPos()).type = BodyType.Space;
				}
				open.clear();
				closed.clear();
				open.add(current);
				path.clear();
				break;
			}
			i++;
		}
	}
	
	@Override
	public void tick() {
		if (System.currentTimeMillis() - startTime > 10) {
			if (this.start == null || this.goal == null) { return; }
			if (this.solved == false) {
				this.nextStepInPath();
//				System.out.println(open.size());
			}
			startTime = System.currentTimeMillis();
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				blocks[x][y].render(g);
			}
		}
	}
	
	@Override
	public void clear() {
		this.start = null;
		this.goal = null;
		this.startVec = null;
		this.clearPath();
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				blocks[x][y].type = BodyType.Space;
			}
		}
		
	}
	
	public void createGravityWell(Node node) {
		// Could just use null check step and a for loop and modulus;
		try {
			blocks[node.getPos().getX() + 1][node.getPos().getY() + 1].type = BodyType.Gravity;
		} catch (Exception e) {
		};
		try {
			blocks[node.getPos().getX() + 1][node.getPos().getY()].type = BodyType.Gravity;
		} catch (Exception e) {
		};
		try {
			blocks[node.getPos().getX() + 1][node.getPos().getY() - 1].type = BodyType.Gravity;
		} catch (Exception e) {
		};
		try {
			blocks[node.getPos().getX()][node.getPos().getY() + 1].type = BodyType.Gravity;
		} catch (Exception e) {
		};
		try {
			blocks[node.getPos().getX()][node.getPos().getY() - 1].type = BodyType.Gravity;
		} catch (Exception e) {
		};
		try {
			blocks[node.getPos().getX() - 1][node.getPos().getY() + 1].type = BodyType.Gravity;
		} catch (Exception e) {
		};
		try {
			blocks[node.getPos().getX() - 1][node.getPos().getY()].type = BodyType.Gravity;
		} catch (Exception e) {
		};
		try {
			blocks[node.getPos().getX() - 1][node.getPos().getY() - 1].type = BodyType.Gravity;
		} catch (Exception e) {
		};
		try {
			blocks[node.getPos().getX()][node.getPos().getY()].type = BodyType.GravityWell;
		} catch (Exception e) {
		};
	}
	
	public void generateTypes(long seed) {
		int n = blocks.length * blocks[0].length;
		int numGravs = (int) Math.floor(n * .05);
		int numAst = (int) Math.floor(n * .1);
		if(seed == 111){
			for(int x = 0; x < blocks.length; x++){
				for(int y = 0; y < blocks[x].length; y++)
				blocks[x][y].type = BodyType.Space;
				return;
			}
		}
		Random rand = new Random(seed);
		for (int i = 0; i < numGravs; i++) {
			createGravityWell(getRandomNode(rand));
		}
		for (int i = 0; i < numAst; i++) {
			Node node = getRandomNode(rand);
			if (node.type != BodyType.GravityWell) blocks[node.getPos().getX()][node.getPos().getY()].type = BodyType.Asteroid;
		}
		System.out.printf("Generated %dx%d map with %d gravity wells and %d asteroids\n", getWidth(), getHeight(), numGravs, numAst);
	}
	
	public void createMaze(){
		//Maze code
	}
	
	protected static Node getRandomNodeFromList(ArrayList<Node> nodes) {
		Random rand = new Random();
		return nodes.get(rand.nextInt(nodes.size()));
	}
	
	protected static boolean nodeInList(Node nod, ArrayList<Node> nodes) {
		for (Node n : nodes) {
			if (n == nod) { return true; }
		}
		return false;
	}
	
	public Node getCell(int x, int y) {
		if (x > this.width - 1 || x < 0 || y > this.height - 1 || y < 0) { return null; }
		return blocks[x][y];
	}
	
	public Node getCell(Vector2i vec) {
		if (vec.getX() > this.width - 1 || vec.getX() < 0 || vec.getY() > this.height - 1 || vec.getY() < 0) { return null; }
		return blocks[vec.getX()][vec.getY()];
	}
	int stepsTaken = 0;
	public void nextStepInPath() {
		stepsTaken += 1;
		if (open.size() > 0) {
			Collections.sort(open, sorter);
			current = open.get(0);
//			System.out.println(current.getPos().toString());
			if (current.getPos().equals(this.goal)) {
				while (current.getParent() != null) {
					path.add(current);
					current = current.getParent();
				}
				this.solved = true;
				this.currentPos = path.size() - 1;
				renderPath(path);
				System.out.println("Took " + stepsTaken + " Steps.");
				return;
			}
			open.remove(current);
			if (this.getCell(current.getPos()).type != BodyType.Start && this.getCell(current.getPos()).type != BodyType.Goal) this.getCell(current.getPos()).type = BodyType.Checked;
			closed.add(current);
			for (int i = this.forStart; i < this.forAmount; i += this.forIter) {
				if (!only4) if (i == 4) continue;
				int x = current.getPos().getX();
				int y = current.getPos().getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Node at = this.getCell(x + xi, y + yi);
				if (at == null) continue;
				if (at.type != BodyType.Space && at.type != BodyType.Goal && at.type != BodyType.Path && at.type != BodyType.Checked && at.type != BodyType.Open) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = /*current.getgCost()*/ + algo.calculate(current.getPos(), a);// current.getPos().dist_euclidian(a);
				double hCost = algo.calculate(a, goal);// a.dist_euclidian(goal);
				AStarNode newnode = new AStarNode(a, current, gCost, hCost);
				if (AStarSolver.NodeInList(closed, a)) continue;
				if (!AStarSolver.NodeInList(open, a)) { // || gCost < newnode.getgCost()
					open.add(newnode);
					if (this.getCell(newnode.getPos()).type != BodyType.Start && this.getCell(newnode.getPos()).type != BodyType.Goal) this.getCell(newnode.getPos()).type = BodyType.Open;
				}
			}
		}
	}
	
	public void renderPath(ArrayList<AStarNode> nodes) {
		for (AStarNode node : nodes) {
			if (this.getCell(node.getPos()).type == BodyType.Goal) {
				continue;
			}
			this.getCell(node.getPos()).type = BodyType.Path;
		}
	}
	
	public Node getRandomNode(Random rand) {
		return blocks[rand.nextInt(blocks.length)][rand.nextInt(blocks[0].length)];
	}
	
	public void createStart(int x, int y) {
		if (this.start != null) {
			this.getCell(start.getPos()).type = BodyType.Space;
			clearPath();
			this.start = null;
			this.solved = false;
		}
		this.blocks[x][y].type = BodyType.Start;
		startVec = new Vector2i(x, y);
		if (this.goal != null) {
			this.start = new AStarNode(startVec, null, 0, startVec.dist_euclidian(goal));
			this.open.add(start);
			this.solved = false;
			return;
		}
	}
	
	public void createEnd(int x, int y) {
		if (this.goal != null) {
			this.getCell(goal).type = BodyType.Space;
			clearPath();
			this.goal = null;
			this.solved = false;
		}
		this.goal = new Vector2i(x, y);
		if (startVec != null) {
			start = new AStarNode(startVec, null, 0, startVec.dist_euclidian(goal));
			open.add(start);
			this.solved = false;
		}
		this.blocks[x][y].type = BodyType.Goal;
	}
	
	public void clearPath() {
		if (open.size() > 0) {
//			System.out.println(open.size());
			for (AStarNode node : open) {
				if (this.getCell(node.getPos()).type == BodyType.Start || this.getCell(node.getPos()).type == BodyType.Goal || this.getCell(node.getPos()).type == BodyType.Asteroid) {
					continue;
				}
				this.getCell(node.getPos()).type = BodyType.Space;
			}
		}
		if (closed.size() > 0) {
			for (AStarNode node : closed) {
				if (this.getCell(node.getPos()).type == BodyType.Start || this.getCell(node.getPos()).type == BodyType.Goal || this.getCell(node.getPos()).type == BodyType.Asteroid) {
					continue;
				}
				this.getCell(node.getPos()).type = BodyType.Space;
			}
		}
		path.clear();
		open.clear();
		closed.clear();
		stepsTaken = 0;
	}
	
	public void redrawPath() {
		if (goal == null || start == null) { return; }
		this.clearPath();
		this.solved = false;
		open.add(start);
	}
	
	public void destroyStart() {
		if (start == null && this.startVec == null) { return; }
		clearPath();
		this.getCell(startVec).type = BodyType.Space;
		this.startVec = null;
		this.start = null;
	}
	
	public void destroyGoal() {
		if (goal == null) { return; }
		clearPath();
		this.getCell(goal).type = BodyType.Space;
		this.goal = null;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}
	
}
