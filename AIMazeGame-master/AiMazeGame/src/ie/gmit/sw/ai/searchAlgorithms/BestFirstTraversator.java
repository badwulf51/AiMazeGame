package ie.gmit.sw.ai.searchAlgorithms;

import java.util.*;

import javax.swing.JOptionPane;

import ie.gmit.sw.ai.Node;
public class BestFirstTraversator implements Traversator{
	private Node goal;
	private int stepsToExit;
	private boolean quitWhile = true;

	private boolean foundGoal;
	private Node pathGoal;
	
	public BestFirstTraversator(Node goal){
		this.goal = goal;
	}
	
	public void traverse(Node[][] maze, Node node) {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addFirst(node);
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		while(!queue.isEmpty() && quitWhile == true){
			node = queue.poll();
			node.setVisited(true);	
			visitCount++;
			
			if (node.isGoalNode()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        setStepsToExit(TraversatorStats.printStats(node, time, visitCount, false));
		        setFoundGoal(true);
				setPathGoal(node);
				System.out.println("Found goal node at: "+ node);
				JOptionPane.showMessageDialog(null, "I found the exit, this is the path I took", "InfoBox: " + "Success!",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			
			try { 
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Node[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					queue.addFirst(children[i]);
				}
			}
			
			Collections.sort(queue,(Node current, Node next) -> current.getHeuristic(goal) - next.getHeuristic(goal));		
		}
		setStepsToExit(TraversatorStats.printStats(node, time, visitCount, false));
        setFoundGoal(true);
		setPathGoal(node);
		JOptionPane.showMessageDialog(null, "I did not find the exit, this is the path I took", "InfoBox: " + "Unsuccessful...",
				JOptionPane.INFORMATION_MESSAGE);
	}
	public int getStepsToExit() {
		return stepsToExit;
	}

	public void setStepsToExit(int stepsToExit) {
		this.stepsToExit = stepsToExit;
	}
	public boolean isFoundGoal() {
		return foundGoal;
	}

	public void setFoundGoal(boolean foundGoal) {
		this.foundGoal = foundGoal;
	}

	public Node getPathGoal() {
		return pathGoal;
	}

	public void setPathGoal(Node pathGoal) {
		this.pathGoal = pathGoal;
	}
}
