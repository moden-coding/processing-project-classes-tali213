import java.security.Key;

import processing.core.*;

public class Cup {

  private int x;
  private int y;
  private int speed;
  private int color;
  private PApplet canvas;
  PImage img;
  boolean left = false;
  boolean right = false;

  public Cup(PApplet c) { // initializing the cup variables
    x = 260;
    y = 600;
    speed = 45;
    canvas = c;
    img = canvas.loadImage("Cup.png");
    img.resize(100, 100);
  }

  public void move(int dx, int dy) { //allowing the cup to move left and right
    x += dx;
    y += dy;
  }

  public void move() { // gives the cup movement
    if (left && x > 0) { // makes sure it can only move between the screen limits, moves left
      move(-5, 0);
    }
    if (right && x < 500) { // does not pass the right isde of the screen, moves right
      move(5, 0);
    }
  }

  public void draw() {
    canvas.image(img, x, y);
  }

  public void goRight() { // makes the cup go right
    right = true;
  }

  public void goLeft() { // makes the cup go left
    left = true;
  }

  public void stop() { // makes he cup stop moving
    left = false;
    right = false;
  }

  public boolean touching(Fruit fruit) { // detects when the cup is touching a fruit
    int fruitLeft = fruit.getX();
    int fruitRight = fruit.getX() + fruit.getWidth();
    int fruitTop = fruit.getY();
    int fruitBottom = fruit.getY() + fruit.getHeight();

    int cupLeft = x;
    int cupRight = x + 100;
    int cupTop = y;
    int cupBottom = y + 20;

    if (fruitLeft < cupRight) {
      if (fruitRight > cupLeft) {
        if (fruitBottom > cupTop) {
          if (fruitTop < cupBottom) {
            return true;
          }
        }
      }
    }
    return false;
  }

}