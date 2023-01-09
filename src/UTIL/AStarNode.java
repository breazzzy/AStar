package UTIL;


public class AStarNode {
	private Vector2i pos;
	private AStarNode parent;
	private double fCost, gCost, hCost;
	
	public AStarNode(Vector2i pos, AStarNode parent, double gCost, double hCost) {
		this.pos = pos;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
	
	public Vector2i getPos() {
		return pos;
	}
	
	public void setPos(Vector2i pos) {
		this.pos = pos;
	}
	
	public AStarNode getParent() {
		return parent;
	}
	
	public void setParent(AStarNode parent) {
		this.parent = parent;
	}
	
	public double getfCost() {
		return fCost;
	}
	
	public void setfCost(double fCost) {
		this.fCost = fCost;
	}
	
	public double getgCost() {
		return gCost;
	}
	
	@Override
	public String toString() {
		return "AStarNode [pos=" + pos.toString() + ", parent=" + parent + ", fCost=" + fCost
				+ ", gCost=" + gCost + ", hCost=" + hCost + "]";
	}
	
	public void setgCost(double gCost) {
		this.gCost = gCost;
	}
	
	public double gethCost() {
		return hCost;
	}
	
	public void sethCost(double hCost) {
		this.hCost = hCost;
	}
	
}
