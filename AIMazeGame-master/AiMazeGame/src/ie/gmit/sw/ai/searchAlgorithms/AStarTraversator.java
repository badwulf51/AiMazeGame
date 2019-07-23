package ie.gmit.sw.ai.searchAlgorithms;

import ie.gmit.sw.ai.*;
import java.util.*;

public class AStarTraversator extends ResetTraversal implements Traversator { // Adapted from the traversal labs.
	private Node goal;

	private Node pathGoal;
	private int stepsToExit;
	private boolean countSteps;
	private boolean foundGoal;
	private boolean enemySearch;

	public AStarTraversator(Node goal, boolean countSteps) {
		this.goal = goal;
		this.countSteps = countSteps;
	}

	public AStarTraversator(Node goal, boolean countSteps, boolean enemySearch) {
		this.goal = goal;
		this.countSteps = countSteps;
		this.enemySearch = enemySearch;
	}

	@Override
	public void traverse(Node[][] maze, Node node) {

		if (!countSteps && !enemySearch) {
			// once the player collects the pin icon the location will be searched for
			System.out.println("Looking for the maze exit!\n");
			resetA(maze);
		} else if (enemySearch) {
			resetB(maze);
		} else {
			resetB(maze);
		}

		long time = System.currentTimeMillis();
		int visitCount = 0;

		PriorityQueue<Node> open = new PriorityQueue<Node>(20,
				(Node current, Node next) -> (current.getPathCost() + current.getHeuristic(goal))
						- (next.getPathCost() + next.getHeuristic(goal)));
		java.util.List<Node> closed = new ArrayList<Node>();

		open.offer(node);
		node.setPathCost(0);
		while (!open.isEmpty()) {
			node = open.poll();
			closed.add(node);
			node.setVisited(true);
			visitCount++;

			if (node.isGoalNode() && node.getNodeType() != 'P' && !enemySearch) {
				time = System.currentTimeMillis() - time; // Stop the clock
				setStepsToExit(TraversatorStats.printStats(node, time, visitCount, countSteps));
				setFoundGoal(true);
				setPathGoal(node);
				break;
			} else

			if (node.isGoalNode() && node.getNodeType() == 'P' && enemySearch) {
				setFoundGoal(true);
				setPathGoal(node);
				break;
			}

			// Processes the adjancent nodes
			Node[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				Node child = children[i];
				int score = node.getPathCost() + 1 + child.getHeuristic(goal);
				int existing = child.getPathCost() + child.getHeuristic(goal);

				if ((open.contains(child) || closed.contains(child)) && existing < score) {
					continue;
				} else {
					open.remove(child);
					closed.remove(child);
					child.setParent(node);
					child.setPathCost(node.getPathCost() + 1);
					open.add(child);
				}
			}
		}
	}
	 //getters and setters
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

	public boolean isEnemySearch() {
		return enemySearch;
	}

	public void setEnemySearch(boolean enemySearch) {
		this.enemySearch = enemySearch;
	}

}
