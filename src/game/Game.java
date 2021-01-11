/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author stant
 */
public class Game extends Canvas implements Runnable, KeyListener{
    
    public int level = 1; //indicates what level the player on, which influences the difficulty
    state state;//declares a new state enum with the name state
    public static JFrame f = new JFrame("CPT"); //intializes a new JFrame which allows the program to run graphics and names it CPT
    private BufferedImage image; //declares a new BufferedImage object called image, used for graphics
    Graphics g;//declares a new graphics object used to render images and shapes
    public Player p = new Player(length/2,width/2,this);
    public Object background = new Object(-500, -2*width, length*2, width*3, this);
    public int tickspeed =10; //speed the game runs, the lower the tickspeed the faster the game runs
    public static int length = 1060; 
    public static int width = 760;
    private long tick1 = System.currentTimeMillis();//placeholder variable to keep track of system time
    private long tick2 = 0;
    
    Controller c = new Controller(p,this); //adds other objects to the main 
    OHandler h = new OHandler(this);
    PHandler ph = new PHandler(p,this);
    EHandler eh = new EHandler(h,ph,this);
    Cursor cursor; 
    LHandler l = new LHandler(this,h,eh,ph,p);
    Overlay ol = new Overlay();


    public enum state{ // enum tells the program what state the game is currently in, mostly for the render method to display the correct graphics
        GAME, MENU, INSTRUCTIONS, DEATH, PAUSE, STORE
    }
    
    public static void main(String[] args) throws InterruptedException, IOException {
        Game game = new Game(); //creates a new instance of the CPT class to add to the jframe and run non static methods
        //sets the preffered size of the canvas and class at 1000, and 1000, anything past that those dimensions will not run or render
        game.setPreferredSize(new Dimension(10000,10000 ));
        f.add(game); //add the instance to the jframe
        f.pack(); //sizes frame so the content equlas the preferred sizes
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //allows the user to close the game by exiting the jframe
        f.setVisible(true); //sets the jframe visible
        f.setResizable(false); //disallows the user to resize the jframe
        f.setSize(length, width); //sets the size of the frame to a width of 730 pixels and a height of 530
        game.setSize(length,width); //sets the actual size of the canvas (where the graphics and shown) to 730 by 530
        game.init();    // TODO code application logic here
    }
    Container contentPane = f.getContentPane();
    
    public void init() throws InterruptedException, IOException{ 
        cursor = new Cursor(p,this,c);
        addKeyListener(this); //adds the keylistener that the class implements to the game
        addMouseListener(cursor); //adds the mouselistener that the class implements to the game
        addMouseMotionListener(cursor);//adds mouselistener and mouseadaptor to 
        p.init(c); //initiates the player object using a controller class;
        ol.init(p, c, l,this);//initiates 
        l.init();
        background.init(ImageIO.read(new File("ground.png"))); //file reads the background image
        
        state = state.MENU; //changes the enum state to MENU, telling the game that the player is on the menu screen
        

        while(1 == 1){ //continuosly renders images and graphics onto the screen
            render(); //runs the render method
        }
    }
    
    public void run(){ 
        
        Thread a = new Thread(){ //creates a new thread that will run simultaneos to other threads, allows multiple classes and methods to be running at once
            public void run(){
                long tick1 = System.currentTimeMillis();//first tick variable(current time),along with tick2, allows the program to run a certain task at a pace
                long tick2= 0; //second tick variable (previous time)
                
                while (state == state.GAME || state == state.PAUSE){ //continuosly attempts to tick the game while it is in the state game
            
                    try {
                        tick1 = System.currentTimeMillis(); //continously sets the tick1 variable to the current time in milliseconds
                        
                        if(tick1 - tick2 > tickspeed){ //if the current time minus the previous time is greater than 10 milliseconds, the tick method will be called
                            tick();
                            tick2 = tick1; //sets previous time to currentime
                        }
                        
                    } catch (Exception g) {
                       //since tick() throws an interrupted exception, calling it requires a try-catch block.
                    }
                }
            }
           
        };
        a.start(); //starts each of the threads
 
       
    }

    
    private void tick() throws InterruptedException, IOException{
        //runs the tick methods of every class, performing the methods each class runs constantly
        l.tick();
        
        if(state == state.PAUSE){
            p.pause(); //if the game is not paused, ticks every object
        }else{
            eh.tick(p);
            ph.tick();
            p.tick();
        if(p.getHealth()<1){
            state = state.DEATH;
        }
        background.tick(p);
        h.tick(p);
        c.tick();
        
        }
        
    }
    
    private void render() throws InterruptedException, IOException{
        BufferStrategy bs = this.getBufferStrategy() ; //attempts to get a buffer strategy(how the program creates images) from the class
        if(bs==null){
            /*since no bufferstrategy exists at first, if the value gotten is null, it will create a new bufferstrategy that creates three images before loading them
            on each subsequent calling of the method, the program will use the created buffer strategy and load the images prior to running them*/
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics(); //assigns the graphics from the bufferstrategy to the graphics g object and uses it to draw images
        
        
        if(null != state)switch (state) { //switch statement using the state enum to tell the program which graphics to run
            case GAME:
                g.setColor(Color.black);
                g.fillRect(-1000,-1000,10000,10000);
                background.render(g);
                h.render(g);
                
                eh.render(g);
                ph.render(g);
                cursor.render(g);
                c.render(g);
                p.render(g);
                ol.render(g);
                g.setFont(new Font("",Font.PLAIN, 30));
               
                break;
            case MENU: //if the game is in the menu state
                image = ImageIO.read(new File ("Menu.png"));
                g.drawImage(image, 0,0, length,width,this);

                break;
            case DEATH: //if the game is in the DEATH state
                image = ImageIO.read(new File ("Death.png"));
                g.drawImage(image, 0,0, length,width,this);
                break;
            case INSTRUCTIONS: //if the game is in the instructions state, prints the controls
                image = ImageIO.read(new File ("Instructions.png"));
                g.drawImage(image, 0,0, length,width-30,this);
                break;
            case PAUSE: //if the game is in pause state, prints pause button
                image = ImageIO.read(new File ("pause.png"));
                g.drawImage(image, 0,0, length,width,this);
                break;
            case STORE: //if the game is store state, prints the picture of the store
                image = ImageIO.read(new File("store.png"));
                g.drawImage(image,0,0,length,width,this);
                g.setFont(new Font("Serif",Font.BOLD, 30));
                g.setColor(Color.LIGHT_GRAY);
                g.drawString("MONEY: $" + p.getMoney(), length-300, width-110);
            default :
                break;
        }

        g.dispose(); //gets rid of the graphics so that it can be reassigned to the bufferstrategy again when the method is recalled
        bs.show(); //shows the bufferstrategy, running the graphics
  
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (e.getKeyCode() == KeyEvent.VK_W){ //if W is pressed
          
            if(p.up != p.up.STUCK){
                p.up = p.up.MOVING;
            }

        } else if (e.getKeyCode() == KeyEvent.VK_S){
          
            if(p.down != p.down.STUCK){
                p.down = p.down.MOVING;
            }
         
        } else if (e.getKeyCode() == KeyEvent.VK_A){
         
            if(p.left != p.left.STUCK){
                p.left = p.left.MOVING;
            }

           

        } else if (e.getKeyCode() == KeyEvent.VK_D){
   
            if(p.right != p.right.STUCK){
                p.right = p.right.MOVING;
            }

         
        } if (e.getKeyCode() == KeyEvent.VK_SPACE){ //Dashing button
            tick1 = System.currentTimeMillis();
            if(tick1-tick2>1200){
                p.act = p.act.DODGE;
                tick2 = tick1;
                p.setDtick1(System.currentTimeMillis());
                p.setDtick2(p.getDtick1());
            }
           
            
        } if (e.getKeyCode() == KeyEvent.VK_R){ //Special attack button(airstrike)   
            if(c.gun == c.gun.PRIMARY){
                if(c.getPmag()<c.getPmaxmag()){
                    if(c.getPammo()<=c.getPmaxmag()&&c.getPammo()+c.getPmag()<=c.getPmaxmag()){
                        c.setPmag(c.getPmag()+c.getPammo());
                        c.setPammo(0);
                    }else {
                        c.setPammo(c.getPammo()-(c.getPmaxmag()-c.getPmag()));
                        c.setPmag(c.getPmaxmag());
                    }
                    if(c.getPammo()>0){
                        p.act = p.act.RELOAD;
                        p.setFtick1(System.currentTimeMillis());
                        p.setFtick2(p.getFtick1());
                    }
                }
            }else{
                if(c.getSmag()<c.getSmaxmag()){
                    if(c.getSammo()<=c.getSmaxmag()&&c.getSammo()+c.getSmag()<=c.getSmaxmag()){
                        c.setSmag(c.getSmag()+c.getSammo());
                        c.setSammo(0);
                    }else {
                        c.setSammo(c.getSammo()-(c.getSmaxmag()-c.getSmag()));
                        c.setSmag(c.getSmaxmag());
                    }
                    if(c.getSammo()>0){
                        p.act = p.act.RELOAD;
                        p.setFtick1(System.currentTimeMillis());
                        p.setFtick2(p.getFtick1());
                    }
                }
            }
                
            
        } if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            if(state == state.GAME){
            state = state.PAUSE;
            }else if (state == state.PAUSE){
                state = state.GAME;
            }else if (state == state.INSTRUCTIONS){
                state = state.MENU;
            }
        } if (e.getKeyCode() == KeyEvent.VK_Q){
            if(c.gun == c.gun.PRIMARY){
                c.gun = c.gun.SECONDARY;
            } else if (c.gun == c.gun.SECONDARY){
                c.gun = c.gun.PRIMARY;
            }
        }
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {

       if (e.getKeyCode() == KeyEvent.VK_W){

            p.up = p.up.NOTMOVING;
            
        } else if (e.getKeyCode() == KeyEvent.VK_S){

            p.down = p.down.NOTMOVING;
            
        } else if (e.getKeyCode() == KeyEvent.VK_A){

            p.left = p.left.NOTMOVING;
            
        } else if (e.getKeyCode() == KeyEvent.VK_D){

            p.right = p.right.NOTMOVING;
        }  else if (e.getKeyCode() == KeyEvent.VK_Q){
            
        }  
    }


    
    public void reset() { //called when the player dies and chooses to play again, resets all the values
        state = state.MENU;
        level = 1;
        p.setMoney(0);
        p.setHealth(100);
        c.setPammo(c.getPmaxammo());
        c.setPmag(c.getPmaxmag());
        c.setSammo(c.getSmaxammo());
        c.setSmag(c.getSmaxmag());
        l.clear();
        
    }
    
   
    
    
}
