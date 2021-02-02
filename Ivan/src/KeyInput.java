import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	private boolean[] buttons = new boolean[5];
	static boolean z = false;
	static int countA = 0, countD = 0;
	static GameObject playerObject;
	/*
	 * 0-W 1-A 2-S 3-D 4-SPACE
	 */
	
	Ivan game;
	Player player;
	CoolDown cd = new CoolDown();
	
	
	public KeyInput(Handler handler, Ivan game, Player p) {
		this.handler = handler;
		
		this.game = game;
		this.player = p;
		
		Arrays.fill(buttons, false);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_Z) {
			LevelEditor.open = !LevelEditor.open;
		}
		for(int i = 0; i < handler.object.size(); ++i)
		{
			GameObject o = handler.object.get(i);
			KeyInput.playerObject = o;
			
			if(o.getId() == ID.Player) {
				//MAKE MAP AND STFU
				//System.out.println(key + Arrays.toString(buttons));
				/*
//				if(key == KeyEvent.VK_A) {
//					tempObject.y = 0;
//					tempObject.speed = tempObject.speedInUse;
//					tempObject.setSpeedX(player.speed[0]);
//				}
				
				//purvo D posle A
				
				if(key == KeyEvent.VK_A && buttons[3]) {
					//tempObject.y = 0;
					++countA;
					buttons[3] = false;
					tempObject.speed[0] = tempObject.speedInUse[0];
					buttons[1] = true;
					
					tempObject.setSpeedX(player.speed[0]);
					return;
//					++countA;
//					if(countA >= 1) {
//						tempObject.speed[1] = tempObject.speedInUse[1];
//						tempObject.y = 0;
//						tempObject.setSpeedX(player.speed[1]);
//						System.out.println("GGGGGGGGGG");
//						countA = 0;
//						return;
//					}
					
					
				}else {
					if(countA >= 1) {
//						if(key == KeyEvent.VK_D);
						
						buttons[1] = false;
						tempObject.speed[1] = tempObject.speedInUse[1];
						buttons[3] = true;
						
						//tempObject.setSpeedX(player.speed[1]);
					}
				}
				countA = 0;
				//purvo A posle D
				if(key == KeyEvent.VK_D && buttons[1]) {
					System.out.println("OOOOOOOOO");
					tempObject.speed[0] = tempObject.speedInUse[0];
					
					tempObject.setSpeedX(player.speed[0]);
				}
				*/
				if(key == KeyEvent.VK_W) {
					//tempObject.setSpeedY(-Player.defSpeed);
					buttons[0] = true;
				}
				if(key == KeyEvent.VK_A) {
					o.sidefaced = false;//left
					
					buttons[3] = false;
					o.speed[0] = o.speedInUse[0];
					buttons[1] = true;
					
					o.setSpeedX(o.speed[0]);
				}
				if(key == KeyEvent.VK_S) {
					buttons[2] = true;
				}
				if(key == KeyEvent.VK_D) {
					if(o.rightIndex == o.right.length - 1) o.rightIndex = 0;
			    	else ++o.rightIndex;
					o.sprite = SpriteSheet.nextRender(o.right, o.rightIndex);
					
					o.sidefaced = true;
					
					buttons[1] = false;
					o.speed[1] = o.speedInUse[1];
					buttons[3] = true;
					
					o.setSpeedX(o.speed[1]);
				}
				
				
				if(key == KeyEvent.VK_SHIFT) {
					key = 0;
					if(o.dashReady) {
						CoolDown.isMoving = buttons;
						CoolDown.obj = o;
						(new Thread(new CoolDown())).start();
						
					}
					else System.out.println("Dash on CD");
						
				}
				
				if(Ivan.Bound.doesCollide(o.x,
						o.y+1, o.width,
						o.height, handler.object).collide) {
					o.jumpReady = true;
					o.saveY = o.y;
					
					if(key == KeyEvent.VK_SPACE && o.jumpReady) {
						
						o.tryJump = true;
						buttons[4] = true;
					}
				}
					
				
				if(key == KeyEvent.VK_Q) {
					o.x = 0;
					o.y = 0;
				}
			}
			
		}
	 
	if(key == KeyEvent.VK_ESCAPE) {
		
		System.exit(0);
	}
}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); ++i) {
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				//for player 1
				if(key == KeyEvent.VK_W) buttons[0] = false;
				if(key == KeyEvent.VK_A) buttons[1] = false;
				if(key == KeyEvent.VK_S) buttons[2] = false;
				if(key == KeyEvent.VK_D) buttons[3] = false;
				if(key == KeyEvent.VK_SPACE) buttons[4] = false;
				
				if(!buttons[4]) {
					tempObject.tryJump = false;
					//tempObject.setSpeedY(tempObject.gravityInUse);
				}
				if(!buttons[3] && !buttons[1]) tempObject.setSpeedX(0);
			}
		}
	}
}
