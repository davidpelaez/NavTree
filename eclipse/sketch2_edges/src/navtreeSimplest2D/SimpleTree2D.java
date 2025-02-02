package navtreeSimplest2D;

import processing.core.*;
import myCam2D.*;

public class SimpleTree2D extends PApplet {
	
	public PImage bg;
	Navtree navtree;
	MyCam cam;
	float zoom = 1, tx = 0, ty = 0;

	public void setup() {	
		bg = loadImage("bg.png");
		size(1250, 550);
		frameRate(12);
		navtree = new Navtree(this);
		smooth();
		cam = new MyCam(this);
		int times = 50;
		System.out.println("Updating tree " + times +  " times before initial draw");		
		for(int i=0; i<times;i++){
				navtree.update();
				System.out.println(100*i/times + "%");
		}
		System.out.println("Drawing begins");
	}

	public void draw() {
		background(231,232,233);
		image(bg, (width-bg.width)/2, (height-bg.height)/2);
		pushMatrix();
		cam.feed();
		scale(zoom);
		translate(tx, ty);
		navtree.draw();
		popMatrix();
	}


}
