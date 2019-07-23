package ie.gmit.sw.ai;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ie.gmit.sw.ai.searchAlgorithms.*;

public class GameStats {
	
	// elements for the left side of the games UI
		private static JLabel lblCurScore;
		private static JLabel lblCurStepstoExit;
		private static JLabel lblCurSteps;
		private static JLabel lblHealthPoints;
		private static JLabel lblCurWeapon;
		private static JLabel lblCurWeaponStr;
		private static JLabel lblCurSpecial;
		private static JLabel lblEnemiesAmount;
		private static JLabel lblCommonAmount;
		private static JLabel lblBossesAmount;
		
		// Setup for the games main panal as well as options for GUI 
		public static void uiPanel(JPanel mainPanel, JFrame gameFrame){
			mainPanel = new JPanel();
			mainPanel.setBackground(Color.green);
			mainPanel.setBounds(0, 0, 210, 760);
			gameFrame.getContentPane().add(mainPanel);
			mainPanel.setLayout(null);

			JLabel lblPlayer = new JLabel("Player");
			lblPlayer.setBounds(10, 10, 180, 30);
			lblPlayer.setForeground(Color.black);
			lblPlayer.setFont(new Font("Arial", Font.BOLD, 24));
			mainPanel.add(lblPlayer);

			JLabel lblScore = new JLabel("Points: ");
			lblScore.setBounds(10, 50, 60, 24);
			lblScore.setForeground(Color.black);
			lblScore.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(lblScore);

			lblCurScore = new JLabel("0");
			lblCurScore.setBounds(70, 50, 80, 24);
			lblCurScore.setForeground(Color.black);
			lblCurScore.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(lblCurScore);

			JLabel lblStepstoExit = new JLabel();
			lblStepstoExit.setBounds(10, 90, 115, 24);
			lblStepstoExit.setForeground(Color.black);
			lblStepstoExit.setFont(new Font("Arial", Font.BOLD, 18));
			

			lblCurStepstoExit = new JLabel("");
			lblCurStepstoExit.setBounds(125, 90, 60, 24);
			lblCurStepstoExit.setForeground(Color.green);
			lblCurStepstoExit.setFont(new Font("Arial", Font.BOLD, 18));
			

			JLabel lblSteps = new JLabel();
			lblSteps.setBounds(10, 130, 60, 24);
			lblSteps.setForeground(Color.black);
			lblSteps.setFont(new Font("Arial", Font.BOLD, 18));
			

			lblCurSteps = new JLabel();
			lblCurSteps.setBounds(70, 130, 80, 24);
			lblCurSteps.setForeground(Color.black);
			lblCurSteps.setFont(new Font("Arial", Font.BOLD, 18));
			

			JLabel lblHealth = new JLabel("HP: ");
			lblHealth.setBounds(10, 90, 115, 24);
			lblHealth.setForeground(Color.black);
			lblHealth.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(lblHealth);

			lblHealthPoints = new JLabel("100");
			lblHealthPoints.setBounds(70, 90, 60, 24);
			lblHealthPoints.setForeground(Color.black);
			lblHealthPoints.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(lblHealthPoints);


			JLabel lblWeapon = new JLabel("Weapon: ");
			lblWeapon.setBounds(10, 170, 65, 24);
			lblWeapon.setForeground(Color.black);
			lblWeapon.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(lblWeapon);

			lblCurWeapon = new JLabel("No Weapon");
			lblCurWeapon.setBounds(75, 170, 80, 24);
			lblCurWeapon.setForeground(Color.black);
			lblCurWeapon.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(lblCurWeapon);

			JLabel lblWeaponStr = new JLabel("");
			lblWeaponStr.setBounds(10, 290, 140, 24);
			lblWeaponStr.setForeground(Color.black);
			lblWeaponStr.setFont(new Font("Arial", Font.BOLD, 18));
			

			lblCurWeaponStr = new JLabel("");
			lblCurWeaponStr.setBounds(160, 290, 40, 24);
			lblCurWeaponStr.setForeground(Color.black);
			lblCurWeaponStr.setFont(new Font("Arial", Font.BOLD, 18));
			

			JLabel lblSpecial = new JLabel("");
			lblSpecial.setBounds(10, 330, 130, 24);
			lblSpecial.setForeground(Color.black);
			lblSpecial.setFont(new Font("Arial", Font.BOLD, 18));
			

			lblCurSpecial = new JLabel("");
			lblCurSpecial.setBounds(148, 330, 90, 24);
			lblCurSpecial.setForeground(Color.black);
			lblCurSpecial.setFont(new Font("Arial", Font.BOLD, 18));
			

			JLabel lblEnemy = new JLabel("Spiders");
			lblEnemy.setBounds(10, 370, 170, 24);
			lblEnemy.setForeground(Color.black);
			lblEnemy.setFont(new Font("Arial", Font.BOLD, 24));
			mainPanel.add(lblEnemy);

			JLabel lblEnemies = new JLabel("Red Spiders");
			lblEnemies.setBounds(10, 415, 100, 24);
			lblEnemies.setForeground(Color.black);
			lblEnemies.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(lblEnemies);

			lblEnemiesAmount = new JLabel("20");
			lblEnemiesAmount.setBounds(105, 415, 80, 24);
			lblEnemiesAmount.setForeground(Color.black);
			lblEnemiesAmount.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(lblEnemiesAmount);

			JLabel lblCommon = new JLabel("Total:");
			lblCommon.setBounds(10, 455, 90, 24);
			lblCommon.setForeground(Color.black);
			lblCommon.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(lblCommon);

			lblCommonAmount = new JLabel("15");
			lblCommonAmount.setBounds(70, 455, 80, 24);
			lblCommonAmount.setForeground(Color.black);
			lblCommonAmount.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(lblCommonAmount);

			JLabel lblBosses = new JLabel("Bosses:");
			lblBosses.setBounds(10, 495, 70, 24);
			lblBosses.setForeground(Color.black);
			lblBosses.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(lblBosses);

			lblBossesAmount = new JLabel("5");
			lblBossesAmount.setBounds(70, 495, 80, 24);
			lblBossesAmount.setForeground(Color.black);
			lblBossesAmount.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(lblBossesAmount);
		}

		public static void updateStatsGUI(GameSetup game) {
			// Updates GUI elements
			lblCurScore.setText(Integer.toString(game.getPlayer().getScore()));
			lblCurStepstoExit.setText(Integer.toString(calStepsToExit(game)));
			lblCurSteps.setText(Integer.toString(game.getPlayer().getSteps()));
			lblHealthPoints.setText(Integer.toString(game.getPlayer().getHealth()));
			lblCurWeapon.setText(game.getPlayer().getWeaponName());
			lblCurWeaponStr.setText(Integer.toString(game.getPlayer().getWeaponStrength()));
			lblCurSpecial.setText(Integer.toString(game.getPlayer().getSpecial()));

			int commonCount = 0;
			int bossCount = 0;

			for (int i = 0; i < game.getEnemies().size(); i++) {
				if (game.getEnemies().get(i).isBoss()) {
					if (game.getEnemies().get(i).getHealth() > 0)
						bossCount++;
				} else {
					if (game.getEnemies().get(i).getHealth() > 0)
						commonCount++;
				}
			}

			lblEnemiesAmount.setText(Integer.toString(bossCount + commonCount));
			lblCommonAmount.setText(Integer.toString(commonCount));
			lblBossesAmount.setText(Integer.toString(bossCount));
		
		}
		
		public static int calStepsToExit(GameSetup game) {
			// Suppoosed to calculate the moves to the goal node but unsure if it works or not
			AStarTraversator traverse = new AStarTraversator(game.getModel().getGoalNode(), true);
			traverse.traverse(game.getMaze(), game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()]);
			game.getPlayer().setStepsToExit(traverse.getStepsToExit());
			return game.getPlayer().getStepsToExit();
		}

}
