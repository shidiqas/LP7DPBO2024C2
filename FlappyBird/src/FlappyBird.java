import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidht = 360;
    int frameHeight = 640;

    //image attributes
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    //player
    int playerStartPosX = frameWidht / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidht = 34;
    int playerHeight = 24;
    Player player;

    //pipes attrributes
    int pipeStartPosX = frameWidht;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;

    //game logic
    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;

    //score
    float score = 0;
    JLabel scoreLabel;

    // boolean to track game over status
    private boolean gameOver = false;
    private boolean massageGameOver = false;

    //constructor
    public FlappyBird() {
        setPreferredSize(new Dimension(frameWidht, frameHeight));
        setFocusable(true);
        addKeyListener(this);
        //setBackground(Color.blue);

        //load images
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidht, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pipa");
                placePipes();
            }
        });
        pipesCooldown.start();

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

        // score label
        scoreLabel = new JLabel("Score: " + (int) score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(scoreLabel);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

        if (massageGameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            String gameOverMessage = "Game Over";
            String scoreMessage = "Your score: " + (int) score;
            String restartMessage = "Press 'R' to restart";
            int gameOverWidth = g.getFontMetrics().stringWidth(gameOverMessage);
            int scoreWidth = g.getFontMetrics().stringWidth(scoreMessage);
            int restartWidth = g.getFontMetrics().stringWidth(restartMessage);
            g.drawString(gameOverMessage, (frameWidht - gameOverWidth) / 2, frameHeight / 2 - 60);
            g.drawString(scoreMessage, (frameWidht - scoreWidth) / 2, frameHeight / 2 - 20);
            g.drawString(restartMessage, (frameWidht - restartWidth) / 2, frameHeight / 2 + 20);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, frameWidht, frameHeight, null);

        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }
    }

    public void move() {
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

            // add score
            if (!pipe.isPassed() && pipe.getPosX() < playerStartPosX - pipeWidth) {
                pipe.setPassed(true);
                score += 0.5;
                scoreLabel.setText("Score: " + (int) score);
            }
        }
        // Check for collisions
        checkCollision();
    }

    public void checkCollision() {
        // Check collision with pipes
        for (Pipe pipe : pipes) {
            // Check collision with upper pipe
            if (player.getPosX() + player.getWidth() > pipe.getPosX()
                    && player.getPosX() < pipe.getPosX() + pipe.getWidth()
                    && player.getPosY() < pipe.getPosY() + pipe.getHeight()
                    && player.getPosY() + player.getHeight() > pipe.getPosY()) {
                gameOver();
            }
            // Check collision with lower pipe
            else if (player.getPosX() + player.getWidth() > pipe.getPosX()
                    && player.getPosX() < pipe.getPosX() + pipe.getWidth()
                    && player.getPosY() + player.getHeight() > pipe.getPosY()
                    && player.getPosY() < pipe.getPosY() + pipe.getHeight()) {
                gameOver();
            }
        }

        // Check collision with bottom frame
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            gameOver();
        }
    }

    public void gameOver() {
        gameOver = true;
        massageGameOver = true;
        gameLoop.stop();
        pipesCooldown.stop();
        repaint();
    }

    public void restartGame() {
        gameOver = false;
        massageGameOver = false;
        // Reset player position
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);

        // Clear pipes and reset score
        pipes.clear();
        score = 0;
        scoreLabel.setText("Score: " + (int) score);

        // Restart timers
        gameLoop.start();
        pipesCooldown.start();
    }

    public void placePipes(){
        int randomPosY = (int) (pipeStartPosY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.setVelocityY(-10);
        }

        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
