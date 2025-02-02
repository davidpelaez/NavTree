package navtreeContrainedXY;

import java.util.Date;

import org.json.JSONObject;
import processing.core.PApplet;

public class Node implements Comparable {
	public int r=1; //This default is used by the roots

	public static final int ROOT_LEVEL = 1, SECOND_LEVEL = 2, THIRD_LEVEL = 3;
	public static final int NULL_PARENT = -1;
	public int id, parentId, extra, depth, level;
	public int unixDate, index;
	public String url;
	public int[] children = null; // Array with the ids of the children
	public boolean isRoot, hasChildren, draw = true;
	public Navtree navtree;
	public Node parent;
	public float x, y;
	public float dx, dy; // Offset added is nodes are too close
	public PApplet applet;
	public boolean fixedX = false, fixedY = false;

	public static int MAX_DELTA = 20, MAX_TREE_WIDTH = 3000;

	Node(Navtree _navtree, JSONNodeReader jNode) {
		navtree = _navtree;
		applet = navtree.applet;
		x = applet.random(applet.width);
		y = applet.random(applet.height);
		build(jNode.id, jNode.url, jNode.parentId, jNode.isRoot, jNode.extra, jNode.unixDate, jNode.hasChildren, jNode.childrenIds, jNode.depth);
	}

	public void build(int _id, String _url, int _parentId, boolean _isRoot, int _extra, int _unixDate, boolean _hasChildren, String _childrenIds, int _depth) {
		id = _id;
		depth = _depth;
		parentId = _parentId;
		isRoot = _isRoot;
		extra = _extra;
		unixDate = _unixDate;
		hasChildren = _hasChildren;
		// Process children IDS
		if (_childrenIds.length() > 0) {
			String[] splitted = _childrenIds.split(",");
			children = new int[splitted.length];
			for (int i = 0; i < children.length; i++) {
				children[i] = Integer.parseInt(splitted[i].trim());
			}
		}
		switch (depth) {
		case 0:
			level = ROOT_LEVEL;
			break;
		case 1:
		case 2:
			level = SECOND_LEVEL;
			break;
		default:
			level = THIRD_LEVEL;
			break;
		}

		// Discard this node if is childless
		if (isRoot) {
			if (!hasChildren) {
				draw = false; // Root withotu children, dont' draw it
				// navtree.removeNode(this);
			}
		}
		if(depth==1){  //Randomly put above or under the center of the screen if its 2nd level
			if(r>0){
				r = 1;
			}else{
				r = -1;
			}
		}
		if(isRoot){
			y = (applet.height / 2);
			fixY();
		}
	}// Constructor ends
	
	//Set the height of the node depending on its depth
	public void setFixedY(){
		int delta = depth*20; 
		float r = applet.random(-50, 50);
		fixY();
		if(isRoot){
			y = (applet.height / 2); 
		}else if(depth == 1){
			y = (applet.height / 2) + (delta*r);
		}else if(depth < 10){
			y = (applet.height / 2) + (delta*parent.getR());
		}else{
			y = (applet.height / 2) + (200*parent.getR());
		}
	}
	
	public int getR(){ //Recursion until second levend
		if(depth == 1){
			return r;
		}else{
			try{
				return parent.r;
			}catch(java.lang.NullPointerException e){
				navtree.removeNode(this);
				return 1;
			}
		}
	}

	public void pointToParent() {
		if (!isRoot) {
			try {
				parent = navtree.findNode(parentId);
			} catch (Exception e) {
				navtree.removeNode(this);
			}
		}
	}

	// Check that the node has is parent and all its children
	public void checkFamily() {
		float px;
		try {
			px =  parent.x;
		} catch (java.lang.NullPointerException e) {
			navtree.removeNode(this);
		}
		try{
			for(int i : children)
				px = navtree.findNode(i).x;
		}catch(Exception e){
			navtree.removeNode(this);
		}

	}

	// The index is the position in the array, this is used to position the obj
	// according to its date to improve the tree alg.
	public void setIndex(int i) {
		index = i;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public boolean hasChildren() {
		return hasChildren;
	}

	public Date getDate() {
		Date time = new Date();
		time.setTime((long) unixDate * 1000);
		return time;
	}

	/*
	 * Calculation methods
	 */

	public void relax() {
		float ddx = 0;
		float ddy = 0;
		for (int j = 0; j < navtree.nodes.length; j++) {
			Node n = navtree.nodes[j];
			if (n != this && n.depth == depth) { //Only compare to nodes at the same leve
				float vx = x - n.x;
				float vy = y - n.y;
				float lensq = vx * vx + vy * vy;
				if (lensq == 0) {
					ddx += applet.random(1);
					ddy += applet.random(1);
				} else if (lensq < 100 * 100) {
					ddx += vx / lensq;
					ddy += vy / lensq;
				}
			}
		}
		float dlen = PApplet.mag(ddx, ddy) / 2;
		if (dlen > 0) {
			dx += ddx / dlen;
			dy += ddy / dlen;
		}
	}// Relax ends

	void update() {
		if (!fixedX) {
			x += PApplet.constrain(dx, -MAX_DELTA, MAX_DELTA);
			if (!isRoot) { // All children must be to the right of its parent
				if (x < parent.x) {
					x = parent.x + MAX_DELTA;
				}
				if(y > applet.height/2){ //Is under the center
					if (y < parent.y) {
						y = parent.y + MAX_DELTA;
					}
				}else{//is over the center
					if (y > parent.y) {
						y = parent.y - MAX_DELTA;
					}
				}
				
			}
		}
		if (!fixedY) {
			y += PApplet.constrain(dy, -MAX_DELTA, MAX_DELTA);
			// y = PApplet.constrain(y, 0, applet.height);
		}
		dx /= 2;
		dy /= 2;
	}// Update ends

	void draw() {

		setFill();
		applet.stroke(180);
		applet.strokeWeight((float) 0.5);
		if (draw) {
			applet.ellipse(x, y, 3, 3);
		}
	}// Draw ends

	public void setFill() {
		switch (depth) {
		case 0:
			applet.fill(26);
			break;
		case 1:
			applet.fill(245, 217, 0);
			break;
		case 2:
			applet.fill(27, 123, 157);
			break;
		case 3:
			applet.fill(131, 217, 167);
			break;
		case 4:
			applet.fill(63, 169, 245);
			break;
		case 5:
			applet.fill(122, 201, 67);
			break;
		default:
			applet.fill(34, 181, 115);
			break;
		}
	}

	// Lock the Y position of the node
	public void fixY() {
		fixedY = true;
	}

	// Lock the Y position of the node
	public void fixX() {
		fixedX = true;
	}

	public void setInitialX() {
		// The position that the node should have in the graph in X
		/*
		 * int y2_y1 = MAX_TREE_WIDTH; int x2_x1 = (navtree.dateDelta) - (0); //
		 * Only for clarity purposes! double mx = (unixDate-navtree.minDate) *
		 * y2_y1 / x2_x1; x = (int)mx;
		 */
		if (isRoot) {
			x = index * 50;// *(applet.width/(navtree.rootCount-navtree.singleCount));
		} else { // This is recursive, right?
			parent.setInitialX();
			x = parent.x;
		}
	}

	public int compareTo(Object obj) {
		Node node = (Node) obj;
		int oDate = node.unixDate;
		if (unixDate == oDate) {
			return 0;
		} else if (unixDate > oDate) {
			return 1;
		} else {
			return -1;
		}
	}

}
