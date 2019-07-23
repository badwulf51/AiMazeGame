package ie.gmit.sw.ai;


public class GameCharacter {
	private int rowPos;
    private int colPos;
	private int health;
   
    public GameCharacter() {
        setHealth(100);
       
    }
    
    public GameCharacter(int health) {
        setHealth(health);
       
    }

    public GameCharacter(int health, int colPos, int rowPos) {
        setHealth(health);
        setColPos(colPos);
        setRowPos(rowPos);
    }
    // getters and setters
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

  
    public int getRowPos() {
        return rowPos;
    }

    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }

    public int getColPos() {
        return colPos;
    }

    public void setColPos(int colPos) {
        this.colPos = colPos;
    }
}