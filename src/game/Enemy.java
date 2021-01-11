/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author stant
 */
public class Enemy{
    private int x ;
    private int y ;
    private int velx = 0;
    private int vely = 0;
    private int width;
    private int height;
    private int health = 100;
    private double angle;
    private Random gen = new Random();
    private int range;
    
    BufferedImage image;
    AffineTransform at;
    Game game;
    Player p;
    Controller c;
    OHandler oh;
    Bullet b;
    
    
    public Enemy(int x, int y,int width, int height, Game game, OHandler oh, Player p) throws IOException{
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.game = game;
        this.p = p;
        this.oh = oh;
        c = new Controller(p,game);
        image = ImageIO.read(new File("EnemyBolt_Rifle.gif"));
        range = gen.nextInt(600)-300;
    }

    public void init(BufferedImage image){
        this.image = image;
    }
    
    public void tick (){
        //adds enemies velocity to its x and y
        x+=velx;
        y+=vely;
        
        //calculates angle based on the player position and enemy position
        angle = Math.atan2(((double)p.getX()-(double)x-width/2),((double)y+height/2-(double)p.getY()));
        
        //moves the enemy based on the player movement
        if(p.left == p.left.MOVING || p.right == p.right.MOVING){
            x-=p.getVelX();
        }else if (p.act == p.act.DODGE){
        
            if(p.left == p.left.STUCK || p.right == p.right.STUCK){
               
            }else {
                x-=p.getVelX();
            }

        }
        
        if(p.up == p.up.MOVING || p.down == p.down.MOVING){
             y-=p.getVelY();
        }else if (p.act == p.act.DODGE){
            if(p.up == p.up.STUCK ||p.down == p.down.STUCK){
               
            }else {
                 y-=p.getVelY();
            }
        }
        //ticks controller of the enemy shooting the bullets from the enemy
        c.tick();
        
        if(x>100 && x<900 && y>0 && y<700){
            if(gen.nextInt(100) == 1){
                try {
                    //spawns bullets periodically
                    b = new Bullet(x+width/2,y+height/2,12, p.getX()+p.getWidth()/2, p.getY()+p.getHeight()/2,5,false,p,game );
                } catch (IOException ex) {
                    Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
                }
                c.addBullet(b);
            }
            //calculates velocity based on the angle of the enemy
            if(angle<=Math.PI/4 && angle>=-Math.PI/4){
                if(y>p.getY()+range){
                    setVelY(-1);
                }else{
                    setVelY(0);
                }
            }else if (angle<=3*Math.PI/4 && angle>=Math.PI/4){

                if(x+range<p.getX()){
                    setVelX(1);
                }else{
                    setVelX(0);
                }
            }else if (angle<=-3*Math.PI/4 || angle>=3*Math.PI/4){
                if(y+range<p.getY()){
                    setVelY(1);
                }else{
                    setVelY(0);
                }
            }else if (angle<=-Math.PI/4 && angle>=-3*Math.PI/4){
                if(x>p.getX()+range){
                    setVelX(-1);
                }else{
                    setVelX(0);
                }
            }
        }
    }
    
    public void render(Graphics g){
        //draws enemy and rotates it based on angle
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform backup = g2d.getTransform();
        at = AffineTransform.getRotateInstance(angle, x+width/2, y+height/2);
        g2d.setTransform(at);
        g.drawImage(image, x, y,width,height, game);

        c.render(g);
        g2d.setTransform(backup);
    }
    //checks if another enemy is touching it 
    public boolean isStuckUp(Enemy p){
        if(p.getX()>=x-p.getWidth() && p.getX()<x+width&&p.getY()<=y+height+2 && p.getY()+p.getHeight()>= y +height){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isStuckDown(Enemy p){
        if(p.getX()>=x-p.getWidth() && p.getX()<x+width&&p.getY()+p.getHeight()+2>=y && p.getY()<=y){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isStuckLeft(Enemy p){
        if(p.getY()>=y-p.getHeight() && y+height>=p.getY()&&p.getX()<x+width+2 && p.getX()+p.getWidth() >= x+width){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isStuckRight(Enemy p){
        if(p.getY()<=y+height && p.getY()+p.getHeight()>= y&&p.getX()>=x-p.getWidth()-2 && p.getX()<x){
            return true;
        }else{
            return false;
        }
    }
    

    public double getAngle() {
        return angle;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
       public int getVelX() {
        return velx;
    }

    public void setVelX(int velx) {
        this.velx = velx;
    }

    public int getVelY() {
        return vely;
    }

    public void setVelY(int vely) {
        this.vely = vely;
    }
    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
}
