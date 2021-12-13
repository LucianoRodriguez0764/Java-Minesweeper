package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameMain {
	
	public static void main(String[] args) {
		
		System.out.println("Holitassss");
		
		new GameFrame();
		
		System.out.println(123/30 +" "+ 201/30);
		
	}
	
	
}

class GameFrame extends JFrame {
	
	GamePanel gamePanel = new GamePanel(this); 
	ImageIcon minesweeperIcon = new ImageIcon(getClass().getResource("/minesweepericon2.0.png"));
	Thread thread = new Thread(new Rendering(this)); 
	static boolean winTheGame = false;
	MouseKeys mouseKeys = new MouseKeys(this);

	
	public GameFrame() {
		
		setTitle("BuscaMinas 0.7");
		setBounds(200, 150, 800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(minesweeperIcon.getImage());
		
		add(this.gamePanel);
		addMouseListener(this.mouseKeys);
		
		setVisible(true);
		setResizable(false);
				
		thread.start();

		
	}
	
	public void updateGame() {
		
		gamePanel.repaint();
		winTheGame = gamePanel.checkWin();
		
	}
	
	
	

}

class GamePanel extends JPanel {
	
	Image cubeImage = new ImageIcon(getClass().getResource("/newSprites/cube.png")).getImage();
	Image cube0bombsImage = new ImageIcon(getClass().getResource("/newSprites/cube0bombs.png")).getImage();
	Image cube1bombImage = new ImageIcon(getClass().getResource("/newSprites/cube1bomb.png")).getImage();
	Image cube2bombsImage = new ImageIcon(getClass().getResource("/newSprites/cube2bombs.png")).getImage();
	Image cube3bombsImage = new ImageIcon(getClass().getResource("/newSprites/cube3bombs.png")).getImage();
	Image cube4bombsImage = new ImageIcon(getClass().getResource("/newSprites/cube4bombs.png")).getImage();
	Image cube5bombsImage = new ImageIcon(getClass().getResource("/newSprites/cube5bombs.png")).getImage();
	Image cube6bombsImage = new ImageIcon(getClass().getResource("/newSprites/cube6bombs.png")).getImage();
	Image cube7bombsImage = new ImageIcon(getClass().getResource("/newSprites/cube7bombs.png")).getImage();
	Image cube8bombsImage = new ImageIcon(getClass().getResource("/newSprites/cube8bombs.png")).getImage();
	Image bombImage = new ImageIcon(getClass().getResource("/newSprites/bomb.png")).getImage();
	Image redBombImage = new ImageIcon(getClass().getResource("/newSprites/redbomb.png")).getImage();
	Image bombFoundImage = new ImageIcon(getClass().getResource("/newSprites/bombFound.png")).getImage();
	Image cubeFlaggedImage = new ImageIcon(getClass().getResource("/newSprites/cubeFlagged.png")).getImage();
	Image cubeIncognitoImage = new ImageIcon(getClass().getResource("/newSprites/cubeIncognito.png")).getImage();
	Image defaultImage = new ImageIcon(getClass().getResource("/newSprites/default.png")).getImage();
	Image happyFaceImage = new ImageIcon(getClass().getResource("/newSprites/happyButton.png")).getImage();
	Image deathFaceImage = new ImageIcon(getClass().getResource("/newSprites/sadButton.png")).getImage();
	Image surpriseFaceImage = new ImageIcon(getClass().getResource("/newSprites/surpriseButton.png")).getImage();
	Image coolFaceImage = new ImageIcon(getClass().getResource("/newSprites/coolFaceButton.png")).getImage();

	
	public static byte imageState = 1;
	
	Random rnd = new Random();

	GameFrame gameFrame;
	
	byte gameWidth;
	byte gameHeight;
	static int BOMBS_QUANTITY; 
	static int[] BOMBS_LOCATIONS;

	JLabel coords = new JLabel("Good Luck!");
	JLabel cheat = new JLabel();
	JButton again = new JButton("Play Again!");
	
	int coordX, coordY;
	byte[] allCubeStatus;
	
	public GamePanel(GameFrame gameFrame) {
		
		setLayout(null);
		setBounds(200, 150, 800, 600);
		setBackground(new Color(100,100,100));
		this.gameWidth  = 10;
		this.gameHeight = 10;
		this.BOMBS_QUANTITY = 10;
		this.BOMBS_LOCATIONS = new int[BOMBS_QUANTITY]; 
		this.allCubeStatus = new byte[gameWidth*gameHeight];
		this.gameFrame = gameFrame;
		
		coords.setBounds(10, 500, 200, 35);
		coords.setFont(new Font(getFont().getName(), Font.PLAIN, 30));
		coords.setForeground(new Color(255,255,255));
		
		cheat.setBounds(10, 535, 200, 15);
		cheat.setFont(new Font(getFont().getName(), Font.PLAIN, 15));
		cheat.setForeground(new Color(176,176,176));
		
		add(cheat);
		add(coords);
		again.setBounds(650,500,100,40);
		again.addActionListener(new ButtonAction(this));
		again.setFocusable(false);
		add(again);
		
		// CUBE STATUS RANDOMIZER
		
		resetGame();
		
		
	}
	
	public boolean checkWin() {

		int k=0;
		
		for(int i=0; i< allCubeStatus.length; i++) {
			
			if(allCubeStatus[i]>=10 && allCubeStatus[i]<=18 || allCubeStatus[i]>=0 && allCubeStatus[i]<=8) {
				return false;
			} else 
			
			if(allCubeStatus[i]==19) {
				k++;
			}
			// ALL OTHER FLAGS WILL TRIGGER YOU WIN
			
			
		}
		
		
		if(k==BOMBS_QUANTITY) {
			coords.setText("YOU WIN");
			imageState=4;
			return true;
		} else {
			return false;
		}
		
		
	}

	void resetGame() {
		
		int j;
		boolean isSame = false;
		GamePanel.imageState = 1;
		cheat.setText("");
		
		for(int k=0; k<allCubeStatus.length; k++) {
			
			allCubeStatus[k] = 0;
			
		}
		
		
		
//		for(int k=0; k<BOMBS_LOCATION.length; k++) {
//			
//			BOMBS_LOCATION[k] = -1;
//			
//		}
		

				
		for(int i=0; i<BOMBS_QUANTITY; i++) {
			
			do {
				
			j = rnd.nextInt(gameHeight*gameWidth);
			isSame = false;
			
			for(int k=0; k<BOMBS_LOCATIONS.length; k++) {
				if (j == BOMBS_LOCATIONS[k]) { 
					isSame = true;
					break;
				}
			}
			} while(isSame);
			
			allCubeStatus[j] = 9;
			BOMBS_LOCATIONS[i] = j;
			
			
		}	
		
		setTheCorrectNumbers();
		
	}

	private void setTheCorrectNumbers() {
		
		int bombId;
		int neighbor;

		for(int k=0; k<BOMBS_LOCATIONS.length; k++) {
			
			bombId = BOMBS_LOCATIONS[k];
			
			for(int i=-1; i<= 1; i++) {
			for(int j=-1; j<= 1; j++) {
					
				neighbor = (bombId + i + j*gameWidth); 
				
				if(!(neighbor == bombId || neighbor < 0 || neighbor > (gameWidth*gameHeight-1) )) {
					if(allCubeStatus[neighbor]!=9) {
						
						
					if(!(i==1 && (bombId%gameWidth == gameWidth-1)) &&
						!(i==-1 && (bombId%gameWidth == 0))) {	
						
					allCubeStatus[neighbor]++;
					}
					}
				}
					
			}		
			}
			
			
			
			
			
			
		}
		
	}

	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		super.paintComponent(g);
		
		g2.fillRect(0, 0, 40, 40);

		paintGrilla(g2);
		
		paintFace(g2);
		
	}

	private void paintFace(Graphics2D g2) {

		switch (imageState) {
		case 1 -> g2.drawImage(happyFaceImage, 700, 15, 50,50, null);
		case 2 -> g2.drawImage(deathFaceImage, 700, 15, 50,50, null);
		case 3 -> g2.drawImage(surpriseFaceImage, 700, 15, 50,50, null);
		case 4 -> g2.drawImage(coolFaceImage, 700, 15, 50,50, null);
		}
	}

	private void paintGrilla(Graphics2D g2) {
				
		for(int i=0; i< allCubeStatus.length; i++) {
			
			coordX = (i% gameWidth)*30;
			coordY = (i/ gameWidth)*30;
						
			switch (allCubeStatus[i]) {
			
			
			// BOMB UNCOVERED AFTER LOSING						
			case -2 -> g2.drawImage(bombImage, coordX, coordY, 30,30, null);
			// THIS IS THE BOMB THAT MAKE YOU LOOSE
			case -1 -> g2.drawImage(redBombImage, coordX, coordY, 30,30, null);
			// 0 TO 8 ARE THE NORMAL BOXES WITH 0-8 NUMBERS
			// 9 IS THE COVERED BOMB
			case 0,1,2,3,4,5,6,7,8,9
			        
			        -> g2.drawImage(cubeImage, coordX, coordY, 30,30, null);
			
			// THIS IS ALL THE SAME BUT FLAGGED
			case 10,11,12,13,14,15,16,17,18,19
			
			        -> g2.drawImage(cubeFlaggedImage, coordX, coordY, 30,30, null);
			 
			// ALL THIS CASES ARE THE ACTUAL TEXTURES FOR WHEN IS CLICKED
			// 29 IS THE BOMB WITH A RED CROSS. ORIGINAL, IS WHEN YOU FAIL A FLAG
			// BUT I LIKE IT MORE THAT THIS TEXTURE IS FOR THE ONE YOU FOUND.
			case 20 -> g2.drawImage(cube0bombsImage, coordX, coordY, 30,30, null);
			case 21 -> g2.drawImage(cube1bombImage, coordX, coordY, 30,30, null);
			case 22 -> g2.drawImage(cube2bombsImage, coordX, coordY, 30,30, null);
			case 23 -> g2.drawImage(cube3bombsImage, coordX, coordY, 30,30, null);
			case 24 -> g2.drawImage(cube4bombsImage, coordX, coordY, 30,30, null);
			case 25 -> g2.drawImage(cube5bombsImage, coordX, coordY, 30,30, null);
			case 26 -> g2.drawImage(cube6bombsImage, coordX, coordY, 30,30, null);
			case 27 -> g2.drawImage(cube7bombsImage, coordX, coordY, 30,30, null);
			case 28 -> g2.drawImage(cube8bombsImage, coordX, coordY, 30,30, null);
			case 29 -> g2.drawImage(bombFoundImage, coordX, coordY, 30,30, null);
			
			// ALL THIS ARE THE ICOGNITED SYMBOL CUBES.
			case 30,31,32,33,34,35,36,37,38,39
			
	        -> g2.drawImage(cubeIncognitoImage, coordX, coordY, 30,30, null);	
	        
	        // AND ALL OF THIS IS WHEN YOU KEEP THE CLICK AND LOOKS LIKE NOTHING.
			case 40,41,42,43,44,45,46,47,48,49
			
	        -> g2.drawImage(cube0bombsImage, coordX, coordY, 30,30, null);	
				
	        // FOR SOME KIND OF ERROR, A "D" LETTER.
			default -> g2.drawImage(defaultImage, coordX, coordY, 30,30, null);
			
			}
			
			
			
		}
		
		
	}
	
	public static String revealStatus(byte i) {
		
		String state = "";
		switch(i) {
		case 0 ->  state = "It's all clear!";
		case 1 ->  state = "1 bomb near!";
		case 2 ->  state = "2 bombs near!";
		case 3 ->  state = "3 bombs near!";
		case 4 ->  state = "4 bombs near!";
		case 5 ->  state = "5 bombs near!";
		case 6 ->  state = "6 bombs near!";
		case 7 ->  state = "7 bombs near!";
		case 8 ->  state = "8 bombs near!";
		case 9 ->  state = "it's a bomb!";
		}
		return state;
	}

	public void setVisibleBombs() {

		for(int i=0; i< allCubeStatus.length; i++) {
			// If is a covered-bomb or incognited-bomb
			if  (allCubeStatus[i]==9 || allCubeStatus[i]==39) 
				allCubeStatus[i]=-2 ;
			else if (allCubeStatus[i]==19)
				allCubeStatus[i]=29;
			
		}	
		
		
	}

	
	
	
}

class Rendering extends Thread{
	
	private GameFrame gameFrame;
	private GamePanel gamePanel;
	
	private boolean isOnGame = true;
	final short FPS = 60;
	final long delayTime = 1000/FPS;
	long start;
	
	public Rendering(GameFrame gameFrame) {
		this.gameFrame=gameFrame;
		this.gamePanel=gameFrame.gamePanel;
	}
	
	
	public void run() {
		
		while(isOnGame) {
			
			gameFrame.updateGame();
			start = System.currentTimeMillis();
			
			while(start >= System.currentTimeMillis()-delayTime); //Just keep on wait.
			
		}
		
		
	}
	
	
}



class MouseKeys implements MouseListener{
	
	
	GamePanel gamePanel;
	GameFrame gameFrame;
	int x=0;
	int y=20;
	int coordX;
	int coordY;
	int cubeId;
	public boolean weAreAlive = true;
	static int[] WHITE_IDS;
	int id_iterator = 0;
	
	
	public MouseKeys(GameFrame gameFrame) {
		this.gameFrame=gameFrame;
		this.gamePanel=gameFrame.gamePanel;
		WHITE_IDS = new int[gamePanel.gameHeight*gamePanel.gameWidth];
		
		for(int i=0; i<WHITE_IDS.length; i++) {
			WHITE_IDS[i] = -1;
		}
		
		
	}
	
	private void kills() {

		GamePanel.imageState = 2;
		weAreAlive=false;
		gamePanel.setVisibleBombs();
		gamePanel.coords.setText("Death");
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
		coordX = e.getX()-8;
		coordY = e.getY()-30;
		cubeId = coordX/30 + gamePanel.gameWidth*(coordY/30);
		
		
		if(e.isShiftDown() && e.isControlDown() && e.isAltDown() /*&& e.isAltGraphDown()*/) {
			System.out.print(GamePanel.revealStatus(gamePanel.allCubeStatus[cubeId]) +" ");
			gamePanel.cheat.setText(GamePanel.revealStatus(gamePanel.allCubeStatus[cubeId]));
			}
		
		
		
		if(e.getButton() == MouseEvent.BUTTON1 && weAreAlive && !(coordX/30 > 19 || coordY/30 > 14)) {
			
			GamePanel.imageState = 3;
			
			// Normal box, will be showed	
			if(gamePanel.allCubeStatus[cubeId] >= 0 && gamePanel.allCubeStatus[cubeId] <= 9) {
				
				gamePanel.allCubeStatus[cubeId] += 40;
				
			}
			
		}
		
		
		
//		ERA DE TESTING
//		gamePanel.setBackground(new Color(100+x,100+x,100+x));
//		
//		if(x+y+100 > 255 || x+y+100 < 100) {
//			y = -y;
//		}
//		
//		x+=y;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		GamePanel.imageState = 1;

		
//		gamePanel.setBackground(new Color(50,50,50));
		
		if(!(coordX/30 >= gamePanel.gameWidth || coordY/30 >= gamePanel.gameHeight) && weAreAlive
			&& !GameFrame.winTheGame	) {
		if(!((e.getX()-8)/30 >= gamePanel.gameWidth || (e.getY()-30)/30 >= gamePanel.gameHeight))
			gamePanel.coords.setText(String.format("(%s, %s)", coordX/30 +1,coordY/30 +1));

				
		// CLICK BUTTON
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			
		gamePanel.cheat.setText("");

			
		// Normal box, will be showed	
		if(gamePanel.allCubeStatus[cubeId] >= 0 && gamePanel.allCubeStatus[cubeId] <= 8) {
			
			gamePanel.allCubeStatus[cubeId] += 20;
			
		} else
		
		// A bomb being uncovered
		if(gamePanel.allCubeStatus[cubeId] == 9) {
			
			gamePanel.allCubeStatus[cubeId] = -1;
			kills();


		} else
		
		// A incognito clicked being showed
		if(gamePanel.allCubeStatus[cubeId] >= 30 && gamePanel.allCubeStatus[cubeId] <= 38) {
			
			if(!((e.getX()-8)/30 >= gamePanel.gameWidth || (e.getY()-30)/30 >= gamePanel.gameHeight)) {
			gamePanel.allCubeStatus[cubeId] -= 10;
			}
		} else
			
		if(gamePanel.allCubeStatus[cubeId] == 39) {
			
			if(!((e.getX()-8)/30 >= gamePanel.gameWidth || (e.getY()-30)/30 >= gamePanel.gameHeight)) {
			gamePanel.allCubeStatus[cubeId] = -1;
			kills();
			}
		} else
		
		// Is being "click-keeped"
		if(gamePanel.allCubeStatus[cubeId] >= 40 && gamePanel.allCubeStatus[cubeId] <= 48) {
			
			// Si el click se released fuera del margen, se cancela
			if((e.getX()-8)/30 >= gamePanel.gameWidth || (e.getY()-30)/30 >= gamePanel.gameHeight)
				gamePanel.allCubeStatus[cubeId] -= 40;
			else {
				
				if(gamePanel.allCubeStatus[cubeId]==40) {
					showWhites(cubeId);
				} else {
				
				gamePanel.allCubeStatus[cubeId] -= 20;
				}
			}
			
		} else
			
		if(gamePanel.allCubeStatus[cubeId] == 49) {
				
			if((e.getX()-8)/30 >= gamePanel.gameWidth || (e.getY()-30)/30 >= gamePanel.gameHeight)
				gamePanel.allCubeStatus[cubeId] -= 40;
			else {	
			
			gamePanel.allCubeStatus[cubeId] = -1;
			kills();
			
			}
				
		}
			

		
		
		// RIGHT CLICK
		
		} else if (e.getButton() == MouseEvent.BUTTON3){
			
		// A normal box being flaged
		if(gamePanel.allCubeStatus[cubeId] >= 0 && gamePanel.allCubeStatus[cubeId] <= 9) {
			
			gamePanel.allCubeStatus[cubeId] += 10;
					
		} else
			
		// A flagged box being incognited
		if(gamePanel.allCubeStatus[cubeId] >= 10 && gamePanel.allCubeStatus[cubeId] <= 19) {
				
			gamePanel.allCubeStatus[cubeId] += 20;
						
		} else
		
		// A incognited box will turn normal again
		if(gamePanel.allCubeStatus[cubeId] >= 30 && gamePanel.allCubeStatus[cubeId] <= 39) {
				
			gamePanel.allCubeStatus[cubeId] -= 30;
						
		}
		
		}
		
		// ONLY FOR TESTING
//		 if (e.isControlDown()){
//		gamePanel.allCubeStatus[cubeId] = 2;
//		}
//		 if (e.isShiftDown()){
//		gamePanel.allCubeStatus[cubeId] = 3;
//		}
		 
		 
//		if(e.isShiftDown() && e.isControlDown() && e.isAltDown() /*&& e.isAltGraphDown()*/) {
//		System.out.print(GamePanel.revealStatus(gamePanel.allCubeStatus[cubeId]) +" ");
//		gamePanel.cheat.setText(GamePanel.revealStatus(gamePanel.allCubeStatus[cubeId]));
//		}
		System.out.print(String.format("(%s, %s)", coordX,coordY));
		System.out.println("  " + (coordX/30 +1) + "--" + (coordY/30 +1));

		
		
		}
		
	}

	

	private void showWhites(int cubeId) {
		
		System.out.println("SHOULD SHOW WHITES");	
		
		int neighbor;
		
		
		for(int i=-1; i<= 1; i++) {
			for(int j=-1; j<= 1; j++) {
					
				neighbor = (cubeId + i + j*gamePanel.gameWidth); 
				
				if(!(neighbor == cubeId || neighbor < 0 || neighbor > (gamePanel.gameWidth*gamePanel.gameHeight-1) )) {
					
					if( gamePanel.allCubeStatus[neighbor] >= 0 && gamePanel.allCubeStatus[neighbor] <= 9 ) {
						
						
					if(!(i==1 && (cubeId%gamePanel.gameWidth == gamePanel.gameWidth-1)) &&
						!(i==-1 && (cubeId%gamePanel.gameWidth == 0))) {	
						
						System.out.println("NEIGHBOR: "+neighbor+" , STATUS:  "+gamePanel.allCubeStatus[neighbor]);
						
						if(gamePanel.allCubeStatus[neighbor] == 0 ) {
							WHITE_IDS[id_iterator] = neighbor;
							id_iterator++;
						}
						
						gamePanel.allCubeStatus[neighbor] += 20;
						
						
						
						
					}}}}}
					
		for(int i=0; i<WHITE_IDS.length; i++) {
			if(WHITE_IDS[i] == -1) break;
			System.out.println(WHITE_IDS[i]);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	
	
	
	
}


class ButtonAction implements ActionListener{
	
	GamePanel gamePanel;
	GameFrame gameFrame;
	
	
	public ButtonAction(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		this.gameFrame = gamePanel.gameFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		gamePanel.resetGame();
		gamePanel.coords.setText("Try again!");
		gameFrame.mouseKeys.weAreAlive=true;
		
	}
	
	
}
