import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;
import java.util.Random;
import java.lang.Math;

/** This represents an enemy
 * @author Ai Ling Chiam
 * @version 3.0
 *
 * Credits to the project 1 sample solution for the idea of the constructor
 */
public class Enemy extends Entity implements RandomMovable, Attackable{
    private final int MAXIMUM_DISPLACEMENT;
    private final int RANDOM_SPEED;
    private final double DAMAGE_SIZE;
    private boolean isHit = false;
    private int currentDisplacement = 0;
    private int direction = randomDirection();

    /** This constructor initializes all attributes in the class and the superclass
     * @param x This is the initial x-coordinate
     * @param y This is the initial y-coordinate
     * @param props This contains all the resources from the app.properties file
     */
    public Enemy(int x, int y, Properties props) {
        super(x, y, props);
        this.setRadius(Double.parseDouble(props.getProperty("gameObjects.enemy.radius")));
        MAXIMUM_DISPLACEMENT = Integer.parseInt(props.getProperty("gameObjects.enemy.maxRandomDisplacementX"));
        RANDOM_SPEED = Integer.parseInt(props.getProperty("gameObjects.enemy.randomSpeed"));
        DAMAGE_SIZE = Double.parseDouble(props.getProperty("gameObjects.enemy.damageSize"));
        this.setImage(new Image(props.getProperty("gameObjects.enemy.image")));
        this.setSpeedX(Integer.parseInt(props.getProperty("gameObjects.enemy.speed")));
    }

    /** This method updates the player and the movement of the enemy based on the input
     * @param input This is the input from the user
     * @param player This is the player
     */
    public void updateWithPlayer(Input input, Player player) {
        move(input);
        this.getImage().draw(this.getX(), this.getY());

        // Does the enemy still damages the player once the invincibility is gone and collided with player?
        if (this.hasCollided(player) && !isHit) {
            isHit = true;
            if (!player.getInvincibility()) {
                damagePlayer(player);
            }
        }
    }

    /** This updates the movement of the enemy
     * @param input This is the input from the user
     */
    public void move(Input input) {
        if (Math.abs(currentDisplacement) >= MAXIMUM_DISPLACEMENT) {
            direction *= -1;
        }

        this.setX(this.getX() + (RANDOM_SPEED * direction));
        if (input.isDown(Keys.LEFT)) {
            this.setX(this.getX() + this.getSpeedX());
        } else if (input.isDown(Keys.RIGHT)) {
            this.setX(this.getX() - this.getSpeedX());
        }
        currentDisplacement += RANDOM_SPEED * direction;
    }

    /** This is used to reduce the player's health
     * @param player This is the player
     */
    public void damagePlayer(Player player) {
        double newHealth = player.getHealth() - DAMAGE_SIZE;
        player.setHealth(newHealth);

        if (newHealth <= 0) {
            player.dead();
        }
    }

    /** This randomly chooses the direction where the enemy moves
     * @return The initial direction of the enemy
     */
    public int randomDirection() {
        Random rd = new Random();
        return (rd.nextInt(2) == 0) ? -1 : 1;
    }
}
