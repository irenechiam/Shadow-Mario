import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

/** This represents the platform
 * @author Ai Ling Chiam
 * @version 3.0
 *
 * Credits to the project 1 sample solution for the idea of the constructor
 */
public class Platform extends Entity{
    private static final int MAX_COORDINATE = 3000;

    /** This constructor initializes all attributes in the class and the superclass
     * @param x This is the initial x-coordinate
     * @param y This is the initial y-coordinate
     * @param props This contains all the resources from the app.properties file
     */
    public Platform(int x, int y, Properties props) {
        super(x, y, props);
        this.setImage(new Image(props.getProperty("gameObjects.platform.image")));
        this.setRadius(0);
        this.setSpeedX(Integer.parseInt(props.getProperty("gameObjects.platform.speed")));
    }

    /** This method updates the player and the movement of the platform based on the input
     * @param input This is the input from the user
     * @param player This is the player
     */
    public void updateWithPlayer(Input input, Player player) {
        move(input);
        this.getImage().draw(this.getX(), this.getY());
    }

    /** This updates the movement of the platform
     * @param input This is the input from the user
     */
    public void move(Input input) {
        if (input.isDown(Keys.LEFT)) {
            if (this.getX() < MAX_COORDINATE) {
                this.setX(this.getX() + this.getSpeedX());
            }
        } else if (input.isDown(Keys.RIGHT)) {
            this.setX(this.getX() - this.getSpeedX());
        }
    }
}
