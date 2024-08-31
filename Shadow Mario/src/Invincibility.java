import bagel.Image;
import bagel.Input;
import java.util.Properties;

/** This represents the invincibility power-up
 * @author Ai Ling Chiam
 * @version 3.0
 */
public class Invincibility extends PowerUps{

    /** This constructor initializes all attributes in the class and the superclass
     * @param x This is the initial x-coordinate
     * @param y This is the initial y-coordinate
     * @param props This contains all the resources from the app.properties file
     */
    public Invincibility(int x, int y, Properties props) {
        super(x, y, props);
        this.setImage(new Image(props.getProperty("gameObjects.invinciblePower.image")));
        this.setRadius(Double.parseDouble(props.getProperty("gameObjects.invinciblePower.radius")));
        this.setSpeedX(Integer.parseInt(props.getProperty("gameObjects.invinciblePower.speed")));
    }

    /** This method updates the player and the movement of the invincibility power-up based on the input
     * @param input This is the input from the user
     * @param player This is the player
     */
    public void updateWithPlayer(Input input, Player player) {
        move(input);
        this.getImage().draw(this.getX(), this.getY());

        if (hasCollided(player) && !(this.isCollided)) {
            this.isCollided = true;
            this.speedY = this.VERTICAL_SPEED;
            player.setInvincibility(true);
        }

        if (player.getInvincibility()) {
            this.frameCount++;
        }

        if (this.frameCount >= this.MAX_FRAMES) {
            player.setInvincibility(false);
        }
    }
}
