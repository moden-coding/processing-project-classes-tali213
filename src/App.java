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
    private int count = 0;
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

            if (highScore == 0 || highScore < count) {
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

    public void fruitMaker() {
        float rx = random(550);
        float ry = random(-10000);
        int x = (int) rx;
        int y = (int) ry;
        int random = (int) random(10);
        if (random > 2) {
            Fruit fruit = new Fruit(x, y, this, false);
            fruits.add(fruit);
        } else {
            Fruit fruit = new Fruit(x, y, this, true);
            fruits.add(fruit);
        }
    }

    public void createFruit() {
        for (int i = 0; i < 200; i++) {
            fruitMaker();
        }
    }

    public void playGame() {
        cup.draw();
        for (int i = 0; i < fruits.size(); i++) {
            Fruit f = fruits.get(i);
            f.display();
            f.move();

            if (cup.touching(f)) {
                if (f.isPickle()) {
                    count -= 2;
                } else {
                    count++;
                }
                fruits.remove(f);
            }
            if (f.getY() > 800) {
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
        try (PrintWriter writer = new PrintWriter("highscore.txt")) {
            writer.println(highScore);
            writer.close();

        } catch (IOException e) {
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
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
