import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

/** This represents the end flag
 * @author Ai Ling Chiam
 * @version 3.0
 *
 * Credits to the project 1 sample solution for the idea of the constructor
 */
public class EndFlag extends Entity{
    private boolean isCollided = false;

    /** This constructor initializes all attributes in the class and the superclass
     * @param x This is the initial x-coordinate
     * @param y This is the initial y-coordinate
     * @param props This contains all the resources from the app.properties file
     */
    public EndFlag(int x, int y, Properties props) {
        super(x, y, props);
        this.setRadius(Double.parseDouble(props.getProperty("gameObjects.endFlag.radius")));
        this.setImage(new Image(props.getProperty("gameObjects.endFlag.image")));
        this.setSpeedX(Integer.parseInt(props.getProperty("gameObjects.endFlag.speed")));
    }

    /** This method updates the player and the movement of the end flag based on the input
     * @param input This is the input from the user
     * @param player This is the player
     */
    public void updateWithPlayer(Input input, Player player) {
        move(input);
        this.getImage().draw(this.getX(), this.getY());

        if (hasCollided(player) && !isCollided) {
            isCollided = true;
        }
    }

    /** This updates the movement of the end flag
     * @param input This is the input from the user
     */
    public void move(Input input) {
        if (input.isDown(Keys.LEFT)) {
            this.setX(this.getX() + this.getSpeedX());
        } else if (input.isDown(Keys.RIGHT)) {
            this.setX(this.getX() - this.getSpeedX());
        }
    }

    /** This returns whether the player collides with the end flag
     * @return A boolean value of isCollided
     */
    public boolean isCollided() {
        return isCollided;
    }
}
