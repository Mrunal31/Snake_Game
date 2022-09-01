import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Board extends JPanel implements ActionListener{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image apple;
	private Image dot;
	private Image head;
	
	private final int DOT_SIZE= 10;  //300*300= 90000 /100 = 900
	private final int ALL_DOTS=900;
	private final int RANDOM_POSITION = 29;
	
	private int apple_x;
	private int apple_y;
	
	private final int x[]=new int[ALL_DOTS];
	private final int y[]=new int[ALL_DOTS];
	
	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;
	
	private int dots;
	private int Count=0;
	private Timer timer;
	Board()
	{
		addKeyListener(new TAdapter());
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(300,300));
		
		setFocusable(true);
		loadImages();
		initGame();
	}
	
  public void loadImages() {
	  ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
      apple=i1.getImage();
	  
	  ImageIcon i2= new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
	  dot=i2.getImage();
	  
	  ImageIcon i3= new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
	  head=i3.getImage();
  }
  
  public void initGame() {
	  dots=3;
	  for(int z=0;z<dots;z++) {
		  x[z]=50 - z * DOT_SIZE;  // 1: 50; 2: 50-dot_size=50-10= 40,3: 50-2*10= 30
		  y[z]=50;
		  
	  }
	  locateApple();
	  
	  timer = new Timer(140, this);
	  timer.start();
	  
  }
  
  public void locateApple() {
	  int r;
	  apple_x=(r =(int)(Math.random()*RANDOM_POSITION))*DOT_SIZE;
	 // apple_x = {r * DOT_SIZE};1*29=29*10=290 0*20=0*10=0
	  //1*30=30*10=300   1*29=29*10=290
	  apple_y=(r =(int)(Math.random()*RANDOM_POSITION))*DOT_SIZE;
	  //apple_y = {r * DOT_SIZE};
	  }
  
  public void checkApple() {
	  if((x[0] == apple_x) && (y[0] == apple_y)) {
		  dots++;
		  locateApple();
		  Count++;
	  }
  }
  public void actionPerformed(ActionEvent ae) {
      if(inGame) {
    	  checkApple();
    	  checkCollision();
    	  move();
      }
      
      repaint();
	  
  }
 public void move() {
	  
	  for(int z = dots;z>0;z--) {
		  x[z]=x[z-1];  
		  y[z]=y[z-1];
	  }
	  if(leftDirection) {
		  x[0] -= DOT_SIZE;
	  }
	  if(rightDirection) {
		  x[0] += DOT_SIZE;
	  }
	  if(upDirection) {
		  y[0] -= DOT_SIZE;
	  }
	  if(downDirection) {
		  y[0] += DOT_SIZE;
	  }
  }
  
  public void paintComponent(Graphics g) {
	  super.paintComponent(g);
	  
	  draw(g);
  }
  
  public void draw(Graphics g) {
	  if(inGame) {
		  g.drawImage(apple, apple_x, apple_y,this);
		  
		  for(int z=0;z<dots;z++) {
			  if(z == 0) {
				  g.drawImage(head,x[z],y[z],this);
			  }else {
				  g.drawImage(dot,x[z],y[z],this);
			  }
		  }
		  Toolkit.getDefaultToolkit().sync();
		  
		 
		  Font font = new Font("Ink Free",Font.BOLD,20);//SAN_SERIF
		  FontMetrics matrices = getFontMetrics(g.getFont()); //To align at the center of the screen
		  
		  g.setColor(Color.WHITE);
		  g.setFont(font);
		  g.drawString("Score: "+Count,(300 - matrices.stringWidth("Score: "+Count))/2,g.getFont().getSize());
	  }else {
		  gameOver(g);
	  }
  }
  
  public void gameOver(Graphics g) {
	  
	  //Score
	  FontMetrics matrices1 = getFontMetrics(g.getFont());
	  g.setColor(Color.WHITE);
	  g.setFont(new Font("Ink Free",Font.BOLD,20));
	  g.drawString("Score: "+Count,(300 - matrices1.stringWidth("Score: "+Count))/2,g.getFont().getSize());
	  
	  //Game Over
	  FontMetrics matrices2 = getFontMetrics(g.getFont()); //To align at the center of the screen
	  g.setColor(Color.WHITE);
	  g.setFont(new Font("Ink Free",Font.BOLD,24));
	  g.drawString("Game Over",(300 - matrices2.stringWidth("Game Over"))/2,150);
	  
	  //Press Spacebar to resume
	  FontMetrics matrices3 = getFontMetrics(g.getFont()); //To align at the center of the screen
	  g.setColor(Color.WHITE);
	  g.setFont(new Font("Ink Free",Font.BOLD,14));
	  g.drawString("Press spacebar to resume",(300 - matrices3.stringWidth("Press spacebar to resume")),170);
	  
  }
  
  public void checkCollision(){
	  
	  for(int z=dots ; z>0 ; z--) {
		  if((z > 4) && (x[0] == x[z]) && (y[0]==y[z])) {
			  inGame= false;
		  }
	  }
	  if(y[0] >= 300) {
		  inGame = false;
	  }
	  
	  if(x[0]>=300) {
		  inGame = false;
	  }
	  
	  if(x[0] <0) {
		  inGame = false;
	  }
	  
	  if(y[0] <0) {
		  inGame = false;
	  }
	  
	  if(!(inGame)) {
		  timer.stop();
	  }
  }
  
 
	
  
  
  private class TAdapter extends KeyAdapter {
	 public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT && (!rightDirection)) {
			leftDirection = true;
			upDirection = false;
			downDirection = false;
	 }
		if(key == KeyEvent.VK_RIGHT && (!leftDirection)) {
			rightDirection = true;
			upDirection = false;
			downDirection = false;		
      }
		if(key == KeyEvent.VK_UP && (!downDirection)) {
			leftDirection = false;
			upDirection = true;
			rightDirection = false;
					
      }
		if(key == KeyEvent.VK_DOWN && (!upDirection)) {
			downDirection = true;
			rightDirection = false;
			leftDirection = false;
			
		}
		if(key == KeyEvent.VK_SPACE) {
			if(!inGame) {
				inGame=true;
			addKeyListener(new TAdapter());
			setBackground(Color.BLACK);
			setPreferredSize(new Dimension(300,300));
			
			setFocusable(true);
			loadImages();
			initGame();
			}
		}
	 }
  }
}


