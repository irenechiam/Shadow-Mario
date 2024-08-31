import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;
import java.util.*;

/** This represents the player
 * @author Ai Ling Chiam
 * @version 3.0
 *
 * Credits to the project 1 sample solution for the idea of the constructor
 */
public class Player implements Shootable{
    private final Properties PROPS;
    private final int X;
    private final int INITIAL_Y;
    private final double RADIUS;
    private static final int VERTICAL_SPEED = -20;
    private static final int FALL_SPEED = 2;
    private int y;
    private int speedY = 0;
    private double health;
    private int score = 0;
    private Image image;
    private boolean gotDoubleScore = false;
    private boolean gotInvincibility = false;
    private boolean isWithinRange = false;
    private List<Fireball> fireballs = new ArrayList<>();

    /** This constructor initializes all attributes in the class and the superclass
     * @param x This is the initial x-coordinate
     * @param y This is the initial y-coordinate
     * @param props This contains all the resources from the app.properties file
     */
    public Player(int x, int y, Properties props) {
        this.X = x;
        this.INITIAL_Y = y;
        this.y = y;
        this.PROPS = props;
        this.RADIUS = Double.parseDouble(props.getProperty("gameObjects.player.radius"));
        this.image = new Image(props.getProperty("gameObjects.player.imageRight"));
        this.health = Double.parseDouble(props.getProperty("gameObjects.player.health"));
    }

    /** This method updates the player's actions / image based on the input
     * @param input This is the input from the user
     */
    public void update(Input input) {
        if (input.wasPressed(Keys.LEFT)) {
            image = new Image(PROPS.getProperty("gameObjects.player.imageLeft"));
        }
        if (input.wasPressed(Keys.RIGHT)) {
            image = new Image(PROPS.getProperty("gameObjects.player.imageRight"));
        }

        if (input.wasPressed(Keys.S) && isWithinRange) {
            shootFireball();
        }
        image.draw(X, y);
        jump(input);
    }

    /** The enemy boss shoots the fireball */
    public void shootFireball() {
        Fireball newFireball = new Fireball(X, y, PROPS, 1);
        fireballs.add(newFireball);
    }

    /** This allows the player to jump
     * @param input The input from the user
     */
    private void jump(Input input) {
        if (input.wasPressed(Keys.UP)) {
            speedY = VERTICAL_SPEED;
        }

        if (speedY > 0 && y >= INITIAL_Y && !isDead()) {
            speedY = 0;
            y = INITIAL_Y;
        }

        y += speedY;
        if (!this.isDead()) {
            speedY++;
        }
    }


    /** This updates the y-speed to FALL_SPEED */
    public void dead() {
        speedY = FALL_SPEED;
    }

    /** This updates the isWithinRange attribute to set */
    public void setWithinRange(boolean set) {
        isWithinRange = set;
    }


    /**
     * @return the x-coordinate of the player
     */
    public int getX() {
        return this.X;
    }


    /**
     * @return the y-coordinate of the player
     */
    public int getY() {
        return this.y;
    }

    /**
     * @param newY The new y-coordinate of the player
     */
    public void setY(int newY) {
        this.y = newY;
    }


    /**
     * @return The player's radius
     */
    public double getRadius() {
        return this.RADIUS;
    }


    /**
     * @return The player's health
     */
    public double getHealth() {
        return this.health;
    }


    /** This updates the health attribute to newHealth
     * @param newHealth The new health value
     */
    public void setHealth(double newHealth) {
        this.health = newHealth;
    }


    /** This checks whether the player is dead
     * @return The boolean value of the health condition
     */
    public boolean isDead() {
        return health <= 0;
    }


    /**
     * @param set The boolean value of player getting the double score power-up
     */
    public void setDoubleScore(boolean set) {
        gotDoubleScore = set;
    }


    /**
     * @return The boolean value of player having double score power-up
     */
    public boolean getDoubleScore() {
        return gotDoubleScore;
    }


    /**
     * @param set The boolean value of player getting the invincibility power-up
     */
    public void setInvincibility(boolean set) {
        gotInvincibility = set;
    }


    /**
     * @return The boolean value of player having the invincibility power-up
     */
    public boolean getInvincibility() {
        return gotInvincibility;
    }

    /** This updates the speedY attribute to newSpeedY
     * @param newSpeedY The new value of the y-speed
     */
    public void setSpeedY(int newSpeedY) {
        speedY = newSpeedY;
    }


    /**
     * @return The list of fireballs created
     */
    public List<Fireball> getFireballs() {
        return fireballs;
    }


    /**
     * @return The player's score
     */
    public int getScore() {
        return score;
    }


    /** This updates the score attribute to the newScore
     * @param newScore The new player's score
     */
    public void setScore(int newScore) {
        score = newScore;
    }
}
