import bagel.*;
import java.util.Properties;
import java.util.*;
import bagel.DrawOptions;
import bagel.util.Colour;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2024
 * Most of the attributes and methods are taken from the sample solution of Project 1
 *
 * Please enter your name below
 * @author Ai Ling Chiam
 *
 * Credits to the project 1 sample solution for the idea for update, initializeGameObjects and updateGameObjects methods
 */
public class ShadowMario extends AbstractGame {
    private boolean gameStart = false;
    private boolean gameEnd = false;
    private final int WINDOW_HEIGHT;
    private final Image BACKGROUND_IMAGE;
    private final Properties PROPS;
    private final Properties MESSAGE_PROPS;

    // Fonts
    private final String FONT_FILE;
    private final Font TITLE_FONT;
    private final Font INSTRUCTION_FONT;
    private final Font MESSAGE_FONT;
    private final Font SCORE_FONT;
    private final Font HEALTH_FONT;

    // Messages
    private final String TITLE;
    private final String INSTRUCTION;
    private final String SCORE;
    private final String HEALTH;
    private final String WINMESSAGE;
    private final String LOSEMESSAGE;

    // TITLE
    private final int TITLE_X;
    private final int TITLE_Y;

    // Instruction
    private final int INSTRUCTION_Y;

    // Score
    private final int SCORE_X;
    private final int SCORE_Y;

    // Player Health
    private final int PLAYER_HEALTH_X;
    private final int PLAYER_HEALTH_Y;

    // EnemyBoss Health
    private final int BOSS_HEALTH_X;
    private final int BOSS_HEALTH_Y;

    // Message
    private final int MESSAGE_Y;

    private int level;
    private Player player;
    private Platform platform;
    private List<Enemy> enemies;
    private List<Coin> coins;
    private List<FlyingPlatform> flyingPlatforms;
    private List<DoubleScore> doubleScores;
    private List<Invincibility> invincibilities;
    private EnemyBoss enemyBoss;
    private EndFlag endFlag;
    private final DrawOptions options;


    /**
     * The constructor
     */
    public ShadowMario(Properties game_props, Properties message_props) {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
              Integer.parseInt(game_props.getProperty("windowHeight")),
              message_props.getProperty("title"));

        this.PROPS = game_props;
        this.MESSAGE_PROPS = message_props;

        WINDOW_HEIGHT = Integer.parseInt(game_props.getProperty("windowHeight"));
        TITLE = message_props.getProperty("title");
        FONT_FILE = game_props.getProperty("font");
        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));

        TITLE_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("title.fontSize")));
        TITLE_X = Integer.parseInt(game_props.getProperty("title.x"));
        TITLE_Y = Integer.parseInt(game_props.getProperty("title.y"));

        INSTRUCTION = message_props.getProperty("instruction");
        INSTRUCTION_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("instruction.fontSize")));
        INSTRUCTION_Y = Integer.parseInt(game_props.getProperty("instruction.y"));

        SCORE = message_props.getProperty("score");
        SCORE_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("score.fontSize")));
        SCORE_X = Integer.parseInt(game_props.getProperty("score.x"));
        SCORE_Y = Integer.parseInt(game_props.getProperty("score.y"));

        HEALTH = message_props.getProperty("health");
        HEALTH_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("playerHealth.fontSize")));
        PLAYER_HEALTH_X = Integer.parseInt(game_props.getProperty("playerHealth.x"));
        PLAYER_HEALTH_Y = Integer.parseInt(game_props.getProperty("playerHealth.y"));

        BOSS_HEALTH_X = Integer.parseInt(game_props.getProperty("enemyBossHealth.x"));
        BOSS_HEALTH_Y = Integer.parseInt(game_props.getProperty("enemyBossHealth.y"));

        MESSAGE_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("message.fontSize")));
        MESSAGE_Y = Integer.parseInt(game_props.getProperty("message.y"));
        WINMESSAGE = MESSAGE_PROPS.getProperty("gameWon");
        LOSEMESSAGE = MESSAGE_PROPS.getProperty("gameOver");
        options = new DrawOptions();
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
        ShadowMario game = new ShadowMario(game_props, message_props);
        game.run();
    }

    /**
     * Performs a state update of the selected level.
     * Allows the game to exit when the escape key is pressed.
     * Handle screen navigation between levels and instruction pages here.
     */
    @Override
    protected void update(Input input) {

        // close window
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        if (!gameStart) {
            TITLE_FONT.drawString(TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_FONT.drawString(INSTRUCTION,
                    Window.getWidth() / 2.0 - INSTRUCTION_FONT.getWidth(INSTRUCTION) / 2, INSTRUCTION_Y);
            if (input.wasPressed(Keys.NUM_1) || input.wasPressed(Keys.NUM_2) || input.wasPressed(Keys.NUM_3)) {
                gameStart = true;
                gameEnd = false;
                // Put this in a private method
                List<String[]> itemsLevelSelected = new ArrayList<>();
                if (input.wasPressed(Keys.NUM_1)) {
                    itemsLevelSelected = IOUtils.readCsv(PROPS.getProperty("level1File"));
                    level = 1;
                } else if (input.wasPressed(Keys.NUM_2)) {
                    itemsLevelSelected = IOUtils.readCsv(PROPS.getProperty("level2File"));
                    level = 2;
                } else if (input.wasPressed(Keys.NUM_3)) {
                    itemsLevelSelected = IOUtils.readCsv(PROPS.getProperty("level3File"));
                    level = 3;
                }
                initializeGameObjects(itemsLevelSelected);
            }
        } else if (player.isDead() && player.getY() > WINDOW_HEIGHT) {
            MESSAGE_FONT.drawString(LOSEMESSAGE,
                    Window.getWidth() / 2.0 - MESSAGE_FONT.getWidth(LOSEMESSAGE) / 2, MESSAGE_Y);
            if (input.wasPressed(Keys.SPACE)) {
                gameStart = false;
            }
        } else {
            if (gameEnd) {
                MESSAGE_FONT.drawString(WINMESSAGE,
                        Window.getWidth() / 2.0 - MESSAGE_FONT.getWidth(WINMESSAGE) / 2, MESSAGE_Y);
                if (input.wasPressed(Keys.SPACE)) {
                    gameStart = false;
                }
            } else {
                // Game is running
                SCORE_FONT.drawString(SCORE + player.getScore(), SCORE_X, SCORE_Y);
                HEALTH_FONT.drawString(HEALTH + Math.round(player.getHealth()*100),
                        PLAYER_HEALTH_X, PLAYER_HEALTH_Y);
                if (level == 3) {
                    HEALTH_FONT.drawString(HEALTH +
                            Math.round(enemyBoss.getHealth()*100), BOSS_HEALTH_X, BOSS_HEALTH_Y,
                            options.setBlendColour(Colour.RED));
                }
                updateGameObjects(input);
            }
        }
    }

    /**
     * Initializes all the entities in the items list
     * @param items The list of entities in the game
     */
    private void initializeGameObjects(List<String[]> items) {
        enemies = new ArrayList<>();
        coins = new ArrayList<>();
        flyingPlatforms = new ArrayList<>();
        doubleScores = new ArrayList<>();
        invincibilities = new ArrayList<>();

        for (String[] item : items) {
            int x = Integer.parseInt(item[1]);
            int y = Integer.parseInt(item[2]);

            switch (item[0]) {
                case "PLAYER":
                    player = new Player(x, y, PROPS);
                    break;
                case "PLATFORM":
                    platform = new Platform(x, y, PROPS);
                    break;
                case "ENEMY":
                    Enemy enemy = new Enemy(x, y, PROPS);
                    enemies.add(enemy);
                    break;
                case "COIN":
                    Coin coin = new Coin(x, y, PROPS);
                    coins.add(coin);
                    break;
                case "FLYING_PLATFORM":
                    FlyingPlatform fPlatform = new FlyingPlatform(x, y, PROPS);
                    flyingPlatforms.add(fPlatform);
                    break;
                case "DOUBLE_SCORE":
                    DoubleScore doubleScore = new DoubleScore(x, y, PROPS);
                    doubleScores.add(doubleScore);
                    break;
                case "INVINCIBLE_POWER":
                    Invincibility inv = new Invincibility(x, y, PROPS);
                    invincibilities.add(inv);
                    break;
                case "ENEMY_BOSS":
                    enemyBoss = new EnemyBoss(x, y, PROPS);
                    break;
                case "END_FLAG":
                    endFlag = new EndFlag(x, y, PROPS);
                    break;
            }
        }
    }

    /**
     * Updates all the entities in the game based on the user input
     * @param input Input from the user
     */
    private void updateGameObjects(Input input) {
        platform.updateWithPlayer(input, player);
        for (Enemy e : enemies) {
            e.updateWithPlayer(input, player);
        }
        for (Coin c : coins) {
            c.updateWithPlayer(input, player);
        }
        for (FlyingPlatform fPlatform : flyingPlatforms) {
            fPlatform.updateWithPlayer(input, player);
        }
        for (DoubleScore doubleScore : doubleScores) {
            doubleScore.updateWithPlayer(input, player);
        }
        for (Invincibility inv : invincibilities) {
            inv.updateWithPlayer(input, player);
        }
        if (level == 3) {
            for (Fireball fireball : player.getFireballs()) {
                fireball.updateWithEnemyBoss(input, enemyBoss);
            }
            enemyBoss.updateWithPlayer(input, player);
            for (Fireball fireball : enemyBoss.getFireballs()) {
                fireball.updateWithPlayer(input, player);
            }
        }
        player.update(input);
        endFlag.updateWithPlayer(input, player);

        if (checkForWin()) {
            gameEnd = true;
        }
    }

    /**
     * Checks if the player met the requirements to win
     * @return The boolean value of whether the user has won
     */
    private boolean checkForWin() {
        if (level == 3) {
            return winLevel3();
        }
        return endFlag.isCollided();
    }

    /**
     * Checks whether the player won in Level 3
     * @return The boolean value of player's win in Level 3
     * */
    private boolean winLevel3() {
        return enemyBoss.isDead() && endFlag.isCollided();
    }
}
