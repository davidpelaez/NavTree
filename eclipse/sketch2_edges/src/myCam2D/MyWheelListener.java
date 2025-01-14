package myCam2D;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MyWheelListener implements MouseWheelListener  {
	
	public MyCam cam;
	public float wheelScale = (float)0.05;
	public MyWheelListener(MyCam _cam){
		cam = _cam;
	}
	
	public void mouseWheelMoved(final MouseWheelEvent e) {
		System.out.println("The wheel scrolled");
		int delta = -e.getWheelRotation();
		//TODO animate zoom dampedZoom.impulse(wheelScale * delta);
		cam.zoom(cam.zoom + delta*wheelScale);
	}
	
}
