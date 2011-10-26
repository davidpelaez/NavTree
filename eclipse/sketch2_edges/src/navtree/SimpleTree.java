package navtree;

import processing.core.*;

public class SimpleTree extends PApplet {

	Navtree navtree;

	public void setup() {
		size(1000, 400);		
		smooth();		
		frameRate(12); // Reduces memory ocnsumtion?		
		navtree = new Navtree(this);
	}

	public void draw() {
		background(255);
		navtree.draw();
	}
}
