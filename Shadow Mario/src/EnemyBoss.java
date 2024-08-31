import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.*;
import java.util.Properties;
import java.util.Random;

/** This represents the enemy boss
 * @author Ai Ling Chiam
 * @version 3.0
 */
public class EnemyBoss extends Entity implements Shootable{
    private static final int FRAMES = 100;
    private final int ACTIVATION_RADIUS;
    private static final int FALL_SPEED = 2;
    private List<Fireball> fireballs = new ArrayList<>();
    private double health;
    private boolean isKilled = false;
    private int frameCount = 0;
    private int speedY = 0;

    /** This constructor initializes all attributes in the class and the superclass
     * @param x This is the initial x-coordinate
     * @param y This is the initial y-coordinate
     * @param props This contains all the resources from the app.properties file
     */
    public EnemyBoss(int x, int y, Properties props) {
        super(x, y, props);
        this.setImage(new Image(props.getProperty("gameObjects.enemyBoss.image")));
        this.setRadius(Double.parseDouble(props.getProperty("gameObjects.enemyBoss.radius")));
        this.setSpeedX(Integer.parseInt(props.getProperty("gameObjects.enemyBoss.speed")));
        health = Double.parseDouble(props.getProperty("gameObjects.enemyBoss.health"));
        ACTIVATION_RADIUS = Integer.parseInt(props.getProperty("gameObjects.enemyBoss.activationRadius"));
    }

    /** This method updates the player and the movement of the enemy boss based on the input
     * @param input This is the input from the user
     * @param player This is the player
     */
    public void updateWithPlayer(Input input, Player player) {
        move(input);
        this.getImage().draw(this.getX(), this.getY());

        if (isWithinRange(player)) {
            if (frameCount == 0) {
                if (randomShot()) {
                    shootFireball();
                }
            }
        } else {
            player.setWithinRange(false);
        }
        frameCount++;
        frameCount = frameCount % FRAMES;
        this.setY(this.getY() + speedY);
    }

    /** This updates the movement of the enemy boss
     * @param input This is the input from the user
     */
    public void move(Input input) {
        if (input.isDown(Keys.LEFT)) {
            this.setX(this.getX() + this.getSpeedX());
        } else if (input.isDown(Keys.RIGHT)) {
            this.setX(this.getX() - this.getSpeedX());
        }
    }

    private boolean isWithinRange(Player player) {
        boolean isRange = Math.sqrt(Math.pow(player.getX() - this.getX(), 2) +
                Math.pow(player.getY() - this.getY(), 2)) <= ACTIVATION_RADIUS;
        player.setWithinRange(true);
        return isRange;
    }

    private boolean randomShot() {
        Random rd = new Random();
        return rd.nextBoolean();
    }

    /** The enemy boss shoots the fireball */
    public void shootFireball() {
        Fireball newFireball = new Fireball(this.getX(), this.getY(), this.getPROPS(), -1);
        fireballs.add(newFireball);
    }

    /** This changes the y-speed and sets the isKilled attribute to true */
    public void dead() {
        speedY = FALL_SPEED;
        isKilled = true;
    }

    /** This tells whether the enemy boss is dead
     * @return The boolean value of isKilled
     */
    public boolean isDead() {
        return isKilled;
    }

    /**
     * @return the health of the enemy boss
     */
    public double getHealth() {
        return health;
    }


    /** This sets the new health value to the attribute health
     * @param newHealth This is the new health value
     */
    public void setHealth(double newHealth) {
        this.health = newHealth;
    }


    /**
     * @return The list of fireballs created
     */
    public List<Fireball> getFireballs() {
        return fireballs;
    }
}
