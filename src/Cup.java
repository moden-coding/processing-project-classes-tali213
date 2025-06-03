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

  public Cup(PApplet c) {
    x = 260;
    y = 600;
    speed = 45;
    canvas = c;
    img = canvas.loadImage("Cup.png");
    img.resize(100, 100);
  }

  public void move(int dx, int dy) {
    x += dx;
    y += dy;
  }

  public void move() {
    if (left && x > 0) {
      move(-5, 0);
    }
    if (right && x < 500) {
      move(5, 0);
    }
  }

  public void draw() {
    canvas.image(img, x, y);
  }

  public void goRight() {
    right = true;
  }

  public void goLeft() {
    left = true;
  }

  public void stop() {
    left = false;
    right = false;
  }

  public boolean touching(Fruit fruit) {
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