package ie.gmit.sw.ai;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GameView extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;	
	private int cellspan;
	private int cellpadding;
	private int currentRow;
	private int currentCol;
	private boolean zoomOut;
	private int imageIndex;
	private int playerState;
	private int enemyState;
	private int enemyBossState;
	private Node[][] maze;
	private BufferedImage[] images;
	private Timer timer;
	private Sprite[] sprites;

	public GameView() {
	}

	public GameView(GameSetup game, Sprite[] sprites) {
		this.sprites = sprites;
		init();
		this.maze = game.getMaze();
		this.imageIndex = -1;
		this.playerState = 6;
		this.enemyState = 7;
		this.enemyBossState = 18;
		this.cellspan = 5;
		this.cellpadding = 2;
		this.timer = new Timer(100, this);
		this.timer.start();
		setBackground(Color.LIGHT_GRAY);
		setDoubleBuffered(true);
	}
	
	public void setCurrentRow(int row) {
		if (row < cellpadding){
			currentRow = cellpadding;
		}else if (row > (maze.length - 1) - cellpadding){
			currentRow = (maze.length - 1) - cellpadding;
		}else{
			currentRow = row;
		}
	}

	public void setCurrentCol(int col) {
		if (col < cellpadding){
			currentCol = cellpadding;
		}else if (col > (maze.length - 1) - cellpadding){
			currentCol = (maze.length - 1) - cellpadding;
		}else{
			currentCol = col;
		}
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
              
        cellspan = zoomOut ? maze.length : 5;         
        final int size = DEFAULT_VIEW_SIZE/cellspan;
        g2.drawRect(0, 0, GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
        for(int row = 0; row < cellspan; row++) {
        	for (int col = 0; col < cellspan; col++){  
        		int x1 = col * size;
        		int y1 = row * size;
        		
        		char ch = 'X';
        		ch = maze[row][col].getNodeType();
        	
       		
        		if (zoomOut){
        			if(ch == 'E'){
						g2.setColor(Color.RED);
						g2.fillRect(x1, y1, size, size);
					}
					if(ch == 'F'){
						g2.setColor(Color.BLACK);
						g2.fillRect(x1, y1, size, size);
					}
					if(ch == 'D'){
						g2.setColor(Color.BLUE);
						g2.fillRect(x1, y1, size, size);
					}
					if (row == currentRow && col == currentCol){
						g2.setColor(Color.MAGENTA);
						g2.fillRect(x1, y1, size, size);
					}
        		}else{
        			ch = maze[currentRow - cellpadding + row][currentCol - cellpadding + col].getNodeType();
        		}
        		
				switch(ch){
				case 'X':// hedges and walls
					imageIndex = 0;
					break;
				case 'W':// swords
					imageIndex = 1;
					break;
				case '?':
					imageIndex = 2;
					break;
				case 'B':// regular bombs
					imageIndex = 3;
					break;
				case 'H'://Hydrogen bombs
					imageIndex = 4;
					break;
				case 'E':
					imageIndex = enemyState;
					break;
				case 'F':
					imageIndex = enemyBossState;
					break;
				case 'D': // blue spiders
					imageIndex = 9;
					break;
				case 'T': // breadcrumbs
					imageIndex = 10;
					break;
				case 'M': // health
					imageIndex = 11;
					break;
				case 'P':
					imageIndex = playerState;
					break;
				case 'G': // goals
					imageIndex = 12;
					break;
				case 'Z':
					imageIndex = 13;
					break;
				case 'L': // tombstone
					imageIndex = 14;
					break;
				case 'O':
					imageIndex = 15;
					break;
				case 'N':
					imageIndex = 16;
					break;
				default:
					imageIndex = -1;
					break;
			}
        		
        		
        	
        		if (imageIndex >= 0){
        			
        			try {
        				g2.drawImage(sprites[imageIndex].getNext(), x1, y1, null);	
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e);
					}
        			
        		}else{
					if(maze[row][col].getNodeType() == ' '){
						g2.setColor(Color.LIGHT_GRAY);
						g2.fillRect(x1, y1, size, size);
					}
        		}
        		
        		
        		if (maze[row][col].getNodeType() == 'T'){
					g2.setColor(maze[row][col].getColor());
					g2.fillRect(x1, y1, size, size);
				}

				if (maze[row][col].isGoalNode() && maze[row][col].getNodeType() != 'P'){
					g2.setColor(Color.WHITE);
					g2.fillRect(x1, y1, size, size);
				}
        		
        	}
        }
	}
	
	public void toggleZoom(){
		zoomOut = !zoomOut;		
	}

	public void actionPerformed(ActionEvent e) {	
		
		if (enemyState < 0 || enemyState == 7){
			enemyState = 8;
		}else{
			enemyState = 7;
		}

		if (enemyBossState < 0 || enemyBossState == 18){
			enemyBossState = 19;
		}else{
			enemyBossState = 18;
		}

		if(e.getSource() == timer){
			repaint();
		}
		
	} 
	
	private void init() {
		
		// used for intialising the images and resources
		images = new BufferedImage[20];
		
		try {
			images[0] = ImageIO.read(new java.io.File("resources/images/objects/hedge.png"));
			images[1] = ImageIO.read(new java.io.File("resources/images/objects/sword.png"));
			images[2] = ImageIO.read(new java.io.File("resources/images/objects/help.png"));
			images[3] = ImageIO.read(new java.io.File("resources/images/objects/bomb.png"));
			images[4] = ImageIO.read(new java.io.File("resources/images/objects/h_bomb.png"));
			images[5] = ImageIO.read(new java.io.File("resources/images/player/d1.png"));
			images[6] = ImageIO.read(new java.io.File("resources/images/player/d2.png"));
			images[7] = ImageIO.read(new java.io.File("resources/images/spiders/red_spider_1.png"));
			images[8] = ImageIO.read(new java.io.File("resources/images/spiders/red_spider_2.png"));
			images[9] = ImageIO.read(new java.io.File("resources/images/spiders/blue_spider_2.png"));
			images[10] = ImageIO.read(new java.io.File("resources/images/objects/coin.png")); // Temporary
			images[11] = ImageIO.read(new java.io.File("resources/images/objects/health.png"));
			images[12] = ImageIO.read(new java.io.File("resources/images/objects/trophy.jpg"));
			images[13] = ImageIO.read(new java.io.File("resources/images/objects/trophy.jpg"));
			images[14] = ImageIO.read(new java.io.File("resources/images/objects/lose.png"));
			images[15] = ImageIO.read(new java.io.File("resources/images/player/d1.png"));
			images[16] = ImageIO.read(new java.io.File("resources/images/objects/bomb.png"));
			images[17] = ImageIO.read(new java.io.File("resources/images/spiders/black_spider_1.png"));
			images[18] = ImageIO.read(new java.io.File("resources/images/spiders/black_spider_2.png"));
		} catch (IOException error) {
			System.out.println("Error - " + error);
		}
	}

	public int getPlayerState() {
		return playerState;
	}

	public void setPlayerState(int playerState) {
		this.playerState = playerState;
	}
}