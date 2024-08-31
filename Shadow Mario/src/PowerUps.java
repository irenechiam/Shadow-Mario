import bagel.Input;
import bagel.Keys;
import java.util.Properties;

/** This represents all the power-ups
 * @author Ai Ling Chiam
 * @version 3.0
 */
public abstract class PowerUps extends Entity{
    protected final int MAX_FRAMES;
    protected int frameCount = 0;
    protected boolean isCollided = false;
    protected final int VERTICAL_SPEED = -10;
    protected int speedY = 0;

    /** This constructor initializes all attributes in the class and the superclass
     * @param x This is the initial x-coordinate
     * @param y This is the initial y-coordinate
     * @param props This contains all the resources from the app.properties file
     */
    public PowerUps(int x, int y, Properties props) {
        super(x, y, props);
        MAX_FRAMES = Integer.parseInt(props.getProperty("gameObjects.doubleScore.maxFrames"));
    }

    /** This is an abstract updateWithPlayer method */
    public abstract void updateWithPlayer(Input input, Player player);

    /** This updates the movement of the power-ups
     * @param input This is the input from the user
     */
    public void move(Input input) {
        if (input.isDown(Keys.LEFT)) {
            this.setX(this.getX() + this.getSpeedX());
        } else if (input.isDown(Keys.RIGHT)) {
            this.setX(this.getX() - this.getSpeedX());
        }
        this.setY(this.getY() + speedY);
    }
}
