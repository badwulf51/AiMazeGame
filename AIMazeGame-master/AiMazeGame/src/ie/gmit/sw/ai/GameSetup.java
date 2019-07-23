package ie.gmit.sw.ai;

import java.util.ArrayList;
import java.util.Random;

public class GameSetup {
	 
	private ControlledSprite player;
	private Maze model;
    private Node[][] maze;
    private ArrayList<Spider> spiders;
    
    public GameSetup(Maze model, Node[][] maze, ControlledSprite player, ArrayList<Spider> enemies) {
        setModel(model);
        setMaze(maze);
        setPlayer(player);
        setEnemies(enemies);
    }
    
    public GameSetup() {
    	
    }

    // used to try and make sure the player does not spwan very close to the exit goal when a maze is generated
    public void playerPlacment(int goalPos){
    		
        Random random = new Random();
        boolean playerPosSet = false;

        // While loop increments until a position that is far enough from the player is found 
        while(playerPosSet != true){

            switch(goalPos){
                case 0:
                    // Top side
                    getPlayer().setRowPos(random.nextInt((3 - 2) + 1) + 2);
                    getPlayer().setColPos(random.nextInt((getMaze()[0].length - 5) + 1) + 5);
                    break;
                case 1:
                    // left side
                    getPlayer().setRowPos(random.nextInt(((getMaze().length - 15) - 1) + 1) + 1);
                    getPlayer().setColPos(random.nextInt((3 - 2) + 1) + 2);
                    break;
                case 2:
                    // bottom
                    getPlayer().setRowPos(random.nextInt(((getMaze().length - 15) - (getMaze().length - 15)) + 1) + (getMaze().length - 15));
                    getPlayer().setColPos(random.nextInt((getMaze()[0].length - 5) + 1) + 5);
                    break;
                default:
                    getPlayer().setRowPos(random.nextInt(((getMaze().length - 15) - 1) + 1) + 1);
                    getPlayer().setColPos(random.nextInt((3 - 2) + 1) + 2);
                    break;
            }

            // Checks to see if the area is walkable
            try {
                if(getMaze()[getPlayer().getRowPos()][getPlayer().getColPos()].isWalkable()){
                    getMaze()[getPlayer().getRowPos()][getPlayer().getColPos()].setNodeType('P');
                    getMaze()[getPlayer().getRowPos()][getPlayer().getColPos()].setGoalNode(true);
                    playerPosSet = true;
                }
            } catch (Exception e) {
            }
        }
    }

    // trying to implement a difficultiy system, can work with default and easy mode but other modes do not seem to work right 
    public void enemySetup(String gameDifficulty){

        int amount;
        int health;
        int strength;
        int difficulty;
        int bosses;
        boolean isBoss = false;

        Random random = new Random();

        // This sets up the enemys health and strength randomly based on a "mutateor" or modifier, could not implement other diffictuly levels, also sets the amount of enemies in the maze
        switch(gameDifficulty){
            case "Easy":
                amount = 35;
                health = random.nextInt((60 - 25) + 1) + 25;
                strength = random.nextInt((60 - 30) + 1) + 30;
                difficulty = 0;
                bosses = 3;
                break;
            default:
                amount = 35;
                health = random.nextInt((60 - 25) + 1) + 25;
                strength = random.nextInt((60 - 30) + 1) + 30;
                difficulty = 1;
                bosses = 3;
                break;
        }

        setEnemies(new ArrayList<Spider>());

	// creates enemies with seperate threads and adujusts the games difficulty
        for(int i = 0; i < amount; i++){
        	// creates boss spiders, these are the black ones
            if(bosses > 0){
                isBoss = true;
                health = 100;
                strength = 1000;
                difficulty = 5;
                bosses--;
            }

            // Creates an object of the enemy and then creates a new thread
            Runnable enemy = new Spider(i, health, strength, difficulty, isBoss);
            Thread thread = new Thread(enemy);
            getEnemies().add((Spider) enemy);
            getEnemies().get(i).setInstance(thread);
            getEnemies().get(i).setMaze(getMaze());
            getEnemies().get(i).setPlayer(getPlayer());
            getEnemies().get(i).setRun(true);
            getEnemies().get(i).setMinUpdateTime(600);
            getEnemies().get(i).setMaxUpdateTime(750);

            // Boss spiders use the a * algorithim to attempt to hunt you down 
            if(isBoss)
                getEnemies().get(i).setAlgorithm(random.nextInt((1 - 1) + 1) + 1);

            boolean enemyPosSet = false;

            // loop goes until a good path is found
            while(enemyPosSet != true){
                getEnemies().get(i).setRowPos((int)(GameRunner.MAZE_DIMENSION * Math.random()));
                getEnemies().get(i).setColPos((int)(GameRunner.MAZE_DIMENSION * Math.random()));

                // Checks to see if the area is walkable
                if(getMaze()[getEnemies().get(i).getRowPos()][getEnemies().get(i).getColPos()].isWalkable()){
                    getMaze()[getEnemies().get(i).getRowPos()][getEnemies().get(i).getColPos()].setNodeType('E');
                    if(getEnemies().get(i).isBoss())
                        getMaze()[getEnemies().get(i).getRowPos()][getEnemies().get(i).getColPos()].setNodeType('F');
                    getMaze()[getEnemies().get(i).getRowPos()][getEnemies().get(i).getColPos()].setEnemyID(i);
                    enemyPosSet = true;
                }
            }

            isBoss = false;
            thread.start();
        }
    }

    // kills all the previously running enemy threads
    public void killEnemyThreads() {
        if(getEnemies() == null || getEnemies().size() <= 0) return;
        for(int i = 0; i < getEnemies().size(); i++){
            getEnemies().get(i).setHealth(0);
            getEnemies().get(i).setRun(false);
        }
    }
    // getters and setters
    public Maze getModel() {
        return model;
    }

    public void setModel(Maze model) {
        this.model = model;
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

    public ArrayList<Spider> getEnemies() {
        return spiders;
    }

    public void setEnemies(ArrayList<Spider> enemies) {
        this.spiders = enemies;
    }

}
