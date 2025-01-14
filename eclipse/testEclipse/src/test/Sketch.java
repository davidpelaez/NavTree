package test;
import processing.core.*;
import processing.opengl.*;
import controlP5.*;
import peasy.*;
import org.json.*; 

public class Sketch extends PApplet { 
  
  PeasyCam cam;

  public void setup() {
    size(200,200,OPENGL);
    cam = new PeasyCam(this, 100);
    cam.setMinimumDistance(50);
    cam.setMaximumDistance(500);
  }
  public void draw() {
    rotateX((float) -.5);
    rotateY((float)-.5);
    background(0);
    fill(255,0,0);
    box(30);
    pushMatrix();
    translate(0,0,20);
    fill(0,0,255);
    box(5);
    popMatrix();
  }

}
