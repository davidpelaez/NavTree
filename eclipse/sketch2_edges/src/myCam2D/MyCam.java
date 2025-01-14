package myCam2D;

import processing.core.*;

public class MyCam {
	
	public PApplet applet;
	public MyMouseListener mouseListener;
	public MyWheelListener mouseWheelListener;
	public float zoom=1, tx=0,ty=0, maxZoom=(float)3.5, minZoom=(float)0.35;
	
	public MyCam(PApplet p){
		applet = p;
		linkListeners();
	}
	
	public void linkListeners(){
		mouseListener = new MyMouseListener(this);
		mouseWheelListener = new MyWheelListener(this); 
		applet.registerMouseEvent(mouseListener);
		applet.registerKeyEvent(mouseListener);
		applet.addMouseWheelListener(mouseWheelListener);
	}
	
	//The animation isn't handled here, only the value is updated for feed to use
	public void pan(float px, float py){
		tx = px;
		ty = py;
	}
	//The animation isn't handled here, only the value is updated for feed to use	
	public void zoom(float zo){
		safelyZoom(zo);
	}
	
	public void safelyZoom(float zo){
		zoom = Math.min(maxZoom, Math.max(minZoom, zo));
	}
	
	public void feed(){
		applet.translate(tx,ty);
		applet.scale(zoom);		
	}

}
