package navtree;

import processing.core.*;
import myCam2D.*;

public class SimpleTree2D extends PApplet {

	Navtree navtree;
	MyCam cam;
	float zoom = 1, tx = 0, ty = 0;

	public void setup() {
		
		size(1250, 550);
		frameRate(12);
		navtree = new Navtree(this);
		smooth();
		cam = new MyCam(this);

	}

	public void draw() {
		background(255);
		pushMatrix();
		cam.feed();
		scale(zoom);
		translate(tx, ty);
		navtree.draw();
		popMatrix();
	}


}
