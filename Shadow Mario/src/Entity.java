import bagel.Image;
import bagel.Input;
import java.util.Properties;

/** This represents all the entities (except the player)
 * @author Ai Ling Chiam
 * @version 3.0
 */
public abstract class Entity {
    private final Properties PROPS;
    private int x;
    private int y;
    private int speedX;
    private double radius;
    private Image image;

    /** This constructor initializes all attributes in the class and the superclass
     * @param x This is the initial x-coordinate
     * @param y This is the initial y-coordinate
     * @param props This contains all the resources from the app.properties file
     */
    public Entity(int x, int y, Properties props) {
        this.x = x;
        this.y = y;
        this.PROPS = props;
    }

    /** It checks whether the entities collided with the player
     * @param player This is the player
     * @return The boolean value of collision detection
     */
    // Try making it into a generic method
    public boolean hasCollided(Player player) {
        return Math.sqrt(Math.pow(player.getX() - x, 2) +
                Math.pow(player.getY() - y, 2)) <= player.getRadius() + radius;
    }

    /** It checks whether the entities collided with the enemy boss
     * @param boss This is the enemy boss
     * @return The boolean value of collision detection
     */
    public boolean hasCollided(EnemyBoss boss) {
        return Math.sqrt(Math.pow(boss.getX() - x, 2) +
                Math.pow(boss.getY() - y, 2)) <= boss.getRadius() + radius;
    }

    /** This is an abstract updateWithPlayer method */
    public abstract void updateWithPlayer(Input input, Player player);

    /** This is an abstract move method */
    public abstract void move(Input input);


    /** This sets the new x-speed to the speedX attribute
     * @param newSpeedX This is the new x-speed
     */
    public void setSpeedX(int newSpeedX) {
        speedX = newSpeedX;
    }


    /**
     * @return The value of x-speed
     */
    public int getSpeedX() {
        return speedX;
    }


    /** This sets the value of new radius to the attribute radius
     * @param radius The new value of radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * @return the value of the radius
     */
    public double getRadius() {
        return radius;
    }


    /** This sets the new image to the attribute image
     * @param image The new image of the entity
     */
    public void setImage(Image image) {
        this.image = image;
    }


    /**
     * @return The entity's image
     */
    public Image getImage() {
        return image;
    }


    /**
     * @return The x-coordinate of the entity
     */
    public int getX() {
        return x;
    }


    /** This sets the new x-coordinate to the attribute x
     * @param x The x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }


    /**
     * @return The y-coordinate of the entity
     */
    public int getY() {
        return y;
    }


    /** This sets the new y-coordinate to the attribute y
     * @param y The new y-coordinate of the entity
     */
    public void setY(int y) {
        this.y = y;
    }


    /**
     * @return PROPS
     */
    public Properties getPROPS() {
        return PROPS;
    }
}
