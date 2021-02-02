import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class LevelEditor extends MouseAdapter {
	static boolean open = true;
	static Window editor;
	static Handler handler;
	//static Handler handler = new Handler();
	
	HUD hud;
	Font font;
	static JFrame frame;
	static Player player = Ivan.player;
	//static Map<Integer, GameObject> addedObjects = new HashMap<>();
	//+ Buton za dobavqne na obekti ot scene kum handler
	
	//public static Canvas a = new Canvas();
	
	static JLabel frameNum;
	static JLabel statusLabel;
	static JButton confirm;
	static JTextArea coord, size;
	static JButton addObject = new JButton();
	static JButton changeObject = new JButton();
	static JButton prev = new JButton("prev");
	static JButton next = new JButton("next");
	static JLabel typeObject = new JLabel();
	static JLabel sceneNum = new JLabel();
	static JLabel currObj = new JLabel();
	static JTextArea levelNameArea = new JTextArea();
	static JComboBox<String> scenesCBox = new JComboBox<>();
	
	static int x, y, width, height;
	static String level = "", scene = "";
//	static Wall wall;
//	static Player player;
	
	static int sceneIndex = 0;
	
	static String fileName = "";
	static String levelName = "";
	
	public LevelEditor(Handler handler, HUD hud) {
		
		this.hud = hud;
		LevelEditor.handler = handler;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(open)
			if(mouseOver(mx, my, 1150, 250, 200, 64)) {
				if(!Window.editor)
					editor = new Window(650, 400, "Level editor", this);
					addButtons();
			}//find way to choose background
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			}else return false;
		}else return false;
	}
	
	public static void tick() {
		if(open && Window.editor) {
			//izvadi butoni
			
		}
	}
	
	public void render(Graphics2D g) {

		if(open) {
			
			font = new Font("arial", 1, 40);
			g.setFont(font);
			
			g.drawString("Editor", 1150, 220);
			
			
			g.drawRect(1150, 250, 200, 64);
			g.drawString("Add", 1210, 295);
			
		}
		//handler.render(g);
	}
	
	private static void addButtons() {
		frame.setLayout(null);//bad practice
		
		JLabel frameNum = new JLabel("Objects: " + handler.object.size());
		frameNum.setBounds(10, 2, 100, 30);
		frame.add(frameNum);
		
		JLabel label = new JLabel("Add object: ");
		label.setBounds(10, 10, 200, 50);
		frame.add(label);
		
		statusLabel = new JLabel("");
		statusLabel.setBounds(160, 10, 100, 30);
		frame.add(statusLabel);
		
		coord = new JTextArea();
    	coord.setBounds(160, 50, 100, 102);//napravi taka che da se chete 5 i 6 ti red
    	frame.add(coord);
		
		addObjButtons();
		col3();
		removeAndSaveButtons();
		
	    frame.setVisible(true);
	}
	
	private static void addObjButtons() {
		
    	addObject.setBounds(160, 180, 100, 30);
		
    	addObject.setText("Add");
    	addObject.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			//System.out.println("render wall");
    			final String []crd = coord.getText().split("\n");
    			
    			x = Integer.parseInt(crd[0]);
				y = Integer.parseInt(crd[1]);
				width = Integer.parseInt(crd[2]);
				height = Integer.parseInt(crd[3]);
				if(crd.length == 6) {
					level = crd[4];
					scene = crd[5];//try change level and scene
				}
				
				
				switch(typeObject.getText()) {
				case "Wall": handler.addObject(
						new Wall(x, y, ID.Wall, width, height, Wall.DEF_COLOR));
					break;
				case "Player":
					player.change(x, y, width, height);
					if(!handler.object.contains(player)) handler.addObject(player);
						/*
						new Player(x, y, ID.Player, width, height,
						Player.DEF_HEALTH, Player.DEF_SPEED, Player.DEF_COLOR, handler)); 
						 */
						break;
				case "Enemy": handler.addObject(
						new Enemy(x, y, ID.Enemy, width, height, 100, 3, Enemy.DEF_COLOR, handler));
					break;
					
				case "Door": handler.addObject(
					new Door(x, y, ID.Door, width, height, Door.DEF_COLOR, level, scene, player));
					//udulzi koordinatite pri dobavqne za da ima opciq level i scene
					break;
					
				case "Spike": handler.addObject(
						new Spike(x, y, ID.Spike, width, height, Spike.DEF_COLOR));
						break;
					
				default: System.out.println("Error adding " + typeObject);	
				}
    		}
    	});
    	
		JButton walls = new JButton("Add wall");  
	    walls.setBounds(10, 50, 100, 30);
	    walls.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	System.out.println("wall");
	        	//frame.setLayout(new FlowLayout());
	        	statusLabel.setText("Adding");
	        	typeObject.setText("Wall");
	        	
	        	changeObject.setVisible(false);
		        addObject.setVisible(true);
		        
		        sceneNum.setText("In scene: " + handler.object.size());
	         }
	      });
	    
		JButton players = new JButton("Add player");  
	    players.setBounds(10, 100, 100, 30);
	    players.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	System.out.println("player");
	        	//frame.setLayout(new FlowLayout());
	        	statusLabel.setText("Adding");
	        	typeObject.setText("Player");
	        	
	        	changeObject.setVisible(false);
		        addObject.setVisible(true);
		        
		        sceneNum.setText("In scene: " + handler.object.size());
	         }
	      });
	    
		JButton enemys = new JButton("Add enemy");  
	    enemys.setBounds(10, 150, 100, 30);
	    enemys.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	System.out.println("enemy");
	        	//frame.setLayout(new FlowLayout());
	        	statusLabel.setText("Adding");
	        	typeObject.setText("Enemy");
	        	
	        	changeObject.setVisible(false);
		        addObject.setVisible(true);
		        
		        sceneNum.setText("In scene: " + handler.object.size());
	         }
	      });
	    
	    //dobavi door buton za dobavqne
	    JButton doors = new JButton("Add door");  
	    doors.setBounds(10, 200, 100, 30);
	    doors.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	System.out.println("door");
	        	//frame.setLayout(new FlowLayout());
	        	statusLabel.setText("Adding");
	        	typeObject.setText("Door");
	        	
	        	changeObject.setVisible(false);
		        addObject.setVisible(true);
		        
		        sceneNum.setText("In scene: " + handler.object.size());
	         }
	      });
	    
	    JButton spikes = new JButton("Add spike");  
	    spikes.setBounds(10, 250, 100, 30);
	    spikes.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	System.out.println("spike");
	        	//frame.setLayout(new FlowLayout());
	        	statusLabel.setText("Adding");
	        	typeObject.setText("Spike");
	        	
	        	changeObject.setVisible(false);
		        addObject.setVisible(true);
		        
		        sceneNum.setText("In scene: " + handler.object.size());
	         }
	      });
	    
	    
	    frame.add(walls);
	    frame.add(players);
	    frame.add(enemys);
	    frame.add(doors);
	    frame.add(spikes);
    	frame.add(addObject);
	}
	
	private static void col3() {
    	
		typeObject.setText("");
		typeObject.setBounds(240, 10, 100, 30);
		frame.add(typeObject);
		
    	JLabel scrollLabel = new JLabel("Scroll trought objects");
    	scrollLabel.setBounds(330, 10, 150, 30);
    	frame.add(scrollLabel);
    	
    	JButton scroll = new JButton("scroll");
    	scroll.setBounds(350, 50, 100, 30);
    	scroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				statusLabel.setText("scrolling");
				
				if(!handler.object.isEmpty()) {
					GameObject o = handler.object.getFirst();
					o.select();
					
					sceneIndex = 0;
					
					String bounds = boundsToString(o);
					coord.setText(bounds);
					typeObject.setText(o.id.toString());
					coord.setText(boundsToString(o));
					
					if(o.id == ID.Door) {
						Door d = (Door)o;
						coord.setText(coord.getText() + '\n' + 
							d.getLevel() + '\n' + d.getScene());
					}
					
				}
				
				sceneNum.setText("In scene: " + handler.object.size());
				currObj.setText("Current: " + sceneIndex);
		    	
		    	changeObject.setText("Change");
		    	changeObject.setBounds(addObject.getBounds());
		    	changeObject.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						final String []crd = coord.getText().split("\n");
		    			
		    			final int x = Integer.parseInt(crd[0]);
						final int y = Integer.parseInt(crd[1]);
						final int width = Integer.parseInt(crd[2]);
						final int height = Integer.parseInt(crd[3]);
						
						GameObject o = handler.object.get(sceneIndex);
						
						switch(typeObject.getText()) {
							case "Door":
								((Door) o).setLevel(crd[4]);;
								((Door) o).setScene(crd[5]);
								
								break;
//							case "Shot": 
								
						}
						o.setX(x);
						o.setY(y);
						o.setWidth(width);
						o.setHeight(height);//
					}
		    	});
		    	frame.add(changeObject);
		    	addObject.setVisible(false);
				changeObject.setVisible(true);
				
				
				scenesCBox.removeAllItems();
				levelName = levelNameArea.getText();
				for(String s : getFileNames()) 
					scenesCBox.addItem(s);
			}
    	});
    	frame.add(scroll);
    	
    	sceneNum.setText("In scene: " + handler.object.size());
    	sceneNum.setBounds(350, 80, 100, 30);
    	frame.add(sceneNum);

    	currObj.setText("Current: " + sceneIndex);
    	currObj.setBounds(350, 100, 100, 30);
    	frame.add(currObj);
    	
    	prev.setBounds(330, 130, 70, 30);
    	prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(handler.object.size() > 1) {
					GameObject o = handler.object.get(sceneIndex);
					o.unselect();
					if(sceneIndex == 0)
						sceneIndex = handler.object.size() - 1;
					else --sceneIndex;
					
					o = handler.object.get(sceneIndex);
					typeObject.setText(o.id.toString());
					coord.setText(boundsToString(o));
					
					if(o.id == ID.Door) {
						Door d = (Door)o;
						coord.setText(coord.getText() + '\n' + 
							d.getLevel() + '\n' + d.getScene());
					}
					
					o.select();
				}
				currObj.setText("Current: " + sceneIndex);
				sceneNum.setText("In scene: " + handler.object.size());
			}
		});
    	frame.add(prev);
    	
    	next.setBounds(400, 130, 70, 30);
    	next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final int hsize = handler.object.size();
				if(hsize > 1) {
					GameObject o = handler.object.get(sceneIndex);
					o.unselect();
					if(sceneIndex == hsize - 1)
						sceneIndex = 0;
					else ++sceneIndex;
					
					o = handler.object.get(sceneIndex);
					typeObject.setText(o.id.toString());
					coord.setText(boundsToString(o));
					
					if(o.id == ID.Door) {
						Door d = (Door)o;
						coord.setText(coord.getText() + '\n' + 
							d.getLevel() + '\n' + d.getScene());
					}
					
					o.select();
				}
				currObj.setText("Current: " + sceneIndex);
				sceneNum.setText("In scene: " + handler.object.size());
			}
		});
    	frame.add(next);
	}
	
	private static void removeAndSaveButtons() {

    	JButton removeObj = new JButton("remove");
    	removeObj.setBounds(160, 260, 100, 30);
    	removeObj.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				final int delIndex = sceneIndex;
				if(sceneIndex == 0) next.doClick();
				else prev.doClick();
				if(handler.object.get(delIndex).id == ID.Player) Ivan.player.remove();
				handler.removeObject(delIndex);
			}
		});
    	frame.add(removeObj);
    	
    	JButton removeAllObj = new JButton("remove all");
    	removeAllObj.setBounds(160, 310, 100, 30);
    	removeAllObj.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				handler.clearAll();
//				scroll.doClick();
				currObj.setText("Current: 0");
				typeObject.setText("");
				coord.setText("");
				sceneNum.setText("0");
			}
		});
    	frame.add(removeAllObj);
    	
    	JLabel levelNumLabel = new JLabel("Level: ");
    	levelNumLabel.setBounds(350, 190, 100, 30);
    	frame.add(levelNumLabel);
    	
    	levelNameArea.setBounds(350, 220, 100, 15);
    	frame.add(levelNameArea);
    	
    	JLabel nameLabel = new JLabel("scene name:");
    	nameLabel.setBounds(350, 230, 100, 30);
    	frame.add(nameLabel);
    	
    	JTextArea sceneName = new JTextArea();
    	sceneName.setBounds(350, 260, 100, 15);
    	frame.add(sceneName);
    	
    	scenesCBox.setBounds(270, 180, 70, 30);
    	
    	frame.add(scenesCBox);
    	JButton saveScene = new JButton("save");
    	saveScene.setBounds(400, 310, 70, 30);
    	saveScene.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				levelName = levelNameArea.getText();
				fileName = sceneName.getText();
				saveScene();
//				loadScene();
			}
		});
    	frame.add(saveScene);
    	
    	JButton loadScene = new JButton("load");
    	loadScene.setBounds(330, 310, 70, 30);
    	loadScene.addActionListener(new ActionListener() {
    		
			public void actionPerformed(ActionEvent e) {
				levelName = levelNameArea.getText();
				fileName = sceneName.getText();
				loadScene();
			}
		});
    	frame.add(loadScene);
	}
	static String boundsToString(GameObject o) {
		
		String res = "";
		res += (int)o.getX();
		res += "\n";
		res += (int)o.getY();
		res += "\n";
		res += (int)o.getWidth();
		res += "\n";
		res += (int)o.getHeight();
		
		return res;
	}
	
	private static void saveScene() {
		
		try {
			final String path = "Levels" + "/" + levelName;
			FileOutputStream f = new FileOutputStream(new File(path, fileName + ".txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			for(GameObject obj : handler.object)
				switch(obj.getId()) {
					
					case Shot: break;
					
//					case Enemy:
//						o.writeObject(new Enemy(
//							obj.x, obj.y, obj.id, obj.width, obj.height,
//							obj.health, obj.curSpeed, obj.color ,handler));
//						break;
					
					default: o.writeObject(obj);
				}
			
			System.out.println("Saved " + levelName + " " + fileName);
			o.close();
			f.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File save not found");
		} catch (IOException e) {
			System.out.println("Error stream");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void loadScene() {
		
		try {
			final String path = "Levels" + "/" + levelName;
			FileInputStream f = new FileInputStream(new File(path, fileName + ".txt"));
			ObjectInputStream o = new ObjectInputStream(f);
			GameObject obj;// = (GameObject) o.readObject();
			
			while(true) {
				try { obj = (GameObject) o.readObject();						
				} catch(Exception e) {break;}
				
				if(obj instanceof GameObject) {
					switch(obj.getId()) {
					 case Player: 
						player.change(obj.x, obj.y, obj.width, obj.height);
						player.sprite = SpriteSheet.grabImage(Ivan.playerSpriteSheet, 3, 1, 9, 15);
						player.loadPostions();
						obj = player;
						break;
					 case Shot:
						try {
							obj = (GameObject) o.readObject();						
						} catch(Exception e) {
							break;
						}
						 continue;
					 case Enemy: 
						 obj = new Enemy(
							obj.x, obj.y, obj.id, obj.width, obj.height,
							obj.health, obj.curSpeed, obj.color, handler);
						break;
					 
					 default: 
//						continue;
					}
				/*
					if(obj.getId() == ID.Player) {
						player.change(obj.x, obj.y, obj.width, obj.height);
						player.sprite = SpriteSheet.grabImage(Ivan.playerSpriteSheet, 3, 1, 9, 15);
						player.loadPostions();
						obj = player;
					} */
					handler.addObject(obj);
					
				} else break;
			}
			System.out.println("Loaded " + levelName + " " + fileName);
			
			o.close();
			f.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File " + levelName + "/" + fileName + " not found on load");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static String[] getFileNames() {
		File folder = new File("Levels" + "/" + levelName);
		File[] files = folder.listFiles();
		String[] names = new String[files.length];
		System.out.println(levelName);
		for (int i = 0; i < files.length; i++) {
		  if(files[i].isFile()) {
			  names[i] = files[i].getName();
			  System.out.println(files[i].getAbsoluteFile());
//		    System.out.println("File " + names[i]);
		  } else if (files[i].isDirectory()) {
//		    System.out.println("Directory " + files[i].getName());
		  }
		}
		return names;
	}
	
	public static void changeStage(String level, String scene) {
		handler.clearAll();
		levelName = level;
		fileName = scene;
		loadScene();
	}
	
}
