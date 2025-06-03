import processing.core.*;

public class Fruit {
    private int x;
    private int y;
    private int size;
    private int speed;
    private int color;
    private int width;
    private int height;
    PImage img;
    private PApplet canvas;
    private boolean pickle;

    public Fruit(int posx, int posy, PApplet c, boolean isPickle) {
        x = posx;
        y = posy;
        width = 60;
        height = 60;
        canvas = c;
        size = 40;
        speed = 1;
        pickle = isPickle;

        if (isPickle) {
            img = canvas.loadImage("Pickle.png");
            img.resize(50, 50);
        } else {
            img = canvas.loadImage("Strawberry.png");
            img.resize(60, 60);
        }

    }

    public void display() {
        canvas.image(img, x, y);
    }

    public void move() {
        y += 5;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isPickle(){
        return pickle;
    }

}
