package trash;
import navtree.Node;
import controlP5.*;

public class SimpleTree extends navigable.NavigableSketch {
	
	public int maxTreeWidth;
	public final int  MAX_WIDTH = 4500;
	
	public Navtree2 navtree;
	
	
	@Override
	public void myDraw() {
		noStroke();
		Node theRoot;
		// Draw the roots and all their subtrees
		for (int i = 0; i < navtree.roots.size(); i++) {
			theRoot = (Node) navtree.roots.get(i);
			theRoot.drawNode();
		}
	}

	public void treeWidth(int value) {
		maxTreeWidth = value;
	}

	@Override
	public void mySetup() {
		
		  maxTreeWidth = width;
		  navtree = new Navtree2(this); // new Navtree((navigable.NavigableSketch)this); 
		  System.out.println("INITED navtree");
		  System.out.println(navtree.roots);
		
		// Slider to control the width of the tree
		super.controlP5.addSlider("treeWidth", super.width - 20, 4500, super.width - 20,super.width - 505, 10, 170, 10).setColorCaptionLabel(super.TEXT_COLOR); // def,
																			// x,
																			// y,
																			// w,h
																			// .setGroup(toolsGroup);
		super.toConsole("Added slider");
	}

	/*
	 * Connecting this sketch with the parent
	 */

	

	@Override
	public void controlEvent(ControlEvent theEvent) {
		super.controlEvent(theEvent);
	}

}
