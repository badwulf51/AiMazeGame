package ie.gmit.sw.ai;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ie.gmit.sw.ai.neuralnet.NnFight;
import ie.gmit.sw.ai.searchAlgorithms.*;

public class GameRunner implements KeyListener {
	public static final int MAZE_DIMENSION = 80;
	public static final int IMAGE_COUNT = 20;
	private ControlledSprite player;
	private JFrame gameFrame;
	private JPanel coverPanel;
	private JPanel leftPanel;
	

	private JComboBox<String> gameDifficulty1;

	// Instance for the game controller
	private GameView gamePanel;
	private GameSetup game;
	private Sprite[] sprites;
	private NnFight nnfight;
	private int nnIfHealth;
	private int nnHealth;
	private int nnWeaponStrength;
	private int nnAnger;
	private int deadSpider = 0;
	private int stepCount = 0;

	public GameRunner() throws Exception {

		nnfight = new NnFight();
		nnfight.train();
		newFrame();
		menuSetup();
		gameFrame.repaint();

	}

	// Builds the view of the main window
	private void newFrame() {
		gameFrame = new JFrame("Maze Of Spiders");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.addKeyListener(this);
		gameFrame.getContentPane().setLayout(null);
		gameFrame.getContentPane().setBackground(Color.black);
		gameFrame.setSize(1200, 750);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
	}

	// This is the frame that displays the main menu
	private void menuSetup() {
		// Cover panel
		coverPanel = new JPanel();
		coverPanel.setBounds(204, 0, 797, 670);
		coverPanel.setBackground(Color.RED);
		coverPanel.setLayout(null);
		gameFrame.getContentPane().add(coverPanel);

		// Title at the top of the window
		JLabel label1 = new JLabel("The Maze Of Spiders!");
		label1.setForeground(Color.BLACK);
		label1.setFont(new Font("Serif", Font.BOLD, 40));
		label1.setBounds(255, 200, 750, 100);
		coverPanel.add(label1);

		// Drop down menu for the difficfulty settings, unfortunatly could not get the other difficulty settings to work, kept running into an exception i could not fix
		JLabel label = new JLabel("Difficulty:");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Serif", Font.BOLD, 24));
		label.setBounds(307, 350, 126, 24);
		coverPanel.add(label);
		// various options for difficulty, only easy works, selecting any other options will make it the default difficulty setting
		gameDifficulty1 = new JComboBox<String>();
		gameDifficulty1.setFont(new Font("Serif", Font.BOLD, 24));
		gameDifficulty1.setBounds(435, 350, 120, 30);
		gameDifficulty1.addItem("Easy");
		coverPanel.add(gameDifficulty1);

		// Option for starting the game
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameFrame.getContentPane().remove(coverPanel);
				GameStats.uiPanel(leftPanel, gameFrame);
				//GameStats.setupRightPanel(rightPanel, gameFrame);
				intialiseNewGame(gameDifficulty1.getSelectedItem().toString());
			}
		});
		btnNewGame.setFont(new Font("Serif", Font.BOLD, 24));
		btnNewGame.setBounds(332, 430, 211, 33);
		btnNewGame.setFocusable(false);
		coverPanel.add(btnNewGame);

	}

	public void intialiseNewGame(String gameDifficulty) {
		// Kills old enemy threads if they are still running
		if (game != null)
			game.killEnemyThreads();

		if (gamePanel != null)
			gameFrame.getContentPane().remove(gamePanel);

		game = new GameSetup();

		newGame(gameDifficulty);

		gameFrame.repaint();
		
		if (game.getPlayer().getStepsToExit() <= 0)
			intialiseNewGame(gameDifficulty);
	}

	private void newGame(String gameDifficulty) {

		// creates the insantances of the enemies and player
		Maze model = new Maze(MAZE_DIMENSION);
		Node[][] maze = model.getMaze();
		try {
			Weapon w = new Weapon("", 0);
			// resources for the player sprite
			this.player = new ControlledSprite("Main Player", 3, w, "resources/images/player/d1.png",
					"resources/images/player/d2.png", "resources/images/player/d3.png",
					"resources/images/player/l1.png", "resources/images/player/l2.png",
					"resources/images/player/l3.png", "resources/images/player/r1.png",
					"resources/images/player/r2.png", "resources/images/player/r3.png");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<Spider> enemies = new ArrayList<Spider>();
		Dimension d = new Dimension(800, 800);

		game.setModel(model);
		game.setMaze(maze);
		game.setPlayer(player);
		game.setEnemies(enemies);
		game.playerPlacment(model.getGoalPos());
		game.enemySetup(gameDifficulty);

		try {
			sprites = getSprites();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error reading images.");
			e.printStackTrace();
		}
		gamePanel = new GameView(game, sprites);
		gamePanel.setBounds(204, 0, 800, 700);
		gamePanel.setPreferredSize(d);
		gamePanel.setMinimumSize(d);
		gamePanel.setMaximumSize(d);
		gameFrame.getContentPane().add(gamePanel);

		updateView();
	}

	private void updateView() {
		gamePanel.setCurrentRow(game.getPlayer().getRowPos());
		gamePanel.setCurrentCol(game.getPlayer().getColPos());
		GameStats.updateStatsGUI(game);
	}

	public void keyPressed(KeyEvent e) {

		if (game.getPlayer() == null || game.getPlayer().isGameOver())
			return; 
		// Checks if a block contains an item such as a bomb or sword
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && game.getPlayer().getColPos() < MAZE_DIMENSION - 1) {
			if (isMoveValid(game.getPlayer().getRowPos(), game.getPlayer().getColPos() + 1)
					&& game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos() + 1].isWalkable()) {
				gamePanel.setPlayerState(6);
				game.getPlayer().setColPos(game.getPlayer().getColPos() + 1);
				game.getPlayer().setSteps(game.getPlayer().getSteps() + 1);
				game.getPlayer().setDirection(Direction.RIGHT);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && game.getPlayer().getColPos() > 0) {
			if (isMoveValid(game.getPlayer().getRowPos(), game.getPlayer().getColPos() - 1)
					&& game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos() - 1].isWalkable()) {
				gamePanel.setPlayerState(6);
				game.getPlayer().setColPos(game.getPlayer().getColPos() - 1);
				game.getPlayer().setSteps(game.getPlayer().getSteps() + 1);
				game.getPlayer().setDirection(Direction.LEFT);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP && game.getPlayer().getRowPos() > 0) {
			if (isMoveValid(game.getPlayer().getRowPos() - 1, game.getPlayer().getColPos())
					&& game.getMaze()[game.getPlayer().getRowPos() - 1][game.getPlayer().getColPos()].isWalkable()) {
				gamePanel.setPlayerState(6);
				game.getPlayer().setRowPos(game.getPlayer().getRowPos() - 1);
				game.getPlayer().setSteps(game.getPlayer().getSteps() + 1);
				game.getPlayer().setDirection(Direction.UP);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && game.getPlayer().getRowPos() < MAZE_DIMENSION - 1) {
			if (isMoveValid(game.getPlayer().getRowPos() + 1, game.getPlayer().getColPos())
					&& game.getMaze()[game.getPlayer().getRowPos() + 1][game.getPlayer().getColPos()].isWalkable()) {
				gamePanel.setPlayerState(6);
				game.getPlayer().setRowPos(game.getPlayer().getRowPos() + 1);
				game.getPlayer().setSteps(game.getPlayer().getSteps() + 1);
				game.getPlayer().setDirection(Direction.DOWN);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_Z) {
			gamePanel.toggleZoom();
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			if (game.getPlayer().getSpecial() <= 0) {
				return;
			} else {
				Traversator traverse = algorithm(0); 
				traverse.traverse(game.getMaze(),
						game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()]);
				game.getPlayer().setSearchCount(game.getPlayer().getSearchCount() + 1);
				game.getPlayer().setSpecial(game.getPlayer().getSpecial() - 1);
			}
		}
		updateView();
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	private boolean isMoveValid(int r, int c) {

		stepCount++;
		// Checks the pathing and makes sure player can move
		if (!(r <= game.getMaze().length - 1 && c <= game.getMaze()[r].length - 1))
			return false;

		nnIfHealth = player.getHealth();

		if (nnIfHealth < 66) {
			nnHealth = 0;
		} else if (nnIfHealth < 130 && nnIfHealth > 66) {
			nnHealth = 1;
		} else if (nnIfHealth > 130) {
			nnHealth = 2;
		} else {
			nnHealth = 0;
		}

		if (player.getWeaponStrength() > 0) {
			nnWeaponStrength = 1;
		} else {
			nnWeaponStrength = 0;
		}

		if (deadSpider == 0) {
			nnAnger = 1;
		} else {
			nnAnger = 0;
		}

		if (stepCount % 20 == 0) {
			try {
				nnfight.action(nnHealth, nnWeaponStrength, nnAnger);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		switch (game.getMaze()[r][c].getNodeType()) {
		case ' ':
			// This is for the health pick up
			game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
			game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setGoalNode(false);
			game.getMaze()[r][c].setNodeType('P');
			game.getMaze()[r][c].setGoalNode(true);
			return true;
		case 'W':
			// Pick up for the sword, weakest weapon in the game
			if (!game.getPlayer().getWeaponName().equals("Sword")) {
				game.getPlayer().setWeaponName("Sword");
				game.getPlayer().setWeaponStrength(45);
				game.getMaze()[r][c].setNodeType('X');
			}
			return true;
		case '?':
			Traversator traverse = algorithm(0); // A * attempt
			traverse.traverse(game.getMaze(),
					game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()]);
			
			return true;
		case 'B':
			// Pickup for bomb, second most powerful weapon in the game
			if (!game.getPlayer().getWeaponName().equals("Bomb")) {
				game.getPlayer().setWeaponName("Bomb");
				game.getPlayer().setWeaponStrength(65);
				game.getMaze()[r][c].setNodeType('X');
			}
			return true;
		case 'H':
			// Pickup for hydrogen bomb, the most powerful weapon in the game
			if (!game.getPlayer().getWeaponName().equals("H-Bomb")) {
				game.getPlayer().setWeaponName("H-Bomb");
				game.getPlayer().setWeaponStrength(85);
				game.getMaze()[r][c].setNodeType('X');
			}
			return true;
		case 'M':
			// Medpack pickup, picking this up increases player health by 100
			if (game.getPlayer().getHealth() < 100) {
				game.getPlayer().setHealth(game.getPlayer().getHealth() + 100);
				if (game.getPlayer().getHealth() > 100)
					game.getPlayer().setHealth(100);
				game.getMaze()[r][c].setNodeType('X');
			}
			return true;
		
		case 'T':
			game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
			game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setGoalNode(false);
			game.getMaze()[r][c].setNodeType('P');
			game.getMaze()[r][c].setGoalNode(true);
			return true;
		case 'G':
			game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
			game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setGoalNode(false);
			game.getMaze()[r][c].setNodeType('Z');
			game.getPlayer().setGameOver(true);
			JOptionPane.showMessageDialog(null, "You have escaped the maze of deadly spiders!", "Info: " + "Success!",
					JOptionPane.INFORMATION_MESSAGE);
			// ============================================================================================================
			System.exit(0);
			return true;
		case 'N':
			if (!game.getPlayer().getWeaponName().equals("Bomb")) {
				game.getPlayer().setWeaponName("Bomb");
				game.getPlayer().setWeaponStrength(100);
				game.getMaze()[r][c].setNodeType('X');
			}
			return true;
		case 'E':
			FuzzyFight fuzzyBattle1 = new FuzzyFight();
			boolean enemyWon1 = fuzzyBattle1.startBattle(game.getPlayer(),
					game.getEnemies().get(game.getMaze()[r][c].getEnemyID()), "resources/fuzzy/fuzzyFight.fcl");
			if (enemyWon1 == true) {
				// Output for the player losing the game
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setEnemyID(0);
				game.getPlayer().setGameOver(true);
				game.getMaze()[r][c].setNodeType('L');
				JOptionPane.showMessageDialog(null, "Sorry, Mom will miss you.", "Info: " + "You Lose!",
						JOptionPane.INFORMATION_MESSAGE);
				// =============================================================================
				System.exit(0);
				return false;
			} else {
				game.getEnemies().get(game.getMaze()[r][c].getEnemyID()).setHealth(0);
				game.getMaze()[r][c].setNodeType('D');
				game.getMaze()[r][c].setEnemyID(0);
				deadSpider++;
				return false;
			}
		case 'F':
			FuzzyFight fuzzyBattle2 = new FuzzyFight();
			boolean enemyWon2 = fuzzyBattle2.startBattle(game.getPlayer(),
					game.getEnemies().get(game.getMaze()[r][c].getEnemyID()), "resources/fuzzy/fuzzyFight.fcl");
			if (enemyWon2 == true) {
				
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setEnemyID(0);
				game.getPlayer().setGameOver(true);
				game.getMaze()[r][c].setNodeType('D');
				
				JOptionPane.showMessageDialog(null, "You have died.", "Info: " + "You Lose!",
						JOptionPane.INFORMATION_MESSAGE);
				//==============================================================================================
				System.exit(0);
				return false;
			} else {
				game.getEnemies().get(game.getMaze()[r][c].getEnemyID()).setHealth(0);
				game.getMaze()[r][c].setNodeType('L');
				game.getMaze()[r][c].setEnemyID(0);
				deadSpider++;
				return false;
			}
		default:
			return false;
		}
	}

	private Traversator algorithm(int randNum) {
		// Tells you which algorithim is being used
		switch (randNum) {
		case 0:
			System.out.println("A* Search.");
			return new AStarTraversator(game.getModel().getGoalNode(), false);
		case 1:
			return new BestFirstTraversator(game.getModel().getGoalNode());
		case 2:
			return new DepthLimitedDFSTraversator(5);
		default:
			return new AStarTraversator(game.getModel().getGoalNode(), false);
		}
	}

	private Sprite[] getSprites() throws Exception {
		
		Sprite[] sprites = new Sprite[IMAGE_COUNT];
		sprites[0] = new Sprite("Hedge", 1, "resources/images/objects/hedge.png");
		sprites[1] = new Sprite("Sword", 1, "resources/images/objects/sword.png");
		sprites[2] = new Sprite("Help", 1, "resources/images/objects/help.png");
		sprites[3] = new Sprite("Bomb", 1, "resources/images/objects/bomb.png");
		sprites[4] = new Sprite("Hydrogen Bomb", 1, "resources/images/objects/h_bomb.png");
		sprites[5] = this.player;
		sprites[6] = new Sprite("Player2", 1, "resources/images/player/d2.png");
		sprites[7] = new Sprite("Red1", 1, "resources/images/spiders/red_spider_1.png");
		sprites[8] = new Sprite("Red2", 1, "resources/images/spiders/red_spider_2.png");
		sprites[9] = new Sprite("Blue2", 1, "resources/images/spiders/blue_spider_2.png");
		sprites[10] = new Sprite("Green1", 1, "resources/images/objects/coin.png");
		sprites[11] = new Sprite("Health", 1, "resources/images/objects/health.png");
		sprites[12] = new Sprite("Goal", 1, "resources/images/objects/trophy.jpg");
		sprites[13] = new Sprite("End Goal", 1, "resources/images/objects/trophy.jpg");
		sprites[14] = new Sprite("Lose", 1, "resources/images/objects/lose.png");
		sprites[15] = new Sprite("Player", 1, "resources/images/player/d1.png");
		sprites[16] = new Sprite("Bomb", 1, "resources/images/objects/bomb.png");
		sprites[17] = new Sprite("Black1", 1, "resources/images/spiders/black_spider_1.png");
		sprites[18] = new Sprite("Black2", 1, "resources/images/spiders/black_spider_2.png");

		return sprites;
	}

	public static void main(String[] args) throws Exception {
		new GameRunner();
	}
}