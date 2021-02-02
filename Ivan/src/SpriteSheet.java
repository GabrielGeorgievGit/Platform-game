import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class SpriteSheet implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private BufferedImage sprite;
	
	public SpriteSheet(BufferedImage ss) {
		this.sprite = ss;
	}
	
	public BufferedImage grabImage(int x, int y, int width, int height) {
		BufferedImage img = sprite.getSubimage(x, y, width, height);
		img = resize(img, 100*(15/10), 100);
		return img;
	}
	
	public static BufferedImage grabImage(BufferedImage sprite, int x, int y, int width, int height) {
		BufferedImage img = sprite.getSubimage(x, y, width, height);
		//img = resize(img, 100*(15/10), 100);
		return img;
	}
	
    public static BufferedImage resize(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    
    public static BufferedImage nextRender(BufferedImage[] positions, int index) {
//    	if(index == positions.length - 1) index = 0;
//    	else ++index;
    	
    	return positions[index];
    	
    }
}
