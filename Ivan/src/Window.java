import java.awt.Canvas;
import javax.swing.JFrame;

public class Window extends Canvas{

	private static final long serialVersionUID = 2474038862145463208L;
	
	public static boolean editor = false;
	
	public Window(int width, int height, String title, Ivan game) {
		
		
		JFrame frame = new JFrame(title);
		if(Ivan.defResolution) {
			frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			frame.setUndecorated(true);
		}else 
			frame.setSize((int) (width/1.25), (int) (height/1.25));//for custom
			
//		width *= 1/Ivan.scale;
//			height *= 1/Ivan.scale;
			
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);
		frame.setVisible(true);
		
		
		frame.add(game);
		
		
//		frame.setSize(1080,768);
//		frame.setPreferredSize(new Dimension(width, height));
//		frame.setMaximumSize(new Dimension(width, height));
//		frame.setMinimumSize(new Dimension(width, height));
		
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setResizable(false);
//		frame.setLocationRelativeTo(null);//da ne se poqvqva prozoreca gore v lqvo
//		frame.add(game);
		//frame.setVisible(true);
		game.start();
		
		
	}
	
	public Window(int width, int height, String title, LevelEditor editor) {
		JFrame frame = new JFrame("Level Maker");
		frame.setAlwaysOnTop( true );
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Display the window.
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
//		frame.setResizable(false);
		Window.editor = true;
		frame.setSize(width, height);
		LevelEditor.frame = frame;
		
//		frame.setIgnoreRepaint(true);
		//frame.setVisible(true);
		//frame.add(editor.a);
		
	}
}
