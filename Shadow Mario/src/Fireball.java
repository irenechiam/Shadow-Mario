import bagel.Input;
import bagel.Image;
import bagel.Keys;
import java.util.Properties;

/** This represents a fireball
 * @author Ai Ling Chiam
 * @version 3.0
 */
public class Fireball extends Entity implements Attackable{
    private final int DIRECTION;
    private final double DAMAGE_VALUE;
    private final int PROJECTILE_SPEED;
    private boolean isHit;

    /** This constructor initializes all attributes in the class and the superclass
     * @param x This is the initial x-coordinate
     * @param y This is the initial y-coordinate
     * @param props This contains all the resources from the app.properties file
     */
    public Fireball(int x, int y, Properties props, int direction) {
        super(x, y, props);
        this.setImage(new Image(props.getProperty("gameObjects.fireball.image")));
        this.setRadius(Double.parseDouble(props.getProperty("gameObjects.fireball.radius")));
        this.setSpeedX(Integer.parseInt(props.getProperty("gameObjects.fireball.speed")));
        DAMAGE_VALUE = Double.parseDouble(props.getProperty("gameObjects.fireball.damageSize"));
        PROJECTILE_SPEED =Integer.parseInt(props.getProperty("gameObjects.fireball.projectileSpeed"));
        DIRECTION = direction;
    }

    /** This method updates the player and the movement of the fireball based on the input
     * @param input This is the input from the user
     * @param player This is the player
     */
    // Make this a generic method maybe??
    public void updateWithPlayer(Input input, Player player) {
        if (this.hasCollided(player) && !isHit) {
            isHit = true;
            if (!player.getInvincibility()) {
                damagePlayer(player);
            }
        }

        if (!isHit) {
            move(input);
            this.getImage().draw(this.getX(), this.getY());
        }
    }

    /** This method updates the boss and the movement of the fireball based on the input
     * @param input This is the input from the user
     * @param boss This is the enemy boss
     */
    public void updateWithEnemyBoss(Input input, EnemyBoss boss) {
        if (this.hasCollided(boss) && !isHit) {
            isHit = true;
            damageBoss(boss);
        }

        if (!isHit) {
            move(input);
            this.getImage().draw(this.getX(), this.getY());
        }
    }

    /** This updates the movement of the fireball
     * @param input This is the input from the user
     */
    public void move(Input input) {
        if (input.isDown(Keys.LEFT)) {
            this.setX(this.getX() + this.getSpeedX());
        } else if (input.isDown(Keys.RIGHT)) {
            this.setX(this.getX() - this.getSpeedX());
        }
        this.setX(this.getX() + (PROJECTILE_SPEED * DIRECTION));
    }

    /** This is used to reduce the player's health
     * @param player This is the player
     */
    public void damagePlayer(Player player) {
        double newHealth = player.getHealth() - DAMAGE_VALUE;
        player.setHealth(newHealth);

        if (newHealth <= 0) {
            player.dead();
        }
    }

    /** This is used to reduce the player's health
     * @param boss This is the enemy boss
     */
    private void damageBoss(EnemyBoss boss) {
        double newHealth = boss.getHealth() - DAMAGE_VALUE;
        boss.setHealth(newHealth);

        if (newHealth <= 0) {
            boss.dead();
        }
    }
}
