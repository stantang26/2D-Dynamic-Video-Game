/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 *
 * @author tangs6732
 */
public class Bullet {
    private int x; //position along x axis
    private int y; //position along y axis
    private int width = 7;
    private int length = 14;
    private int speed;
    private int damage;
    private boolean player;
    private double angle;//placeholder variables to keep track of what angle the bullet is being shot from
    private double angle2;
    private BufferedImage Bullet; 
    Game game;
    Player p;
    Enemy e;
   
    public Bullet(int x, int y, int speed,int tx, int ty,int damage,boolean player,Player p,Game game) throws IOException{
        //when Bullet object is created, sets x,y, and direction to the parameters given

        this.x = x;
        this.y = y;
        this.game = game;
        this.speed = speed;
        this.p = p;
        this.e = e;
        this.player = player;
        this.damage = damage;
        Bullet = ImageIO.read(new File ("bullet 1.png"));
        angle2 = p.getAngle();
        
        angle = Math.atan2(((double)y-(double)ty),((double)tx-(double)x)); //calculates angle based on cursor position and where the player is
        
    }
   
    public void shoot(){
        // moves the bullet based on the player movement
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

        
        x+=(double)speed*Math.cos(angle); //moves bullet position based on speed and angle of the bullet

                
        y-= (double)speed * Math.sin(angle);

        
        //essentially the tick method of the class, changes either the x or the y variable by 10 each tick, depending on the direction
        
       
    }
    public void render(Graphics g){
       //renders a small bullet at x and y coordinate and rotates based on angle
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform backup = g2d.getTransform();
        AffineTransform at = AffineTransform.getRotateInstance(angle2, x+width/2, y+length/2);
        g2d.setTransform(at);
        g.fillRect(x-1, y-1, width+2, length+2);
        g2d.drawImage(Bullet, x, y, width, length,game);
            
            
        g2d.setTransform(backup);
            
    }
    
    //getters and setters for ease of access by other classes
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
    public boolean getPlayer(){
        return player;
    }
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}

