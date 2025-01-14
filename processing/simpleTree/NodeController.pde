//This manages the basic visual representation of the Node
//And its reactive component as a GUI element. The Node completes the logic to connect it all.

class NodeController extends Controller {

  NodeController(ControlP5 theControlP5, String theName, int theX, int theY, int theWidth, int theHeight) {
    // the super class Controller needs to be initialized with the below parameters
    super(theControlP5, (Tab)(theControlP5.getTab("default")), theName, theX, theY, theWidth, theWidth);
    // the Controller class provides a field to store values in an 
    // float array format. for this controller, 2 floats are required.
    _myValue = -1;
  }

  // overwrite the updateInternalEvents method to handle mouse and key inputs.
  public void updateInternalEvents(PApplet theApplet) {
    if (getIsInside()) {
//      println("The mouse is hovering");
      if (isMousePressed) {
        println("Clicked");
        setValue(0);
      }
    }
  }

  // overwrite the draw method for the controller's visual representation.
  public void draw(PApplet theApplet) {
    // use pushMatrix and popMatrix when drawing
    // the controller.
    theApplet.pushMatrix();
    theApplet.translate(position().x(), position().y());
    // draw the background of the controller.
    if (getIsInside()) {
      theApplet.fill(255,0,0);
    } 
    else {
      theApplet.fill(100);
    }
  ellipse(0, 0, width, height);

    /*/ draw the controller-handle
    fill(255);
    rect(cX, cY, cWidth, cHeight);
    // draw the caption- and value-label of the controller
    // they are generated automatically by the super class
    captionLabel().draw(theApplet, 0, height + 4);
    valueLabel().draw(theApplet, 40, height + 4);*/

    theApplet.popMatrix();
  } 

  public void setValue(float theValue) {
    // setValue is usually called from within updateInternalEvents
    // in case of changes, updates. the update of values or 
    // visual elements is done here.
    
    // update the value label.
    //valueLabel().set(adjustValue(_myArrayValue[0], 0)+" / "+adjustValue(_myArrayValue[1], 0));

    // broadcast triggers a ControlEvent, updates are made to the sketch, 
    // controlEvent(ControlEvent) is called.
    // the parameter (FLOAT or STRING) indicates the type of 
    // value and the type of methods to call in the main sketch.
    broadcast(FLOAT);
  }

  // needs to be implemented since it is an abstract method in controlP5.Controller
  // nothing needs to be set since this method is only relevant for saving 
  // controller settings and only applies to (most) default Controllers.
  public void addToXMLElement(ControlP5XMLElement theElement) {
  }
}

