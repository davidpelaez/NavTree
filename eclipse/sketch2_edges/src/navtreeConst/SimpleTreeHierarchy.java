package navtreeConst;

import processing.core.*;
import myCam2D.*;
import controlP5.*;

public class SimpleTreeHierarchy extends PApplet {
	int verticalCenter;
	public PImage bg;
	Navtree navtree;
	MyCam cam;
	ControlP5 controlP5;
	ControlWindow controlWindow;
	float zoom = 1, tx = 0, ty = 0;

	public void setup() {
		bg = loadImage("bg.png");
		
		size(1000, 550);
		navtree = new Navtree(this);
		setupGui();
		verticalCenter = height/2;
		frameRate(12);
		
		smooth();
		cam = new MyCam(this);
		int times = 50;
		System.out.println("Updating tree " + times + " times before initial draw");
		for (int i = 0; i < times; i++) {
			navtree.update();
			System.out.println(100 * i / times + "%");
		}
		System.out.println("Drawing begins");
	}

	public void setupGui() {
		controlP5 = new ControlP5(this);
		controlP5.setAutoDraw(false);
		controlWindow = controlP5.addControlWindow("controlP5window", 100, 100, 250, 500);
		controlWindow.hideCoordinates();
		createControllers();
	}

	public void createControllers() {
		Controller rootsWidth = controlP5.addSlider("rootsWidth", navtree.minRootY, 500, 40, 40, 100, 10);
		Controller secondWidth = controlP5.addSlider("secondWidth", navtree.minSecondY, 500, 40, 70, 100, 10);
		Controller thirdWidth = controlP5.addSlider("thirdWidth", navtree.minThirdY, 500, 40, 100, 100, 10);
		rootsWidth.moveTo(controlWindow);
		secondWidth.moveTo(controlWindow);
		thirdWidth.moveTo(controlWindow);
		controlWindow.setTitle("controls");
	}

	public void draw() {
		background(231, 232, 233);
		image(bg, (width - bg.width) / 2, (height - bg.height) / 2);
		pushMatrix();
		cam.feed();
		scale(zoom);
		translate(tx, ty);
		navtree.draw();
		popMatrix();
		controlP5.draw();
	}

	/*
	 * Events from sliders
	 */

	
	
	public void rootsWidth(int value) {
		navtree.minRootY = verticalCenter - (value/2);
		navtree.maxRootY = verticalCenter + (value/2);		
	}

	public void secondWidth(int value) {
		navtree.minSecondY = verticalCenter- (value/2);
		navtree.maxSecondY = verticalCenter + (value/2);
	}

	public void thirdWidth(int value) {
		navtree.minThirdY = verticalCenter - (value/2);
		navtree.maxThirdY = verticalCenter + (value/2);
	}

}
