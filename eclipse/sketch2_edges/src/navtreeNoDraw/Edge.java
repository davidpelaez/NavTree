package navtreeNoDraw;

public class Edge {

	public Node from, to;
	public float length;
	public int fromId, toId;
	private Navtree navtree;

	Edge(Navtree _navtree, int _fromId, int _toId) {
		navtree = _navtree;
		fromId = _fromId;
		toId = _toId;
	}

	public void pointToNodes() {
		from = navtree.findNode(fromId);
		to = navtree.findNode(toId);

	}

	public void draw() {

	}

}
