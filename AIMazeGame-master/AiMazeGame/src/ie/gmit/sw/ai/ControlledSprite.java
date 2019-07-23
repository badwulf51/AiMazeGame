// Imports packages and the likes
package ie.gmit.sw.ai;

// Class for controlled Sprite
public class ControlledSprite extends Sprite{	
	// variables	
    private boolean gameOver;
    private int health;
    private int weaponStrength;
    private int score;
    private int stepsToExit;
    private int steps;
    private int special;
    private String weaponName  = "";
    private int searchCount;
    private Weapon weapon;
    private int rowPos;
    private int colPos;

	public ControlledSprite(String name, int frames, Weapon weapon, String... images) throws Exception{
		super(name, frames, images);
		this.weapon = weapon;
	}
	// Direction for the main player character
	public void setDirection(Direction d){
		switch(d.getOrientation()) {
		case 0: case 1:
			super.setImageIndex(0); // Moves the player up or down
			break;
		case 2:
			super.setImageIndex(1);  // Moves player left
			break;
		case 3:
			super.setImageIndex(2);  // Moves player right
		default:
			break; 
		}		
	}
	
	// getters and setters
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
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

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public int getWeaponStrength() {
		return weaponStrength;
	}

	public void setWeaponStrength(int weaponStrength) {
		this.weaponStrength = weaponStrength;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getStepsToExit() {
		return stepsToExit;
	}

	public void setStepsToExit(int stepsToExit) {
		this.stepsToExit = stepsToExit;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getSpecial() {
		return special;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public String getWeaponName() {
		return weaponName;
	}

	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}

	public int getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
}