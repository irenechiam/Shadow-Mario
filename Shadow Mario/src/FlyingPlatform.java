import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;
import java.util.Random;
import java.lang.Math;

/** This represents a flying platform
 * @author Ai Ling Chiam
 * @version 3.0
 */
public class FlyingPlatform extends Entity implements RandomMovable{
    private final int MAXIMUM_DISPLACEMENT;
    private final int HALF_LENGTH;
    private final int HALF_HEIGHT;
    private int currentDisplacement = 0;
    private final int RANDOM_SPEED;
    private int direction = randomDirection();

    /** This constructor initializes all attributes in the class and the superclass
     * @param x This is the initial x-coordinate
     * @param y This is the initial y-coordinate
     * @param props This contains all the resources from the app.properties file
     */
    public FlyingPlatform(int x, int y, Properties props) {
        super(x, y, props);
        this.setRadius(0);
        this.setImage(new Image(props.getProperty("gameObjects.flyingPlatform.image")));
        this.setSpeedX(Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.speed")));
        MAXIMUM_DISPLACEMENT = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX"));
        HALF_HEIGHT = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.halfHeight"));
        HALF_LENGTH = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.halfLength"));
        RANDOM_SPEED = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.randomSpeed"));
    }


    /** This method updates the player and the movement of the flying platform based on the input
     * @param input This is the input from the user
     * @param player This is the player
     */
    public void updateWithPlayer(Input input, Player player) {
        move(input);
        this.getImage().draw(this.getX(), this.getY());

        if (onFlyingPlatform(player)) {
            player.setSpeedY(0);
            player.setY(this.getY() - HALF_HEIGHT);
        }
    }

    /** This updates the movement of the flying platform
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

    /** This randomly chooses the direction where the enemy moves
     * @return The initial direction of the enemy
     */
    public int randomDirection() {
        Random rd = new Random();
        return (rd.nextInt(2) == 0) ? -1 : 1;
    }

    /** This checks if the conditions are met in order for the player to be on the flying platform
     * @param player This is the player
     * @return The boolean value of the 3 conditions
     */
    private boolean onFlyingPlatform(Player player) {
        return condition1(player) && condition2(player) && condition3(player);
    }

    /** This is the first condition
     * @param player This is the player
     * @return The boolean value of the first condition
     */
    private boolean condition1(Player player) {
        return Math.abs(player.getX() - this.getX()) < HALF_LENGTH;
    }

    /** This is the second condition
     * @param player This is the player
     * @return The boolean value of the second condition
     */
    private boolean condition2(Player player) {
        return (this.getY() - player.getY()) <= HALF_HEIGHT;
    }

    /** This is the third condition
     * @param player This is the player
     * @return The boolean value of the third condition
     */
    private boolean condition3(Player player) {
        return (this.getY() - player.getY()) >= (HALF_HEIGHT - 1);
    }
}
