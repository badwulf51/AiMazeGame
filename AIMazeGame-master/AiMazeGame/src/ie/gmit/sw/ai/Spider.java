package ie.gmit.sw.ai;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import ie.gmit.sw.ai.searchAlgorithms.*;

public class Spider extends GameCharacter implements Runnable{
	
	    private int id;
	    private int strength;
	    private int difficulty;
	    private boolean boss;
	    private int algorithm;
	    private Thread instance;
	    private Node[][] maze;
	    private ControlledSprite player;
	    private boolean run;
	    private int playerRowTemp;
	    private int playerColTemp;
	    private int minUpdateTime;
	    private int maxUpdateTime;
	    private Node pathGoal;
	    private AStarTraversator traverse;
	    private LinkedList<Node> nodeListPath;

	    public Spider() {
	        super();
	        setStrength(0);
	        setDifficulty(0);
	        setBoss(false);
	        setAlgorithm(0);
	    }

	    public Spider(int id, int health, int strength, int difficulty, boolean boss) {
	        super(health);
	        setId(id);
	        setStrength(strength);
	        setDifficulty(difficulty);
	        setBoss(boss);
	        setAlgorithm(0);
	    }

	    @Override
	    public void run() {
	        while(isRun()){ // keeps the spider moving while its alive
	            try {
	                if(this.getHealth() <= 0 || getPlayer().isGameOver()){
	                    setRun(false);
	                    break;
	                }
	                // puts the thread to sleep
	                Thread.sleep(new Random().nextInt((getMaxUpdateTime() - getMinUpdateTime()) + 1) + getMinUpdateTime());
	                switch(getAlgorithm()){
	                    case 0:
	                        checkMove(new Random().nextInt((3 - 0) + 1) + 0);
	                        break;
	                    case 1:
	                    	huntPlayer();
	                        break;
	                    default:
	                        checkMove(new Random().nextInt((3 - 0) + 1) + 0);
	                        break;
	                }

	            } catch (InterruptedException error) {
	                System.out.println("Error - " + error);
	            }
	        }
	    }

	   
	    private boolean isValidMove(int row, int col){
	        if(getPlayer().isGameOver()) return false;
	        if((row < 0) || (col < 0) || !(row <= getMaze().length - 1 && col <= getMaze()[row].length - 1)) return false;

	        switch(getMaze()[row][col].getNodeType()){
	            case ' ':
	                // moves enemy to an empty block
	                getMaze()[getRowPos()][getColPos()].setNodeType(' ');
	                getMaze()[row][col].setNodeType('E');
	                if(this.isBoss())
	                    getMaze()[row][col].setNodeType('F');
	                getMaze()[row][col].setEnemyID(getMaze()[getRowPos()][getColPos()].getEnemyID());
	                getMaze()[getRowPos()][getColPos()].setEnemyID(0);
	                return true;
	            case 'P':
	                // starts fight with player using the fuzzy logic
	            	FuzzyFight fuzzyFight = new FuzzyFight();
	                boolean enemyWon = fuzzyFight.startBattle(getPlayer(), this, "resources/fuzzy/fuzzyFight.fcl");
	                if(enemyWon == true){
	                    // if the player loses the fight
	                    getMaze()[getRowPos()][getColPos()].setNodeType(' ');
	                    getMaze()[getRowPos()][getColPos()].setEnemyID(0);
	                    getPlayer().setGameOver(true);
	                    getMaze()[row][col].setNodeType('L');
	                    
	                }else{
	                    getMaze()[getRowPos()][getColPos()].setNodeType('D');
	                    if(this.isBoss())
	                        getMaze()[getRowPos()][getColPos()].setNodeType('L');
	                    getMaze()[getRowPos()][getColPos()].setEnemyID(0);
	                    this.setHealth(0);
	                    
	                }
	                return enemyWon;
	            case 'T':
	                
	                getMaze()[getRowPos()][getColPos()].setNodeType(' ');
	                getMaze()[row][col].setNodeType('E');
	                if(this.isBoss())
	                    getMaze()[row][col].setNodeType('F');
	                getMaze()[row][col].setEnemyID(getMaze()[getRowPos()][getColPos()].getEnemyID());
	                getMaze()[getRowPos()][getColPos()].setEnemyID(0);
	                return true;
	            default:
	                return false;
	        }
	    }

	    // checks direction player is going in
	    private void checkMove(int direction){
	        if(this.getHealth() <= 0) return;
	        // moves enemy to new position
	        switch(direction){
	            // Going up 
	            case 0:
	                if (isValidMove(getRowPos() - 1, getColPos())){
	                    setRowPos(getRowPos() - 1);
	                }
	                break;
	            // Going down 
	            case 1:
	                if (isValidMove(getRowPos() + 1, getColPos())){
	                    setRowPos(getRowPos() + 1);
	                }
	                break;
	            // Going left 
	            case 2:
	                if (isValidMove(getRowPos(), getColPos() - 1)){
	                    setColPos(getColPos() - 1);
	                }
	                break;
	            // Going right 
	            case 3:
	                if (isValidMove(getRowPos(), getColPos() + 1)){
	                    setColPos(getColPos() + 1);
	                }
	                break;
	            default:
	                if (isValidMove(getRowPos() + 1, getColPos())){
	                    setRowPos(getRowPos() + 1);
	                }
	                break;
	        }
	    }

	   // used to make the spiders hunt the player, the A* algorithim is used here
	    private void huntPlayer() {
	       
			
	        if(getPlayerRowT() != getPlayer().getRowPos() || getPlayerColT() != getPlayer().getColPos()){
	            setMinUpdateTime(400);
	            setMaxUpdateTime(500);
	            setNodeListPath(new LinkedList<Node>());
	            setPlayerRowT(getPlayer().getRowPos());
	            setPlayerColT(getPlayer().getColPos());
	            setTraverse(new AStarTraversator(getMaze()[getPlayer().getRowPos()][getPlayer().getColPos()], false, true));
	            getTraverse().traverse(getMaze(), getMaze()[getRowPos()][getColPos()]);// the current maze and node
	            // if it has found the player the values are added to the linked list
	            if(getTraverse().isFoundGoal()){
	            
	                setPathGoal(getTraverse().getPathGoal());
	                while (getPathGoal() != null){
	                    getNodeListPath().add(getPathGoal());
	                    setPathGoal(getPathGoal().getParent());
	                }
	                // Reverses the collection of paths
	                Collections.reverse(getNodeListPath());
	                getNodeListPath().removeFirst();
	            }
	        }

	        // when the path is found the enemy should hopefully start making there way towards the player 
	        if(getTraverse().isFoundGoal()){

	            Node nextPath = getNodeListPath().poll();
	            boolean foundNextPath = false;
	            // movement options for enemy
	            while (nextPath != null && !foundNextPath){
	   
	                if(nextPath.getRow() == (getRowPos() - 1)){
	                    if (isValidMove(getRowPos() - 1, getColPos())){
	                        setRowPos(getRowPos() - 1);
	                        foundNextPath = true;
	                        break;
	                    }
	                }
	                
	                if(nextPath.getRow() == (getRowPos() + 1)){
	                    if (isValidMove(getRowPos() + 1, getColPos())){
	                        setRowPos(getRowPos() + 1);
	                        foundNextPath = true;
	                        break;
	                    }
	                }
	                
	                if(nextPath.getCol() == (getColPos() - 1)){
	                    if (isValidMove(getRowPos(), getColPos() - 1)){
	                        setColPos(getColPos() - 1);
	                        foundNextPath = true;
	                        break;
	                    }
	                }
	                
	                if(nextPath.getCol() == (getColPos() + 1)){
	                    if (isValidMove(getRowPos(), getColPos() + 1)){
	                        setColPos(getColPos() + 1);
	                        foundNextPath = true;
	                        break;
	                    }
	                }
	                

					// handles if an enemy gets stuck in the maze walls
	                if(!foundNextPath){
	                    checkMove(new Random().nextInt((3 - 0) + 1) + 0);
	                }

					// if the game is over this will end the loop 
	                if(this.getHealth() <= 0 || getPlayer().isGameOver()){
	                    setRun(false);
	                    break;
	                }
	                
	            } 
	        }
	    }
	    // getters and setters
	    public int getId()
	    {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public int getStrength() {
	        return strength;
	    }

	    public void setStrength(int strength) {
	        this.strength = strength;
	    }

	    public int getDifficulty() {
	        return difficulty;
	    }

	    public void setDifficulty(int difficulty) {
	        this.difficulty = difficulty;
	    }

	    public boolean isBoss() {
	        return boss;
	    }

	    public void setBoss(boolean boss) {
	        this.boss = boss;
	    }

	    public int getAlgorithm() {
	        return algorithm;
	    }

	    public void setAlgorithm(int algorithm) {
	        this.algorithm = algorithm;
	    }

	    public Thread getInstance() {
	        return instance;
	    }

	    public void setInstance(Thread instance) {
	        this.instance = instance;
	    }

	    public Node[][] getMaze() {
	        return maze;
	    }

	    public void setMaze(Node[][] maze) {
	        this.maze = maze;
	    }

	    public ControlledSprite getPlayer() {
	        return player;
	    }

	    public void setPlayer(ControlledSprite player) {
	        this.player = player;
	    }

	    public boolean isRun() {
	        return run;
	    }

	    public void setRun(boolean run) {
	        this.run = run;
	    }

	    public int getPlayerRowT() {
	        return playerRowTemp;
	    }

	    public void setPlayerRowT(int playerRowT) {
	        this.playerRowTemp = playerRowT;
	    }

	    public int getPlayerColT() {
	        return playerColTemp;
	    }

	    public void setPlayerColT(int playerColT) {
	        this.playerColTemp = playerColT;
	    }

	    public int getPlayerRowTemp() {
	        return playerRowTemp;
	    }

	    public void setPlayerRowTemp(int playerRowTemp) {
	        this.playerRowTemp = playerRowTemp;
	    }

	    public int getPlayerColTemp() {
	        return playerColTemp;
	    }

	    public void setPlayerColTemp(int playerColTemp) {
	        this.playerColTemp = playerColTemp;
	    }

	    public int getMinUpdateTime() {
	        return minUpdateTime;
	    }

	    public void setMinUpdateTime(int minUpdateTime) {
	        this.minUpdateTime = minUpdateTime;
	    }

	    public int getMaxUpdateTime() {
	        return maxUpdateTime;
	    }

	    public void setMaxUpdateTime(int maxUpdateTime) {
	        this.maxUpdateTime = maxUpdateTime;
	    }

	    public Node getPathGoal() {
	        return pathGoal;
	    }

	    public void setPathGoal(Node pathGoal) {
	        this.pathGoal = pathGoal;
	    }

	    public AStarTraversator getTraverse() {
	        return traverse;
	    }

	    public void setTraverse(AStarTraversator traverse) {
	        this.traverse = traverse;
	    }

	    public LinkedList<Node> getNodeListPath() {
	        return nodeListPath;
	    }

	    public void setNodeListPath(LinkedList<Node> nodeListPath) {
	        this.nodeListPath = nodeListPath;
	}

}
