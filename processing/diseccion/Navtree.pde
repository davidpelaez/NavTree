
class Navtree {

  public int NULL_PARENT = -1;
  int dateDelta = 0;

  String[] myJsonStrings;
  public java.util.Hashtable nodes;
  public ArrayList roots;
  public int maxDate, minDate; //This is to find what span of time's being graphed
  public Node oldestNode, youngestNode;

  Navtree() {
    nodes = new Hashtable();
    roots = new ArrayList();
    //Load the Json file, build in memory the tree.  
    myJsonStrings = loadStrings("myNavtree.json");
    toConsole(myJsonStrings.length + " nodes Loaded");
    String theLine;
    //For node creation in memory
    int id, date, parent, extra;
    String url, children;
    boolean root, hasChildren;
    Node theNode;
    for (int i=0; i < myJsonStrings.length; i++) {
      theLine = myJsonStrings[i];    
      try {
        //For each line there's the Json of a node.
        //Read the node
        JSONObject jsonData = new JSONObject(theLine);
        id = (Integer)((Number)jsonData.get("id")).intValue();
        try {
          parent = (Integer)((Number)jsonData.get("parent")).intValue();
        }
        catch (ClassCastException e) {
          parent = NULL_PARENT;
        }
        extra = (Integer)((Number)jsonData.get("extra")).intValue();
        root = jsonData.getBoolean("root");
        hasChildren = jsonData.getBoolean("has_children");
        date = (Integer)((Number)jsonData.get("date")).intValue();
        //Is this is the first node in the tree, then set that node's date as the reference point & avoid nullPointer
        if (i==0) {
          maxDate = date;
          minDate = date;
        }
        compareDate(date);
        children = (String)jsonData.get("children");
        try {
          url = (String)jsonData.get("url");
        }
        catch (ClassCastException e) {
          url = "";
        }
        //Create the node in memory & attach to hastable
        //println(parent);
        theNode = new Node(this, id, url, parent, root, extra, date, hasChildren, children);
        nodes.put(theNode.id, theNode);
        if (root) {
          roots.add(theNode);
        }
        //Check if this Node has the date to become the oldest or youngest Node.
        if (date == maxDate) {
          oldestNode = theNode;
        }
        if (date == minDate) {
          youngestNode = theNode;
        }
        //println(JSONObject.getNames(nytData));
      }
      catch (JSONException e) {
        println ("There was an error parsing the JSONObject.");
        println (e);
      }
    }//Tree mapping in memory ends
    toConsole(nodes.size() + " nodes");
    toConsole(roots.size() + " roots");
    toConsole("maxDate: " + getMaxDate() + " - " + maxDate);
    toConsole("minDate: " + getMinDate() + " - " + minDate);
    toConsole("oldest: " + oldestNode.date);
    toConsole("youngest: " + youngestNode.date);
    dateDelta = (maxDate-minDate); //SHOU
    toConsole("dateDelta: " + dateDelta);
    toConsole("maxdateDelta: " + oldestNode.getDelta());
    toConsole("mindateDelta: " + youngestNode.getDelta());
  } //Constuctor ends


  public void compareDate(int theDate) {
    //The date is bigger
    if (theDate>maxDate) {
      maxDate = theDate;
    }
    //The date is smaller that the one recorded so far
    if (theDate<minDate) {
      minDate = theDate;
    }
  }//CompareDate ends

  public Date getMaxDate() {
    Date time = new Date();
    time.setTime((long)maxDate*1000);
    return time;
  }

  public Date getMinDate() {
    Date time = new Date();
    time.setTime((long)minDate*1000);
    return time;
  }

  //Get the difference in second the max and the min date  
  public int getTimespanSeconds() {
    // Get msec from each, and subtract.
    long diff = getMaxDate().getTime() - getMinDate().getTime();
    return (int)(diff/1000);
    //Thanks to http://bit.ly/qqpd5e
  }

  public int getTimespanDays() {
    return getTimespanSeconds()/86400;
  }
}

