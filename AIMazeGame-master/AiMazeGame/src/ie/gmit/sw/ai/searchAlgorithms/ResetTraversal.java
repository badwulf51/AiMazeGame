package ie.gmit.sw.ai.searchAlgorithms;

import java.awt.Color;
import ie.gmit.sw.ai.*;

public class ResetTraversal {

	
	public void resetA(Node[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
				if (maze[i][j].getNodeType() == 'T' || maze[i][j].getNodeType() == ' ') {
					maze[i][j].setNodeType(' ');
					maze[i][j].setColor(Color.LIGHT_GRAY);
				}
			}
		}
	}

	// re runs algorigthim by resetting nodes
	public void resetB(Node[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
			}
		}
	}

	// puts thread to sleep for the listed amount of seconds 
	public void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
