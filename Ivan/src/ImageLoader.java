import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	BufferedImage img;
	
	public BufferedImage loadImg(String path) {
		try {
			img = ImageIO.read(new File(path));
//			img = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
}
