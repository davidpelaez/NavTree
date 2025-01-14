package navtreeWheat;
import controlP5.*;
import processing.core.*;

public class NodeController extends Controller {
	Node node;
	Navtree navtree;
	NodeController(Node _node, Navtree nt) {
		super(nt.ntControlP5, (Tab)(nt.ntControlP5.getTab("default")), "node"+_node.id, _node.x, _node.y, 6, 6);
		navtree=nt;
		node = _node;		
 		_myValue = -1; //NOT USED
	}

	// overwrite the updateInternalEvents method to handle mouse and key inputs.
	public void updateInternalEvents(PApplet theApplet) {
		if (getIsInside()) {
			//	      println("The mouse is hovering");
			if (isMousePressed) {
				System.out.println("Clicked");
				setValue(0);
			}
		}
	}

	// overwrite the draw method for the controller's visual representation.
	public void draw(PApplet theApplet) {
		node.setFill();
		// use pushMatrix and popMatrix when drawing
		// the controller.
		this.setPosition(node.x, node.y);
		theApplet.pushMatrix();
		theApplet.translate(position().x(), position().y());
		// draw the background of the controller.
		if (getIsInside()) {
			theApplet.fill(255);
			theApplet.rect(-width, -height, width*2, height*2);
			navtree.featureNode(node);
		} 
		else {
			if(navtree.featuredNodeId == node.id) navtree.unfeatureNode();
			theApplet.ellipse(-width/2, -height/2, width, height);
		}
		theApplet.popMatrix();
	} 

	public void setValue(float theValue) {
		broadcast(FLOAT);
	}

	// needs to be implemented since it is an abstract method in controlP5.Controller
	public void addToXMLElement(ControlP5XMLElement theElement) {
	}
}
