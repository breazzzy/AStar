package AStar;

import java.awt.Color;
import java.awt.Graphics2D;

import UTIL.Block;
import UTIL.Vector2i;

public class Node extends Block{
	
	enum BodyType{
		GravityWell,
		Gravity,
		Space,
		Asteroid,
		Goal,
		Start,
		Path,
		Checked,
		Open;
	}
	protected BodyType type = BodyType.Space;
	//used for mazes
	protected Vector2i dir;
	
	public Node(int x, int y, int h, int w) {
		super(x, y, h, w);
		
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		if(type == BodyType.Space){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(getPointWidth(), getPointHeight(), getWidth(), getHeight());
		}
		if(type == BodyType.Asteroid){
			g.setColor(Color.RED);
			g.fillRect(getPointWidth(), getPointHeight(), getWidth(), getHeight());
		}
		if(type == BodyType.GravityWell){
			g.setColor(Color.DARK_GRAY);
			g.fillRect(getPointWidth(), getPointHeight(), getWidth(), getHeight());
		}
		if(type == BodyType.Gravity){
			g.setColor(Color.BLACK);
			g.fillRect(getPointWidth(), getPointHeight(), getWidth(), getHeight());
		}
		if(type == BodyType.Goal){
			g.setColor(Color.YELLOW);
			g.fillRect(getPointWidth(), getPointHeight(), getWidth(), getHeight());
		}
		if(type == BodyType.Start){
			g.setColor(Color.BLUE);
			g.fillRect(getPointWidth(), getPointHeight(), getWidth(), getHeight());
		}
		if(type == BodyType.Path){
			g.setColor(Color.ORANGE);
			g.fillRect(getPointWidth(), getPointHeight(), getWidth(), getHeight());
		}
		if(type == BodyType.Checked){
			g.setColor(new Color(86, 130, 3));
			g.fillRect(getPointWidth(), getPointHeight(), getWidth(), getHeight());
		}
		if(type == BodyType.Open){
			g.setColor(new Color(163,222,87));
			g.fillRect(getPointWidth(), getPointHeight(), getWidth(), getHeight());
		}
		this.renderOutline(g, Color.BLACK);
	}
	
}
