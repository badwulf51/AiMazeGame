package ie.gmit.sw.ai;

import net.sourceforge.jFuzzyLogic.*;
import net.sourceforge.jFuzzyLogic.rule.Variable;

// Used to do the fighting system with fuzzy logic
public class FuzzyFight {
	
	public FuzzyFight() {
		
	}
	
	 public boolean startBattle(ControlledSprite player, Spider enemy, String fclFilePath){
		 	// loads in the fcl file
	        FIS fis = FIS.load(fclFilePath, true);
	        // error output if the file cannot be loaded
	        if(fis == null){
	            System.err.println("Error loading: '" + fclFilePath + "'");
	            return true;
	        }
	        // used for working with the fuzzy logic fcl file
	        FunctionBlock functionBlock = fis.getFunctionBlock("fight");

	        fis.setVariable("health", player.getHealth());
	        fis.setVariable("weapon", player.getWeaponStrength());
	        fis.evaluate();

	        Variable annihilation = functionBlock.getVariable("annihilation");
	        // ment to calculate the percentage of how cloe you are to death, unsure if working
	        System.out.println("Death Percentage: " + (int)annihilation.getValue() + "%\n");
	        // so the game doesnt end straight away 
	        boolean enemyWon = false;

	        player.setHealth((int)(player.getHealth() - (100 - annihilation.getValue())));
	        player.setWeaponStrength((int)(player.getWeaponStrength() * (annihilation.getValue() / 100)));

	        Weapon w = new Weapon("Unarmed", 0);
	     
	        // If the players health goes to 0 then the game is lost, display message saying its game over
	        if(player.getHealth() <= 0){
	            player.setHealth(0);
	            player.setGameOver(true);
	            System.out.println("You Have Died Alone In A Maze.");
	            System.out.println("Your Score: " + player.getScore() + "\n");
	            enemyWon = true;
	        }

	        // If the enemy loses the fight then the stats are updated to reflec the damage, score increase by 25 per win. 
	        if(!enemyWon){
	            player.setScore(player.getScore() + 25);
	            System.out.println("Player Health: " + player.getHealth());
	            
	        }

	        return enemyWon;
	    }

}
