    
package game;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Player{
    private int x; //indicates position of player along x axis
    private int y; //indicates position of player along y axis
    private int velx; //determines velocity of the player along x axis
    private int vely; //determines velocity of the player along y axis
    private int health = 100; //the health of the player, determines how many hits they can take before dying
    
     //used with slowtick2 to track time/cooldown, only used for the SLOWED state of the movement enum
    private int width = 75;
    private int height = 75;
    private long ftick1 = System.currentTimeMillis(); //tracks time/cooldown for shooting
    private long ftick2 = 0;
    private long dtick1 = System.currentTimeMillis();//tracks time/cooldown dodge
    private long dtick2 = 0;
    private long htick1 = System.currentTimeMillis();//tracks if the player is hit
    private long htick2 = 0;
    private int money = 0;
    private int speed = 5;
    private boolean hit;

    private BufferedImage[] PlayerA; //declares a new array of BufferedImages named PlayerA, stores the different images of the player used 
    private BufferedImage Player; //image that is drawn by the program of the player, 
    private BufferedImage Fire;
    private BufferedImage Gun;
    private BufferedImage[] GunA;
    private double angle;

    
    AffineTransform at;
    Game game; //new instance of the CPT class
    Controller c;
    act act;
    movement up;
    movement down;
    movement left;
    movement right;//new movement enum called move

    
    public Player(int x, int y, Game game){ //when the player is created, it sets x, y , and game to the same variables/objects used as arguments
        this.x = x;
        this.y = y;
        this.game = game;
    }
    public enum movement{
        //enum that tracks what state the player's movement is. Moving is normal movement, Notmoving is used for dashes, Slowed means the player moves slower
        MOVING, NOTMOVING,STUCK
    }
    
    public enum act{//enum tracks the action a player is in the process of doin
        NONE,FIRE,BLOCK,DODGE,RELOAD
    }
    
    public void init(Controller c ) throws IOException{
        //when called, initialized the PlayerA array with files in the CPT folder, for ease of access when drawing the images
        PlayerA = new BufferedImage[]{ImageIO.read(new File ("Jhongman1.gif")),ImageIO.read(new File ("Jhongman2.gif")),ImageIO.read(new File ("Jhongman3.gif"))};
        GunA = new BufferedImage[] {ImageIO.read(new File("ShotgunB.gif")),ImageIO.read(new File("PistolB.gif")),ImageIO.read(new File ("RifleB.png"))};
        up = up.NOTMOVING; //sets a movement enum for each of the directions
        down = down.NOTMOVING;
        left = left.NOTMOVING;
        right = right.NOTMOVING;
        act = act.NONE;
        this.c = c;
    }
    public void tick(){
        //if the player is hit, the player becomes invincible for a couple seconds
        if(hit == true){
            htick1 = System.currentTimeMillis();
            if(htick1-htick2 >100*game.tickspeed){
                hit = false;
                htick2 = htick1;
            }
        }
        //if the player is dodging change player speed but keep orientation for a couple seconds
            if(act == act.DODGE){
                
                dtick1 = System.currentTimeMillis();
                setVelY(-(int)(12*Math.cos(angle)));
                setVelX((int) (12*Math.sin(angle)));
                
                if(dtick1-dtick2 >30*game.tickspeed){
                    act = act.NONE;
                    dtick2 = dtick1;
                }
            }else{
                //if the player is moving in a direction, change velocity in that direction
                    if(up ==up.MOVING){
                        setVelY(-speed);
                    }else if (down == down.MOVING){
                        setVelY(speed);
                    }else {
                        setVelY(0);
                     }
                      
                    if(left == left.MOVING){
                        setVelX(-speed);
                    }else if (right == right.MOVING){
                        setVelX(speed);
                    }else {
                        setVelX(0);
                     }  
                //play fire animation for a couple seconds and change back  
                if(act == act.FIRE){
                   ftick1 = System.currentTimeMillis();
                   if(ftick1 - ftick2 > 100){
                        act = act.NONE;
                        ftick2 = ftick1;
                   }
                //if the player is reloading change back after a couple seconds 
                }else if (act == act.RELOAD){
                   ftick1 = System.currentTimeMillis();
                   if(ftick1 - ftick2 > 1000){
                        act = act.NONE;
                        ftick2 = ftick1;
                   }
                }
            
       }
    }
    public void render(Graphics g) throws IOException{
        //uses the PlayerA array and the dir variable to determine what image to load as the BufferedImage Player, then draws Player
        if(up == up.NOTMOVING&&down == down.NOTMOVING&&left == left.NOTMOVING&&right == right.NOTMOVING){
            Player = PlayerA[0];
            //switches between player sprites to animate runnng
        }else{
            if(System.currentTimeMillis()/200%2==0){
                Player = PlayerA[1];
            }else{
                Player = PlayerA[2];
            }
        }
        if(act != act.DODGE){
            angle = Math.atan2(((double)game.cursor.getX()-(double)x-width/2),((double)y+height/2-(double)game.cursor.getY())); //calculate angle based on cursor position
        }
        //renders the gun in the players hand
        if(c.gun == c.gun.PRIMARY){
            if(c.primary == c.primary.SHOTGUN){
                Gun = GunA[0];
            }else if(c.primary == c.primary.RIFLE){
                Gun = GunA[2];
            }
        }else{
            Gun = GunA[1];
        }
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform backup = g2d.getTransform();
        at = AffineTransform.getRotateInstance(angle, x+width/2, y+height/2);
        g2d.setTransform(at);
        if(hit == true){
            if(System.currentTimeMillis()%2 == 0){
                
            }else{ 
                g2d.drawImage(Player, this.x, this.y, 75,75,game);
                g2d.drawImage(Gun, this.x+30,this.y-35,25,55, game);
            }
        }else{
            g2d.drawImage(Player, this.x, this.y, 75,75,game);
            g2d.drawImage(Gun, this.x+30,this.y-35,25,55, game);
        }
        //rotates the image and gun and draws the image

        if(act == act.FIRE){
            Fire = ImageIO.read(new File ("muzzle flash 1.png"));
            if(c.gun == c.gun.PRIMARY ){
                if(c.primary == c.primary.SHOTGUN){
                    g.drawImage(Fire, x+15, y-90, 60,60, game);g.drawImage(Fire, x+5, y-90, 60,60, game);
                }
            }
            
        }
     
        g2d.setTransform(backup);
    }
    public void pause(){
        //pause method freezes the player 
        up = up.NOTMOVING;
        down = down.NOTMOVING;
        left = left.NOTMOVING;
        right = right.NOTMOVING;
            
    }
    
    //These get and set methods allow other classes to access the private variables in this method. Done to avoid confusion between variables
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getVelX(){
        return velx;
    }
    public int getVelY(){
        return vely;
    }
    public void setVelX(int velx){
        this.velx = velx;
    }
    public void setVelY(int vely){
        this.vely = vely;
    }  
   
    public void setHealth(int health){
        this.health = health;
    }
    public int getHealth(){
        return health;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    public long getFtick1() {
        return ftick1;
    }

    public void setFtick1(long ftick1) {
        this.ftick1 = ftick1;
    }

    public long getFtick2() {
        return ftick2;
    }

    public void setFtick2(long ftick2) {
        this.ftick2 = ftick2;
    }
    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
     public long getDtick1() {
        return dtick1;
    }

    public void setDtick1(long dtick1) {
        this.dtick1 = dtick1;
    }

    public long getDtick2() {
        return dtick2;
    }

    public void setDtick2(long dtick2) {
        this.dtick2 = dtick2;
    }
    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }
    
    
    public long getHtick1() {
        return htick1;
    }

    public void setHtick1(long htick1) {
        this.htick1 = htick1;
    }

    public long getHtick2() {
        return htick2;
    }

    public void setHtick2(long htick2) {
        this.htick2 = htick2;
    }
    
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
        
    



