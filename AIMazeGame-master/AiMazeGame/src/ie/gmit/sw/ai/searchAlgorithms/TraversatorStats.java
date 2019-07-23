package ie.gmit.sw.ai.searchAlgorithms;

import ie.gmit.sw.ai.*;

import java.awt.Color;

//This class is used to get statistics of the search algorithms and set the nodes
public class TraversatorStats {
	public static int printStats(Node node, long time, int visitCount, boolean countSteps){
		double depth = 0;
		int count = 0;
		
		while (node != null){			
			node = node.getParent();
			if (node != null){
				if(!countSteps){
					if(node.getNodeType() != 'P'){
						node.setColor(Color.YELLOW);
						node.setNodeType('T');
						node.setWalkable(true);
					}
				}
				count++;
			}
			depth++;		
		}
        
        return count;
	}
}