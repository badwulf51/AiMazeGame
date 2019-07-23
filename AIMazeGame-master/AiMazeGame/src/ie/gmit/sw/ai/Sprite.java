package ie.gmit.sw.ai;

import javax.imageio.*;
import java.awt.image.*;

public class Sprite {
	private String name; 
	private BufferedImage[][] images; // animates sprite
 	private int index = 0; // starting direction sprite faces when game is loaded
 	private int frame = 0; // starting index
	
	public Sprite(String name, int frames, String... files) throws Exception{
		this.name = name;
		this.index = 0; // intisalises the starting index as 0 
		this.images = new BufferedImage[files.length / frames][frames]; 
		
		
		int row = 0;
		int col = 0;
		for (int i = 0; i < files.length; i++){
			images[row][col] = ImageIO.read(new java.io.File(files[i])); 

			col++;
			if (col % frames == 0){
				row++;
				col = 0;
			} 
		}
	}
	
	public BufferedImage getNext(){ // returns the next image frame 
		frame++;
		if (frame == images[index].length) frame = 0; // goes back to start of the array

		return images[index][frame]; 
	}
	// getters and setters
	public int getImageIndex(){
		return this.index;
	}
	
	public void setImageIndex(int idx){
		this.index = idx;
	}

	public String getName(){
		return this.name;
	}
}