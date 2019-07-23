package ie.gmit.sw.ai.neuralnet;


import java.util.Scanner;

import ie.gmit.sw.ai.neuralnet.activator.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

// contains training data
public class NnFight {

	public double[] action(double health, double weapon, double angerLevel) throws Exception{		

		double[] params = {health, weapon, angerLevel};
		NeuralNetwork nn;
		
		// suppoosed to select the best function, seems to always use hyperbolic
		if(health > 5){
			nn = new NeuralNetwork(Activator.ActivationFunction.Sigmoid, 3, 3, 3);
			System.out.println("[INFO] Sigmoid is being used.");
		}
		else
		{
			nn = new NeuralNetwork(Activator.ActivationFunction.HyperbolicTangent, 3, 3, 3);
			System.out.println("[INFO] HyperbolicTangent is being used.");
		}

		double[] result = nn.process(params);

		int choice = (Utils.getMaxIndex(result) + 1);

		switch(choice){
		case 1:
			System.out.println("The spiders are panicing!");
			break;
		case 2:
			System.out.println("The spiders are attacking!");
			break;
		case 3:
			System.out.println("The Spiders are hiding!");
			break;
		default:
			System.out.println("The spiders are running away!");
		}

		double[] outcome = {health, weapon, angerLevel};
		return outcome;
	}

	public void train() {
		// TODO Auto-generated method stub
		
		
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new BufferedReader(new FileReader("resources/neural/data.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("[ERROR] Unable to read training data");
			return;
		}
	      int rows = 18;
	      int cols = 3;
	      double [][] data = new double[rows][cols];
	      while(sc.hasNextLine()) {
	         for (int i=0; i<data.length; i++) {
	            String[] line = sc.nextLine().trim().split(",");
	            for (int j=0; j<line.length; j++) {
	            	data[i][j] = Integer.parseInt(line[j]);
	            }
	         }
	      }	      
	      
	      try {
				sc = new Scanner(new BufferedReader(new FileReader("resources/neural/expected.txt")));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("[ERROR] Unable to read training data");
				return;
			}
	      
		      double [][] expected = new double[rows][cols];
		      while(sc.hasNextLine()) {
		         for (int i=0; i<expected.length; i++) {
		            String[] line = sc.nextLine().trim().split(",");
		            for (int j=0; j<line.length; j++) {
		            	expected[i][j] = Integer.parseInt(line[j]);
		            }
		         }
		      }		
		
		      // trains the neural network
		NeuralNetwork nn;
		nn = new NeuralNetwork(Activator.ActivationFunction.HyperbolicTangent, 3, 3, 3);
		System.out.println("[INFO] Training Neural Network...");
		Trainator trainer = new BackpropagationTrainer(nn);
		trainer.train(data, expected, 0.2, 10000);
		
	}

}