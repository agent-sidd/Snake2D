import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener{
	//setting image icons for game panel
	private ImageIcon snaketitle =  new ImageIcon( getClass().getResource("snaketitle.jpg"));
	private ImageIcon upmouth =  new ImageIcon( getClass().getResource("upmouth.png"));
	private ImageIcon downmouth =  new ImageIcon( getClass().getResource("downmouth.png"));
	private ImageIcon leftmouth =  new ImageIcon( getClass().getResource("leftmouth.png"));
	private ImageIcon rightmouth =  new ImageIcon( getClass().getResource("rightmouth.png"));
	private ImageIcon snakeimage =  new ImageIcon( getClass().getResource("snakeimage.png"));
	private ImageIcon enemy =  new ImageIcon( getClass().getResource("enemy.png"));
	private int[] snakexlength=new int[750];
	private int[] snakeylength=new int[750];
	private int lengthofsnake = 4;
	private int moves = 0;
	private int score = 0;
	private boolean gameOver=false;
	private boolean right=true;
	private boolean left=false;
	private boolean up=false;
	private boolean down=false;
	private Timer timer;
	private int  delay = 100;
	//enemy pos
	private int[] Xpos= {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,
			600,625,650,675,700,725,750,775,800,825,850};
	
	private int[] Ypos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,
			600,625};
	//declaration ends here
	private Random random = new Random();
	private int enemyX, enemyY;
	public GamePanel() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer = new Timer(delay,this);
		timer.start();
		newEnemy(); 
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.WHITE);
		g.drawRect(24,10,851,55);
		g.drawRect(24,74,851,576);
		snaketitle.paintIcon(this, g, 25, 11);
		g.setColor(Color.BLACK);
		g.fillRect(25,75,850,575);
		if(moves == 0) {
			snakexlength[0] = 100;
			snakexlength[1] = 75;
			snakexlength[2] = 50;
			snakeylength[0] = 100;
			snakeylength[1] = 100;
			snakeylength[2] = 100;
			
		}
		if(left) {
			leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		}
		if(right) {
			rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		}
		if(up) {
			upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		}
		if(down) {
			downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		}
		
		for(int i =0;i<lengthofsnake;i++) {
			
			snakeimage.paintIcon(this, g, snakexlength[i], snakeylength[i]);
		}
		enemy.paintIcon(this, g, enemyX, enemyY);
		if(gameOver )
		{
			g.setColor(Color.white);
			g.setFont(new Font("Arial",Font.BOLD, 50));
			g.drawString("GameOver", 300, 300);
			g.setFont(new Font("Arial",Font.PLAIN, 20));
			g.drawString("Press SPACE to start", 320, 350);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.PLAIN, 15));
		g.drawString("Score: "+ score, 750, 30);
		g.setFont(new Font("Arial",Font.PLAIN, 15));
		g.drawString("Length: "+ lengthofsnake, 750, 50);
		
		
		g.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = lengthofsnake-1;i>0;i--) {
			snakexlength[i] =  snakexlength[i-1];
			snakeylength[i] =  snakeylength[i-1];
		}
		if(left) {
			snakexlength[0] =  snakexlength[0] - 25;
		}
		if(right) {
			snakexlength[0] =  snakexlength[0] + 25;
		}
		if(up) {
			snakeylength[0] =  snakeylength[0] - 25;
		}
		if(down) {
			snakeylength[0] =  snakeylength[0] + 25;
		}
		if(snakexlength[0]>850) snakexlength[0]=25;
		if(snakexlength[0]<25) snakexlength[0]=850;
		if(snakeylength[0]>625) snakeylength[0]=75;
		if(snakeylength[0]<75) snakeylength[0]=625;
		
		collidesWithEnemy();
		collidesWithBody();
		repaint();
	}

	

	@Override
	public void keyTyped(KeyEvent e) {
	
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			restart();
			
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT &&(!right)) {
			left = true;
			right = false;
			up = false;
			down = false;
			moves ++;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT &&(!left)) {
			left = false;
			right = true;
			up = false;
			down = false;
			moves ++;
					
		}
		if(e.getKeyCode() == KeyEvent.VK_UP &&(!down)) {
			left = false;
			right = false;
			up = true;
			down = false;
			moves ++;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN &&(!up)) {
			left = false;
			right = false;
			up = false;
			down = true;
			moves ++;
					
		}
	}
	private void restart() {
		// TODO Auto-generated method stub
		left = false;
		right = true;
		up = false;
		down = false;
		lengthofsnake  = 3;
		moves =0;
		score =0;
		gameOver = false;
		newEnemy();
		timer.start();
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}
	// creating new  enemy 
	private void newEnemy() {
		enemyX= Xpos[random.nextInt(34)];
		enemyY =Ypos[random.nextInt(23)];
		//checking if snake body collide with food , if it collide do not show food over snake body, hide snake body
		for(int i=lengthofsnake-1;i>=0;i--) {
			if(snakexlength[i] == enemyX && snakeylength[i]== enemyY) {
				newEnemy();
			}
		}
		}
	//check if snake collides with the enemy 
	private void collidesWithEnemy() {
		// TODO Auto-generated method stub
		if(snakexlength[0] == enemyX && snakeylength[0]== enemyY) {
			newEnemy();
			lengthofsnake++;
			score++;
		}
	}
	private void collidesWithBody() {
		// TODO Auto-generated method stub
		for(int i=lengthofsnake-1;i>0;i--) {
		 if(snakexlength[i] == snakexlength[0] && snakeylength[i] == snakeylength[0]) {
			 timer.stop();
			 gameOver = true;
		 }
		}
		
	}
	
}
