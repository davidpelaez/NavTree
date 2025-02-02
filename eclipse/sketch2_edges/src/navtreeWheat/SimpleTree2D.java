package navtreeWheat;

import controlP5.*;
import processing.core.*;
import myCam2D.*;

public class SimpleTree2D extends PApplet {

	Navtree navtree;
	MyCam cam;
	float zoom = 1, tx = 0, ty = 0;
	ControlP5 controlP5;
	ControlWindow controlWindow;
	CheckBox checkbox;
	
	/*
	 * This constants are used by the chexbox in the controls to keep all the related booleans updated
	 */
	public static final int ROOTS=98, SECOND=97, THIRD=96,TABS=95,WINDOWS=94,BLANK=93;
	
	/*
	 * Booleans controlling what is being drawn
	 */
	
	public boolean showRoots=true,showSecond=true,showThird=true,showTabs=true,showWindows=true,showBlank=false;

	public void setup() {
		size(1250, 550);
		frameRate(12);
		navtree = new Navtree(this);
		smooth();
		setupGui();
		cam = new MyCam(this);
		int times = 10;
		System.out.println("Updating tree " + times + " times before initial draw");
		for (int i = 0; i < times; i++) {
			navtree.update();
			System.out.println(100 * i / times + "%");
		}
		System.out.println("Drawing begins");
	}

	public void draw() {
		background(51, 51, 51);
		pushMatrix();
		cam.feed();
		scale(zoom);
		translate(tx, ty);
		navtree.draw();
		popMatrix();
		controlP5.draw();
	}

	public void setupGui() {
		controlP5 = new ControlP5(this);
		controlP5.setAutoDraw(false);
		controlWindow = controlP5.addControlWindow("controlP5window", 100, 100, 250, 500);
		controlWindow.hideCoordinates();
		createControllers();
	}

	public void createControllers() {
		controlP5.addButton("pause", 0, 20, 150, 80, 19).moveTo(controlWindow);
		controlP5.controller("pause").setLabel("Pause Animation");
		controlP5.addButton("colorByTime", 0, 20, 180, 80, 19).moveTo(controlWindow);
		controlP5.controller("colorByTime").setLabel("Color by Time");
		controlP5.addButton("colorByDepth", 1, 110, 180, 80, 19).moveTo(controlWindow);
		controlP5.controller("colorByDepth").setLabel("Color by Depth");

		controlWindow.setTitle("controls");
		// Create checkboxes
		checkbox = controlP5.addCheckBox("checkbox", 20, 20);
		checkbox.setId(999);
		checkbox.moveTo(controlWindow);
		;
		// make adjustments to the layout of a checkbox.
		/*
		 * checkbox.setColorForeground(color(120));
		 * checkbox.setColorActive(color(255));
		 * checkbox.setColorLabel(color(128));
		 */
		checkbox.setItemsPerRow(1);
		checkbox.setSpacingColumn(30);
		checkbox.setSpacingRow(10);
		// add items to a checkbox.
		checkbox.addItem("Root Nodes", ROOTS).setValue(showRoots);
		checkbox.addItem("2nd Level Nodes", SECOND).setValue(showSecond);
		checkbox.addItem("3rd Level Nodes", THIRD).setValue(showThird);
		checkbox.addItem("New Windows", WINDOWS).setValue(showWindows);
		checkbox.addItem("Tabs", TABS).setValue(showTabs);
		checkbox.addItem("Blank Nodes", BLANK).setValue(showBlank);

	}

	public void pause() {
		navtree.update = !navtree.update;
	}

	public void colorByDepth() {
		System.out.println("BY DEPTH");
	}

	public void colorByTime() {
		System.out.println("BY TIME");
	}

	/*
	 * manage the selections in the checkbox-items
	 */
	public void controlEvent(ControlEvent theEvent) {
		
		if (!theEvent.isGroup()) return; //this avoid to many nested conditional checks
		// checkbox uses arrayValue to store the state of individual checkbox-items.
		if(theEvent.group().name() == "checkbox"){
			for (int i = 0; i < theEvent.group().arrayValue().length; i++) {
				int n = (int) theEvent.group().arrayValue()[i];
				if (n == 1) { // If that item/box in the array is active
					checkboxSwitch((int)checkbox.getItem(i).internalValue(),true);
				}else{ //If it's not active
					checkboxSwitch((int)checkbox.getItem(i).internalValue(),false);
				}
			}//FOR ENDs
		}
	}//Event control ENDs
	
	public void checkboxSwitch(int val, boolean newValue){
		System.out.println("EVAL: " + val +" - " + newValue);
		switch(val){
		case ROOTS:
			showRoots = newValue;
			break;
		case SECOND:
			showSecond = newValue;
			break;
		case THIRD:
			showThird = newValue;
			break;
		case TABS:
			showTabs = newValue;
			break;
		case WINDOWS:
			showWindows = newValue;
			break;
		case BLANK:
			showBlank = newValue;
			break;
		}
	}
}
