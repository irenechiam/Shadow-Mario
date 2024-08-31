import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

/** This class represents a coin
 * @author Ai Ling Chiam
 * @version 3.0
 *
 * Credits to the project 1 sample solution for the idea of the constructor
 */
public class Coin extends Entity{
    private final int COIN_VALUE;
    private static final int VERTICAL_SPEED = -10;
    private int speedY = 0;
    private boolean isCollided = false;

    /** This constructor initializes all attributes in the class and the superclass
     * @param x This is the initial x-coordinate
     * @param y This is the initial y-coordinate
     * @param props This contains all the resources from the app.properties file
     */
    public Coin(int x, int y, Properties props) {
        super(x, y, props);
        this.setRadius(Double.parseDouble(props.getProperty("gameObjects.coin.radius")));
        COIN_VALUE = Integer.parseInt(props.getProperty("gameObjects.coin.value"));
        this.setImage(new Image(props.getProperty("gameObjects.coin.image")));
        this.setSpeedX(Integer.parseInt(props.getProperty("gameObjects.coin.speed")));
    }

    /** This method updates the player and the movement of the coin based on the input
     * @param input This is the input from the user
     * @param player This is the player
     */
    public void updateWithPlayer(Input input, Player player) {
        move(input);
        this.getImage().draw(this.getX(), this.getY());

        if (hasCollided(player) && !isCollided) {
            isCollided = true;
            speedY = VERTICAL_SPEED;
            int currentScore = player.getScore();
            if (player.getDoubleScore()) {
                player.setScore(currentScore + 2 * COIN_VALUE);
            } else {
                player.setScore(currentScore + COIN_VALUE);
            }
        }
    }

    /** This updates the movement of the coin
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