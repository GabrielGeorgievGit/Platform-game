import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.LinkedList;

public class Handler implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LinkedList<GameObject> object = new LinkedList<>();
	
	public void tick() {
		for(int i = 0; i < object.size(); ++i) {
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
	}

	
	public void render(Graphics2D g) {
		//g.scale(Ivan.scale, Ivan.scale);
		for(int i = 0; i < object.size(); ++i) {
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
			
		}
		//g.scale(1/Ivan.scale, 1/Ivan.scale);
	}
	
	public void clearAll() {
		Ivan.player.setX(0);
		Ivan.player.setY(0);
		Ivan.player.width = 0;
		Ivan.player.height = 0;
		this.object.clear();
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	public void removeObject(int index) {
		this.object.remove(index);
	}
}
