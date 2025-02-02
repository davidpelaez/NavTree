package navtreeContrainedXY;

import java.io.*;
import processing.core.*;
import org.json.*;
import java.util.Arrays;

public class Navtree {
	
	public int maxRootY=100, minRootY=50, maxSecondY=250, minSecondY=150, maxThirdY=450, minThirdY=300;

	public java.util.HashMap<Integer, Node> nodeTable;
	public Node[] nodes = new Node[100], roots = new Node[100], singles = new Node[100];
	public Edge[] edges = new Edge[500];
	public int nodeCount = 0, edgeCount = 0, rootCount = 0, singleCount = 0;
	public PApplet applet;
	public boolean update = true;// Define if continue updating the tree or only
									// drawing it.

	public int maxDate, minDate, dateDelta, maxDepth = 0; // This is to find
															// what span of
															// time's being
															// graphed

	Navtree(PApplet _applet) {
		applet = _applet;
		nodeTable = new java.util.HashMap<Integer, Node>();
		String[] myJsonStrings = loadFile("navtree10days.json");
		System.out.println("JSon lines loaded = " + myJsonStrings.length);
		String jsonLine;
		JSONObject jsonData;
		JSONNodeReader jNode;
		for (int i = 0; i < myJsonStrings.length; i++) {
			jsonLine = myJsonStrings[i];
			try {
				jsonData = new JSONObject(jsonLine);
				jNode = new JSONNodeReader(jsonData);
				addNode(jNode);
				if (jNode.depth > maxDepth) {
					maxDepth = jNode.depth;
				}
				if (i == 0) {
					minDate = jNode.unixDate;
					maxDate = jNode.unixDate;
				}
			} catch (JSONException e) {
				System.out.println("There was an error parsing the JSONObject.");
				System.out.println(e);
			}
		} // Line read ends. All nodes in memory
		dateDelta = (maxDate - minDate);
		// Point all edges to its nodes and all nodes to its parent node.
		shrinkArrays();
		java.util.Arrays.sort(nodes, 0, nodes.length);
		java.util.Arrays.sort(roots, 0, roots.length);
		// Set indexes so that each node or edge can request navtree to remove
		// it
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].setIndex(i); //This is used for location of the nodes
		}
		
		for (Node n : nodes)
			n.pointToParent();
		//for (Node n : nodes)
			//n.setFixedY();
		//	for (Node n : nodes) BUG
		//	n.checkFamily();
		for (Edge e : edges)
			e.pointToNodes();
		System.out.println("EdgeCount = " + edgeCount);	
		System.out.println("NodeCount = " + nodeCount);
		
		System.out.println("RootCount = " + rootCount);
		System.out.println("SingleCount = " + singleCount);
		System.out.println("maxDepth = " + maxDepth);
		System.out.println("maxDate = " + maxDate);
		System.out.println("minDate = " + minDate);
	}// Constructor ends
	
	public void generateCoords(){
		
	}

	protected void shrinkArrays() {
		roots = java.util.Arrays.copyOfRange(roots, 0, rootCount);
		nodes = java.util.Arrays.copyOfRange(nodes, 0, nodeCount);
		edges = java.util.Arrays.copyOfRange(edges, 0, edgeCount);
		singles = java.util.Arrays.copyOfRange(singles, 0, singleCount);
	}

	protected void addEdge(int fromId, int toParentId) {
		Edge e = new Edge(this, fromId, toParentId);
		if (edgeCount == edges.length) {
			edges = (Edge[]) PApplet.expand(edges);
		}
		edges[edgeCount++] = e;
	}

	public void addRoot(Node n) {
		if (rootCount == roots.length) {
			roots = (Node[]) PApplet.expand(roots);
		}
		roots[rootCount++] = n;
		if (!n.hasChildren) {
			addSingle(n);
		}
	}

	public void addSingle(Node n) {
		if (singleCount == singles.length) {
			singles = (Node[]) PApplet.expand(singles);
		}
		singles[singleCount++] = n;

	}

	protected void addNode(JSONNodeReader jNode) {
		// Manage dates
		if (minDate > jNode.unixDate) {
			minDate = jNode.unixDate;
		}
		if (minDate < jNode.unixDate) {
			maxDate = jNode.unixDate;
		}
		// Add node
		Node node = new Node(this, jNode);
		if (nodeCount == nodes.length) {
			nodes = (Node[]) PApplet.expand(nodes);
		}
		nodeTable.put(node.id, node);
		if (!node.isRoot()) {
			addEdge(node.id, node.parentId);
		} else {
			addRoot(node);
		}
		nodes[nodeCount++] = node;
	}

	// TODO: Modify the behaviour because all nodes will be added when find is
	// to be used

	public Node findNode(int id) throws Exception {
		Node n = (Node) nodeTable.get(id);
		if (n == null) {
			throw new Exception("The requested node isn't registered. Possible bug or Json Navtree integrity error.");
		}
		return n;
	}

	/*
	 * Load file and return it as an array of strings
	 */
	protected String[] loadFile(String path) {
		java.util.ArrayList<String> fileLines = new java.util.ArrayList<String>();
		String[] lines = null;
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(path);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				fileLines.add(strLine);
			}
			// Close the input stream
			in.close();
			lines = fileLines.toArray(new String[1]);
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return lines;
	}
	public void update(){
		for (Edge e : edges)
			e.relax(); //This produces tension between nodes bringing them together
		for (Node n : nodes)
			n.relax();
		for (Node n : nodes)
			n.update();		
	}
	public void draw() {
		if (update) {
			update();
		}// Update block ends
		for (Edge e : edges)
			e.draw();
		for (Node n : nodes)
			n.draw();
	}

	public void removeNode(Node n) {
		int i;
		for(i=0;i<nodes.length;i++){
			if(nodes[i] == n){
				nodeTable.remove(nodes[i]); //Remove from node table
				break;				
			}
		}
		
		
		Node[] result = new Node[nodes.length - 1];
		if (i == 0) { // The obj is the first of the array
			result = Arrays.copyOfRange(nodes, 1, nodes.length);
		} else if (i == nodes.length) {
			result = Arrays.copyOfRange(nodes, 0, nodes.length - 1);
		} else {// The object is in the middle
			Node[] part1 = Arrays.copyOfRange(nodes, 0, i);
			Node[] part2 = Arrays.copyOfRange(nodes, i + 1, nodes.length);
			result = mergeNodesArrays(part1, part2);
		}
		nodes = result;
		nodeCount--;		
	}

	public Node[] mergeNodesArrays(Node[] a, Node[] b) {
		Node[] result = new Node[a.length + b.length];
		for (int i = 0; i < a.length; i++) {
			result[i] = a[i];
		}
		for (int i = 0; i < b.length; i++) {
			result[i + a.length] = b[i];
		}
		return result;
	}

	public void removeEdge(Edge e) {
		int i;
		for(i=0;i<edges.length;i++){
			if(edges[i] == e){
				break;				
			}
		}

		Edge[] result = new Edge[edges.length - 1];
		if (i == 0) { // The obj is the first of the array
			result = Arrays.copyOfRange(edges, 1, edges.length);
		} else if (i == edges.length) {
			result = Arrays.copyOfRange(edges, 0, edges.length - 1);
		} else {// The object is in the middle
			Edge[] part1 = Arrays.copyOfRange(edges, 0, i);
			Edge[] part2 = Arrays.copyOfRange(edges, i + 1, edges.length);
			result = mergeEdgesArrays(part1, part2);
		}
		edges = result;
		edgeCount--;

	}

	public Edge[] mergeEdgesArrays(Edge[] a, Edge[] b) {
		Edge[] result = new Edge[a.length + b.length];
		for (int i = 0; i < a.length; i++) {
			result[i] = a[i];
		}
		for (int i = 0; i < b.length; i++) {
			result[i + a.length] = b[i];
		}
		return (Edge[]) result;
	}

}
