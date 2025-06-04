import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.*;

public class App extends PApplet {

    ArrayList<Fruit> fruits;
    Cup cup;

    boolean left = false;
    boolean right = false;
    private int count = 0; // variable for the score
    double highScore;
    int scene;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        cup = new Cup(this);
        fruits = new ArrayList<>();
        createFruit();
        scene = 0;

        readHighScore();
    }

    public void settings() {
        size(600, 800);
    }

    public void draw() {
        background(135, 206, 235);

        if (scene == 0) {
            textSize(30);
            fill(0);
            text("Click Anywhere to Start Game", 110, 400);
            textSize(20);
            text("Catch Strawberries, not Pickles", 165, 450);
        }

        if (scene == 1) {
            playGame();

            if (highScore == 0 || highScore < count) { // seeing if the new score is bigger. if it is, making that the new high score
                highScore = count;
                saveHighScore();
            }
        }
        if (scene == 2) {
            textSize(30);
            fill(0);
            text("Score: " + count, 245, 300);
            text("High Score: " + (int) highScore, 210, 400);
            text("Click Anywhere to Play Again", 125, 500);
        }
    }

    public void fruitMaker() { //creates the strawberries and pickles 
        float rx = random(550); //fruits can appear between 0 and this x coordinate
        float ry = random(-10500); // fruits spawn anywhere off the screen between these coordinates
        int x = (int) rx;
        int y = (int) ry;
        int random = (int) random(10); //using a random number to determine the amounts of pickles and strawberries
        if (random > 2) {
            Fruit fruit = new Fruit(x, y, this, false);
            fruits.add(fruit);
        } else {
            Fruit fruit = new Fruit(x, y, this, true);
            fruits.add(fruit);
        }
    }

    public void createFruit() { 
        for (int i = 0; i < 300; i++) { // makes 300 fruits
            fruitMaker();
        }
    }

    public void playGame() { //makes the game screen appear with all of the different components moving
        cup.draw();
        for (int i = 0; i < fruits.size(); i++) {
            Fruit f = fruits.get(i);
            f.display();
            f.move();

            if (cup.touching(f)) { // if the cup touches the pickle, 2 points are taken away
                if (f.isPickle()) {
                    count -= 2;
                } else { //if the cup touches the pickle, you get a point
                    count++;
                }
                fruits.remove(f); // the fruits get removed when the touch the cup
            }
            if (f.getY() > 800) { // if the fruits reach the bottom, they are removed from the list
                fruits.remove(f);
            }
        }
        cup.move();

        textSize(30);
        fill(0);
        text("Score: " + count, 50, 50);
        if (fruits.size() == 0) {
            scene = 2;
        }
    }

    public void keyPressed() {
        if (keyCode == LEFT) {
            cup.goLeft();
        }
        if (keyCode == RIGHT) {
            cup.goRight();
        }
        if (keyCode == UP) {
            fruits.clear();
        }
    }

    public void keyReleased() {
        if (keyCode == LEFT) {
            cup.stop();
        }
        if (keyCode == RIGHT) {
            cup.stop();
        }
    }

    public void mousePressed() {
        if (scene == 0) {
            scene = 1;
        }

        if (scene == 2) {
            scene = 1;
            createFruit();
            count = 0;
        }
    }

    public void saveHighScore() {
        try (PrintWriter writer = new PrintWriter("highscore.txt")) { // reading the file
            writer.println(highScore); // saving current high score
            writer.close();

        } catch (IOException e) { // catching the errors in reading the file
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public void readHighScore() {
        try (Scanner scanner = new Scanner(Paths.get("highscore.txt"))) { 
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                highScore = Double.valueOf(row);
            }
        } catch (Exception e) { //catching any errors
            System.out.println("Error: " + e.getMessage());
        }
    }
}
