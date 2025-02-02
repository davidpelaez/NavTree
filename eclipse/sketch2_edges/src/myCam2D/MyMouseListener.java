package myCam2D;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.Cursor;

import processing.core.PApplet;

public class MyMouseListener {
	
	public boolean panning=false; //This is set to true when CMD is down
	public MyCam cam;
	
	public MyMouseListener(MyCam _cam){
		cam = _cam;
		
	}

	
	public void keyEvent(final KeyEvent e) {
		//Track when cmd is pressed or released
		if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_META) {
			panning = false;
			cam.applet.cursor(PApplet.ARROW);
		}else if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_META) {
			panning = true;
			cam.applet.cursor(PApplet.CROSS);
		}
	}

	public void mouseEvent(final MouseEvent e) {
		if (e.getID() == MouseEvent.MOUSE_CLICKED	&& e.getClickCount() == 2) {
			//Double click
			System.out.println("2clicks");
			cam.pan(0,0);
			cam.zoom(1);
		} else if (e.getID() == MouseEvent.MOUSE_DRAGGED && panning) {
			//Mouse dragged, calculate deltas compared to the last frame
			float dx = cam.applet.mouseX - cam.applet.pmouseX;
			float dy = cam.applet.mouseY - cam.applet.pmouseY;
			//TODO: Interpolate change in position updating the cam through pan on every step
			cam.pan(cam.tx+dx, cam.ty+dy);
		}
	}

}
